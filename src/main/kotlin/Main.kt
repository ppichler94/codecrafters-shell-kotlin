fun main() {
    while (true) {
        print("$ ")
        val input = readln() // Wait for user input
        val parts = input.split(" ")

        when (parts.first()) {
            "exit" -> return
            else -> println("$input: command not found")
        }
    }
}
