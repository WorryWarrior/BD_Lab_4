import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.timestamp

object Tamer : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", length = 50)
    val surname = varchar("surname", length = 50)
    val dateOfBirth = date("date_of_birth")

    override val primaryKey = PrimaryKey(id)
}

object Performance : Table() {
    val id = long("id").autoIncrement()
    val tamerId = long("tamer_id") references Tamer.id

    override val primaryKey = PrimaryKey(id)
}

object Animal_Performer : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", length = 50)
    val family = varchar("family", length = 50)
    val dateOfBirth = date("date_of_birth")

    override val primaryKey = PrimaryKey(id)
}

object Hall : Table() {
    val id = long("id").autoIncrement()
    val hallName = varchar("hall_name", length = 50)

    override val primaryKey = PrimaryKey(id)
}

object Cashbox : Table() {
    val id = long("id").autoIncrement()
    val isAutomated = bool("is_automated")

    override val primaryKey = PrimaryKey(id)
}

object Row : Table() {
    val id = long("id").autoIncrement()
    val hallId = long("hall_id") references Hall.id
    val rowNumber = integer("row_number")

    override val primaryKey = PrimaryKey(id)
}

object Performance_Session : Table() {
    val id = long("id").autoIncrement()
    val performanceId = long("performance_id") references Performance.id
    val hallId = long("hall_id") references Hall.id
    val sessionTime = timestamp("session_time")
    val sessionPrice = integer("session_price")

    override val primaryKey = PrimaryKey(id)
}

object Ticket : Table() {
    val id = long("id").autoIncrement()
    val sessionId = long("session_id") references Performance_Session.id
    val cashboxId = long("cashbox_id") references Cashbox.id
    val rowId = long("row_id") references Row.id
    val seat = integer("seat")
    val soldTime = timestamp("sold_time")

    override val primaryKey = PrimaryKey(id)
}