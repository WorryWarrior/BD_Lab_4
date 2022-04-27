import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.concurrent.CyclicBarrier

private fun connectToDatabase() {
    fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/Lab1_1_New"
        config.username = "postgres"
        config.password = "5504"
        config.addDataSourceProperty("cachePrepStmts", "false")

        config.validate()
        return HikariDataSource(config)
    }

    Database.connect(hikari())
}

private val cyclicBarrier = CyclicBarrier(7)

abstract class TestThread(val iterations: Int, val interaction: DatabaseInteraction) : Thread() {
    val times = mutableListOf<Long>()
}


class GetAllPerformancesThread(iterations: Int, interaction: DatabaseInteraction) : TestThread(iterations, interaction) {
    override fun run() {
        connectToDatabase()

        cyclicBarrier.await()

        while (cyclicBarrier.numberWaiting < 4) {
            val start = System.nanoTime()
            interaction.getAllPerformances()
            times.add(System.nanoTime() - start)
        }

        cyclicBarrier.await()
    }
}
class GetTamersThread(iterations: Int, interaction: DatabaseInteraction) : TestThread(iterations, interaction) {
    override fun run() {
        connectToDatabase()

        cyclicBarrier.await()

        val tamersId = transaction { Tamer.selectAll().map { it[Tamer.id] } }

        while (cyclicBarrier.numberWaiting < 4) {
            val start = System.nanoTime()
            interaction.getTamer(tamersId.random())
            times.add(System.nanoTime() - start)
        }

        cyclicBarrier.await()
    }
}

class GetAnimalPerformersThread(iterations: Int, interaction: DatabaseInteraction) : TestThread(iterations, interaction) {
    override fun run() {
        connectToDatabase()

        cyclicBarrier.await()

        val animalsId = transaction { Animal_Performer.selectAll().map { it[Animal_Performer.id] } }

        while (cyclicBarrier.numberWaiting < 4) {
            val start = System.nanoTime()
            interaction.getAnimalPerformer(animalsId.random())
            times.add(System.nanoTime() - start)
        }

        cyclicBarrier.await()
    }
}

class AddTamerThread(iterations: Int, interaction: DatabaseInteraction)
    : TestThread(iterations, interaction) {
    override fun run() {
        connectToDatabase()

        cyclicBarrier.await()

        //val usersId = transaction { UserEntity.selectAll().map { it[UserEntity.id] } }
        //val notesId = transaction { Note.selectAll().map { it[Note.id] } }

        repeat(iterations) {
            val start = System.nanoTime()
            interaction.addTamer("Ivan", "Ivanov"/*usersId.random(), notesId.random()*/)
            times.add(System.nanoTime() - start)
        }

        cyclicBarrier.await()
    }
}

class ChangeTamerNameThread(iterations: Int, interaction: DatabaseInteraction) : TestThread(iterations, interaction) {
    override fun run() {
        connectToDatabase()

        cyclicBarrier.await()

        val usersId = transaction { Tamer.selectAll().map { it[Tamer.id] } }

        repeat(iterations) {
            val start = System.nanoTime()
            interaction.changeTamerName(usersId.random(), "Ivan")
            times.add(System.nanoTime() - start)
        }

        cyclicBarrier.await()
    }
}

class ChangeAnimalFamilyThread(iterations: Int, interaction: DatabaseInteraction)
    : TestThread(iterations, interaction) {
    override fun run() {
        connectToDatabase()

        cyclicBarrier.await()

        val animalsId = transaction { Animal_Performer.selectAll().map { it[Animal_Performer.id] } }

        repeat(iterations) {
            val start = System.nanoTime()
            interaction.changeAnimalFamily(animalsId.random(), "Petr")
            times.add(System.nanoTime() - start)
        }

        cyclicBarrier.await()
    }
}

class RemoveTamersThread(iterations: Int, interaction: DatabaseInteraction) : TestThread(iterations, interaction) {
    override fun run() {
        connectToDatabase()

        cyclicBarrier.await()

        val tamerId = transaction { Tamer.selectAll().map { it[Tamer.id] } }

        repeat(iterations) {
            val start = System.nanoTime()
            interaction.removeTicket(tamerId.random())
            times.add(System.nanoTime() - start)
        }

        cyclicBarrier.await()
    }
}
