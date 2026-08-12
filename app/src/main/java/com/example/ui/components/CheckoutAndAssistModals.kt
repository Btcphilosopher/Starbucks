package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.data.models.*
import com.example.ui.theme.*
import com.example.viewmodel.AiChatMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutBottomSheet(
    cartItems: List<CartItem>,
    selectedStore: StarbucksStore,
    selectedFulfillment: OrderFulfillmentType,
    cardBalance: Double,
    onFulfillmentChange: (OrderFulfillmentType) -> Unit,
    onRemoveItem: (String) -> Unit,
    onPlaceOrder: () -> Unit,
    onDismiss: () -> Unit
) {
    val subtotal = cartItems.sumOf { it.totalPrice }
    val tax = subtotal * 0.08
    val total = subtotal + tax
    val starsEarned = (total * 20).toInt()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = StarbucksWarmCream,
        modifier = Modifier.testTag("checkout_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "YOUR MOBILE ORDER",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = StarbucksHouseGreen,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Fulfillment selector chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrderFulfillmentType.values().forEach { type ->
                    val isSelected = selectedFulfillment == type
                    FilterChip(
                        selected = isSelected,
                        onClick = { onFulfillmentChange(type) },
                        label = {
                            Text(
                                when (type) {
                                    OrderFulfillmentType.PICKUP -> "Pickup (7 min)"
                                    OrderFulfillmentType.DRIVE_THRU -> "Drive-Thru"
                                    OrderFulfillmentType.DELIVERY -> "Delivery (22 min)"
                                }
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StarbucksHouseGreen,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chip_fulfillment_${type.name}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Store Info Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Storefront, contentDescription = null, tint = StarbucksHouseGreen)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(selectedStore.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("${selectedStore.distanceMiles} miles • ${selectedStore.openStatus}", fontSize = 11.sp, color = StarbucksSubtext)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Order items list
            Text("ITEMS", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = StarbucksSubtext)
            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems) { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.drinkItem.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(
                                    "${item.customization.size.label} • ${item.customization.milk.displayName}",
                                    fontSize = 11.sp,
                                    color = StarbucksSubtext
                                )
                            }
                            Text(
                                "$${String.format("%.2f", item.totalPrice)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = StarbucksHouseGreen
                            )
                            IconButton(onClick = { onRemoveItem(item.id) }) {
                                Icon(Icons.Default.DeleteOutline, contentDescription = "Remove", tint = Color.Gray)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Payment Summary Card
            Card(
                colors = CardDefaults.cardColors(containerColor = StarbucksLightGreen),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Subtotal", fontSize = 12.sp, color = StarbucksDarkGreen)
                        Text("$${String.format("%.2f", subtotal)}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Tax & Fees", fontSize = 12.sp, color = StarbucksDarkGreen)
                        Text("$${String.format("%.2f", tax)}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Divider(modifier = Modifier.padding(vertical = 6.dp), color = StarbucksHouseGreen.copy(alpha = 0.2f))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = StarbucksDarkGreen)
                        Text("$${String.format("%.2f", total)}", fontWeight = FontWeight.Black, fontSize = 18.sp, color = StarbucksHouseGreen)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "⭐ You will earn +$starsEarned Stars with this order!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = StarbucksHouseGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onPlaceOrder,
                colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_place_order"),
                shape = RoundedCornerShape(50)
            ) {
                Text("PLACE SIMULATED ORDER • $${String.format("%.2f", total)}", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun DriveThruCodeDialog(
    orderNumber: String = "DT-882",
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = StarbucksWarmCream,
        title = {
            Text("🚘 Drive-Thru Order Confirmed", fontWeight = FontWeight.Bold, color = StarbucksDarkGreen)
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Arrive at the Drive-Thru speaker and state your order number:")
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = StarbucksDarkGreen,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = orderNumber,
                        color = StarbucksGold,
                        fontWeight = FontWeight.Black,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Estimated wait: 4 mins • Starbucks — Main Street", fontSize = 11.sp, color = StarbucksSubtext)
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen)
            ) {
                Text("I Have Arrived")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReloadCardBottomSheet(
    currentBalance: Double,
    onReloadConfirm: (Double) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedAmount by remember { mutableStateOf(25.0) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = StarbucksWarmCream,
        modifier = Modifier.testTag("reload_card_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("RELOAD STARBUCKS CARD", fontWeight = FontWeight.Bold, color = StarbucksHouseGreen, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Current Balance: $${String.format("%.2f", currentBalance)}", fontSize = 14.sp, color = StarbucksDarkText)

            Spacer(modifier = Modifier.height(16.dp))

            val amounts = listOf(10.0, 25.0, 50.0, 100.0)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                amounts.forEach { amt ->
                    val isSel = selectedAmount == amt
                    FilterChip(
                        selected = isSel,
                        onClick = { selectedAmount = amt },
                        label = { Text("+$${amt.toInt()}") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StarbucksHouseGreen,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onReloadConfirm(selectedAmount) },
                colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text("ADD $${selectedAmount.toInt()} TO CARD", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAssistantDialog(
    messages: List<AiChatMessage>,
    isLoading: Boolean,
    onSendMessage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = StarbucksWarmCream,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        modifier = Modifier.testTag("ai_assistant_dialog")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = StarbucksHouseGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("STARBUCKS ASSIST AI", fontWeight = FontWeight.Bold, color = StarbucksDarkGreen)
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)

            // Quick suggestion chips
            val quickPrompts = listOf(
                "What should I order?",
                "Reorder my usual",
                "Low-calorie cold drinks?",
                "How many Stars do I have?"
            )
            LazyColumn(modifier = Modifier.height(40.dp)) {
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        quickPrompts.forEach { q ->
                            SuggestionChip(
                                onClick = { onSendMessage(q) },
                                label = { Text(q, fontSize = 11.sp) },
                                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Chat Messages list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { msg ->
                    val isUser = msg.sender == "user"
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (isUser) StarbucksHouseGreen else Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Text(
                                text = msg.text,
                                modifier = Modifier.padding(12.dp),
                                color = if (isUser) Color.White else StarbucksDarkText,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                if (isLoading) {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = StarbucksHouseGreen)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Brewing answer...", fontSize = 12.sp, color = StarbucksSubtext)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input field
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Ask Starbucks Assist...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(50)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        onSendMessage(inputText)
                        inputText = ""
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(StarbucksHouseGreen)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    }
}
