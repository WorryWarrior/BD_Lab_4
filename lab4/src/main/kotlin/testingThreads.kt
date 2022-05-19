import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun connectToDatabase() {
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

abstract class TestCoroutine(val interaction: DatabaseInteraction) : suspend () -> Unit {
    val times = mutableListOf<Long>()
}

class GetAllPerformancesThread(interaction: DatabaseInteraction) : TestCoroutine(interaction) {
    override suspend fun invoke() {
        val performanceId = transaction { Performance.selectAll().map { it[Performance.id] } }

        val start = System.nanoTime()
        interaction.getPerformance(performanceId.random())
        times.add(System.nanoTime() - start)
    }
}
class GetTamersThread(interaction: DatabaseInteraction) : TestCoroutine(interaction) {
    override suspend fun invoke() {
        val tamersId = transaction { Tamer.selectAll().map { it[Tamer.id] } }

        val start = System.nanoTime()
        interaction.getTamer(tamersId.random())
        times.add(System.nanoTime() - start)

    }
}

class GetAnimalPerformersThread(interaction: DatabaseInteraction) : TestCoroutine(interaction) {
    override suspend fun invoke() {
        val animalsId = transaction { Animal_Performer.selectAll().map { it[Animal_Performer.id] } }

        val start = System.nanoTime()
        interaction.getAnimalPerformer(animalsId.random())
        times.add(System.nanoTime() - start)

    }
}

class AddTamerThread(interaction: DatabaseInteraction) : TestCoroutine(interaction) {
    override suspend fun invoke() {

        val start = System.nanoTime()
        interaction.addTamer("Ivan", "Ivanov")
        times.add(System.nanoTime() - start)
    }
}

class ChangeTamerNameThread(interaction: DatabaseInteraction) : TestCoroutine(interaction) {
    override suspend fun invoke() {
        val usersId = transaction { Tamer.selectAll().map { it[Tamer.id] } }

        val start = System.nanoTime()
        interaction.changeTamerName(usersId.random(), "Ivan")
        times.add(System.nanoTime() - start)
    }
}

class ChangeAnimalFamilyThread(interaction: DatabaseInteraction) : TestCoroutine(interaction) {
    override suspend fun invoke() {
        val animalsId = transaction { Animal_Performer.selectAll().map { it[Animal_Performer.id] } }

        val start = System.nanoTime()
        interaction.changeAnimalFamily(animalsId.random(), "Petr")
        times.add(System.nanoTime() - start)
    }
}

class RemoveTamersThread(interaction: DatabaseInteraction) : TestCoroutine(interaction) {
    override suspend fun invoke() {
        val ticketId = transaction { Ticket.selectAll().map { it[Ticket.id] } }

        val start = System.nanoTime()
        interaction.removeTicket(ticketId.random())
        times.add(System.nanoTime() - start)
    }
}
