package com.example.ui.screens

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StarbucksData
import com.example.data.models.StarbucksStore
import com.example.ui.theme.*

@Composable
fun StoresScreen(
    selectedStore: StarbucksStore,
    onSelectStore: (StarbucksStore) -> Unit,
    onDriveThruOrderClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredStores = StarbucksData.sampleStores.filter { store ->
        searchQuery.isBlank() || store.name.contains(searchQuery, ignoreCase = true) || store.address.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(StarbucksWarmCream)
            .padding(bottom = 80.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Screen Header & Search
        item {
            Column {
                Text(
                    text = "FIND A STARBUCKS",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = StarbucksHouseGreen,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search address, city, zip...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = StarbucksHouseGreen) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }
        }

        // Simulated Interactive Map Card
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = StarbucksDarkGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .testTag("map_simulation_card")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Map, contentDescription = null, tint = StarbucksGold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("SIMULATED GIS STORE MAP", color = StarbucksGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                        Text("3 Starbucks Stores near Downtown", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }

        // Stores List
        item {
            Text(
                text = "NEARBY LOCATIONS",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = StarbucksDarkGreen,
                letterSpacing = 1.sp
            )
        }

        items(filteredStores) { store ->
            val isSelected = store.id == selectedStore.id
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) StarbucksLightGreen else Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) StarbucksHouseGreen else Color(0xFFE2DDD5),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .clickable { onSelectStore(store) }
                    .testTag("store_card_${store.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = store.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = StarbucksDarkText
                            )
                            Text(
                                text = "${store.distanceMiles} miles • ${store.address}",
                                fontSize = 11.sp,
                                color = StarbucksSubtext
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = StarbucksHouseGreen
                        ) {
                            Text(
                                text = "${store.estimatedWaitMinutes} MIN WAIT",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Amenities badges row
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        if (store.hasPickup) {
                            SuggestionChip(
                                onClick = {},
                                label = { Text("Pickup ✓", fontSize = 10.sp) },
                                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White)
                            )
                        }
                        if (store.hasDriveThru) {
                            SuggestionChip(
                                onClick = {},
                                label = { Text("Drive-Thru ✓", fontSize = 10.sp) },
                                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White)
                            )
                        }
                        if (store.hasWifi) {
                            SuggestionChip(
                                onClick = {},
                                label = { Text("Wi-Fi ✓", fontSize = 10.sp) },
                                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = store.openStatus,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = StarbucksHouseGreen
                        )

                        if (store.hasDriveThru) {
                            OutlinedButton(
                                onClick = onDriveThruOrderClick,
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = StarbucksHouseGreen),
                                border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(StarbucksHouseGreen, StarbucksHouseGreen))),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text("Drive-Thru Order", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
