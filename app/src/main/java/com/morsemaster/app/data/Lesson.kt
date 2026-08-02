package com.morsemaster.app.data

data class Lesson(
    val id: Int,
    val title: String,
    val description: String,
    val exercises: List<Exercise>
)

data class Exercise(
    val question: String,       // e.g. "What is the Morse code for 'A'?"
    val questionType: QuestionType,
    val correctAnswer: String,
    val options: List<String>   // for multiple choice
)

enum class QuestionType {
    LETTER_TO_MORSE,   // show letter -> pick morse
    MORSE_TO_LETTER    // show morse -> pick letter
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
        )
    )
}
