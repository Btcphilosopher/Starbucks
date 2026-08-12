package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.models.*
import com.example.ui.theme.*

@Composable
fun TopBarHeader(
    userName: String = "Tom",
    onNotificationClick: () -> Unit = {},
    onAiClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good morning,",
                style = MaterialTheme.typography.bodyMedium,
                color = StarbucksSubtext
            )
            Text(
                text = "$userName 👋",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = StarbucksDarkGreen
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onAiClick,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(StarbucksLightGreen)
                    .testTag("btn_ai_assistant")
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI Coffee Assistant",
                    tint = StarbucksHouseGreen
                )
            }

            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, Color(0xFFE2DDD5), CircleShape)
                    .testTag("btn_notifications")
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = StarbucksDarkGreen
                )
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(StarbucksHouseGreen)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.take(1),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun UsualOrderBentoCard(
    usualDrinkName: String = "Iced Caramel Macchiato",
    size: String = "Grande",
    milk: String = "Oat Milk",
    onOrderNowClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(24.dp))
            .testTag("bento_usual_card")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Coffee,
                        contentDescription = null,
                        tint = StarbucksHouseGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "YOUR USUAL?",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StarbucksHouseGreen,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = usualDrinkName,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = StarbucksDarkText
                )

                Text(
                    text = "$size • $milk",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StarbucksSubtext
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onOrderNowClick,
                    colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                    modifier = Modifier.testTag("btn_order_usual")
                ) {
                    Text(
                        text = "ORDER NOW",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(StarbucksLightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalCafe,
                    contentDescription = null,
                    tint = StarbucksHouseGreen,
                    modifier = Modifier.size(54.dp)
                )
            }
        }
    }
}

@Composable
fun StarsBentoCard(
    starsCount: Int = 8420,
    onRewardsClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = StarbucksLightGreen),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRewardsClick() }
            .testTag("bento_stars_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = StarbucksHouseGreen,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = String.format("%,d", starsCount),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = StarbucksHouseGreen
            )

            Text(
                text = "STARS AVAILABLE",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = StarbucksDarkGreen.copy(alpha = 0.8f),
                letterSpacing = 0.8.sp
            )
        }
    }
}

@Composable
fun CardBalanceBentoCard(
    balance: Double = 42.80,
    onPayClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(24.dp))
            .testTag("bento_card_balance")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountBalanceWallet,
                    contentDescription = null,
                    tint = StarbucksHouseGreen,
                    modifier = Modifier.size(24.dp)
                )

                Surface(
                    shape = RoundedCornerShape(50),
                    color = StarbucksLightGreen,
                    modifier = Modifier.clickable { onPayClick() }
                ) {
                    Text(
                        text = "PAY",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StarbucksHouseGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$${String.format("%.2f", balance)}",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = StarbucksDarkText
            )

            Text(
                text = "CARD BALANCE",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = StarbucksSubtext,
                letterSpacing = 0.8.sp
            )
        }
    }
}

@Composable
fun ActiveOrderDeliveryBanner(
    orderRecord: OrderRecord,
    onAdvanceClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = StarbucksDarkGreen),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .testTag("active_delivery_banner")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = StarbucksGold,
                        modifier = Modifier.size(10.dp)
                    ) {}
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ACTIVE ${orderRecord.fulfillmentType.name} ORDER",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StarbucksGold
                    )
                }

                Text(
                    text = "ID: ${orderRecord.orderId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = orderRecord.items.firstOrNull()?.drinkItem?.name ?: "Starbucks Drink",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Text(
                text = "At ${orderRecord.storeName} • Est. ${orderRecord.estimatedMinutes} mins",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Progress timeline
            val statuses = OrderStatus.values()
            val currentIndex = statuses.indexOf(orderRecord.status)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                statuses.forEachIndexed { index, status ->
                    val isActive = index <= currentIndex
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(if (isActive) StarbucksGold else Color.White.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isActive) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = StarbucksDarkGreen,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = status.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
                            fontSize = 9.sp,
                            color = if (isActive) Color.White else Color.White.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onAdvanceClick,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(Color.White, Color.White))),
                shape = RoundedCornerShape(50),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Simulate Courier Step", fontSize = 11.sp)
            }
        }
    }
}
