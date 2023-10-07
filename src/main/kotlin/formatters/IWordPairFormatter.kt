package formatters

import models.WordPair

interface IWordPairFormatter {
    fun format(pairs: List<WordPair>): String
}