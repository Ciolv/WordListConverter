package processing

import extractors.BabbelExtractor
import models.WordPair
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class BabbelExtractorTest {
    private val sampleData = """
        %1324958as 9asdf 0405ly ycxfxiijig
        asdf	asdf	asdf
        God morgen!	Guten Morgen!	
        Hei!	Hallo!
        alsdfj asdf asdf oipbvy
        Hva heter hun?	Wie heißt sie?	
        asdvhjkdklsfhj
    """.trimIndent()

    @Test
    fun textWordListExtraction() {
        val expectedList = listOf(
            WordPair("Guten Morgen!", "God morgen!"),
            WordPair("Hallo!", "Hei!"),
            WordPair("Wie heißt sie?", "Hva heter hun?")
        )
        val extractedList = BabbelExtractor().extract(sampleData)

        assertContentEquals(expectedList, extractedList)
    }
}