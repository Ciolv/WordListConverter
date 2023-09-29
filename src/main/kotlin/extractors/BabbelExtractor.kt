package extractors

import models.WordPair

/**
 * A [WordPairExtractor] for content copied from the [babbel.com](https://babbel.com) repetition menu.
 */
object BabbelExtractor : WordPairExtractor {

    /**
     * Extracts a list of [WordPair] items from the provided [text].
     *
     * A word pair is made of two words or sentences separated by a tab.
     * The pair has to be on the same line.
     */
    override fun extract(text: String): List<WordPair> {
        val regex = Regex("^([^\\t\\r\\n]+?)\\t([^\\t\\r\\n]+?)\\s*$", RegexOption.MULTILINE)
        val matches = regex.findAll(text).toList()
        // Babbel starts with the word and the meaning afterward.
        return matches.map {
            WordPair(it.groups[2]?.value ?: "", it.groups[1]?.value ?: "")
        }.toSet().toList()
    }
}