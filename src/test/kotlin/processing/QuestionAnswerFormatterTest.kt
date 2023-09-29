package processing

import formatters.QuestionAnswerFormatter
import models.WordPair
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class QuestionAnswerFormatterTest {

    private val sampleData = listOf(
        WordPair("Guten Morgen!", "God morgen!"),
        WordPair("Hallo!", "Hei!"),
        WordPair("Wie heißt sie?", "Hva heter hun?")
    )

    @Test
    fun textWordListExtraction() {
        val expectedText = """
            Q:
            Guten Morgen!
            A:
            God morgen!
            
            
            
            Q:
            Hallo!
            A:
            Hei!
            
            
            
            Q:
            Wie heißt sie?
            A:
            Hva heter hun?
        """.trimIndent()
        val formattedText = QuestionAnswerFormatter.format(sampleData)

        assertEquals(expectedText, formattedText)
    }
}