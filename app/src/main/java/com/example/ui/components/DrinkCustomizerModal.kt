package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizerBottomSheet(
    drinkItem: DrinkItem,
    initialCustomization: CustomizationOptions,
    onDismiss: () -> Unit,
    onAddToCart: (CustomizationOptions) -> Unit,
    onSaveFavorite: (String, CustomizationOptions) -> Unit
) {
    var options by remember { mutableStateOf(initialCustomization) }
    var favoriteName by remember { mutableStateOf("") }
    var showFavoriteDialog by remember { mutableStateOf(false) }

    val calculatedPrice = options.calculateTotalPrice(drinkItem.basePrice)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = StarbucksWarmCream,
        modifier = Modifier.testTag("customizer_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            // Header with Drink visual cup preview
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = drinkItem.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = StarbucksDarkGreen
                    )
                    Text(
                        text = "Base $${String.format("%.2f", drinkItem.basePrice)} • ${drinkItem.calories} Cal",
                        style = MaterialTheme.typography.bodySmall,
                        color = StarbucksSubtext
                    )
                }

                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(StarbucksLightGreen)
                        .border(1.dp, StarbucksHouseGreen.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalCafe,
                        contentDescription = null,
                        tint = StarbucksHouseGreen,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dynamic live summary pill
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${options.size.label} • ${options.milk.displayName}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = StarbucksDarkText
                        )
                        Text(
                            text = "${options.shots} Shots • ${options.syrup} • ${options.ice.displayName}",
                            fontSize = 11.sp,
                            color = StarbucksSubtext
                        )
                    }

                    Text(
                        text = "$${String.format("%.2f", calculatedPrice)}",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = StarbucksHouseGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Customization Options List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Size selection
                item {
                    Text("SIZE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = StarbucksSubtext)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(DrinkSize.values()) { size ->
                            val isSelected = options.size == size
                            FilterChip(
                                selected = isSelected,
                                onClick = { options = options.copy(size = size) },
                                label = { Text("${size.label} (${size.volume})") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = StarbucksHouseGreen,
                                    selectedLabelColor = Color.White
                                ),
                                modifier = Modifier.testTag("chip_size_${size.name}")
                            )
                        }
                    }
                }

                // Milk Option
                item {
                    Text("MILK", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = StarbucksSubtext)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(MilkOption.values()) { milk ->
                            val isSelected = options.milk == milk
                            FilterChip(
                                selected = isSelected,
                                onClick = { options = options.copy(milk = milk) },
                                label = {
                                    val extra = if (milk.extraCost > 0) " (+$${milk.extraCost})" else ""
                                    Text("${milk.displayName}$extra")
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = StarbucksHouseGreen,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Espresso Shots Counter
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("ESPRESSO SHOTS", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = StarbucksSubtext)
                            Text("Starbucks® Blonde Roast", fontSize = 11.sp, color = StarbucksSubtext)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { if (options.shots > 1) options = options.copy(shots = options.shots - 1) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease shots", tint = StarbucksDarkGreen)
                            }
                            Text(
                                text = "${options.shots}",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            IconButton(
                                onClick = { if (options.shots < 6) options = options.copy(shots = options.shots + 1) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase shots", tint = StarbucksDarkGreen)
                            }
                        }
                    }
                }

                // Syrups & Sauces
                item {
                    Text("SYRUP", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = StarbucksSubtext)
                    Spacer(modifier = Modifier.height(6.dp))
                    val syrups = listOf("Vanilla", "Brown Sugar", "Caramel", "Hazelnut", "None")
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(syrups) { s ->
                            val isSelected = options.syrup == s
                            FilterChip(
                                selected = isSelected,
                                onClick = { options = options.copy(syrup = s) },
                                label = { Text(s) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = StarbucksHouseGreen,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Ice Level
                item {
                    Text("ICE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = StarbucksSubtext)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(IceLevel.values()) { ice ->
                            val isSelected = options.ice == ice
                            FilterChip(
                                selected = isSelected,
                                onClick = { options = options.copy(ice = ice) },
                                label = { Text(ice.displayName) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = StarbucksHouseGreen,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Sweetness Level
                item {
                    Text("SWEETNESS", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = StarbucksSubtext)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(SweetnessLevel.values()) { sweetness ->
                            val isSelected = options.sweetness == sweetness
                            FilterChip(
                                selected = isSelected,
                                onClick = { options = options.copy(sweetness = sweetness) },
                                label = { Text(sweetness.displayName) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = StarbucksHouseGreen,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Synthetic Nutritional Info
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                "NUTRITIONAL INFO (SYNTHETIC DATA)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = StarbucksHouseGreen
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Calories: ${drinkItem.calories} kcal", fontSize = 12.sp, color = StarbucksSubtext)
                                Text("Caffeine: ${drinkItem.caffeineMg} mg", fontSize = 12.sp, color = StarbucksSubtext)
                                Text("Carbs: 22g", fontSize = 12.sp, color = StarbucksSubtext)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Actions: Save Favorite & Add to Order
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { showFavoriteDialog = true },
                    modifier = Modifier
                        .weight(0.4f)
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(StarbucksHouseGreen, StarbucksHouseGreen)))
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = StarbucksHouseGreen)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Save", color = StarbucksHouseGreen, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onAddToCart(options) },
                    colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen),
                    modifier = Modifier
                        .weight(0.6f)
                        .height(50.dp)
                        .testTag("btn_add_to_order"),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "ADD TO ORDER • $${String.format("%.2f", calculatedPrice)}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }

    if (showFavoriteDialog) {
        AlertDialog(
            onDismissRequest = { showFavoriteDialog = false },
            title = { Text("Save as Favourite", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Give your custom drink creation a memorable name:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = favoriteName,
                        onValueChange = { favoriteName = it },
                        placeholder = { Text("e.g. Tom's Morning Power Brew") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSaveFavorite(favoriteName, options)
                        showFavoriteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StarbucksHouseGreen)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFavoriteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
