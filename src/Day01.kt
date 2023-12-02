fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        val digits = mutableListOf<Int>()

        input.forEach {
            it.forEach { c ->
                if (c.isDigit()) {
                    digits.add(c.digitToInt())
                }
            }
            count += digits.first() * 10 + digits.last()
            digits.clear()
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val map = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )
        var count = 0
        input.forEach {inputString ->
            var numString = ""
            for (i in inputString.indices){
                if (inputString[i].isDigit()){
                    numString += inputString[i]
                }else{
                    for (pair in map) {
                        if (inputString.substring(i).startsWith(pair.key)){
                            numString += pair.value
                            break
                        }
                    }
                }
            }
            count += numString.first().digitToInt() * 10 + numString.last().digitToInt()
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
