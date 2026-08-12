package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.data.models.RewardOffer
import com.example.ui.theme.*

@Composable
fun RewardsScreen(
    starsCount: Int,
    offers: List<RewardOffer>,
    onToggleSaveOffer: (String) -> Unit
) {
    var selectedTier by remember { mutableStateOf(150) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(StarbucksWarmCream)
            .padding(bottom = 80.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Starbucks Rewards Banner
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = StarbucksDarkGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("rewards_banner_card")
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
                        Text(
                            text = "STARBUCKS REWARDS",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = StarbucksGold,
                            letterSpacing = 1.sp
                        )

                        Surface(
                            shape = CircleShape,
                            color = StarbucksHouseGreen
                        ) {
                            Text(
                                text = "GOLD MEMBER",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = String.format("%,d", starsCount),
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Stars",
                            color = StarbucksGold,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Progress bar
                    LinearProgressIndicator(
                        progress = { 0.7f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = StarbucksGold,
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "340 Stars until next bonus reward • Recent earning: +120 Stars",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Reward Tiers Selector
        item {
            Text(
                text = "REDEEM STARS",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = StarbucksDarkGreen,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            val tiers = listOf(25, 100, 150, 200, 400)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tiers.forEach { tier ->
                    val isSel = selectedTier == tier
                    FilterChip(
                        selected = isSel,
                        onClick = { selectedTier = tier },
                        label = { Text("$tier ★") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StarbucksHouseGreen,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Active Selected Reward Tier Details Card
        item {
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
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(StarbucksLightGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (selectedTier) {
                                25 -> Icons.Default.Add
                                100 -> Icons.Default.LocalCafe
                                150 -> Icons.Default.Coffee
                                200 -> Icons.Default.BakeryDining
                                else -> Icons.Default.CardGiftcard
                            },
                            contentDescription = null,
                            tint = StarbucksHouseGreen
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = when (selectedTier) {
                                25 -> "Customize your drink"
                                100 -> "Brewed hot coffee or bakery item"
                                150 -> "Handcrafted drink or hot breakfast"
                                200 -> "Lunch sandwich or protein box"
                                else -> "Select merchandise or coffee bag"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Redeem $selectedTier Stars at checkout",
                            fontSize = 11.sp,
                            color = StarbucksSubtext
                        )
                    }

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("REDEEM", fontSize = 11.sp)
                    }
                }
            }
        }

        // Bonus-Star Challenges
        item {
            Text(
                text = "BONUS-STAR CHALLENGES",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = StarbucksDarkGreen,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                StarbucksData.sampleChallenges.forEach { challenge ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(challenge.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(challenge.progress, fontSize = 11.sp, color = StarbucksSubtext)
                            }
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = StarbucksGold
                            ) {
                                Text(
                                    text = "+${challenge.starsReward} STARS",
                                    color = StarbucksDarkGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Personalised Offers Marketplace
        item {
            Text(
                text = "PERSONALISED OFFERS",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = StarbucksDarkGreen,
                letterSpacing = 1.sp
            )
        }

        items(offers) { offer ->
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
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(StarbucksLightGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.LocalOffer, contentDescription = null, tint = StarbucksHouseGreen)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(offer.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(offer.description, fontSize = 11.sp, color = StarbucksSubtext)
                    }

                    IconButton(onClick = { onToggleSaveOffer(offer.id) }) {
                        Icon(
                            imageVector = if (offer.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save Offer",
                            tint = StarbucksHouseGreen
                        )
                    }
                }
            }
        }
    }
}
