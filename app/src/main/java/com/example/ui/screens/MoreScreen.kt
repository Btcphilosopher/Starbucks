package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StarbucksData
import com.example.data.db.OrderEntity
import com.example.data.models.CoffeeProfile
import com.example.data.models.StarbucksCardDetails
import com.example.ui.theme.*

@Composable
fun MoreScreen(
    starbucksCard: StarbucksCardDetails,
    orderHistory: List<OrderEntity>,
    onReloadCardClick: () -> Unit,
    onReorderUsual: () -> Unit
) {
    var selectedMoreSection by remember { mutableStateOf("CARD_WALLET") }
    val coffeeProfile = remember { CoffeeProfile() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(StarbucksWarmCream)
            .padding(bottom = 80.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Selector Tabs
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val tabs = listOf(
                    "CARD_WALLET" to "Card Wallet",
                    "GIFT_CARDS" to "Gift Cards",
                    "MY_PROFILE" to "Coffee Profile",
                    "MERCHANDISE" to "Coffee & Merch",
                    "ORDER_HISTORY" to "Order History"
                )
                items(tabs) { (key, label) ->
                    val isSelected = selectedMoreSection == key
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedMoreSection = key },
                        label = { Text(label, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StarbucksHouseGreen,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White
                        ),
                        modifier = Modifier.testTag("tab_$key")
                    )
                }
            }
        }

        // Section 1: Digital Starbucks Card Wallet
        if (selectedMoreSection == "CARD_WALLET") {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = StarbucksDarkGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("starbucks_card_wallet")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("DIGITAL STARBUCKS CARD", color = StarbucksGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            Text(starbucksCard.cardNumber, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "$${String.format("%.2f", starbucksCard.balance)}",
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
                            color = Color.White
                        )
                        Text("Available Balance", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(
                                onClick = onReloadCardClick,
                                colors = ButtonDefaults.buttonColors(containerColor = StarbucksGold),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("ADD MONEY", color = StarbucksDarkGreen, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("PAY IN STORE", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Gift Cards Marketplace
        if (selectedMoreSection == "GIFT_CARDS") {
            item {
                Text("GIFT CARDS MARKETPLACE", fontWeight = FontWeight.Bold, color = StarbucksDarkGreen, letterSpacing = 1.sp)
            }
            items(StarbucksData.sampleGiftCards) { gc ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(20.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(StarbucksLightGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CardGiftcard, contentDescription = null, tint = StarbucksHouseGreen)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(gc.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Balance: $${String.format("%.2f", gc.balance)} • Code: ${gc.code}", fontSize = 11.sp, color = StarbucksSubtext)
                        }
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen)
                        ) {
                            Text("SEND", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Section 3: Personal Coffee Profile ("MY COFFEE")
        if (selectedMoreSection == "MY_PROFILE") {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(24.dp))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Psychology, contentDescription = null, tint = StarbucksHouseGreen)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("YOUR COFFEE PROFILE", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = StarbucksDarkGreen)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("You usually order:", fontSize = 12.sp, color = StarbucksSubtext)
                        Text("${coffeeProfile.usualDrinkName} (${coffeeProfile.preferredSize}, ${coffeeProfile.preferredMilk}, ${coffeeProfile.preferredFlavour})", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(14.dp))

                        Card(
                            colors = CardDefaults.cardColors(containerColor = StarbucksLightGreen),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("TRY SOMETHING NEW", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = StarbucksHouseGreen)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(coffeeProfile.recommendation, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = StarbucksDarkGreen)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = onReorderUsual,
                            colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text("REORDER YOUR USUAL NOW")
                        }
                    }
                }
            }
        }

        // Section 4: Coffee & Merchandise Shop
        if (selectedMoreSection == "MERCHANDISE") {
            items(StarbucksData.sampleMerchandise) { merch ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(20.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(StarbucksLightGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = StarbucksHouseGreen)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(merch.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(merch.description, fontSize = 11.sp, color = StarbucksSubtext, maxLines = 2)
                            Text("$${String.format("%.2f", merch.price)}", fontWeight = FontWeight.Black, color = StarbucksHouseGreen, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        // Section 5: Order History with 1-tap Reorder
        if (selectedMoreSection == "ORDER_HISTORY") {
            item {
                Text("RECENT ORDERS", fontWeight = FontWeight.Bold, color = StarbucksDarkGreen, letterSpacing = 1.sp)
            }

            if (orderHistory.isEmpty()) {
                item {
                    Text("No recent orders recorded yet. Place an order from the Order tab!", fontSize = 12.sp, color = StarbucksSubtext)
                }
            } else {
                items(orderHistory) { ord ->
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(18.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(ord.itemsSummary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("${ord.storeName} • $${String.format("%.2f", ord.totalAmount)}", fontSize = 11.sp, color = StarbucksSubtext)
                            }
                            Button(
                                onClick = onReorderUsual,
                                colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen)
                            ) {
                                Text("REORDER", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
