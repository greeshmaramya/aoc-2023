fun main() {
    fun String.value(): Int {
        return when (toSet().size) {
            1 -> 7
            2 -> {
                val c = this.count { it == first() }
                if (c == 4 || c == 1) 6 else 5
            }

            3 -> {
                val h = mutableMapOf<Char, Int>()
                for (c in this) {
                    h[c] = h.getOrDefault(c, 0) + 1
                }
                if (h.containsValue(3)) 4 else 3
            }

            4 -> 2
            else -> 1
        }
    }

    fun String.valueWithJoker(): Int {
        return when (toSet().size) {
            1 -> 7
            2 -> if (contains('J')) 7 else {
                val c = this.count { it == first() }
                if (c == 4 || c == 1) 6 else 5
            }

            3 -> {
                val h = mutableMapOf<Char, Int>()
                for (c in this) {
                    h[c] = h.getOrDefault(c, 0) + 1
                }
                val j = h.getOrDefault('J', 0)
                if (j > 0) {
                    h.remove('J')
                    val p = h.values.max() + j + 2
                    p
                } else {
                    if (h.containsValue(3)) 4 else 3
                }
            }

            4 -> if (contains('J')) 4 else 2
            else -> if (contains('J')) 2 else 1
        }
    }

    fun day7(input: List<String>, part1: Boolean): Int {
        val cards = input.map {
            Pair(
                it.split(" ").first(),
                it.split(" ").last()
            )
        }
        val hands = cards.groupBy { if (part1) it.first.value() else it.first.valueWithJoker() }.toSortedMap()
        val sorted = hands.values.map {
            it.sortedBy { (hand, _) ->
                hand.replace('A', 'E')
                    .replace('T', 'A')
                    .replace('Q', 'C')
                    .replace('K', 'D').replace('J', if (part1) 'B' else '1')  // J is the least imp in part 2
                    .toInt(radix = 16) // and use hex to sort
            }
        }.flatten()

        var winnings = 0
        sorted.forEachIndexed { i, p ->
            winnings += ((i + 1) * p.second.toInt())
        }
        return winnings
    }

    val input = readInput("Day07")
    val testInput = readInput("Day07_test")

    day7(testInput, part1 = true).println()
    day7(input, part1 = true).println()
    day7(testInput, part1 = false).println()
    day7(input, part1 = false).println()
}