fun main() {
    val builtins = setOf("echo", "type", "exit")

    while (true) {
        print("$ ")
        val input = readln() // Wait for user input
        val parts = input.split(" ")

        when (parts.first()) {
            "exit" -> return
            "echo" -> println(parts.drop(1).joinToString(" "))
            "type" ->
                if (parts[1] in builtins) {
                    println("${parts[1]} is a shell builtin")
                } else {
                    println("${parts[1]}: not found")
                }
            else -> println("$input: command not found")
        }
    }
}
