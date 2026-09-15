package com.example.ui

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundEffects
import com.example.model.GtaKeralaData
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GtaGreen
import com.example.ui.theme.KeralaGold
import com.example.ui.theme.KsrtcOrange
import com.example.ui.theme.KsrtcYellow
import com.example.ui.theme.PoliceBlue
import com.example.ui.theme.PoliceRed
import java.util.Locale

@Composable
fun SoundboardScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var activeSpeakingId by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        var speech: TextToSpeech? = null
        speech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Try to set Indian English or Malayalam locale
                val result = speech?.setLanguage(Locale("ml", "IN"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    speech?.setLanguage(Locale("en", "IN"))
                }
            }
        }
        tts = speech
        onDispose {
            speech.stop()
            speech.shutdown()
        }
    }

    fun speakDialogue(id: String, text: String) {
        SoundEffects.vibrate(context, 100)
        activeSpeakingId = id
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, id)
    }

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
                        text = "മാസ്സ് ഡയലോഗുകളും ശബ്ദങ്ങളും",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Surface(
                        color = PoliceRed.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PoliceRed)
                    ) {
                        Text(
                            text = "SOUNDBOARD",
                            color = PoliceRed,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "കേരള സ്റ്റൈൽ ഡയലോഗുകൾ, KSRTC എയർ ഹോൺ, പോലീസ് സൈറൺ, ബുള്ളറ്റ് ഡഗ്ഗ്-ഡഗ്ഗ്",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }

        // Quick Sound Effects Grid
        item {
            Text(
                text = "വാഹന ശബ്ദങ്ങൾ (Vehicle Sound FX)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SoundFxButton(
                        title = "KSRTC എയർ ഹോൺ",
                        subtitle = "Musical Chime",
                        icon = "🚌",
                        color = KsrtcOrange,
                        modifier = Modifier.weight(1f)
                    ) {
                        SoundEffects.playKsrtcAirHorn(context)
                    }

                    SoundFxButton(
                        title = "ബുള്ളറ്റ് തമ്പ്",
                        subtitle = "Dug Dug Rhythm",
                        icon = "🏍️",
                        color = KeralaGold,
                        modifier = Modifier.weight(1f)
                    ) {
                        SoundEffects.playBulletThump(context)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SoundFxButton(
                        title = "കേരള ഓട്ടോ ഹോൺ",
                        subtitle = "2-Stroke Beep",
                        icon = "🛺",
                        color = KsrtcYellow,
                        modifier = Modifier.weight(1f)
                    ) {
                        SoundEffects.playAutoHorn(context)
                    }

                    SoundFxButton(
                        title = "പോലീസ് സൈറൺ",
                        subtitle = "Flying Squad Siren",
                        icon = "🚔",
                        color = PoliceBlue,
                        modifier = Modifier.weight(1f)
                    ) {
                        SoundEffects.playPoliceSiren(context)
                    }
                }
            }
        }

        // Characters Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "കഥാപാത്രങ്ങൾ (Characters)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(GtaKeralaData.characters) { character ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("character_card_${character.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E3544))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = character.nameMalayalam,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = character.role,
                                fontSize = 12.sp,
                                color = KsrtcOrange
                            )
                        }

                        Surface(
                            color = Color(0xFF191D26),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = character.outfit,
                                color = Color.LightGray,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = Color(0xFF141720),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                speakDialogue(character.id, character.quote)
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.RecordVoiceOver,
                                contentDescription = "Play Quote",
                                tint = KeralaGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = character.quote,
                                fontSize = 12.sp,
                                color = Color.White,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "⭐ സ്പെഷ്യൽ സ്കിൽ: ${character.specialSkill}",
                        color = GtaGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Comedy & Mass Dialogues
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "കോമഡി & മാസ്സ് ഡയലോഗുകൾ (Dialogues)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(GtaKeralaData.dialogues) { dialogue ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        speakDialogue(dialogue.id, dialogue.malayalamText)
                    }
                    .testTag("dialogue_card_${dialogue.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E222A)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF28303E))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = KsrtcOrange.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = dialogue.speaker,
                                    color = KsrtcOrange,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = dialogue.category,
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = dialogue.malayalamText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = dialogue.englishPhonetic,
                            fontSize = 11.sp,
                            color = KeralaGold
                        )
                        Text(
                            text = dialogue.translation,
                            fontSize = 11.sp,
                            color = Color.LightGray
                        )
                    }

                    Button(
                        onClick = {
                            speakDialogue(dialogue.id, dialogue.malayalamText)
                        },
                        modifier = Modifier.size(44.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = GtaGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speak",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SoundFxButton(
    title: String,
    subtitle: String,
    icon: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(76.dp)
            .clickable { onClick() }
            .testTag("sound_button_${title}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E242E)),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = color
                )
            }
        }
    }
}
