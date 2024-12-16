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
        } else {
            println("${arguments.first()}: not found")
        }
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

fun main() {
    while (true) {
        print("$ ")
        val input = readln() // Wait for user input
        val parts = input.split(" ")

        val command = CommandNames.entries.find { it.value == parts.first() }?.command

        if (command != null) {
            command(parts.drop(1))
        } else {
            println("$input: command not found")
        }
    }
}
