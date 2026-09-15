package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GtaKeralaData
import com.example.model.ScriptScene
import com.example.model.VideoScript
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GtaGreen
import com.example.ui.theme.KeralaGold
import com.example.ui.theme.KsrtcOrange
import com.example.ui.theme.KsrtcYellow

@Composable
fun ScriptStudioScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedScript by remember { mutableStateOf(GtaKeralaData.videoScripts.first()) }
    var showCustomGenerator by remember { mutableStateOf(false) }

    // Custom Generator State
    var customLocation by remember { mutableStateOf("ആലപ്പുഴ കായൽ") }
    var customVehicle by remember { mutableStateOf("കെ.എസ്.ആർ.ടി.സി ആനവണ്ടി") }
    var customPunchline by remember { mutableStateOf("എടാ മോനേ...!") }

    fun copyToClipboard(text: String, label: String = "GTA Kerala Script") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "സ്ക്രിപ്റ്റ് കോപ്പി ചെയ്തു! (Script Copied)", Toast.LENGTH_SHORT).show()
    }

    fun generateCustomScript(): VideoScript {
        return VideoScript(
            id = "custom_${System.currentTimeMillis()}",
            titleMalayalam = "$customVehicle vs $customLocation മാസ്സ് റീൽ",
            titleEnglish = "Custom 10-Second GTA Reel",
            category = "Custom Generated Reel",
            hook = "$customPunchline ഇതാണ് യഥാർത്ഥ കേരളാ സ്റ്റൈൽ!",
            captionTagline = "GTA V Kerala Edition 🔥 $customVehicle in action! $customPunchline #GTA5Kerala #MalayalamReels",
            scenes = listOf(
                ScriptScene(
                    timeRange = "0:00 - 0:03",
                    visual = "ഡ്രോൺ ഷോട്ട്: $customLocation പ്രദേശത്ത് $customVehicle അതിവേഗത്തിൽ വരുന്നു. ഡ്രൈവർ മുണ്ട് മടക്കിക്കുത്തുന്നു.",
                    audioCue = "റോയൽ എക്സ്ഹോസ്റ്റ് അല്ലെങ്കിൽ ആനവണ്ടി എയർ ഹോൺ ബിജിഎം",
                    dialogueMalayalam = "\"വഴിമാറടാ... $customVehicle വരുന്നുണ്ട്!\"",
                    dialogueEnglish = "Clear the road... here comes the ride!"
                ),
                ScriptScene(
                    timeRange = "0:03 - 0:06",
                    visual = "കേരള പോലീസിന്റെ ജീപ്പ് സൈറൺ മുഴക്കി തിരിയുന്നു. ഒരു ചായക്കടയിലെ ആൾക്കാർ കൈവീശി പ്രോത്സാഹിപ്പിക്കുന്നു.",
                    audioCue = "സൈറൺ സൗണ്ടും ടയർ സ്ക്രീച്ചും",
                    dialogueMalayalam = "\"ദാസാ... സ്പീഡ് കുറയ്ക്കരുത്! പോലീസ് തൊട്ടുപിന്നിലുണ്ട്!\"",
                    dialogueEnglish = "Dasa... don't slow down! Police right behind!"
                ),
                ScriptScene(
                    timeRange = "0:06 - 0:08",
                    visual = "തകർപ്പൻ ഡ്രിഫ്റ്റ്! റോഡിലെ വെള്ളക്കെട്ട് തെറിപ്പിച്ച് വാഹനം വളവ് തിരിയുന്നു.",
                    audioCue = "മാസ്സ് ഡ്രോപ്പ് ബിജിഎം",
                    dialogueMalayalam = "\"$customPunchline\"",
                    dialogueEnglish = customPunchline
                ),
                ScriptScene(
                    timeRange = "0:08 - 0:10",
                    visual = "സ്ലോ-മോഷൻ ഫ്രീസ് ഫ്രെയിം: GTA V 'MISSION PASSED' സൗണ്ട് വരുന്നു. ₹ 7,50,000 ക്യാഷ് ആനിമേഷൻ!",
                    audioCue = "GTA V ജിംഗിൾ + എയർ ഹോൺ",
                    dialogueMalayalam = "\"ഇതൊക്കെ എന്ത്... നമ്മൾ കേരളമാണ്!\"",
                    dialogueEnglish = "This is what we call Kerala power!"
                )
            )
        )
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
                        text = "10-സെക്കൻഡ് വീഡിയോ സ്ക്രിപ്റ്റുകൾ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Surface(
                        color = KeralaGold.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, KeralaGold)
                    ) {
                        Text(
                            text = "VIRAL REELS",
                            color = KeralaGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ഇൻസ്റ്റാഗ്രാം റീൽസിനും യൂട്യൂബ് ഷോർട്ട്സിനും അനുയോജ്യമായ 10-സെക്കൻഡ് GTA Kerala സ്ക്രിപ്റ്റുകൾ",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }

        // Action Buttons: Generate Custom Script
        item {
            Button(
                onClick = { showCustomGenerator = !showCustomGenerator },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("toggle_custom_script_generator"),
                colors = ButtonDefaults.buttonColors(containerColor = KsrtcOrange),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Movie, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (showCustomGenerator) "ക്ലോസ് ചെയ്യുക (Close Generator)" else "പുതിയ സ്ക്രിപ്റ്റ് ഉണ്ടാക്കുക (Create Custom 10s Script)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        // Custom Generator Panel
        if (showCustomGenerator) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_script_generator_panel"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1B202A)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, KsrtcOrange)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "🎬 റീൽ ജനറേറ്റർ (Reel Script Generator)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = KeralaGold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Location options
                        Text("സ്ഥലം (Location):", color = Color.LightGray, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("ആലപ്പുഴ കായൽ", "കൊച്ചി മറൈൻ ഡ്രൈവ്", "തിരുവനന്തപുരം എം.ജി റോഡ്").forEach { loc ->
                                Surface(
                                    color = if (customLocation == loc) KsrtcOrange else DarkCard,
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.clickable { customLocation = loc }
                                ) {
                                    Text(
                                        text = loc,
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Vehicle options
                        Text("വാഹനം (Vehicle):", color = Color.LightGray, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("കെ.എസ്.ആർ.ടി.സി ആനവണ്ടി", "റോയൽ എൻഫീൽഡ് ബുള്ളറ്റ്", "കേരള ഓട്ടോറിക്ഷ").forEach { veh ->
                                Surface(
                                    color = if (customVehicle == veh) KsrtcYellow else DarkCard,
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.clickable { customVehicle = veh }
                                ) {
                                    Text(
                                        text = veh,
                                        fontSize = 11.sp,
                                        color = if (customVehicle == veh) Color.Black else Color.White,
                                        fontWeight = if (customVehicle == veh) FontWeight.Bold else FontWeight.Normal,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Punchline options
                        Text("ഡയലോഗ് (Dialogue Punchline):", color = Color.LightGray, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("എടാ മോനേ...!", "പോളണ്ടിനെ പറ്റി മിണ്ടരുത്!", "സാധനം കയ്യിലുണ്ടോ?").forEach { p ->
                                Surface(
                                    color = if (customPunchline == p) GtaGreen else DarkCard,
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.clickable { customPunchline = p }
                                ) {
                                    Text(
                                        text = p,
                                        fontSize = 11.sp,
                                        color = if (customPunchline == p) Color.Black else Color.White,
                                        fontWeight = if (customPunchline == p) FontWeight.Bold else FontWeight.Normal,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                selectedScript = generateCustomScript()
                                showCustomGenerator = false
                                Toast.makeText(context, "പുതിയ 10s സ്ക്രിപ്റ്റ് റെഡിയായി!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("generate_custom_script_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = GtaGreen),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Videocam, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "10s സ്ക്രിപ്റ്റ് ഉണ്ടാക്കുക (Generate Script)",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // Active Script Card with Timeline Breakdown
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("active_script_detail_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E232E)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF333E50))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selectedScript.titleMalayalam,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = selectedScript.titleEnglish,
                                fontSize = 12.sp,
                                color = Color.LightGray
                            )
                        }

                        Surface(
                            color = KsrtcOrange,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = selectedScript.totalDuration,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "🔥 Hook: \"${selectedScript.hook}\"",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = KeralaGold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Second-by-Second Shot List
                    selectedScript.scenes.forEach { scene ->
                        ScriptSceneItem(scene = scene)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Caption & Hashtags
                    Surface(
                        color = Color(0xFF14171F),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Instagram / YouTube Caption:",
                                color = KsrtcYellow,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = selectedScript.captionTagline,
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Copy Script Button
                    Button(
                        onClick = {
                            val fullScriptText = buildString {
                                appendLine("🎬 GTA V × Kerala — 10-Second Reel Script")
                                appendLine("Title: ${selectedScript.titleMalayalam} (${selectedScript.titleEnglish})")
                                appendLine("Duration: 10 Seconds | Hook: ${selectedScript.hook}")
                                appendLine("----------------------------------------")
                                selectedScript.scenes.forEach { s ->
                                    appendLine("[${s.timeRange}]")
                                    appendLine("Visual: ${s.visual}")
                                    appendLine("Audio BGM: ${s.audioCue}")
                                    appendLine("Malayalam Dialogue: ${s.dialogueMalayalam}")
                                    appendLine("English: ${s.dialogueEnglish}")
                                    appendLine()
                                }
                                appendLine("Caption & Tags: ${selectedScript.captionTagline}")
                            }
                            copyToClipboard(fullScriptText)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("copy_script_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = GtaGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "സ്ക്രിപ്റ്റ് മുഴുവൻ കോപ്പി ചെയ്യുക (Copy Script)",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Script Catalog
        item {
            Text(
                text = "മറ്റ് സ്ക്രിപ്റ്റുകൾ (Script Library)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(GtaKeralaData.videoScripts) { script ->
            val isCurrent = script.id == selectedScript.id

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedScript = script }
                    .testTag("script_item_${script.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrent) Color(0xFF242A36) else DarkCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isCurrent) KsrtcOrange else Color.Transparent
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = script.titleMalayalam,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = script.category,
                            fontSize = 12.sp,
                            color = KeralaGold
                        )
                    }
                    Text(
                        text = "10s",
                        color = KsrtcYellow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ScriptSceneItem(scene: ScriptScene) {
    Surface(
        color = Color(0xFF141720),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262D3B)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = KsrtcOrange,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = scene.timeRange,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = "🔊 ${scene.audioCue}",
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "📹 ${scene.visual}",
                color = Color.White,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "💬 ${scene.dialogueMalayalam}",
                color = KeralaGold,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "   ${scene.dialogueEnglish}",
                color = Color.LightGray,
                fontSize = 11.sp
            )
        }
    }
}
