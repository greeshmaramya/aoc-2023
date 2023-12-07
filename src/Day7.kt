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

    fun part1(input: List<String>): Int {
        val cards = input.map {
            Pair(
                it.split(" ").first(),
                it.split(" ").last()
            )
        }
        val hands = cards.groupBy { it.first.value() }.toSortedMap()
        val sorted = hands.values.map {
            it.sortedBy { (hand, _) ->
                hand.replace('A', 'E')
                    .replace('T', 'A')
                    .replace('J', 'B')
                    .replace('Q', 'C')
                    .replace('K', 'D').toInt(radix = 16) // convert as a hex string to sort the strings
            }
        }.flatten()

        var winnings = 0
        sorted.forEachIndexed { i, (_, bid) ->
            winnings += ((i + 1) * bid.toInt())
        }
        return winnings
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
                h.remove('J')
                if (j > 0) {
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

    fun part2(input: List<String>): Int {
        val cards = input.map {
            Pair(
                it.split(" ").first(),
                it.split(" ").last()
            )
        }
        val hands = cards.groupBy { it.first.valueWithJoker() }.toSortedMap()
        val sorted = hands.values.map {
            it.sortedBy { (hand, _) ->
                hand.replace('A', 'E')
                    .replace('T', 'A')
                    .replace('Q', 'C')
                    .replace('K', 'D').replace('J', '1').toInt(radix = 16) // J is least imp and use hex to sort
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

    part1(testInput).println()
    part1(input).println()
    part2(testInput).println()
    part2(input).println()
}