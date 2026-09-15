package com.example.game

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundEffects
import com.example.model.GtaKeralaData
import com.example.model.Vehicle
import com.example.ui.theme.BackwaterBlue
import com.example.ui.theme.CoconutGreen
import com.example.ui.theme.GtaGreen
import com.example.ui.theme.KeralaGold
import com.example.ui.theme.KsrtcOrange
import com.example.ui.theme.KsrtcYellow
import com.example.ui.theme.PoliceBlue
import com.example.ui.theme.PoliceRed
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

enum class ObstacleType {
    POLICE_JEEP,
    POTHOLE,
    COW,
    BARRICADE
}

enum class PickupType {
    PAZHAM_PORI,
    CHAYA,
    TENDER_COCONUT
}

data class GameObstacle(
    val id: Long,
    var xPercent: Float, // 0.2 to 0.8
    var yPercent: Float, // 0.0 to 1.1
    val type: ObstacleType,
    var isHornCleared: Boolean = false
)

data class GamePickup(
    val id: Long,
    var xPercent: Float,
    var yPercent: Float,
    val type: PickupType
)

@Composable
fun KeralaGameView(
    selectedVehicle: Vehicle,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current

    // Game state
    var isRunning by remember { mutableStateOf(true) }
    var isGameOver by remember { mutableStateOf(false) }
    var health by remember { mutableFloatStateOf(100f) }
    var speedKmH by remember { mutableFloatStateOf(selectedVehicle.topSpeed * 0.6f) }
    var distanceMeters by remember { mutableFloatStateOf(0f) }
    var cashEarned by remember { mutableIntStateOf(0) }
    var wantedStars by remember { mutableIntStateOf(1) }
    var boostSecondsLeft by remember { mutableIntStateOf(0) }
    var shieldSecondsLeft by remember { mutableIntStateOf(0) }
    var bannerText by remember { mutableStateOf<String?>("വണ്ടി വിട്ടോ മോനേ!") }
    var playerX by remember { mutableFloatStateOf(0.5f) } // 0.2f to 0.8f
    var roadScrollOffset by remember { mutableFloatStateOf(0f) }
    var sirenFlash by remember { mutableStateOf(false) }

    val obstacles = remember { mutableStateListOf<GameObstacle>() }
    val pickups = remember { mutableStateListOf<GamePickup>() }

    fun playVehicleHorn() {
        when (selectedVehicle.id) {
            "ksrtc" -> SoundEffects.playKsrtcAirHorn(context)
            "bullet" -> SoundEffects.playBulletThump(context)
            "auto" -> SoundEffects.playAutoHorn(context)
            else -> SoundEffects.playAutoHorn(context)
        }
        // Horn clears immediate obstacles ahead
        obstacles.filter { it.yPercent > 0.4f && it.yPercent < 0.85f }.forEach { obs ->
            obs.isHornCleared = true
            obs.xPercent = if (obs.xPercent > 0.5f) 0.85f else 0.15f
        }
        bannerText = "🔊 ഹോൺ കേട്ട് വഴി മാറി!"
    }

    // Horn / siren flash cycle
    LaunchedEffect(wantedStars) {
        if (wantedStars >= 3) {
            SoundEffects.playPoliceSiren(context)
        }
    }

    // Main Game Loop
    LaunchedEffect(isRunning, isGameOver) {
        if (!isRunning || isGameOver) return@LaunchedEffect

        var tickCounter = 0
        while (isRunning && !isGameOver) {
            delay(33) // ~30 FPS game loop
            tickCounter++

            val currentSpeedMultiplier = if (boostSecondsLeft > 0) 1.5f else 1.0f
            val deltaDistance = (speedKmH * currentSpeedMultiplier) / 30f
            distanceMeters += deltaDistance
            roadScrollOffset = (roadScrollOffset + 0.04f * currentSpeedMultiplier) % 1.0f

            if (tickCounter % 15 == 0) {
                sirenFlash = !sirenFlash
            }

            // Timers countdown
            if (tickCounter % 30 == 0) {
                if (boostSecondsLeft > 0) boostSecondsLeft--
                if (shieldSecondsLeft > 0) shieldSecondsLeft--

                // Wanted level progression
                if (distanceMeters > 500 && wantedStars < 2) {
                    wantedStars = 2
                    bannerText = "⭐ 2-Stars: SI കുഞ്ഞുമോൻ പിന്നാലെയുണ്ട്!"
                } else if (distanceMeters > 1500 && wantedStars < 3) {
                    wantedStars = 3
                    bannerText = "⭐⭐ 3-Stars: ഫ്ളൈയിങ് സ്ക്വാഡ് ജിപ്പുകൾ എത്തി!"
                } else if (distanceMeters > 3000 && wantedStars < 4) {
                    wantedStars = 4
                    bannerText = "⭐⭐⭐ 4-Stars: റോഡിൽ ബാരിക്കേഡുകൾ!"
                } else if (distanceMeters > 5000 && wantedStars < 5) {
                    wantedStars = 5
                    bannerText = "⭐⭐⭐⭐ 5-Stars: കംപ്ലീറ്റ് പോലീസ് ചേസ്!"
                }
            }

            // Spawn obstacles
            if (tickCounter % (50 - (wantedStars * 5).coerceAtMost(30)) == 0) {
                val lanes = listOf(0.28f, 0.5f, 0.72f)
                val randomLane = lanes.random()
                val type = when (Random.nextInt(100)) {
                    in 0..35 -> ObstacleType.POLICE_JEEP
                    in 36..60 -> ObstacleType.POTHOLE
                    in 61..80 -> ObstacleType.COW
                    else -> ObstacleType.BARRICADE
                }
                obstacles.add(
                    GameObstacle(
                        id = System.currentTimeMillis() + Random.nextInt(1000),
                        xPercent = randomLane,
                        yPercent = -0.1f,
                        type = type
                    )
                )
            }

            // Spawn Pickups
            if (tickCounter % 75 == 0) {
                val lanes = listOf(0.3f, 0.5f, 0.7f)
                val type = when (Random.nextInt(3)) {
                    0 -> PickupType.PAZHAM_PORI
                    1 -> PickupType.CHAYA
                    else -> PickupType.TENDER_COCONUT
                }
                pickups.add(
                    GamePickup(
                        id = System.currentTimeMillis() + Random.nextInt(2000),
                        xPercent = lanes.random(),
                        yPercent = -0.1f,
                        type = type
                    )
                )
            }

            // Update Obstacles
            val obsIterator = obstacles.iterator()
            while (obsIterator.hasNext()) {
                val obs = obsIterator.next()
                obs.yPercent += 0.025f * currentSpeedMultiplier

                // Collision detection
                if (obs.yPercent in 0.72f..0.88f && !obs.isHornCleared) {
                    val xDist = kotlin.math.abs(obs.xPercent - playerX)
                    if (xDist < 0.12f) {
                        // Collision!
                        if (shieldSecondsLeft > 0) {
                            // Shield absorbed
                            obsIterator.remove()
                            SoundEffects.playCrashSound(context)
                            bannerText = "🛡️ കരിക്ക് ഷീൽഡ് രക്ഷിച്ചു!"
                        } else {
                            val damage = when (obs.type) {
                                ObstacleType.POLICE_JEEP -> 30f * (100f - selectedVehicle.armor) / 100f
                                ObstacleType.BARRICADE -> 25f * (100f - selectedVehicle.armor) / 100f
                                ObstacleType.POTHOLE -> 15f
                                ObstacleType.COW -> 20f
                            }.coerceAtLeast(8f)

                            health -= damage
                            SoundEffects.playCrashSound(context)
                            obsIterator.remove()

                            val reactionQuotes = listOf(
                                "എടാ മോനേ... നോക്കി ഓടിക്കടാ!",
                                "വണ്ടി പണി പാളി!",
                                "പോലീസ് പിടിച്ചേ!",
                                "റോഡിൽ എന്തൊരു കുഴികൾ!",
                                "അയ്യോ എന്റെ മീൻകറി!"
                            )
                            bannerText = reactionQuotes.random()

                            if (health <= 0f) {
                                health = 0f
                                isGameOver = true
                                bannerText = "🚨 BUSTED / തൊണ്ടിമുതൽ പിടിച്ചു!"
                            }
                        }
                    }
                }

                if (obs.yPercent > 1.15f) {
                    obsIterator.remove()
                }
            }

            // Update Pickups
            val pickIterator = pickups.iterator()
            while (pickIterator.hasNext()) {
                val pick = pickIterator.next()
                pick.yPercent += 0.025f * currentSpeedMultiplier

                if (pick.yPercent in 0.72f..0.88f) {
                    val xDist = kotlin.math.abs(pick.xPercent - playerX)
                    if (xDist < 0.12f) {
                        SoundEffects.playPickupChime(context)
                        when (pick.type) {
                            PickupType.PAZHAM_PORI -> {
                                cashEarned += 500
                                boostSecondsLeft = 6
                                bannerText = "🍌 ചൂട് പഴംപൊരി! സ്പീഡ് ബൂസ്റ്റ്!"
                            }
                            PickupType.CHAYA -> {
                                cashEarned += 200
                                health = (health + 25f).coerceAtMost(100f)
                                bannerText = "☕ സ്ട്രോങ്ങ് ചായ! ആരോഗ്യം കൂടി!"
                            }
                            PickupType.TENDER_COCONUT -> {
                                cashEarned += 300
                                shieldSecondsLeft = 8
                                bannerText = "🥥 കരിക്ക് ഷീൽഡ് ആക്റ്റീവ്!"
                            }
                        }
                        pickIterator.remove()
                    }
                }

                if (pick.yPercent > 1.15f) {
                    pickIterator.remove()
                }
            }
        }
    }

    fun restartGame() {
        health = 100f
        distanceMeters = 0f
        cashEarned = 0
        wantedStars = 1
        boostSecondsLeft = 0
        shieldSecondsLeft = 0
        playerX = 0.5f
        obstacles.clear()
        pickups.clear()
        bannerText = "വണ്ടി വിട്ടോ മോനേ!"
        isGameOver = false
        isRunning = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1117))
    ) {
        // Highway Canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val deltaX = dragAmount.x / size.width
                        playerX = (playerX + deltaX).coerceIn(0.22f, 0.78f)
                    }
                }
        ) {
            drawKeralaRoad(
                roadOffset = roadScrollOffset,
                playerX = playerX,
                obstacles = obstacles,
                pickups = pickups,
                selectedVehicle = selectedVehicle,
                sirenFlash = sirenFlash,
                hasShield = shieldSecondsLeft > 0
            )
        }

        // Top HUD (GTA V Style)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Vehicle & Health
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = selectedVehicle.iconEmoji,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = selectedVehicle.nameMalayalam,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Health",
                            tint = if (health > 30f) GtaGreen else PoliceRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        LinearProgressIndicator(
                            progress = { health / 100f },
                            modifier = Modifier
                                .width(100.dp)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = if (health > 30f) GtaGreen else PoliceRed,
                            trackColor = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${health.toInt()}%",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Wanted Stars
                Row(
                    modifier = Modifier
                        .background(Color(0xCC000000), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star $i",
                            tint = if (i <= wantedStars) KeralaGold else Color.Gray.copy(alpha = 0.4f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Cash & Distance
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "₹ ${cashEarned + (distanceMeters * 2).toInt()}",
                    color = GtaGreen,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
                Text(
                    text = "${(distanceMeters / 1000f).let { String.format("%.2f", it) }} KM",
                    color = KsrtcYellow,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Power-up indicators
            Row(modifier = Modifier.padding(top = 4.dp)) {
                if (boostSecondsLeft > 0) {
                    Surface(
                        color = KsrtcOrange,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        Text(
                            text = "⚡ പഴംപൊരി ബൂസ്റ്റ്: ${boostSecondsLeft}s",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
                if (shieldSecondsLeft > 0) {
                    Surface(
                        color = BackwaterBlue,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "🥥 കരിക്ക് ഷീൽഡ്: ${shieldSecondsLeft}s",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Reaction Dialogue Banner
            bannerText?.let { text ->
                Surface(
                    color = Color(0xE620242C),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, KsrtcOrange),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = text,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Bottom Controls Overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Steer Left Button
                Button(
                    onClick = {
                        playerX = (playerX - 0.12f).coerceIn(0.22f, 0.78f)
                    },
                    modifier = Modifier
                        .size(68.dp)
                        .testTag("steer_left_button"),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xCC282C34))
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Steer Left",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                // Super Horn Button
                Button(
                    onClick = { playVehicleHorn() },
                    modifier = Modifier
                        .height(60.dp)
                        .testTag("horn_action_button"),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KsrtcOrange)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Horn",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ഹോൺ (HORN)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                // Steer Right Button
                Button(
                    onClick = {
                        playerX = (playerX + 0.12f).coerceIn(0.22f, 0.78f)
                    },
                    modifier = Modifier
                        .size(68.dp)
                        .testTag("steer_right_button"),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xCC282C34))
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Steer Right",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        // Game Over / Busted Screen
        if (isGameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xE6000000)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E222B)),
                    border = androidx.compose.foundation.BorderStroke(2.dp, PoliceRed)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "തൊണ്ടിമുതൽ പിടിച്ചു!",
                            color = PoliceRed,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "BUSTED BY KERALA POLICE",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "എസ്.ഐ കുഞ്ഞുമോൻ നിങ്ങളുടെ വണ്ടി കസ്റ്റഡിയിലെടുത്തു. പെറ്റി അടിച്ചിട്ട് അടുത്ത റൗണ്ട് തുടങ്ങാം!",
                            color = Color.White,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("ദൂരം", color = Color.Gray, fontSize = 12.sp)
                                Text(
                                    "${(distanceMeters / 1000f).let { String.format("%.2f", it) }} km",
                                    color = KsrtcYellow,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("ക്യാഷ്", color = Color.Gray, fontSize = 12.sp)
                                Text(
                                    "₹ ${cashEarned + (distanceMeters * 2).toInt()}",
                                    color = GtaGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { restartGame() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("restart_game_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = GtaGreen),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Restart")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "വീണ്ടും കളിക്കുക (Play Again)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawKeralaRoad(
    roadOffset: Float,
    playerX: Float,
    obstacles: List<GameObstacle>,
    pickups: List<GamePickup>,
    selectedVehicle: Vehicle,
    sirenFlash: Boolean,
    hasShield: Boolean
) {
    val canvasW = size.width
    val canvasH = size.height

    // 1. Left border: Backwaters with wave lines
    val leftRoadX = canvasW * 0.18f
    val rightRoadX = canvasW * 0.82f
    val roadWidth = rightRoadX - leftRoadX

    // Backwaters on left
    drawRect(
        color = BackwaterBlue,
        topLeft = Offset(0f, 0f),
        size = Size(leftRoadX, canvasH)
    )

    // Backwater waves
    for (i in 0..12) {
        val y = ((i * 80f + roadOffset * 100f) % canvasH)
        drawLine(
            color = Color(0x33FFFFFF),
            start = Offset(20f, y),
            end = Offset(leftRoadX - 20f, y + 10f),
            strokeWidth = 3f
        )
    }

    // 2. Right border: Tropical lush greenery with coconut tree leaves
    drawRect(
        color = CoconutGreen,
        topLeft = Offset(rightRoadX, 0f),
        size = Size(canvasW - rightRoadX, canvasH)
    )

    // Coconut tree clusters along right bank
    for (i in 0..6) {
        val y = ((i * 180f + roadOffset * 180f) % canvasH)
        val treeBaseX = rightRoadX + 35f

        // Trunk
        drawLine(
            color = Color(0xFF5D4037),
            start = Offset(treeBaseX, y + 40f),
            end = Offset(treeBaseX + 15f, y),
            strokeWidth = 10f
        )
        // Green Palm Fronds
        drawCircle(
            color = Color(0xFF1B5E20),
            radius = 32f,
            center = Offset(treeBaseX + 15f, y)
        )
        drawCircle(
            color = Color(0xFF2E7D32),
            radius = 24f,
            center = Offset(treeBaseX + 22f, y - 6f)
        )
    }

    // 3. Road Surface (Kerala Asphalt)
    drawRect(
        color = Color(0xFF22262D),
        topLeft = Offset(leftRoadX, 0f),
        size = Size(roadWidth, canvasH)
    )

    // Road side curbs (White and Red striped Kerala highway barriers)
    for (i in 0..20) {
        val y = ((i * 60f + roadOffset * 120f) % canvasH)
        val isStripe = (i % 2 == 0)
        val curbColor = if (isStripe) Color.White else PoliceRed

        // Left curb
        drawRect(
            color = curbColor,
            topLeft = Offset(leftRoadX - 8f, y),
            size = Size(8f, 50f)
        )
        // Right curb
        drawRect(
            color = curbColor,
            topLeft = Offset(rightRoadX, y),
            size = Size(8f, 50f)
        )
    }

    // 4. White dashed lane markers
    val lane1X = leftRoadX + roadWidth * 0.33f
    val lane2X = leftRoadX + roadWidth * 0.67f
    val dashHeight = 50f
    val dashGap = 40f

    for (i in 0..16) {
        val y = ((i * (dashHeight + dashGap) + roadOffset * 200f) % (canvasH + 100f)) - 50f
        drawLine(
            color = Color(0x99FFFFFF),
            start = Offset(lane1X, y),
            end = Offset(lane1X, y + dashHeight),
            strokeWidth = 6f
        )
        drawLine(
            color = Color(0x99FFFFFF),
            start = Offset(lane2X, y),
            end = Offset(lane2X, y + dashHeight),
            strokeWidth = 6f
        )
    }

    // 5. Draw Pickups (Pazham pori, chai, coconut)
    for (pick in pickups) {
        val px = canvasW * pick.xPercent
        val py = canvasH * pick.yPercent

        when (pick.type) {
            PickupType.PAZHAM_PORI -> {
                // Golden banana fritter
                drawRoundRect(
                    color = KsrtcYellow,
                    topLeft = Offset(px - 16f, py - 24f),
                    size = Size(32f, 48f),
                    cornerRadius = CornerRadius(16f, 16f)
                )
                drawRoundRect(
                    color = KsrtcOrange,
                    topLeft = Offset(px - 10f, py - 18f),
                    size = Size(20f, 36f),
                    cornerRadius = CornerRadius(10f, 10f)
                )
            }
            PickupType.CHAYA -> {
                // Glass of hot Kerala milk tea
                drawRoundRect(
                    color = Color(0xFFD7CCC8),
                    topLeft = Offset(px - 14f, py - 20f),
                    size = Size(28f, 40f),
                    cornerRadius = CornerRadius(6f, 6f)
                )
                drawRect(
                    color = Color(0xFF8D6E63), // Tea liquid
                    topLeft = Offset(px - 12f, py - 10f),
                    size = Size(24f, 28f)
                )
            }
            PickupType.TENDER_COCONUT -> {
                // Green coconut
                drawCircle(
                    color = CoconutGreen,
                    radius = 20f,
                    center = Offset(px, py)
                )
                drawCircle(
                    color = Color(0xFF81C784),
                    radius = 12f,
                    center = Offset(px - 4f, py - 4f)
                )
            }
        }
    }

    // 6. Draw Obstacles (Police jeep, Pothole, Cow, Barricade)
    for (obs in obstacles) {
        val ox = canvasW * obs.xPercent
        val oy = canvasH * obs.yPercent

        when (obs.type) {
            ObstacleType.POLICE_JEEP -> {
                // White Kerala Police Bolero
                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(ox - 24f, oy - 42f),
                    size = Size(48f, 84f),
                    cornerRadius = CornerRadius(8f, 8f)
                )
                // Black windshields
                drawRect(
                    color = Color(0xFF1E222B),
                    topLeft = Offset(ox - 20f, oy - 20f),
                    size = Size(40f, 20f)
                )
                // Flashing Siren (Red / Blue strobe)
                drawCircle(
                    color = if (sirenFlash) PoliceBlue else PoliceRed,
                    radius = 10f,
                    center = Offset(ox - 10f, oy)
                )
                drawCircle(
                    color = if (sirenFlash) PoliceRed else PoliceBlue,
                    radius = 10f,
                    center = Offset(ox + 10f, oy)
                )
                // Wheels
                drawRect(Color.Black, Offset(ox - 28f, oy - 32f), Size(6f, 18f))
                drawRect(Color.Black, Offset(ox + 22f, oy - 32f), Size(6f, 18f))
                drawRect(Color.Black, Offset(ox - 28f, oy + 18f), Size(6f, 18f))
                drawRect(Color.Black, Offset(ox + 22f, oy + 18f), Size(6f, 18f))
            }
            ObstacleType.POTHOLE -> {
                // Dark irregular asphalt road pothole
                drawOval(
                    color = Color(0xFF0F1115),
                    topLeft = Offset(ox - 26f, oy - 18f),
                    size = Size(52f, 36f)
                )
                drawOval(
                    color = Color(0xFF2A1C16),
                    topLeft = Offset(ox - 18f, oy - 12f),
                    size = Size(36f, 24f)
                )
            }
            ObstacleType.COW -> {
                // White stray Kerala cow on road
                drawOval(
                    color = Color(0xFFECEFF1),
                    topLeft = Offset(ox - 22f, oy - 26f),
                    size = Size(44f, 52f)
                )
                // Cow spots
                drawCircle(Color(0xFF37474F), 10f, Offset(ox - 6f, oy - 8f))
                drawCircle(Color(0xFF37474F), 8f, Offset(ox + 8f, oy + 6f))
                // Horns
                drawLine(Color.DarkGray, Offset(ox - 12f, oy - 24f), Offset(ox - 18f, oy - 36f), 4f)
                drawLine(Color.DarkGray, Offset(ox + 12f, oy - 24f), Offset(ox + 18f, oy - 36f), 4f)
            }
            ObstacleType.BARRICADE -> {
                // Yellow & black police barricade
                drawRoundRect(
                    color = KsrtcYellow,
                    topLeft = Offset(ox - 32f, oy - 14f),
                    size = Size(64f, 28f),
                    cornerRadius = CornerRadius(4f, 4f)
                )
                // Diagonal hazard stripes
                drawLine(Color.Black, Offset(ox - 20f, oy - 14f), Offset(ox - 10f, oy + 14f), 6f)
                drawLine(Color.Black, Offset(ox, oy - 14f), Offset(ox + 10f, oy + 14f), 6f)
                drawLine(Color.Black, Offset(ox + 20f, oy - 14f), Offset(ox + 30f, oy + 14f), 6f)
            }
        }
    }

    // 7. Draw Player's Vehicle at (playerX, 0.8f)
    val playerCenterX = canvasW * playerX
    val playerCenterY = canvasH * 0.8f

    // Shield effect around player
    if (hasShield) {
        drawCircle(
            color = Color(0x6600E5FF),
            radius = 54f,
            center = Offset(playerCenterX, playerCenterY)
        )
        drawCircle(
            color = Color(0xAAFFFFFF),
            radius = 58f,
            center = Offset(playerCenterX, playerCenterY),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
        )
    }

    when (selectedVehicle.id) {
        "ksrtc" -> {
            // Iconic Red & Yellow KSRTC Fast Passenger Bus
            val busW = 54f
            val busH = 120f
            // Yellow bus body
            drawRoundRect(
                color = KsrtcYellow,
                topLeft = Offset(playerCenterX - busW / 2f, playerCenterY - busH / 2f),
                size = Size(busW, busH),
                cornerRadius = CornerRadius(8f, 8f)
            )
            // Red accent stripe (KSRTC signature livery)
            drawRect(
                color = PoliceRed,
                topLeft = Offset(playerCenterX - busW / 2f, playerCenterY - 15f),
                size = Size(busW, 30f)
            )
            // Front & Rear windshields
            drawRoundRect(
                color = Color(0xFF1E2638),
                topLeft = Offset(playerCenterX - busW / 2f + 4f, playerCenterY - busH / 2f + 6f),
                size = Size(busW - 8f, 22f),
                cornerRadius = CornerRadius(4f, 4f)
            )
            drawRoundRect(
                color = Color(0xFF1E2638),
                topLeft = Offset(playerCenterX - busW / 2f + 4f, playerCenterY + busH / 2f - 24f),
                size = Size(busW - 8f, 18f),
                cornerRadius = CornerRadius(4f, 4f)
            )
            // Roof air vents
            drawRect(
                color = Color(0xFFC49000),
                topLeft = Offset(playerCenterX - 8f, playerCenterY - 40f),
                size = Size(16f, 60f)
            )
            // Wheels
            drawRect(Color.Black, Offset(playerCenterX - busW / 2f - 4f, playerCenterY - 38f), Size(5f, 24f))
            drawRect(Color.Black, Offset(playerCenterX + busW / 2f - 1f, playerCenterY - 38f), Size(5f, 24f))
            drawRect(Color.Black, Offset(playerCenterX - busW / 2f - 4f, playerCenterY + 18f), Size(5f, 24f))
            drawRect(Color.Black, Offset(playerCenterX + busW / 2f - 1f, playerCenterY + 18f), Size(5f, 24f))
        }
        "bullet" -> {
            // Royal Enfield Bullet 350
            val bikeW = 28f
            val bikeH = 74f
            // Black frame & tank
            drawRoundRect(
                color = Color(0xFF121417),
                topLeft = Offset(playerCenterX - bikeW / 2f, playerCenterY - bikeH / 2f),
                size = Size(bikeW, bikeH),
                cornerRadius = CornerRadius(6f, 6f)
            )
            // Chrome engine / exhaust
            drawRoundRect(
                color = Color(0xFFE0E0E0),
                topLeft = Offset(playerCenterX + 6f, playerCenterY - 12f),
                size = Size(8f, 38f),
                cornerRadius = CornerRadius(3f, 3f)
            )
            // Gold tank badge
            drawCircle(KeralaGold, 5f, Offset(playerCenterX, playerCenterY - 12f))
            // Rider in White Kasavu Mundu & Black Shirt
            drawCircle(Color(0xFF212121), 12f, Offset(playerCenterX, playerCenterY - 6f)) // Torso
            drawCircle(Color(0xFFFFCC80), 8f, Offset(playerCenterX, playerCenterY - 18f)) // Head/Sunglasses
            drawRoundRect(
                color = Color.White, // White mundu
                topLeft = Offset(playerCenterX - 9f, playerCenterY + 8f),
                size = Size(18f, 22f),
                cornerRadius = CornerRadius(4f, 4f)
            )
            // Handlebars
            drawLine(
                Color(0xFFE0E0E0),
                Offset(playerCenterX - 22f, playerCenterY - 24f),
                Offset(playerCenterX + 22f, playerCenterY - 24f),
                5f
            )
        }
        "auto" -> {
            // Kerala Auto Rickshaw: Yellow roof, black body, green trim
            val autoW = 44f
            val autoH = 72f
            // Black body base
            drawRoundRect(
                color = Color(0xFF1E2024),
                topLeft = Offset(playerCenterX - autoW / 2f, playerCenterY - autoH / 2f),
                size = Size(autoW, autoH),
                cornerRadius = CornerRadius(10f, 10f)
            )
            // Bright Yellow Canopy Roof
            drawRoundRect(
                color = KsrtcYellow,
                topLeft = Offset(playerCenterX - autoW / 2f + 3f, playerCenterY - autoH / 2f + 16f),
                size = Size(autoW - 6f, 46f),
                cornerRadius = CornerRadius(8f, 8f)
            )
            // Windshield in front
            drawRoundRect(
                color = Color(0xFF90CAF9),
                topLeft = Offset(playerCenterX - 14f, playerCenterY - autoH / 2f + 4f),
                size = Size(28f, 14f),
                cornerRadius = CornerRadius(3f, 3f)
            )
            // Wheels: 1 front, 2 rear
            drawRect(Color.Black, Offset(playerCenterX - 4f, playerCenterY - autoH / 2f - 4f), Size(8f, 14f))
            drawRect(Color.Black, Offset(playerCenterX - autoW / 2f - 3f, playerCenterY + 16f), Size(5f, 18f))
            drawRect(Color.Black, Offset(playerCenterX + autoW / 2f - 2f, playerCenterY + 16f), Size(5f, 18f))
        }
        else -> {
            // Honda Activa Scooter
            val actW = 26f
            val actH = 64f
            drawRoundRect(
                color = GtaGreen,
                topLeft = Offset(playerCenterX - actW / 2f, playerCenterY - actH / 2f),
                size = Size(actW, actH),
                cornerRadius = CornerRadius(6f, 6f)
            )
            // Seat
            drawRoundRect(
                color = Color(0xFF3E2723),
                topLeft = Offset(playerCenterX - 9f, playerCenterY - 6f),
                size = Size(18f, 26f),
                cornerRadius = CornerRadius(4f, 4f)
            )
            // Front grocery bag ("പച്ചക്കറി സഞ്ചി")
            drawCircle(Color(0xFFE53935), 6f, Offset(playerCenterX, playerCenterY - 14f))
        }
    }
}
