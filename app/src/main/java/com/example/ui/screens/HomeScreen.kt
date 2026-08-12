package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StarbucksData
import com.example.data.models.*
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    starsCount: Int,
    cardBalance: Double,
    selectedStore: StarbucksStore,
    activeOrder: OrderRecord?,
    favoriteDrinks: List<com.example.data.db.FavoriteDrinkEntity>,
    offers: List<RewardOffer>,
    onOrderUsualClick: () -> Unit,
    onPayClick: () -> Unit,
    onNavigateTab: (com.example.viewmodel.MainTab) -> Unit,
    onSelectDrink: (DrinkItem) -> Unit,
    onAdvanceOrderStep: () -> Unit,
    onNotificationClick: () -> Unit,
    onAiAssistantClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(StarbucksWarmCream)
            .padding(bottom = 80.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Top Bar Header
        item {
            TopBarHeader(
                userName = "Tom",
                onNotificationClick = onNotificationClick,
                onAiClick = onAiAssistantClick
            )
        }

        // Active Order Delivery status banner if available
        if (activeOrder != null) {
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
                    ActiveOrderDeliveryBanner(
                        orderRecord = activeOrder,
                        onAdvanceClick = onAdvanceOrderStep
                    )
                }
            }
        }

        // Bento Grid Row 1: "Your Usual?" Card
        item {
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                UsualOrderBentoCard(
                    usualDrinkName = "Iced Caramel Macchiato",
                    size = "Grande",
                    milk = "Oat Milk",
                    onOrderNowClick = onOrderUsualClick
                )
            }
        }

        // Bento Grid Row 2: Two Columns (Stars Available & Card Balance)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    StarsBentoCard(
                        starsCount = starsCount,
                        onRewardsClick = { onNavigateTab(com.example.viewmodel.MainTab.REWARDS) }
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    CardBalanceBentoCard(
                        balance = cardBalance,
                        onPayClick = onPayClick
                    )
                }
            }
        }

        // Bento Grid Row 3: Nearby Store Pill Card
        item {
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(20.dp))
                        .clickable { onNavigateTab(com.example.viewmodel.MainTab.STORES) }
                        .testTag("bento_store_card")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(StarbucksLightGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = StarbucksHouseGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = selectedStore.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = StarbucksDarkText
                                )
                                Text(
                                    text = "${selectedStore.distanceMiles} miles • ${selectedStore.openStatus}",
                                    fontSize = 11.sp,
                                    color = StarbucksSubtext
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }
        }

        // Featured Seasonal Products Carousel
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SEASONAL FAVORITES",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = StarbucksDarkGreen,
                        letterSpacing = 1.sp
                    )
                    TextButton(onClick = { onNavigateTab(com.example.viewmodel.MainTab.ORDER) }) {
                        Text("View menu", color = StarbucksHouseGreen, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(StarbucksData.sampleDrinks.filter { it.isPopular || it.isSeasonal }) { drink ->
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier
                                .width(170.dp)
                                .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(20.dp))
                                .clickable { onSelectDrink(drink) }
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(StarbucksLightGreen),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalCafe,
                                        contentDescription = null,
                                        tint = StarbucksHouseGreen,
                                        modifier = Modifier.size(48.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = drink.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    maxLines = 2,
                                    color = StarbucksDarkText
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "$${String.format("%.2f", drink.basePrice)} • ${drink.calories} Cal",
                                    fontSize = 11.sp,
                                    color = StarbucksSubtext
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Member Offers Carousel
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "FOR YOU",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = StarbucksDarkGreen,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(offers) { offer ->
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = StarbucksDarkGreen),
                            modifier = Modifier
                                .width(260.dp)
                                .clickable { onNavigateTab(com.example.viewmodel.MainTab.REWARDS) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = StarbucksGold
                                ) {
                                    Text(
                                        text = "OFFER",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        color = StarbucksDarkGreen,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = offer.title,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 15.sp
                                )

                                Text(
                                    text = offer.description,
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 12.sp,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
