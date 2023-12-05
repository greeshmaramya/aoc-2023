import kotlin.math.min

fun main() {
    fun List<String>.maps(): Pair<List<Long>, List<List<Triple<Long, Long, Long>>>> {
        val emptyLinePositions = mutableListOf<Int>()
        forEachIndexed { index, s -> if (s.isBlank()) emptyLinePositions.add(index) }
        val maps = mutableListOf<List<String>>()
        val seeds = this.first().split(": ").last().split(' ').mapNotNull { it.toLongOrNull() }
        for (i in 0..emptyLinePositions.size - 2) {
            maps.add(subList(emptyLinePositions[i] + 2, emptyLinePositions[i + 1]))
        }
        maps.add(subList(emptyLinePositions.last() + 2, size))

        return Pair(
            seeds, maps.map {
                it.map {
                    val nums = it.split(' ')
                    Triple(
                        nums.first().toLong(),
                        nums[1].toLong(),
                        nums.last().toLong()
                    )
                }
            }
        )
    }

    fun findLocation(inputMap: List<List<Triple<Long, Long, Long>>>, seed: Long, n: Int): Long {
        if (n == -1) return seed
        val prev = findLocation(inputMap, seed, n - 1)
        for (i in inputMap[n]) {
            if (prev in i.second until i.second + i.third) {
                return i.first + prev - i.second
            }
        }
        return prev
    }

    fun part1(input: List<String>): Long {
        val (seeds, inputMaps) = input.maps()
        var mini = Long.MAX_VALUE
        seeds.forEach {
            val location = findLocation(inputMaps, it, inputMaps.size - 1)
            mini = min(mini, location)
        }
        return mini
    }

    fun part2(input: List<String>): Long {
        val (seeds, inputMaps) = input.maps()
        var mini = Long.MAX_VALUE
        val seeds2 = seeds.chunked(2).map { Pair(it.first(), it.last()) }
        seeds2.forEach { (seed, range) ->
            for (s in seed until seed + range) {
                val location = findLocation(inputMaps, s, inputMaps.size - 1)
                mini = min(mini, location)
            }
        }
        return mini
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    println()
    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()

}
