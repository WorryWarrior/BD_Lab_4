import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

//import kotlinx.datetime.*

object NonCachingInteraction : DatabaseInteraction {
    override fun getAllPerformances() =
        transaction { Performance.selectAll().toList() }

    override fun getTamer(id: Long) =
        transaction { Tamer.select { Tamer.id eq id }.toList() }

    override fun getAnimalPerformer(id: Long) =
        transaction { Animal_Performer.select { Animal_Performer.id eq id }.toList() }

    override fun addTamer(_name: String, _surname: String) {
        transaction {
            Tamer.insert {
                it[name] = _name
                it[surname] = _surname
                it[dateOfBirth] = LocalDate.now()
            }
        }
    }

    override fun changeTamerName(tamerId: Long, newTamerName: String) {
        transaction {
            Tamer.update({ Tamer.id eq tamerId }) {
                it[name] = newTamerName
            }
        }
    }

    override fun changeAnimalFamily(animalId: Long, newFamily: String) {
        transaction {
            Animal_Performer.update({ Animal_Performer.id eq animalId }) {
                it[family] = newFamily
            }
        }
    }

    override fun removeTicket(ticketId: Long) {
        transaction {
            Ticket.deleteWhere { Ticket.id eq ticketId }
        }
    }
}