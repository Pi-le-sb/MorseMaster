package com.morsemaster.app.data

object MorseCode {
    val alphabet: Map<Char, String> = mapOf(
        'A' to ".-",   'B' to "-...", 'C' to "-.-.", 'D' to "-..",
        'E' to ".",    'F' to "..-.", 'G' to "--.",  'H' to "....",
        'I' to "..",   'J' to ".---", 'K' to "-.-",  'L' to ".-..",
        'M' to "--",   'N' to "-.",   'O' to "---",  'P' to ".--.",
        'Q' to "--.-", 'R' to ".-.",  'S' to "...",  'T' to "-",
        'U' to "..-",  'V' to "...-", 'W' to ".--",  'X' to "-..-",
        'Y' to "-.--", 'Z' to "--..",
        '0' to "-----", '1' to ".----", '2' to "..---",
        '3' to "...--", '4' to "....-", '5' to ".....",
        '6' to "-....", '7' to "--...", '8' to "---..", '9' to "----."
    )

    fun encode(text: String): String =
        text.uppercase().map { char ->
            if (char == ' ') "/"
            else alphabet[char] ?: "?"
        }.joinToString(" ")

    fun decode(morse: String): String =
        morse.split(" / ").joinToString(" ") { word ->
            word.split(" ").joinToString("") { symbol ->
                alphabet.entries.find { it.value == symbol }?.key?.toString() ?: "?"
            }
        }
}
