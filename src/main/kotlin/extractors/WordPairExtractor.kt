package extractors

import models.WordPair

interface WordPairExtractor {
    /**
     * Extracts a list of [WordPair] items from the provided [text].
     */
    fun extract(text: String): List<WordPair>
}