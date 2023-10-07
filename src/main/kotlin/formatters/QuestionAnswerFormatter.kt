package formatters

import models.WordPair

/**
 * A [IWordPairFormatter] to generate content that can be imported by the [ForgetMeNot](https://github.com/tema6120/ForgetMeNot) app.
 */
object QuestionAnswerFormatter : IWordPairFormatter {
    /**
     * Generates a _Q_ and _A_ text for the given [pairs]
     *
     * The pair is made up of the following format:
     * Q:
     * word to translate
     * A:
     * translated word
     *
     * Every pair is separated by three new lines.
     */
    override fun format(pairs: List<WordPair>): String {
        return buildString {
            for ((baseLanguage, learningLanguage) in pairs) {
                appendLine("Q:")
                appendLine(baseLanguage)
                appendLine("A:")
                appendLine(learningLanguage)
                appendLine("\n\n")
            }
        }.trimEnd()
    }
}