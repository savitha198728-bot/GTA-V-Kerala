package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.GtaGreen
import com.example.ui.theme.KeralaGold
import com.example.ui.theme.KsrtcOrange
import com.example.ui.theme.PoliceBlue

data class Vehicle(
    val id: String,
    val nameMalayalam: String,
    val nameEnglish: String,
    val subtitle: String,
    val topSpeed: Int,      // km/h
    val armor: Int,         // 1-100
    val agility: Int,       // 1-100
    val swagLevel: Int,     // 1-100
    val description: String,
    val specialAbility: String,
    val themeColor: Color,
    val iconEmoji: String
)

data class Mission(
    val id: String,
    val titleMalayalam: String,
    val titleEnglish: String,
    val location: String,
    val rewardCash: String,
    val difficulty: String,
    val wantedStars: Int,
    val briefingMalayalam: String,
    val briefingEnglish: String,
    val objectives: List<String>,
    val villainOrTarget: String,
    val recommendedVehicle: String
)

data class CharacterProfile(
    val id: String,
    val nameMalayalam: String,
    val nameEnglish: String,
    val role: String,
    val outfit: String,
    val quote: String,
    val bio: String,
    val specialSkill: String
)

data class ScriptScene(
    val timeRange: String,
    val visual: String,
    val audioCue: String,
    val dialogueMalayalam: String,
    val dialogueEnglish: String
)

data class VideoScript(
    val id: String,
    val titleMalayalam: String,
    val titleEnglish: String,
    val category: String,
    val totalDuration: String = "10 Seconds",
    val hook: String,
    val scenes: List<ScriptScene>,
    val captionTagline: String
)

data class ImagePrompt(
    val id: String,
    val title: String,
    val category: String,
    val fullPrompt: String,
    val aspect: String = "16:9",
    val styleNotes: String
)

data class KeralaDialogue(
    val id: String,
    val malayalamText: String,
    val englishPhonetic: String,
    val translation: String,
    val speaker: String,
    val category: String
)

object GtaKeralaData {
    val vehicles = listOf(
        Vehicle(
            id = "ksrtc",
            nameMalayalam = "കെ.എസ്.ആർ.ടി.സി ആനവണ്ടി",
            nameEnglish = "KSRTC Aana Vandi (Fast Passenger)",
            subtitle = "റോട്ടിലെ സുൽത്താൻ • The Unstoppable Battering Ram",
            topSpeed = 110,
            armor = 98,
            agility = 42,
            swagLevel = 99,
            description = "കേരളത്തിലെ റോഡുകൾ അടക്കിവാഴുന്ന ചുവപ്പും മഞ്ഞയും നിറമുള്ള ഇതിഹാസം. എതിരെ വരുന്ന ഏതൊരു വണ്ടിയും ഭയന്ന് സൈഡ് കൊടുക്കും!",
            specialAbility = "സൂപ്പർ എയർ ഹോൺ (എതിരെ വരുന്ന വണ്ടികൾ വഴിമാറും)",
            themeColor = KsrtcOrange,
            iconEmoji = "🚌"
        ),
        Vehicle(
            id = "bullet",
            nameMalayalam = "റോയൽ എൻഫീൽഡ് ബുള്ളറ്റ് 350",
            nameEnglish = "Royal Enfield Bullet (Thug Edition)",
            subtitle = "ഡഗ്ഗ് ഡഗ്ഗ് ശബ്ദം • The Heavy Iron Thug",
            topSpeed = 130,
            armor = 68,
            agility = 74,
            swagLevel = 100,
            description = "മുണ്ടുമടക്കിക്കുത്തി റേബാൻ കണ്ണടയും വെച്ച് പോകുമ്പോൾ റോഡിൽ നോക്കാത്തവരായി ആരുമില്ല. ശബ്ദം കേട്ടാൽ പോലീസ് വരെ ബഹുമാനിക്കും.",
            specialAbility = "ഡഗ്ഗ് ഡഗ്ഗ് ബൂസ്റ്റ് (സ്പീഡ് +30% & 100% സ്വാഗ്)",
            themeColor = KeralaGold,
            iconEmoji = "🏍️"
        ),
        Vehicle(
            id = "auto",
            nameMalayalam = "കേരള ഓട്ടോറിക്ഷ (2-സ്ട്രോക്ക്)",
            nameEnglish = "Kerala Auto Rickshaw",
            subtitle = "മീറ്റർ ഇടാത്ത ചേട്ടൻ • Shortcut King",
            topSpeed = 95,
            armor = 45,
            agility = 96,
            swagLevel = 92,
            description = "തിരുവനന്തപുരത്തെ ഇടവഴികളിലും കൊച്ചിയിലെ ട്രാഫിക്കിലും കണ്ണടച്ച് തുറക്കുന്ന സമയം കൊണ്ട് എസ്കേപ്പാകുന്ന അത്ഭുത വാഹനം!",
            specialAbility = "ഇടവഴി ഡ്രിഫ്റ്റ് (പോലീസ് ജിപ്പിന് എത്തിപ്പിടിക്കാൻ പറ്റില്ല)",
            themeColor = KsrtcOrange,
            iconEmoji = "🛺"
        ),
        Vehicle(
            id = "activa",
            nameMalayalam = "ഹോണ്ട ആക്റ്റീവ (നാട്ടു സ്കൂട്ടർ)",
            nameEnglish = "Honda Activa (Local Stealth)",
            subtitle = "പച്ചക്കറി സഞ്ചി സഹിതം • The Silent Undercover",
            topSpeed = 85,
            armor = 35,
            agility = 88,
            swagLevel = 70,
            description = "മുന്നിൽ പച്ചക്കറി സഞ്ചിയും പിന്നിൽ അളിയനും. ഏത് ചെക്കിംഗ് പോയിന്റിലും സംശയമില്ലാതെ കടന്നുപോകാം.",
            specialAbility = "പോലീസ് ചെക്കിംഗ് ബ്ലൈൻഡ് സ്പോട്ട് (0 വാണ്ടഡ് ലെവൽ ഡിറ്റക്ഷൻ)",
            themeColor = GtaGreen,
            iconEmoji = "🛵"
        )
    )

    val characters = listOf(
        CharacterProfile(
            id = "dasappan",
            nameMalayalam = "ദാസപ്പൻ (Dasappan)",
            nameEnglish = "Dasappan 'The Mundu Boss'",
            role = "Protagonist / Mastermind",
            outfit = "കസവ് മുണ്ട്, കറുത്ത ഷർട്ട്, കട്ടിമീശ, റേബാൻ ഏവിയേറ്റർ",
            quote = "\"എടാ മോനേ... ഈ കളി നമ്മൾ തുടങ്ങി വെച്ചതല്ല, പക്ഷേ അവസാനിപ്പിക്കുന്നത് നമ്മളായിരിക്കും!\"",
            bio = "ആലപ്പുഴ കായലിൽ നിന്ന് കൊച്ചി തുറമുഖം വരെ വ്യാപിച്ചു കിടക്കുന്ന നാടൻ മാഫിയ തലവൻ. ചായയും പഴംപൊരിയും കിട്ടിയാൽ ഏത് മിഷനും റെഡി.",
            specialSkill = "മുണ്ട് മടക്കിക്കുത്ത് (+50% Melee Power & Bulletproof)"
        ),
        CharacterProfile(
            id = "vijayan",
            nameMalayalam = "വിജയൻ (Vijayan)",
            nameEnglish = "Vijayan 'The Techie Don'",
            role = "Hacker & Getaway Driver",
            outfit = "ജീൻസും കുർത്തയും, തോൾസഞ്ചി, ലാപ്‌ടോപ്പ്",
            quote = "\"സാധനം കയ്യിലുണ്ടോ ദാസാ? ബാക്കി ഞാൻ കൊച്ചി ട്രാഫിക് സിഗ്നലിൽ നോക്കിക്കോളാം.\"",
            bio = "ഇൻഫോപാർക്കിലെ ഐടി ജോലി മടുത്ത് ദാസപ്പന്റെ കൂടെ കൂടിയതാണ്. കൊച്ചിയിലെ സിഗ്നലുകൾ മുഴുവൻ മൊബൈൽ വഴി ഹാക്ക് ചെയ്യും.",
            specialSkill = "സിഗ്നൽ ജാമർ (Kerala Police GPS തടസ്സപ്പെടുത്തും)"
        ),
        CharacterProfile(
            id = "kunjumon_si",
            nameMalayalam = "എസ്.ഐ കുഞ്ഞുമോൻ (SI Kunjumon)",
            nameEnglish = "Sub-Inspector Kunjumon",
            role = "Kerala Police Flying Squad Chief",
            outfit = "കേരള പോലീസ് കാക്കി യൂണിഫോം, വാക്കി-ടോക്കി, ലാത്തി",
            quote = "\"വണ്ടി ഒതുക്കെടാ സൈഡിലേക്ക്! ഹെൽമെറ്റ് എവിടെ? പെറ്റി അടിച്ചിട്ടേ പോകുന്നുള്ളൂ!\"",
            bio = "നാട്ടിലെ ഒരു കുറ്റവാളിയെയും വെറുതെ വിടാത്ത വിരട്ടൽ വീരൻ. ദാസപ്പന്റെ KSRTC ചേസ് തടയാൻ ഫ്ളൈയിങ് സ്ക്വാഡുമായി റോഡിലിറങ്ങിയിട്ടുണ്ട്.",
            specialSkill = "ലാത്തി ചാർജ്ജ് & 5-Star Wanted Escalation"
        )
    )

    val missions = listOf(
        Mission(
            id = "m1",
            titleMalayalam = "ഹൗസ്ബോട്ട് ഹൈസ്റ്റ് (ആലപ്പുഴ)",
            titleEnglish = "The Houseboat Heist (Alappuzha)",
            location = "ആലപ്പുഴ പുന്നമടക്കായൽ & വേമ്പനാട്",
            rewardCash = "₹ 5,00,000 + കരിമീൻ പൊള്ളിച്ചത്",
            difficulty = "Medium",
            wantedStars = 3,
            briefingMalayalam = "നെഹ്റു ട്രോഫി വള്ളംകളി നടക്കുന്നതിനിടയിൽ, പ്രമുഖ സ്വർണ്ണ വ്യാപാരിയുടെ ഹൗസ്ബോട്ടിൽ കടന്നു കയറി സ്യൂട്ട്കേസ് കൈക്കലാക്കുക. സ്പീഡ് ബോട്ടിൽ കായൽ കടന്ന് ദാസപ്പന്റെ ബുള്ളറ്റിൽ രക്ഷപ്പെടുക!",
            briefingEnglish = "Infiltrate the VIP luxury houseboat during the Nehru Trophy Boat Race. Snatch the gold briefcase and escape through backwaters before Kerala Police patrol boats arrive.",
            objectives = listOf(
                "പുന്നമട ജെട്ടിയിൽ എത്തുക",
                "ഹൗസ്ബോട്ടിൽ നിന്ന് സ്യൂട്ട്കേസ് മോഷ്ടിക്കുക",
                "പോലീസ് സ്പീഡ് ബോട്ടുകളെ വെട്ടിക്കുക",
                "തീരത്ത് കാത്തുനിൽക്കുന്ന ബുള്ളറ്റിൽ കയറി തട്ടുകടയിലേക്ക് പായുക"
            ),
            villainOrTarget = "കോസ്റ്റൽ പോലീസ് സ്ക്വാഡ്",
            recommendedVehicle = "റോയൽ എൻഫീൽഡ് ബുള്ളറ്റ്"
        ),
        Mission(
            id = "m2",
            titleMalayalam = "മറൈൻ ഡ്രൈവ് സ്പീഡ് ചേസ് (കൊച്ചി)",
            titleEnglish = "Marine Drive Midnight Rush (Kochi)",
            location = "കൊച്ചി മറൈൻ ഡ്രൈവ്, ബ്രോഡ്‌വേ & ഫോർട്ട് കൊച്ചി",
            rewardCash = "₹ 8,50,000 + കുലുക്കി സർബത്ത്",
            difficulty = "Hard",
            wantedStars = 4,
            briefingMalayalam = "ബ്രോഡ്‌വേയിലെ രഹസ്യ ഇടപാടിൽ നിന്ന് രക്ഷപ്പെടാൻ ഓട്ടോറിക്ഷയിൽ കയറുക. കേരള പോലീസിന്റെ ഫ്ളൈയിങ് സ്ക്വാഡ് ജിപ്പുകൾ നാലുപാടും നിന്നും വളയും. ഇടവഴികളിലൂടെ ഡ്രിഫ്റ്റ് ചെയ്ത് മറൈൻ ഡ്രൈവിലെത്തുക!",
            briefingEnglish = "Evade the Flying Squad jeeps through the narrow alleys of Kochi Broadway in a souped-up 2-stroke Auto Rickshaw.",
            objectives = listOf(
                "ബ്രോഡ്‌വേയിൽ നിന്ന് സാധനം എടുക്കുക",
                "പോലീസ് ബാരിക്കേഡുകൾ വെട്ടിച്ചു കടക്കുക",
                "ഗോശ്രീ പാലത്തിൽ വെച്ച് മാസ്സ് ഓവർടേക്ക് നടത്തുക",
                "ഫോർട്ട് കൊച്ചി കടപ്പുറത്തെ ഒളിത്താവളത്തിൽ എത്തുക"
            ),
            villainOrTarget = "എസ്.ഐ കുഞ്ഞുമോനും ഫ്ളൈയിങ് സ്ക്വാഡും",
            recommendedVehicle = "കേരള ഓട്ടോറിക്ഷ"
        ),
        Mission(
            id = "m3",
            titleMalayalam = "സെക്രട്ടേറിയറ്റ് ധർണ്ണ ബ്രേക്ക്ത്രൂ (തിരുവനന്തപുരം)",
            titleEnglish = "Secretariat Blockade Breakthrough",
            location = "തിരുവനന്തപുരം എം.ജി റോഡ് & സ്റ്റാച്യു",
            rewardCash = "₹ 12,00,000 + പൊറോട്ടയും ബീഫും",
            difficulty = "Extreme",
            wantedStars = 5,
            briefingMalayalam = "സെക്രട്ടേറിയറ്റിന് മുന്നിലെ സമരം കാരണം റോഡുകൾ മുഴുവൻ ബ്ലോക്ക്! KSRTC സൂപ്പർ ഫാസ്റ്റ് ആനവണ്ടിയുടെ സ്റ്റിയറിംഗ് കൈയിലെടുത്ത് ബാരിക്കേഡുകൾ തകർത്ത് കോവളം ബീച്ചിലേക്ക് പറക്കുക!",
            briefingEnglish = "Drive the unstoppable KSRTC Fast Passenger bus right through heavy police barricades on MG Road Trivandrum and escape towards Kovalam Highway.",
            objectives = listOf(
                "തമ്പാനൂർ KSRTC ഡിപ്പോയിൽ നിന്ന് സൂപ്പർഫാസ്റ്റ് ബസ് എടുക്കുക",
                "സെക്രട്ടേറിയറ്റിന് മുന്നിലെ ബാരിക്കേഡുകൾ ഇടിച്ചു തകർക്കുക",
                "എയർ ഹോൺ അടിച്ച് ട്രാഫിക് ക്ലിയർ ചെയ്യുക",
                "കോവളം തീരത്ത് എത്തുന്നതുവരെ 5-സ്റ്റാർ പോലീസ് ചേസിൽ നിന്ന് രക്ഷപ്പെടുക"
            ),
            villainOrTarget = "സിറ്റി പോലീസ് കമ്മീഷണർ ഫോഴ്സ്",
            recommendedVehicle = "കെ.എസ്.ആർ.ടി.സി ആനവണ്ടി"
        ),
        Mission(
            id = "m4",
            titleMalayalam = "കള്ളുഷാപ്പ് മീൻകറി റഷ്",
            titleEnglish = "Toddy Shop Fish Curry Express",
            location = "കുമരകം നാട്ടുവഴികൾ & ചായക്കടകൾ",
            rewardCash = "₹ 2,50,000 + കരിക്ക് സർബത്ത്",
            difficulty = "Easy",
            wantedStars = 2,
            briefingMalayalam = "കള്ളുഷാപ്പിലെ സ്പെഷ്യൽ കപ്പയും മീൻകറിയും ചൂടാറുന്നതിന് മുൻപ് 3 മിനിറ്റിനുള്ളിൽ ദാസപ്പന്റെ ഒളിത്താവളത്തിൽ എത്തിക്കുക. വഴിയിൽ റോഡിലെ കുഴികളും പശുക്കളും ശ്രദ്ധിക്കുക!",
            briefingEnglish = "Deliver steaming hot Kappa and spicy Fish Curry across Kumarakom backwater roads in under 3 minutes while dodging potholes and wandering cattle.",
            objectives = listOf(
                "ഷാപ്പിൽ നിന്ന് മീൻകറി പാഴ്സൽ വാങ്ങുക",
                "കുഴികളും വളവുകളും വെട്ടിച്ച് സ്കൂട്ടർ പായിക്കുക",
                "ഹെൽമെറ്റ് ചെക്കിംഗ് പാർട്ടിയിൽ പെടാതെ രക്ഷപ്പെടുക",
                "3 മിനിറ്റിൽ താഴെ സമയം കൊണ്ട് ഡെലിവറി പൂർത്തിയാക്കുക"
            ),
            villainOrTarget = "ട്രാഫിക് ചെക്കിംഗ് പാർട്ടി & റോഡിലെ കുഴികൾ",
            recommendedVehicle = "ഹോണ്ട ആക്റ്റീവ"
        )
    )

    val videoScripts = listOf(
        VideoScript(
            id = "script_1",
            titleMalayalam = "KSRTC vs പോലീസ് ജിപ്പ് മാസ്സ് ചേസ്",
            titleEnglish = "KSRTC vs Kerala Police Mass Chase",
            category = "High Action / Reel Viral",
            hook = "ആനവണ്ടി തിരിഞ്ഞാൽ പിന്നെ ആരും വഴിമാറും!",
            captionTagline = "GTA V Kerala Edition 🔥 KSRTC Aana Vandi power in Los Santos style! #GTA5Kerala #KSRTC #MalluGamer",
            scenes = listOf(
                ScriptScene(
                    timeRange = "0:00 - 0:03",
                    visual = "ക്ലോസ്-അപ്പ്: ഡ്രൈവർ മുണ്ട് മടക്കിക്കുത്തി ഗിയർ മാറ്റുന്നു. സൈഡ് ഗ്ലാസ്സിൽ പോലീസ് ജിപ്പിന്റെ ചുവപ്പും നീലയും ലൈറ്റുകൾ മിന്നുന്നു.",
                    audioCue = "KSRTC ഇരമ്പുന്ന ഡീസൽ എൻജിൻ + ഉച്ചത്തിലുള്ള എയർ ഹോൺ (Musical Chime)",
                    dialogueMalayalam = "\"വഴിമാറടാ... ആനവണ്ടി വരുന്നുണ്ട്!\"",
                    dialogueEnglish = "Get out of the way... Aana Vandi is coming!"
                ),
                ScriptScene(
                    timeRange = "0:03 - 0:06",
                    visual = "വൈഡ് ഡ്രോൺ ഷോട്ട്: തെങ്ങുകൾ തിങ്ങിനിറഞ്ഞ കേരളത്തിലെ വീതികുറഞ്ഞ റോഡിൽ KSRTC ബസ് പോലീസ് ബാരിക്കേഡ് ഇടിച്ചു തെറിപ്പിച്ച് പറക്കുന്നു.",
                    audioCue = "ബാരിക്കേഡ് തകരുന്ന ശബ്ദം + തകർപ്പൻ ചേസ് ബിജിഎം (Heavy Drums & Bass)",
                    dialogueMalayalam = "പോലീസ് SI മൈക്കിലൂടെ: \"വണ്ടി ഒതുക്കെടാ സൈഡിലേക്ക്!\"",
                    dialogueEnglish = "Police SI over megaphone: Pull over to the side immediately!"
                ),
                ScriptScene(
                    timeRange = "0:06 - 0:08",
                    visual = "ചായക്കടയിൽ ചായ അടിക്കുന്ന ചേട്ടൻ അത്ഭുതത്തോടെ നോക്കി നിൽക്കുമ്പോൾ ഓവർടേക്ക് ചെയ്ത് പൊടി പറത്തി പായുന്ന ബസ്.",
                    audioCue = "ഗ്ലാസ്സ് വീഴുന്ന ഒച്ച + മാസ്സ് പഞ്ചാബി-മലയാളം ഫ്യൂഷൻ ഡ്രോപ്പ്",
                    dialogueMalayalam = "\"ദാസാ... മീൻകറി താഴെ വീഴാതെ നോക്കിക്കോ!\"",
                    dialogueEnglish = "Dasa... hold that fish curry tight!"
                ),
                ScriptScene(
                    timeRange = "0:08 - 0:10",
                    visual = "സ്ലോ-മോഷൻ: GTA V സ്റ്റൈൽ 'MISSION PASSED' സ്ക്രീൻ പച്ച നിറത്തിൽ തെളിയുന്നു. ₹ 5,00,000 അക്കൗണ്ടിൽ കയറുന്നു.",
                    audioCue = "GTA V ലെവൽ കംപ്ലീറ്റ് സിലൗട്ട് ജിംഗിൾ + എയർ ഹോൺ എൻഡിങ്",
                    dialogueMalayalam = "\"എടാ മോനേ... ഇതാണ് യഥാർത്ഥ മാസ്സ്!\"",
                    dialogueEnglish = "Eda Mone... this is real swag!"
                )
            )
        ),
        VideoScript(
            id = "script_2",
            titleMalayalam = "ചായക്കടയിലെ ഡീൽ & മുണ്ട് മടക്കിക്കുത്ത്",
            titleEnglish = "Tea Shop Deal & Mundu Folding Showdown",
            category = "Comedy / Cinematic",
            hook = "സാധനം കയ്യിലുണ്ടോ ദാസാ?",
            captionTagline = "When GTA V meets Kerala Chayakkada 😂🔥 #GTAKerala #MalayalamComedy #MunduGangster",
            scenes = listOf(
                ScriptScene(
                    timeRange = "0:00 - 0:03",
                    visual = "പഴംപൊരിയും പരിപ്പുവടയും ഗ്ലാസ്സ് അലമാരയിൽ. ദാസപ്പൻ റേബാൻ വെച്ച് കസവ് മുണ്ട് കുലുക്കി ചായക്കട ബെഞ്ചിലിരിക്കുന്നു.",
                    audioCue = "ചായ അടിക്കുന്ന 'ങാഹ്' ശബ്ദം + സ്ലോ അക്കോസ്റ്റിക് ബിജിഎം",
                    dialogueMalayalam = "\"ഒരു മീറ്റർ ചായയും രണ്ട് ചൂട് പഴംപൊരിയും എടുക്ക് ചേട്ടാ.\"",
                    dialogueEnglish = "One meter tea and two hot banana fritters, chetta."
                ),
                ScriptScene(
                    timeRange = "0:03 - 0:06",
                    visual = "പെട്ടെന്ന് പുറത്ത് പോലീസ് ജീപ്പ് സഡൻ ബ്രേക്ക് ഇട്ടു നിൽക്കുന്നു. കുഞ്ഞുമോൻ SI ലാത്തിയുമായി ഇറങ്ങുന്നു.",
                    audioCue = "പോലീസ് സൈറൺ വൂപ്-വൂപ് + ഹൃദയമിടിപ്പ് ഡ്രംസ്",
                    dialogueMalayalam = "SI: \"ആഹാ ദാസപ്പനോ! ഇന്ന് നിന്റെ പണി കഴിഞ്ഞു!\"",
                    dialogueEnglish = "SI: Ah Dasappan! Your game ends today!"
                ),
                ScriptScene(
                    timeRange = "0:06 - 0:08",
                    visual = "സ്ലോ മോഷൻ: ദാസപ്പൻ മുണ്ട് ഒറ്റക്കാലിൽ മടക്കിക്കുത്തുന്നു. പുറകിലെ ബുള്ളറ്റിന്റെ ചാവി വായുവിൽ കറക്കി പിടിക്കുന്നു.",
                    audioCue = "ഡഗ്ഗ് ഡഗ്ഗ് എക്സ്ഹോസ്റ്റ് റിവ് + സിഗ്നേച്ചർ മാസ്സ് വിസിൽ",
                    dialogueMalayalam = "\"പോളണ്ടിനെ പറ്റി മിണ്ടരുത് എന്ന് ഞാൻ പലവട്ടം പറഞ്ഞിട്ടുണ്ട് സാറേ!\"",
                    dialogueEnglish = "I've told you many times sir, don't talk about Poland!"
                ),
                ScriptScene(
                    timeRange = "0:08 - 0:10",
                    visual = "ബുള്ളറ്റിൽ വീലി അടിച്ചു പറന്നുയരുന്നു. SI പുകയിൽ ചുമയ്ക്കുന്നു. സ്ക്രീനിൽ GTA 'WASTED' പോലീസിന് നേരെ വരുന്നു!",
                    audioCue = "GTA V Busted സൗണ്ട് ഇഫക്റ്റ് + ചിരി",
                    dialogueMalayalam = "\"പോലീസ് അണ്ണാ... അടുത്ത തവണ കുറച്ചുകൂടി നേരത്തെ വാ!\"",
                    dialogueEnglish = "Police anna... try waking up earlier next time!"
                )
            )
        ),
        VideoScript(
            id = "script_3",
            titleMalayalam = "ഓട്ടോ ചേട്ടന്റെ കൊച്ചി ട്രാഫിക് എസ്കേപ്പ്",
            titleEnglish = "Auto Driver's Broadway Shortcut Escape",
            category = "High Speed / Fun",
            hook = "മീറ്റർ ഇട്ടില്ലെങ്കിലും റൂട്ട് തെറ്റില്ല!",
            captionTagline = "Only Kerala auto drivers can pull off this GTA stunt! 🛺💨 #KochiVibes #AutoDrift #GTA5",
            scenes = listOf(
                ScriptScene(
                    timeRange = "0:00 - 0:03",
                    visual = "കൊച്ചി എം.ജി റോഡിൽ അനങ്ങാൻ പറ്റാത്ത ട്രാഫിക് ജാം. മുന്നിൽ പോലീസ് ജീപ്പുകൾ വഴി തടഞ്ഞിരിക്കുന്നു.",
                    audioCue = "നിരവധി വാഹനങ്ങളുടെ ഹോണുകൾ + റേഡിയോ പാട്ട്",
                    dialogueMalayalam = "\"സാറേ മീറ്റർ ഓടില്ല, പക്ഷേ വണ്ടി പറക്കും!\"",
                    dialogueEnglish = "Sir meter won't run, but this ride will fly!"
                ),
                ScriptScene(
                    timeRange = "0:03 - 0:06",
                    visual = "ഓട്ടോ ഒറ്റ സൈഡിലെ രണ്ട് വീലിൽ പൊങ്ങി രണ്ട് ബസ്സുകൾക്കിടയിലൂടെ തുളച്ചുകയറി ഇടവഴിയിലേക്ക് ചാടുന്നു!",
                    audioCue = "ടയർ സ്ക്രീച്ച് + 2-സ്ട്രോക്ക് ഓട്ടോയുടെ ചീറ്റൽ",
                    dialogueMalayalam = "\"പിടിച്ചോ വിജയണ്ണാ... അടുത്തത് ബോൾഗാട്ടി റൂട്ട്!\"",
                    dialogueEnglish = "Hold on Vijayanna... Bolgatty route next!"
                ),
                ScriptScene(
                    timeRange = "0:06 - 0:08",
                    visual = "വഴിയിലെ തട്ടുകടക്കാരൻ അത്ഭുതത്തോടെ കൈവീശുന്നു. ഓട്ടോയുടെ പിന്നിലെ 'ഗുരുവായൂരപ്പൻ തുണ' സ്റ്റിക്കർ ക്ലോസ്-അപ്പ്.",
                    audioCue = "തട്ടുകടയിലെ പാത്രം മറിയുന്ന ഒച്ച + ചിരി",
                    dialogueMalayalam = "\"ഇതൊക്കെ എന്ത്... നമ്മൾ ഇതിലും വലിയ കുരുക്കഴിച്ചവനാ!\"",
                    dialogueEnglish = "This is nothing... we've escaped bigger jams!"
                ),
                ScriptScene(
                    timeRange = "0:08 - 0:10",
                    visual = "മറൈൻ ഡ്രൈവ് ബോട്ട് ജെട്ടിയിലേക്ക് സുരക്ഷിതമായി ലാൻഡ് ചെയ്യുന്നു. ₹ 10,00,000 ക്യാഷ് റിവാർഡ് ആനിമേഷൻ.",
                    audioCue = "Cash register 'Ka-ching!' + വിസിൽ അടി",
                    dialogueMalayalam = "\"ചില്ലറയില്ല സാറേ, ഗൂഗിൾ പേ മതിയോ?\"",
                    dialogueEnglish = "No change sir, Google Pay okay?"
                )
            )
        )
    )

    val imagePrompts = listOf(
        ImagePrompt(
            id = "p1",
            title = "GTA V Kerala Cover Art (കസവ് മുണ്ട് & ആനവണ്ടി)",
            category = "Game Cover / Poster",
            fullPrompt = "Grand Theft Auto V cover art box design, Kerala Edition. Featuring a rugged Malayali action hero with thick beard and dark Ray-Ban sunglasses wearing a traditional white and gold silk kasavu mundu and black buttoned shirt. In the background, an iconic vintage red and yellow KSRTC 'Aana Vandi' superfast bus drifting on a palm-lined Kerala coastal road beside scenic Alappuzha backwaters and traditional houseboats. Distinctive GTA comic-book cell-shaded art style, bold black outlines, dramatic sunset lighting, high saturation vibrant colors, 8k resolution.",
            styleNotes = "Rockstar Games GTA V official character poster style, heavy ink lines, bold Kerala tropical colors"
        ),
        ImagePrompt(
            id = "p2",
            title = "Kochi Marine Drive Neon Night Police Chase",
            category = "Action Scene",
            fullPrompt = "Intense high-speed police pursuit in Kochi Marine Drive at night, GTA V illustration aesthetic. A modified black Royal Enfield Bullet with bright chrome exhausts escaping between lanes. Behind it, a white Kerala Police Mahindra Bolero flying squad jeep with flashing red and blue strobe lights and siren on. Rain-slicked asphalt reflecting vibrant neon billboards in Malayalam, coconut palms in the misty night breeze, dynamic low-angle camera, comic book graphic novel shading.",
            styleNotes = "Night lighting, neon reflections, rain effect, motion blur on wheels"
        ),
        ImagePrompt(
            id = "p3",
            title = "Alappuzha Houseboat Gangster Meeting",
            category = "Cinematic Story",
            fullPrompt = "GTA V loading screen art of a secret deal on a traditional wooden Kerala Kettuvallam houseboat. A Malayali gangster in stylish sunglasses folding his mundu, leaning on the wooden deck rail while holding a hot glass of local tea and banana fritters. A yellow and black Kerala auto rickshaw parked on the riverbank dock under towering green coconut palm trees, sunny golden hour lighting, GTA V digital painting style.",
            styleNotes = "Golden hour warmth, tropical backwater tranquility blended with gritty crime thriller vibe"
        ),
        ImagePrompt(
            id = "p4",
            title = "Kerala Police Sub-Inspector Character Card",
            category = "Character Art",
            fullPrompt = "GTA V character profile portrait of a strict Kerala Police Sub-Inspector with a thick traditional mustache, khaki police uniform, police beret cap, holding a wooden lathi cane, standing with confident authority in front of a busy Kerala village tea stall ('ചായക്കട') and banana bunch hanging from the roof. Vibrant comic cell shading, GTA V character art style.",
            styleNotes = "Character intro screen style with signature GTA color splash background"
        )
    )

    val dialogues = listOf(
        KeralaDialogue(
            id = "d1",
            malayalamText = "എടാ മോനേ...!",
            englishPhonetic = "Eda Mone...!",
            translation = "Hey son / Dude...! (The ultimate Kerala slang)",
            speaker = "ദാസപ്പൻ",
            category = "Punch"
        ),
        KeralaDialogue(
            id = "d2",
            malayalamText = "പോളണ്ടിനെ പറ്റി ഒരക്ഷരം മിണ്ടരുത്!",
            englishPhonetic = "Poland-ine patti oraksharam mindaruthu!",
            translation = "Don't say a single word about Poland! (Iconic Sandesham reference)",
            speaker = "ദാസപ്പൻ",
            category = "Comedy"
        ),
        KeralaDialogue(
            id = "d3",
            malayalamText = "സാധനം കയ്യിലുണ്ടോ ദാസാ?",
            englishPhonetic = "Sadhanam kayyil undo Dasa?",
            translation = "Do you have the stuff on you, Dasa? (Classic Nadodikkattu)",
            speaker = "വിജയൻ",
            category = "Dialogue"
        ),
        KeralaDialogue(
            id = "d4",
            malayalamText = "വണ്ടി ഒതുക്കെടാ സൈഡിലേക്ക്! പെറ്റി അടിച്ചിട്ട് പോയാൽ മതി!",
            englishPhonetic = "Vandi othukkeda sidelilekku! Petty adichittu poyal mathi!",
            translation = "Pull over to the side! Pay the fine before you leave!",
            speaker = "കുഞ്ഞുമോൻ SI",
            category = "Police"
        ),
        KeralaDialogue(
            id = "d5",
            malayalamText = "റോഡ് നിന്റെ തന്തേടേതാണോടാ?!",
            englishPhonetic = "Road ninte thanthedethaano da?!",
            translation = "Does your father own this road?! (Kerala Road Rage Classic)",
            speaker = "KSRTC ഡ്രൈവർ",
            category = "Horn/Chase"
        ),
        KeralaDialogue(
            id = "d6",
            malayalamText = "ഇതൊക്കെ എന്ത്... നമ്മൾ ഇതിലും വലുത് ചാടിക്കടന്നവനാ!",
            englishPhonetic = "Ithokke enthu... nammal ithilum valuthu chaadikadanavan!",
            translation = "This is nothing... we have leaped across bigger traps!",
            speaker = "ദാസപ്പൻ",
            category = "Swag"
        )
    )
}
