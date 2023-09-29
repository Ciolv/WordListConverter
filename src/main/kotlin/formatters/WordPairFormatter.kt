package formatters

import models.WordPair

interface WordPairFormatter {
    fun format(pairs: List<WordPair>): String
}