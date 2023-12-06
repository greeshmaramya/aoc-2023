fun main() {
    fun List<String>.races() = this.map { str ->
        val regex = Regex("([0-9]+)")
        regex.findAll(str).map { it.value.toInt() }.toList()
    }

    fun List<String>.races2() = this.map { str ->
        val tds = str.split(": ").last().replace(" ", "").toLong()
        tds
    }

    fun part1(input: List<String>): Int {
        val races = input.races()
        val (t, d) = Pair(races.first(), races.last())
        var m = 1
        for (i in t.indices) {
            var c = 0
            for (v in 1 until t[i]) {
                if (v * (t[i] - v) > d[i]) {
                    c++
                }
            }
            m *= c
        }
        return m
    }

    fun part2(input: List<String>): Long {
        val races = input.races2()
        val (t, d) = Pair(races.first(), races.last())
        var c: Long = 0
        for (v in 1 until t) {
            if (v * (t - v) > d) {
                c++
            }
        }
        return c
    }
    val input = readInput("Day06")
    val testInput = readInput("Day06_test")
    part1(testInput).println()
    part1(input).println()
    part2(testInput).println()
    part2(input).println()
}