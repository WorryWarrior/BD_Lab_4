import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    connectToDatabase()
    val iterations = 4_000

    for (interaction in (listOf(NonCachingInteraction, CachingInteraction))) {
        val coroutines = MutableList( 500) { listOf(
                AddTamerThread(interaction),
                ChangeTamerNameThread(interaction),
                ChangeAnimalFamilyThread(interaction),
                RemoveTamersThread(interaction)
        ).random() }

        repeat(iterations - 500) {
            coroutines.add(listOf(
                    GetAllPerformancesThread(interaction),
                    GetTamersThread(interaction),
                    GetAnimalPerformersThread(interaction),
            ).random())
        }

        coroutines.shuffle()
        val jobs = mutableListOf<Job>()
        coroutines.parallelStream().forEach {
            jobs.add(GlobalScope.launch {
                it.invoke()
            })
        }

        jobs.parallelStream().forEach {
            runBlocking {
                it.join()
            }
        }

        println(interaction::class.java)
        println("Average")

        val grouped = coroutines.groupBy { it::class.java }.toSortedMap(compareBy { it.name })
        for (t in grouped) {
            println("${t.key} - ${(t.value.sumOf { it.times.sum() } / t.value.count()) / 1000000f}")
        }

        println()
        println()
    }
}