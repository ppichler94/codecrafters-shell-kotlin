import java.io.File
import kotlin.system.exitProcess

sealed interface Command {
    operator fun invoke(arguments: List<String>)
}

data object Echo : Command {
    override fun invoke(arguments: List<String>) = println(arguments.joinToString(" "))
}

data object Type : Command {
    override fun invoke(arguments: List<String>) {
        if (arguments.first() in CommandNames.entries.map { it.value }) {
            println("${arguments.first()} is a shell builtin")
            return
        }
        findInPath(arguments.first())?.let { path ->
            println("${arguments.first()} is $path")
            return
        }
        println("${arguments.first()}: not found")
    }
}

data object Exit : Command {
    override fun invoke(arguments: List<String>) = exitProcess(0)
}

enum class CommandNames(
    val value: String,
    val command: Command,
) {
    ECHO("echo", Echo),
    EXIT("exit", Exit),
    TYPE("type", Type),
}

private fun findInPath(name: String): String? {
    for (path in System.getenv("PATH").split(":")) {
        if (File("$path/$name").exists()) {
            return "$path/$name"
        }
    }
    return null
}

private fun runProgram(
    path: String,
    arguments: List<String>,
) {
    val process = ProcessBuilder(listOf(path) + arguments).start()
    process.waitFor()
    print(process.inputStream.bufferedReader().readText())
}

fun main() {
    while (true) {
        print("$ ")
        val input = readln() // Wait for user input
        val parts = input.split(" ")

        val command = CommandNames.entries.find { it.value == parts.first() }?.command

        if (command != null) {
            command(parts.drop(1))
            continue
        }

        val path = findInPath(parts.first())
        if (path != null) {
            runProgram(path, parts.drop(1))
        } else {
            println("$input: command not found")
        }
    }
}
