package processing

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import kotlin.test.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConvertCommandTest {
    val sampleInput = """
                %1324958as 9asdf 0405ly ycxfxiijig
                asdf	asdf	asdf
                God morgen!	Guten Morgen!	
                Hei!	Hallo!
                alsdfj asdf asdf oipbvy
                Hva heter hun?	Wie heißt sie?	
                asdvhjkdklsfhj
            """.trimIndent()

    val command = ConvertCommand()
    val inputFile = File.createTempFile("wlc", ".in.txt")
    val outputFile = File.createTempFile("wlc", ".out.txt")

    @BeforeAll
    fun mainSetUp() {
        inputFile.writeText(sampleInput)

        inputFile.setReadOnly()
        inputFile.deleteOnExit()
        outputFile.setWritable(true)
        outputFile.deleteOnExit()
    }

    @Test
    fun getInputText() {
        val readInput = command.getInputText(inputFile.inputStream())
        assertEquals(readInput, sampleInput)
    }

    @Test
    fun writeOutput() {
        command.writeOutput(outputFile.outputStream(), sampleInput)
        val fileContent = outputFile.inputStream().bufferedReader().use { it.readText() }
        assertEquals(fileContent, sampleInput)
    }
}