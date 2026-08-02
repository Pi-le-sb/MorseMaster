package com.morsemaster.app.data

data class Lesson(
    val id: Int,
    val title: String,
    val description: String,
    val exercises: List<Exercise>
)

data class Exercise(
    val question: String,
    val questionType: QuestionType,
    val correctAnswer: String,
    val options: List<String>
)

enum class QuestionType {
    LETTER_TO_MORSE,
    MORSE_TO_LETTER
}

object LessonRepository {
    val lessons: List<Lesson> = listOf(
        Lesson(
            id = 0,
            title = "Lektion 1: E, T, A, N",
            description = "Die häufigsten Buchstaben im Morse-Code",
            exercises = listOf(
                Exercise("E", QuestionType.LETTER_TO_MORSE, ".",    listOf(".", "-", "..", ".-")),
                Exercise("T", QuestionType.LETTER_TO_MORSE, "-",    listOf("-", ".", "--", "..-")),
                Exercise("A", QuestionType.LETTER_TO_MORSE, ".-",   listOf(".-", "-.", "..", "--")),
                Exercise("N", QuestionType.LETTER_TO_MORSE, "-.",   listOf("-.", ".-", "--", "..")),
                Exercise(".",  QuestionType.MORSE_TO_LETTER, "E",   listOf("E", "T", "I", "A")),
                Exercise("-",  QuestionType.MORSE_TO_LETTER, "T",   listOf("T", "E", "N", "M")),
                Exercise(".-", QuestionType.MORSE_TO_LETTER, "A",   listOf("A", "N", "W", "K")),
                Exercise("-.", QuestionType.MORSE_TO_LETTER, "N",   listOf("N", "A", "D", "G"))
            )
        ),
        Lesson(
            id = 1,
            title = "Lektion 2: I, M, S, O",
            description = "Mehr Grundbuchstaben",
            exercises = listOf(
                Exercise("I", QuestionType.LETTER_TO_MORSE, "..",   listOf("..", ".", "...", "..-")),
                Exercise("M", QuestionType.LETTER_TO_MORSE, "--",   listOf("--", "-", ".-", "---")),
                Exercise("S", QuestionType.LETTER_TO_MORSE, "...",  listOf("...", "..", "....", "..-")),
                Exercise("O", QuestionType.LETTER_TO_MORSE, "---",  listOf("---", "--", "....", "-.-")),
                Exercise("..",  QuestionType.MORSE_TO_LETTER, "I",  listOf("I", "E", "S", "U")),
                Exercise("--",  QuestionType.MORSE_TO_LETTER, "M",  listOf("M", "T", "G", "O")),
                Exercise("...", QuestionType.MORSE_TO_LETTER, "S",  listOf("S", "I", "H", "V")),
                Exercise("---", QuestionType.MORSE_TO_LETTER, "O",  listOf("O", "M", "G", "W"))
            )
        ),
        Lesson(
            id = 2,
            title = "Lektion 3: SOS & Wörter",
            description = "Erste Wörter und das SOS-Signal",
            exercises = listOf(
                Exercise("SOS", QuestionType.LETTER_TO_MORSE, "... --- ...", listOf("... --- ...", "..- --- ..-", "... --. ...", "... .- ...")),
                Exercise("HI",  QuestionType.LETTER_TO_MORSE, ".... ..",     listOf(".... ..", ".... .", "... ..", ".... ...")),
                Exercise("... --- ...", QuestionType.MORSE_TO_LETTER, "SOS", listOf("SOS", "OSO", "SSO", "OOS")),
                Exercise(".... ..",     QuestionType.MORSE_TO_LETTER, "HI",  listOf("HI", "HE", "HS", "HT"))
            )
        ),
        Lesson(
            id = 3,
            title = "Lektion 4: D, G, K, R",
            description = "Häufige Konsonanten",
            exercises = listOf(
                Exercise("D", QuestionType.LETTER_TO_MORSE, "-..",  listOf("-..", "-.-", "--.", "---")),
                Exercise("G", QuestionType.LETTER_TO_MORSE, "--.",  listOf("--.", "--", "-.-", "-.")),
                Exercise("K", QuestionType.LETTER_TO_MORSE, "-.-",  listOf("-.-", "-..", ".-.", ".--")),
                Exercise("R", QuestionType.LETTER_TO_MORSE, ".-.",  listOf(".-.", ".-", ".--", "-.-")),
                Exercise("-..", QuestionType.MORSE_TO_LETTER, "D",  listOf("D", "B", "X", "Z")),
                Exercise("--.", QuestionType.MORSE_TO_LETTER, "G",  listOf("G", "M", "Q", "Z")),
                Exercise("-.-", QuestionType.MORSE_TO_LETTER, "K",  listOf("K", "C", "R", "Y")),
                Exercise(".-.", QuestionType.MORSE_TO_LETTER, "R",  listOf("R", "A", "L", "K"))
            )
        ),
        Lesson(
            id = 4,
            title = "Lektion 5: H, U, L, F",
            description = "Vier neue Buchstaben",
            exercises = listOf(
                Exercise("H", QuestionType.LETTER_TO_MORSE, "....", listOf("....", "...", "..-.", "..-.")),
                Exercise("U", QuestionType.LETTER_TO_MORSE, "..-",  listOf("..-", "..", "...", ".-")),
                Exercise("L", QuestionType.LETTER_TO_MORSE, ".-..", listOf(".-..", ".-.", ".--", ".-")),
                Exercise("F", QuestionType.LETTER_TO_MORSE, "..-.", listOf("..-.", "..-", "...", ".-")),
                Exercise("....", QuestionType.MORSE_TO_LETTER, "H", listOf("H", "S", "B", "4")),
                Exercise("..-",  QuestionType.MORSE_TO_LETTER, "U", listOf("U", "I", "V", "F")),
                Exercise(".-..", QuestionType.MORSE_TO_LETTER, "L", listOf("L", "R", "P", "F")),
                Exercise("..-.", QuestionType.MORSE_TO_LETTER, "F", listOf("F", "U", "L", "H"))
            )
        ),
        Lesson(
            id = 5,
            title = "Lektion 6: B, C, P, Q",
            description = "Weniger häufige Buchstaben",
            exercises = listOf(
                Exercise("B", QuestionType.LETTER_TO_MORSE, "-...", listOf("-...", "-..", "--.", "-.-")),
                Exercise("C", QuestionType.LETTER_TO_MORSE, "-.-.", listOf("-.-.", "-.-", "-...", "-.")),
                Exercise("P", QuestionType.LETTER_TO_MORSE, ".--.", listOf(".--.", ".--", ".-", ".-.")),
                Exercise("Q", QuestionType.LETTER_TO_MORSE, "--.-", listOf("--.-", "--.", "-.--", "---")),
                Exercise("-...", QuestionType.MORSE_TO_LETTER, "B", listOf("B", "D", "6", "Z")),
                Exercise("-.-.", QuestionType.MORSE_TO_LETTER, "C", listOf("C", "K", "Y", "G")),
                Exercise(".--.", QuestionType.MORSE_TO_LETTER, "P", listOf("P", "W", "L", "R")),
                Exercise("--.-", QuestionType.MORSE_TO_LETTER, "Q", listOf("Q", "G", "Y", "Z"))
            )
        ),
        Lesson(
            id = 6,
            title = "Lektion 7: V, W, X, Y, Z",
            description = "Die letzten Buchstaben",
            exercises = listOf(
                Exercise("V", QuestionType.LETTER_TO_MORSE, "...-", listOf("...-", "...", "..-", "....")),
                Exercise("W", QuestionType.LETTER_TO_MORSE, ".--",  listOf(".--", ".-", ".--.", ".-.")),
                Exercise("X", QuestionType.LETTER_TO_MORSE, "-..-", listOf("-..-", "-...", "-..", "-.-")),
                Exercise("Y", QuestionType.LETTER_TO_MORSE, "-.--", listOf("-.--", "--.-", "-.-", "--")),
                Exercise("Z", QuestionType.LETTER_TO_MORSE, "--..", listOf("--..", "--.", "---", "-...")),
                Exercise("...-", QuestionType.MORSE_TO_LETTER, "V", listOf("V", "U", "4", "F")),
                Exercise(".--",  QuestionType.MORSE_TO_LETTER, "W", listOf("W", "P", "J", "A")),
                Exercise("-..-", QuestionType.MORSE_TO_LETTER, "X", listOf("X", "D", "B", "C")),
                Exercise("-.--", QuestionType.MORSE_TO_LETTER, "Y", listOf("Y", "Q", "C", "K")),
                Exercise("--..", QuestionType.MORSE_TO_LETTER, "Z", listOf("Z", "G", "B", "7"))
            )
        ),
        Lesson(
            id = 7,
            title = "Lektion 8: Zahlen 0–9",
            description = "Alle Ziffern im Morse-Code",
            exercises = listOf(
                Exercise("1", QuestionType.LETTER_TO_MORSE, ".----", listOf(".----", "..---", ".---", "-----")),
                Exercise("5", QuestionType.LETTER_TO_MORSE, ".....", listOf(".....", "....", "....-", "-....")),
                Exercise("0", QuestionType.LETTER_TO_MORSE, "-----", listOf("-----", "----.", "---", ".----")),
                Exercise(".----", QuestionType.MORSE_TO_LETTER, "1", listOf("1", "J", "2", "A")),
                Exercise(".....", QuestionType.MORSE_TO_LETTER, "5", listOf("5", "H", "4", "6")),
                Exercise("-----", QuestionType.MORSE_TO_LETTER, "0", listOf("0", "O", "9", "T")),
                Exercise("..---", QuestionType.MORSE_TO_LETTER, "2", listOf("2", "1", "Z", "U")),
                Exercise("-....", QuestionType.MORSE_TO_LETTER, "6", listOf("6", "B", "5", "7"))
            )
        )
    )
}
