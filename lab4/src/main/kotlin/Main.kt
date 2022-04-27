fun main() {
    val iterations = 2000

    for (interaction in (listOf(NonCachingInteraction, CachingInteraction))) {
        val testThreads = listOf(
            GetAllPerformancesThread(iterations, interaction),
            GetTamersThread(iterations, interaction),
            GetAnimalPerformersThread(iterations, interaction),
            AddTamerThread(iterations, interaction),
            ChangeTamerNameThread(iterations, interaction),
            ChangeAnimalFamilyThread(iterations, interaction),
            RemoveTamersThread(iterations, interaction)
        )

        testThreads.forEach { it.start() }
        testThreads.forEach { it.join() }

        println(interaction::class.java)
        println("Average")
        for (t in testThreads) {
            println("${t::class.java} - ${(t.times.sum() / t.iterations) / 1000000f}")
        }
        println("Max")
        for (t in testThreads) {
            println("${t::class.java} - ${t.times.maxByOrNull { it }!! / 1000000f}")
        }
        println("Min")
        for (t in testThreads) {
            println("${t::class.java} - ${t.times.minByOrNull { it }!! / 1000000f}")
        }
        println()
        println()
    }
}