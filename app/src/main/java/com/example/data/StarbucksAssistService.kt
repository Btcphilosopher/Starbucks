package com.example.data

import com.example.BuildConfig
import com.google.firebase.ai.FirebaseAI
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class StarbucksAssistService {

    private val apiKey: String = try {
        BuildConfig.GEMINI_API_KEY
    } catch (e: Exception) {
        ""
    }

    suspend fun getCoffeeRecommendation(userPrompt: String): String = withContext(Dispatchers.IO) {
        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                return@withContext callGeminiApiRest(userPrompt, apiKey)
            } catch (e: Exception) {
                // Fallback to local rule engine if network or key issue
            }
        }
        return@withContext generateLocalAssistantResponse(userPrompt)
    }

    private fun callGeminiApiRest(userPrompt: String, apiKey: String): String {
        val url = URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey")
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "application/json")
        conn.doOutput = true

        val systemInstruction = "You are Starbucks Assist, a warm, polite coffee assistant for Starbucks. Provide helpful, custom coffee recommendations, nutritional guidance, and order tips based on Starbucks menu options. Keep answers concise, clear, and friendly (2-3 sentences max). Disclaimer: Nutritional info provided is data-driven and not medical advice."

        val jsonBody = JSONObject().apply {
            put("contents", org.json.JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("parts", org.json.JSONArray().apply {
                        put(JSONObject().put("text", "$systemInstruction\nUser asked: $userPrompt"))
                    })
                })
            })
        }

        conn.outputStream.use { os ->
            os.write(jsonBody.toString().toByteArray())
        }

        if (conn.responseCode == 200) {
            val responseStr = conn.inputStream.bufferedReader().use { it.readText() }
            val jsonResp = JSONObject(responseStr)
            val candidates = jsonResp.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCand = candidates.getJSONObject(0)
                val content = firstCand.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val text = parts.getJSONObject(0).optString("text", "")
                    if (text.isNotBlank()) return text
                }
            }
        }

        return generateLocalAssistantResponse(userPrompt)
    }

    private fun generateLocalAssistantResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("usual") || lower.contains("reorder") -> {
                "☕ Your usual is an Iced Caramel Macchiato (Grande with Oat Milk and Light Ice, $6.25). Tap 'Order Now' on your home screen or say 'Add usual to cart' to reorder instantly!"
            }
            lower.contains("low calorie") || lower.contains("healthy") || lower.contains("diet") -> {
                "🌿 I recommend an Iced Brown Sugar Oatmilk Shaken Espresso (only 120 calories) or a Cold Brew with a splash of oat milk (under 50 calories). Note: Nutritional figures are estimated data-driven values."
            }
            lower.contains("stars") || lower.contains("reward") || lower.contains("points") -> {
                "⭐ You currently have 8,420 Stars in your Starbucks Rewards wallet! That's enough for over 50 free handcrafted drinks (150 Stars each)."
            }
            lower.contains("nearest") || lower.contains("store") || lower.contains("location") -> {
                "📍 Your nearest store is Starbucks — Main Street (0.4 miles away, open until 9:00 PM). It features Mobile Pickup, Drive-Thru, and Nitro Cold Brew."
            }
            lower.contains("similar") || lower.contains("recommend") || lower.contains("what should i order") -> {
                "✨ If you love sweet espresso drinks with oat milk, try the Iced Brown Sugar Oatmilk Shaken Espresso or an Iced Vanilla Oatmilk Latte with Caramel Drizzle!"
            }
            else -> {
                "☕ I'm your Starbucks Assist coffee concierge! I can help you find low-calorie drinks, customize your usual, check your 8,420 Stars balance, or recommend seasonal drinks."
            }
        }
    }
}
