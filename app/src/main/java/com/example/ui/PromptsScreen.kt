package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GtaKeralaData
import com.example.model.ImagePrompt
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GtaGreen
import com.example.ui.theme.KeralaGold
import com.example.ui.theme.KsrtcOrange
import com.example.ui.theme.KsrtcYellow

@Composable
fun PromptsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedPrompt by remember { mutableStateOf(GtaKeralaData.imagePrompts.first()) }

    // Prompt customizer
    var selectedCharacter by remember { mutableStateOf("Malayali gangster in kasavu mundu and dark shirt with Ray-Ban aviators") }
    var selectedVehicle by remember { mutableStateOf("iconic red and yellow KSRTC 'Aana Vandi' superfast bus") }
    var selectedSetting by remember { mutableStateOf("palm-lined Kerala village road beside Alappuzha backwaters and traditional tea shop ('ചായക്കട')") }

    fun copyPrompt(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("GTA Kerala AI Prompt", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "പ്രോംപ്റ്റ് കോപ്പി ചെയ്തു! (Prompt Copied)", Toast.LENGTH_SHORT).show()
    }

    val customizedGeneratedPrompt = remember(selectedCharacter, selectedVehicle, selectedSetting) {
        "Grand Theft Auto V video game art style, Kerala Edition. Featuring a $selectedCharacter, standing beside a $selectedVehicle on a $selectedSetting. Distinctive GTA comic-book cell-shaded art style, bold black outlines, dramatic tropical golden hour sunlight, vibrant saturated colors, high definition concept art, cinematic Rockstar Games aesthetic --ar 16:9"
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
                        text = "GTA Kerala ഇമേജ് പ്രോംപ്റ്റുകൾ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Surface(
                        color = GtaGreen.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GtaGreen)
                    ) {
                        Text(
                            text = "AI ART STUDIO",
                            color = GtaGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Midjourney, Flux, Stable Diffusion എന്നിവയിൽ GTA V Kerala ചിത്രങ്ങൾ ഉണ്ടാക്കാനുള്ള പ്രോംപ്റ്റുകൾ",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }

        // Custom Prompt Builder Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("prompt_builder_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B202B)),
                border = androidx.compose.foundation.BorderStroke(1.dp, KsrtcOrange)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = KeralaGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ഇമേജ് പ്രോംപ്റ്റ് ബിൽഡർ (Prompt Builder)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Character Selector
                    Text("1. കഥാപാത്രം (Character):", color = Color.LightGray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    listOf(
                        "Malayali gangster in kasavu mundu and dark shirt with Ray-Ban aviators" to "കസവ് മുണ്ട് & റേബാൻ",
                        "Strict Kerala Police Sub-Inspector in khaki uniform with mustache and lathi" to "കേരള പോലീസ് SI",
                        "Street-smart young getaway driver in folded mundu and sunglasses" to "നാടൻ ഡ്രൈവർ"
                    ).forEach { (valStr, label) ->
                        val isSel = selectedCharacter == valStr
                        Surface(
                            color = if (isSel) KsrtcOrange else DarkCard,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .clickable { selectedCharacter = valStr }
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Vehicle Selector
                    Text("2. വാഹനം (Vehicle):", color = Color.LightGray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    listOf(
                        "iconic red and yellow KSRTC 'Aana Vandi' superfast bus" to "KSRTC ആനവണ്ടി",
                        "vintage black Royal Enfield Bullet 350 motorcycle with chrome exhaust" to "തഗ്ഗ് ബുള്ളറ്റ്",
                        "yellow and black Kerala auto rickshaw with funny Malayalam bumper stickers" to "കേരള ഓട്ടോറിക്ഷ"
                    ).forEach { (valStr, label) ->
                        val isSel = selectedVehicle == valStr
                        Surface(
                            color = if (isSel) KsrtcYellow else DarkCard,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .clickable { selectedVehicle = valStr }
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                color = if (isSel) Color.Black else Color.White,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Setting Selector
                    Text("3. ലൊക്കേഷൻ (Location Setting):", color = Color.LightGray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    listOf(
                        "palm-lined Kerala village road beside Alappuzha backwaters and traditional tea shop ('ചായക്കട')" to "ആലപ്പുഴ കായൽ & ചായക്കട",
                        "Kochi Marine Drive night highway with neon billboards in Malayalam" to "കൊച്ചി മറൈൻ ഡ്രൈവ്",
                        "bustling Kerala countryside road with coconut trees and street stalls" to "നാട്ടുമ്പുറത്തെ വഴി"
                    ).forEach { (valStr, label) ->
                        val isSel = selectedSetting == valStr
                        Surface(
                            color = if (isSel) GtaGreen else DarkCard,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .clickable { selectedSetting = valStr }
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                color = if (isSel) Color.Black else Color.White,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Generated Output Box
                    Surface(
                        color = Color(0xFF11141A),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Ready Prompt:",
                                color = KeralaGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = customizedGeneratedPrompt,
                                color = Color.White,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { copyPrompt(customizedGeneratedPrompt) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("copy_custom_prompt_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = KsrtcOrange),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "പ്രോംപ്റ്റ് കോപ്പി ചെയ്യുക (Copy AI Prompt)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Ready-made prompt library
        item {
            Text(
                text = "റെഡിമെയ്ഡ് പ്രോംപ്റ്റുകൾ (Pre-made GTA Kerala Prompts)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(GtaKeralaData.imagePrompts) { prompt ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("preset_prompt_card_${prompt.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E3440))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = prompt.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Surface(
                            color = KsrtcOrange.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = prompt.category,
                                color = KsrtcOrange,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = prompt.styleNotes,
                        fontSize = 12.sp,
                        color = KeralaGold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = Color(0xFF13171F),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = prompt.fullPrompt,
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { copyPrompt(prompt.fullPrompt) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .testTag("copy_prompt_${prompt.id}"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A313E)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = GtaGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "കോപ്പി ചെയ്യുക (Copy Prompt)",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
