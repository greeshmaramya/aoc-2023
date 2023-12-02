fun main() {
    fun List<String>.games() = this.map {
        val (game, colors) = it.split(": ")
        Pair(
            game,
            colors.split("; ").map {
                it.split(", ").map {
                    val (num, color) = it.split(" ")
                    Pair(color, num.toIntOrNull()!!)
                }
            }.flatten()
        )
    }

    fun part1(input: List<String>): Int {
        val colorMap = mapOf("red" to 12, "green" to 13, "blue" to 14)
        var result = 0

        input.games().forEach {
            var c = true
            for (cube in it.second) {
                if (cube.second > colorMap[cube.first]!!) {
                    c = false
                    break
                }
            }
            if (c) {
                val gameNumber = it.first.replace("Game ", "").toIntOrNull()!!
                //println(gameNumber)
                result += gameNumber
            }
        }
        println(result)
        return result
    }

    fun part2(input: List<String>): Int {
        val colorMaxCubeMap = mutableMapOf<String, Int>()
        var result = 0
        input.games().forEach {(game, gameCubes) ->
            for (cube in gameCubes){
                if (cube.second > colorMaxCubeMap.getOrPut(cube.first) { 0 }){
                    colorMaxCubeMap[cube.first] = cube.second
                }
            }
            var power = 1
            colorMaxCubeMap.values.forEach {
                power *= it
            }
            result += power
            colorMaxCubeMap.clear()
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
