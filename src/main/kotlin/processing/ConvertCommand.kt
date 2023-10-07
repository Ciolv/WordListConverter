package processing

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.groups.default
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.inputStream
import com.github.ajalt.clikt.parameters.types.outputStream
import extractors.BabbelExtractor
import extractors.DashExtractor
import extractors.IWordPairExtractor
import extractors.QuestionAnswerExtractor
import formatters.QuestionAnswerFormatter
import models.WordPair
import java.io.InputStream
import java.io.OutputStream

const val commandDescription = """
    This program is used to extract word pairs from a source, format those pairs and write the formatted pairs to a destination.
"""

enum class SourceFormat {
    BABBEL,
    QUESTION_ANSWER,
    DASH
}

class ConvertCommand : CliktCommand(name = "wlc", help = commandDescription.trimIndent(), printHelpOnEmptyArgs = true) {
    val verbose by option("-v", "--verbose")
        .help("Enable verbose mode")
        .flag()

    val source by argument()
        .help("Source filename with input data. Use - for stdin.")
        .inputStream()

    val destination by argument()
        .help("Destination filename to write formatted data to. Use - for stdout. File will be overwritten by default.")
        .outputStream(truncateExisting = true)

    val sourceFormat by mutuallyExclusiveOptions(
        option("--babbel")
            .help("Set the Babbel data format as input format. [Default]")
            .flag()
            .convert { SourceFormat.BABBEL },
        option("--dash")
            .help("Set a dash separated list (<lang> - <other lang>) as input format.")
            .flag()
            .convert { SourceFormat.DASH },
        option("--qna")
            .help("Set the Question and Answer data format as input format. Can be used to de-duplicate an existing file.")
            .flag()
            .convert { SourceFormat.QUESTION_ANSWER }
    ).single().default(SourceFormat.BABBEL)

    override fun run() {
        val inputText = getInputText(source)
        val extractorInstance = getExtractor(sourceFormat)
        val languagePairs = extractorInstance.extract(inputText)

        println("Found ${languagePairs.size} pairs.")
        if (languagePairs.isEmpty()) {
            print("Nothing to do.")
            // Exit without overwriting the file.
            return
        }

        if (verbose) {
            printWordPairs(languagePairs)
        }

        val formattedText = QuestionAnswerFormatter.format(languagePairs)
        writeOutput(destination, formattedText)
    }

    /**
     * Creates an instance of the respective [IWordPairExtractor] class.
     */
    private fun getExtractor(format: SourceFormat): IWordPairExtractor {
        return when (format) {
            SourceFormat.BABBEL -> BabbelExtractor()
            SourceFormat.QUESTION_ANSWER -> QuestionAnswerExtractor()
            SourceFormat.DASH -> DashExtractor()
        }
    }

    /**
     * Writes the given [text] to the [destination] stream.
     * The [OutputStream] is closed by this function.
     */
    fun writeOutput(destination: OutputStream, text: String) {
        destination.use { stream ->
            stream.bufferedWriter().use { writer ->
                writer.write(text)
            }
        }
    }

    /**
     * Reads all text in the given source and returns it.
     * The [InputStream] is closed by this function.
     */
    fun getInputText(source: InputStream): String {
        return source.use { stream ->
            stream.bufferedReader().use { reader ->
                reader.readText()
            }
        }
    }

    /**
     * Prints all [WordPair] items int the format "baseLanguage: newLanguage" to the command line.
     */
    fun printWordPairs(languagePairs: List<WordPair>) {
        println(
            languagePairs.joinToString(separator = "\n") {
                "${it.baseLanguage}: ${it.newLanguage}"
            }
        )
    }
}