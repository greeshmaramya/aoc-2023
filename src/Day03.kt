fun main() {
    fun Char.isSpecial() = !this.isDigit() && this != '.'

    // "467..114.." -> [(0, [(467, 0..2), (114, 5..7)])...]
    // number strings and their indices,
    val regex = Regex("([0-9]+)")
    fun List<String>.numberRanges() = this.mapIndexed { strIndex, str ->
        Pair(
            strIndex,
            regex.findAll(str)
                .map { Pair(it.value, it.range) }
                .toList()
        )
    }

    fun part1(input: List<String>): Int {
        fun String.containsSpecialChar(range: IntRange) =
            this.substring(maxOf(0, range.first - 1), minOf(range.last + 2, input.first().length))
                .any { it.isSpecial() }

        return input.numberRanges().sumOf { (strIndex, numbersData) ->
            numbersData.filter { (_, range) ->
                input[strIndex].containsSpecialChar(range) ||
                        input[minOf(input.size - 1, strIndex + 1)].containsSpecialChar(range) ||
                        input[maxOf(0, strIndex - 1)].containsSpecialChar(range)
            }.sumOf { it.first.toInt() }
        }
    }

    fun IntRange.rangeOfSymbol(symbolIndex: Int) = symbolIndex in this.first - 1..this.last + 1

    fun part2(input: List<String>): Int {
        val data = input.numberRanges()
        var count = 0
        input.forEachIndexed { index, s ->
            s.forEachIndexed { index1, c ->
                if (c == '*') {
                    val neighbours = listOf(
                        data[index - 1].second.map { (num, range) ->
                            if (range.rangeOfSymbol(index1)) num else ""
                        },
                        data[index].second.map { (num, range) ->
                            if (range.rangeOfSymbol(index1)) num else ""
                        },
                        data[index + 1].second.map { (num, range) ->
                            if (range.rangeOfSymbol(index1)) num else ""
                        }
                    )
                    neighbours.flatten().filter { it.isNotBlank() }.apply {
                        if (this.size == 2) count += this.first().toInt() * this.last().toInt()
                    }
                }
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)
    part1(input).println()
    part2(input).println()

}
