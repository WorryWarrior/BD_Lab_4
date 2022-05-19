import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import kotlin.random.Random

//import kotlinx.datetime.*

object NonCachingInteraction : DatabaseInteraction {
    override fun getPerformance(id: Long) =
        transaction { Performance.select { Performance.id eq id }.toList() }

    override fun getTamer(id: Long) =
        transaction { Tamer.select { Tamer.id eq id }.toList() }

    override fun getAnimalPerformer(id: Long) =
        transaction { Animal_Performer.select { Animal_Performer.id eq id }.toList() }

    override fun addTamer(_name: String, _surname: String) {
        transaction {
            Tamer.insert {
                it[name] = generateText()
                it[surname] = generateText()
                it[dateOfBirth] = LocalDate.now()
            }
        }
    }

    override fun changeTamerName(tamerId: Long, newTamerName: String) {
        transaction {
            Tamer.update({ Tamer.id eq tamerId }) {
                it[name] = generateText()
            }
        }
    }

    override fun changeAnimalFamily(animalId: Long, newFamily: String) {
        transaction {
            Animal_Performer.update({ Animal_Performer.id eq animalId }) {
                it[family] = generateText()
            }
        }
    }

    override fun removeTicket(ticketId: Long) {
        transaction {
            Ticket.deleteWhere { Ticket.id eq ticketId }
        }
    }

    fun generateVarchar(len: Int): String {
        val generatedLen = Random.nextInt(len)

        return String(CharArray(generatedLen) { (Random.nextInt(26) + 'a'.code).toChar() })
    }

    fun generateText() = generateVarchar(50)

}