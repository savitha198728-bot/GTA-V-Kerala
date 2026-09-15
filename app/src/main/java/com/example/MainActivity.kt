package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.KeralaGameView
import com.example.model.GtaKeralaData
import com.example.model.Vehicle
import com.example.ui.GarageScreen
import com.example.ui.MissionsScreen
import com.example.ui.PromptsScreen
import com.example.ui.ScriptStudioScreen
import com.example.ui.SoundboardScreen
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GtaGreen
import com.example.ui.theme.KeralaGold
import com.example.ui.theme.KsrtcOrange
import com.example.ui.theme.MyApplicationTheme

enum class GtaTab(val title: String, val iconEmoji: String) {
    GAME("ഗെയിം", "🎮"),
    MISSIONS("മിഷനുകൾ", "🗺️"),
    GARAGE("ഷെഡ്ഡ്", "🚌"),
    SCRIPTS("റീൽസ്", "🎬"),
    PROMPTS("പ്രോംപ്റ്റ്", "🎨"),
    SOUNDS("ശബ്ദങ്ങൾ", "🔊")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                GtaKeralaApp()
            }
        }
    }
}

@Composable
fun GtaKeralaApp() {
    var currentTab by remember { mutableStateOf(GtaTab.GAME) }
    var selectedVehicle by remember { mutableStateOf<Vehicle>(GtaKeralaData.vehicles.first()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF13171F),
                tonalElevation = 8.dp
            ) {
                GtaTab.values().forEach { tab ->
                    val isSelected = currentTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        icon = {
                            Text(text = tab.iconEmoji, fontSize = 20.sp)
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) KsrtcOrange else Color.LightGray
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = KsrtcOrange,
                            unselectedIconColor = Color.LightGray,
                            indicatorColor = KsrtcOrange.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkSurface)
        ) {
            when (currentTab) {
                GtaTab.GAME -> {
                    KeralaGameView(
                        selectedVehicle = selectedVehicle,
                        onBack = { currentTab = GtaTab.MISSIONS }
                    )
                }
                GtaTab.MISSIONS -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        GtaHeaderBanner(
                            imageRes = R.drawable.img_kerala_chase,
                            title = "GTA V × KERALA",
                            subtitle = "ആലപ്പുഴ • കൊച്ചി • തിരുവനന്തപുരം"
                        )
                        MissionsScreen(
                            onLaunchMission = { mission ->
                                // Auto set vehicle if suggested
                                val matchingVehicle = GtaKeralaData.vehicles.find {
                                    mission.recommendedVehicle.contains(it.nameMalayalam) || it.nameMalayalam.contains(mission.recommendedVehicle)
                                }
                                if (matchingVehicle != null) {
                                    selectedVehicle = matchingVehicle
                                }
                                currentTab = GtaTab.GAME
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                GtaTab.GARAGE -> {
                    GarageScreen(
                        currentSelectedVehicle = selectedVehicle,
                        onSelectVehicle = { veh ->
                            selectedVehicle = veh
                            currentTab = GtaTab.GAME
                        }
                    )
                }
                GtaTab.SCRIPTS -> {
                    ScriptStudioScreen()
                }
                GtaTab.PROMPTS -> {
                    PromptsScreen()
                }
                GtaTab.SOUNDS -> {
                    SoundboardScreen()
                }
            }
        }
    }
}

@Composable
fun GtaHeaderBanner(
    imageRes: Int,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(start = 16.dp, end = 16.dp, top = 36.dp, bottom = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1F29)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "GTA Kerala Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x77000000))
            )
            // Title & Subtitle
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = title,
                    color = KeralaGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = subtitle,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Kept for testing compatibility
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme { Greeting("Android") }
}
