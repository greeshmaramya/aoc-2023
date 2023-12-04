import kotlin.math.pow

fun main() {
    fun List<String>.cards() = this.map {
        val (win, numbers) = it.split(": ").last().split('|')
        Pair(
            win.split(' ').mapNotNull { winningCards -> winningCards.trim().toIntOrNull() },
            numbers.split(' ').mapNotNull { cards -> cards.trim().toIntOrNull() })
    }

    fun Pair<List<Int>, List<Int>>.totalWinningNumbers() = this.second.filter { this.first.contains(it) }.size


    fun part1(input: List<String>): Int {
        return input.cards().sumOf {
            val total = it.totalWinningNumbers()
            if (total >= 1) 2.0.pow((total - 1).toDouble()) else 0.0
        }.toInt()
    }

    fun part2(input: List<String>): Int {
        val count = mutableMapOf<Int, Int>()
        val data = input.cards()
        data.forEachIndexed { index, cards ->
            val total = cards.totalWinningNumbers()
            count[index] = count.getOrDefault(index, 1)
            for (i in index + 1..index + total) {
                count[i] = count.getOrDefault(i, 1) + count[index]!!
            }
        }
        return count.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    //check(part1(testInput) == 8)

    val input = readInput("Day04")
    part1(testInput).println()
    part1(input).println()
    println()
    part2(testInput).println()
    part2(input).println()
}
