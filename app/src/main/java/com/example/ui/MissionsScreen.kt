package com.example.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GtaKeralaData
import com.example.model.Mission
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GtaGreen
import com.example.ui.theme.KeralaGold
import com.example.ui.theme.KsrtcOrange
import com.example.ui.theme.PoliceRed

@Composable
fun MissionsScreen(
    onLaunchMission: (Mission) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMission by remember { mutableStateOf<Mission?>(GtaKeralaData.missions.first()) }
    var selectedFilterLocation by remember { mutableStateOf<String?>(null) }

    val locations = listOf("എല്ലാം (All)", "ആലപ്പുഴ", "കൊച്ചി", "തിരുവനന്തപുരം", "കുമരകം")

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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "കേരള മിഷനുകൾ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Surface(
                        color = GtaGreen.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GtaGreen)
                    ) {
                        Text(
                            text = "GTA KERALA OPS",
                            color = GtaGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ആലപ്പുഴ, കൊച്ചി, തിരുവനന്തപുരം എന്നിവിടങ്ങളിലെ ആക്ഷൻ മിഷനുകൾ തിരഞ്ഞെടുക്കുക",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }

        // Kerala Map Radar simulation card
        item {
            KeralaMapRadarCard(
                missions = GtaKeralaData.missions,
                selectedMission = selectedMission,
                onSelectMission = { selectedMission = it }
            )
        }

        // Location Filter Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                locations.forEach { loc ->
                    val isSelected = (selectedFilterLocation == null && loc.startsWith("എല്ലാം")) ||
                            (selectedFilterLocation != null && loc.contains(selectedFilterLocation!!))

                    Surface(
                        color = if (isSelected) KsrtcOrange else DarkCard,
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) KsrtcOrange else Color.Gray.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .clickable {
                                selectedFilterLocation = if (loc.startsWith("എല്ലാം")) null else loc
                            }
                    ) {
                        Text(
                            text = loc,
                            color = if (isSelected) Color.White else Color.LightGray,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Mission List
        val filteredMissions = GtaKeralaData.missions.filter { m ->
            selectedFilterLocation == null || m.location.contains(selectedFilterLocation!!) || m.titleMalayalam.contains(selectedFilterLocation!!)
        }

        items(filteredMissions) { mission ->
            val isExpanded = selectedMission?.id == mission.id

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedMission = mission }
                    .testTag("mission_card_${mission.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isExpanded) Color(0xFF222834) else DarkCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isExpanded) KsrtcOrange else Color.Transparent
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = mission.titleMalayalam,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = mission.titleEnglish,
                                fontSize = 12.sp,
                                color = Color.LightGray
                            )
                        }

                        // Wanted level stars
                        Row {
                            for (i in 1..mission.wantedStars) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = KeralaGold,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = KsrtcOrange,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = mission.location,
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                        }

                        Text(
                            text = mission.rewardCash,
                            color = GtaGreen,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 14.dp)) {
                            Surface(
                                color = Color(0xFF181C24),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "മിഷൻ വിവരണം (Briefing):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = KsrtcOrange
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = mission.briefingMalayalam,
                                        fontSize = 13.sp,
                                        color = Color.White,
                                        lineHeight = 18.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "ലക്ഷ്യങ്ങൾ (Objectives):",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = KeralaGold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            mission.objectives.forEachIndexed { idx, obj ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = GtaGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = obj,
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = { onLaunchMission(mission) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("launch_mission_button_${mission.id}"),
                                colors = ButtonDefaults.buttonColors(containerColor = KsrtcOrange),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "മിഷൻ ആരംഭിക്കുക (Start Mission)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeralaMapRadarCard(
    missions: List<Mission>,
    selectedMission: Mission?,
    onSelectMission: (Mission) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF141920)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3444))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Radar Grid Background
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "📍 KERALA GPS RADAR",
                        color = KsrtcOrange,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "LIVE SATELLITE",
                        color = GtaGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Kerala Map Points Representation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color(0xFF0F1318), RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(8.dp))
                ) {
                    // Backwaters line schematic
                    Text(
                        text = "~~~ വേമ്പനാട് കായൽ ~~~",
                        color = Color(0x4400E5FF),
                        fontSize = 10.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    )
                    Text(
                        text = "അറബിക്കടൽ (Arabian Sea)",
                        color = Color(0x22FFFFFF),
                        fontSize = 10.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 12.dp, bottom = 8.dp)
                    )

                    // Pin 1: Kochi
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 10.dp)
                            .clickable { onSelectMission(missions[1]) }
                    ) {
                        MapPin(label = "കൊച്ചി (Kochi)", isSelected = selectedMission?.id == "m2")
                    }

                    // Pin 2: Alappuzha
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable { onSelectMission(missions[0]) }
                    ) {
                        MapPin(label = "ആലപ്പുഴ (Alappuzha)", isSelected = selectedMission?.id == "m1")
                    }

                    // Pin 3: Thiruvananthapuram
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp)
                            .clickable { onSelectMission(missions[2]) }
                    ) {
                        MapPin(label = "തിരുവനന്തപുരം (TVM)", isSelected = selectedMission?.id == "m3")
                    }
                }
            }
        }
    }
}

@Composable
fun MapPin(label: String, isSelected: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                if (isSelected) KsrtcOrange else Color(0xCC1E222A),
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                if (isSelected) Color.White else KsrtcOrange,
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(if (isSelected) Color.White else GtaGreen, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
