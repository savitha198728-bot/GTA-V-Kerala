package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundEffects
import com.example.model.GtaKeralaData
import com.example.model.Vehicle
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GtaGreen
import com.example.ui.theme.KeralaGold
import com.example.ui.theme.KsrtcOrange
import com.example.ui.theme.KsrtcYellow

@Composable
fun GarageScreen(
    currentSelectedVehicle: Vehicle,
    onSelectVehicle: (Vehicle) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var previewVehicle by remember { mutableStateOf(currentSelectedVehicle) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkSurface),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "വണ്ടികളുടെ ഷെഡ്ഡ്",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Surface(
                        color = KsrtcOrange.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, KsrtcOrange)
                    ) {
                        Text(
                            text = "KERALA CUSTOMS",
                            color = KsrtcOrange,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ആനവണ്ടി, ബുള്ളറ്റ്, ഓട്ടോറിക്ഷ, ആക്റ്റീവ - സൗണ്ട് ടെസ്റ്റ് ചെയ്ത് വണ്ടി തിരഞ്ഞെടുക്കുക",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }

        // Active Vehicle Showcase Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("vehicle_showcase_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E222A)),
                border = androidx.compose.foundation.BorderStroke(2.dp, previewVehicle.themeColor)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = previewVehicle.iconEmoji,
                                fontSize = 36.sp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = previewVehicle.nameMalayalam,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = previewVehicle.subtitle,
                                    fontSize = 12.sp,
                                    color = previewVehicle.themeColor
                                )
                            }
                        }

                        if (currentSelectedVehicle.id == previewVehicle.id) {
                            Surface(
                                color = GtaGreen,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "SELECTED",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = previewVehicle.description,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Special Ability
                    Surface(
                        color = Color(0xFF14171E),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🔥", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "പ്രത്യേക കഴിവ് (Special Perk):",
                                    color = KeralaGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = previewVehicle.specialAbility,
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Performance Stats
                    StatBar(
                        label = "ടോപ്പ് സ്പീഡ് (Top Speed)",
                        value = "${previewVehicle.topSpeed} km/h",
                        fraction = previewVehicle.topSpeed / 140f,
                        icon = Icons.Default.Speed,
                        color = KsrtcYellow
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatBar(
                        label = "കവചം / ബോഡി സ്ട്രെങ്ത് (Armor)",
                        value = "${previewVehicle.armor}%",
                        fraction = previewVehicle.armor / 100f,
                        icon = Icons.Default.Security,
                        color = GtaGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatBar(
                        label = "ഡ്രിഫ്റ്റ് / മെയ്വഴക്കം (Agility)",
                        value = "${previewVehicle.agility}%",
                        fraction = previewVehicle.agility / 100f,
                        icon = Icons.Default.ElectricBolt,
                        color = previewVehicle.themeColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatBar(
                        label = "നാടൻ സ്വാഗ് (Swag Level)",
                        value = "${previewVehicle.swagLevel}%",
                        fraction = previewVehicle.swagLevel / 100f,
                        icon = Icons.Default.Star,
                        color = KeralaGold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Audio Sound Test Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                when (previewVehicle.id) {
                                    "ksrtc" -> SoundEffects.playKsrtcAirHorn(context)
                                    "bullet" -> SoundEffects.playBulletThump(context)
                                    "auto" -> SoundEffects.playAutoHorn(context)
                                    else -> SoundEffects.playAutoHorn(context)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("test_horn_button"),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, KsrtcOrange)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Test Horn",
                                tint = KsrtcOrange,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ഹോൺ കേൾക്കുക",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                onSelectVehicle(previewVehicle)
                                SoundEffects.vibrate(context, 100)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("choose_vehicle_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (currentSelectedVehicle.id == previewVehicle.id) GtaGreen else KsrtcOrange
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = if (currentSelectedVehicle.id == previewVehicle.id) "തിരഞ്ഞെടുത്തു" else "വണ്ടി എടുക്കുക",
                                color = Color.Black,
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // Vehicle Selector List
        item {
            Text(
                text = "മറ്റ് വാഹനങ്ങൾ (Select Vehicle)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(GtaKeralaData.vehicles) { vehicle ->
            val isCurrent = vehicle.id == previewVehicle.id

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { previewVehicle = vehicle }
                    .testTag("vehicle_list_item_${vehicle.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrent) Color(0xFF262C38) else DarkCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isCurrent) vehicle.themeColor else Color.Transparent
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = vehicle.iconEmoji, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = vehicle.nameMalayalam,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = vehicle.nameEnglish,
                                fontSize = 12.sp,
                                color = Color.LightGray
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${vehicle.topSpeed} km/h",
                            color = KsrtcYellow,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Swag ${vehicle.swagLevel}%",
                            color = KeralaGold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatBar(
    label: String,
    value: String,
    fraction: Float,
    icon: ImageVector,
    color: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            }
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { fraction.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = Color(0xFF14171E)
        )
    }
}
