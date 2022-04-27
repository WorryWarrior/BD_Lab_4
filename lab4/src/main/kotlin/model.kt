import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.timestamp

/*object UserEntity : Table() {
    val id = long("id").autoIncrement()
    val username = varchar("username", length = 16)

    override val primaryKey = PrimaryKey(id)
}

object Aroma : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", length = 32)
    val description = text("description")

    override val primaryKey = PrimaryKey(id)
}

object Taste : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", length = 32)
    val description = text("description")

    override val primaryKey = PrimaryKey(id)
}

object Type : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", length = 32)
    val description = text("description")

    override val primaryKey = PrimaryKey(id)
}

object Tea : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", length = 255)
    val aromaId = long("aroma_id") references Aroma.id
    val tasteId = long("taste_id") references Taste.id
    val typeId = long("type_id") references Type.id
    val description = text("description")

    override val primaryKey = PrimaryKey(id)
}

object Note : Table() {
    val id = long("id").autoIncrement()
    val teaId = long("tea_id") references Tea.id
    val note = text("note")
    val grade = integer("grade")

    override val primaryKey = PrimaryKey(id)
}

object UserNotes : Table() {
    val id = long("id").autoIncrement()
    val userId = long("user_id") references UserEntity.id
    val noteId = long("note_id") references Note.id

    override val primaryKey = PrimaryKey(id)
}*/

object Tamer : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", length = 50)
    val surname = varchar("surname", length = 50)
    val dateOfBirth = date("date_of_birth")
}

object Performance : Table() {
    val id = long("id").autoIncrement()
    val tamerId = long("tamer_id") references Tamer.id
}

object Animal_Performer : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", length = 50)
    val family = varchar("family", length = 50)
    val dateOfBirth = date("date_of_birth")
}

object Hall : Table() {
    val id = long("id").autoIncrement()
    val hallName = varchar("hall_name", length = 50)
}

object Cashbox : Table() {
    val id = long("id").autoIncrement()
    val isAutomated = bool("is_automated")
}

object Row : Table() {
    val id = long("id").autoIncrement()
    val hallId = long("hall_id") references Hall.id
    val rowNumber = integer("row_number")
}

object Performance_Session : Table() {
    val id = long("id").autoIncrement()
    val performanceId = long("performance_id") references Performance.id
    val hallId = long("hall_id") references Hall.id
    val sessionTime = timestamp("session_time")
    val sessionPrice = integer("session_price")
}

object Ticket : Table() {
    val id = long("id").autoIncrement()
    val sessionId = long("session_id") references Performance_Session.id
    val cashboxId = long("cashbox_id") references Cashbox.id
    val rowId = long("row_id") references Row.id
    val seat = integer("seat")
    val soldTime = timestamp("sold_time")
}