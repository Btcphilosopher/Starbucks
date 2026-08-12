package com.example.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StarbucksData
import com.example.data.models.*
import com.example.ui.theme.*

@Composable
fun OrderScreen(
    selectedCategory: DrinkCategory,
    cartItems: List<CartItem>,
    onSelectCategory: (DrinkCategory) -> Unit,
    onSelectDrink: (DrinkItem) -> Unit,
    onOpenCheckout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredDrinks = StarbucksData.sampleDrinks.filter { drink ->
        val matchesCategory = drink.category == selectedCategory
        val matchesSearch = searchQuery.isBlank() || drink.name.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(StarbucksWarmCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            // Screen Title & Search
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "ORDER MENU",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = StarbucksHouseGreen,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search coffee, tea, breakfast...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = StarbucksHouseGreen) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_menu_input"),
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            // Category Chips Row
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DrinkCategory.values()) { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectCategory(category) },
                        label = { Text(category.displayName, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StarbucksHouseGreen,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White
                        ),
                        modifier = Modifier.testTag("category_${category.name}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Menu Items List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredDrinks) { drink ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE2DDD5), RoundedCornerShape(20.dp))
                            .clickable { onSelectDrink(drink) }
                            .testTag("drink_card_${drink.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(StarbucksLightGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalCafe,
                                    contentDescription = null,
                                    tint = StarbucksHouseGreen,
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = drink.name,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = StarbucksDarkText
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = drink.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = StarbucksSubtext,
                                    maxLines = 2
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "$${String.format("%.2f", drink.basePrice)}",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 15.sp,
                                        color = StarbucksHouseGreen
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "• ${drink.calories} Cal",
                                        fontSize = 11.sp,
                                        color = StarbucksSubtext
                                    )
                                }
                            }

                            IconButton(
                                onClick = { onSelectDrink(drink) },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(StarbucksLightGreen)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Customise", tint = StarbucksHouseGreen)
                            }
                        }
                    }
                }
            }
        }

        // Floating Cart Bar if cart is not empty
        if (cartItems.isNotEmpty()) {
            val totalCartPrice = cartItems.sumOf { it.totalPrice }
            val count = cartItems.sumOf { it.quantity }

            Surface(
                color = StarbucksHouseGreen,
                shape = RoundedCornerShape(50),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 90.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .clickable { onOpenCheckout() }
                    .testTag("floating_cart_bar")
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(StarbucksGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$count",
                                color = StarbucksDarkGreen,
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "VIEW ORDER",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Text(
                        text = "$${String.format("%.2f", totalCartPrice)}",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
