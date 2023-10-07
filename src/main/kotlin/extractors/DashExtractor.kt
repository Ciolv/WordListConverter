package extractors

import models.WordPair

/**
 * A [IWordPairExtractor] for content simply separated by a -
 */
class DashExtractor : IWordPairExtractor {

    /**
     * Extracts a list of [WordPair] items from the provided [text].
     *
     * A word pair is made of two words or sentences separated by a -.
     */
    override fun extract(text: String): List<WordPair> {
        val regex = Regex("""^([^-\r\n]+?)\s*-\s*([^-\r\n]+?)\s*$""", RegexOption.MULTILINE)
        val matches = regex.findAll(text).toList()
        return matches.map {
            WordPair(it.groups[1]?.value ?: "", it.groups[2]?.value ?: "")
        }.toSet().toList()
    }
}