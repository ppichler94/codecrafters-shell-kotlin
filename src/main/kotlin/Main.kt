fun main() {
    while (true) {
        print("$ ")
        val input = readln() // Wait for user input
        val parts = input.split(" ")

        when (parts.first()) {
            "exit" -> return
            "echo" -> println(parts.drop(1).joinToString(" "))
            else -> println("$input: command not found")
        }
    }
}
