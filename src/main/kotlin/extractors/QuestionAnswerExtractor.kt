package extractors

import models.WordPair

/**
 * A [WordPairExtractor] for content exported from the [ForgetMeNot](https://github.com/tema6120/ForgetMeNot) app.
 */
object QuestionAnswerExtractor : WordPairExtractor {
    /**
     * Extracts a list of [WordPair] items from the provided [text].
     *
     * The pair is made up of the following format:
     * Q:
     * word to translate
     * A:
     * translated word
     *
     * A _Q_ or _A_ section **must** not contain line breaks between section content.
     * Line breaks are only allowed between contend end and the next section start.
     * Section start is determined by _Q:_ or _A:_.
     */
    override fun extract(text: String): List<WordPair> {
        val regex = Regex("""^Q:\s*\n([^\t\r\n]+?)\s*\nA:\s*\n([^\t\r\n]+?)\s*$""", RegexOption.MULTILINE)
        val matches = regex.findAll(text).toList()
        return matches.map {
            WordPair(it.groups[1]?.value ?: "", it.groups[2]?.value ?: "")
        }.toSet().toList()
    }
}