package extractors

import models.WordPair
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class DashExtractorTest {
    private val sampleData = """
        können - kunne  
        ein Kohl - en kål  
        ein Kilo - en kilo  
    """.trimIndent()

    @Test
    fun textWordListExtraction() {
        val expectedList = listOf(
            WordPair("können", "kunne"),
            WordPair("ein Kohl", "en kål"),
            WordPair("ein Kilo", "en kilo")
        )
        val extractedList = DashExtractor().extract(sampleData)

        assertContentEquals(expectedList, extractedList)
    }
}