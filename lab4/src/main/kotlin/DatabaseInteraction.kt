import org.jetbrains.exposed.sql.ResultRow

interface DatabaseInteraction {
    fun getPerformance(id: Long) : List<ResultRow>

    fun getTamer(id: Long) : List<ResultRow>

    fun getAnimalPerformer(id: Long) : List<ResultRow>

    fun addTamer(_name: String, _surname: String)

    fun changeTamerName(tamerId: Long, newTamerName: String)

    fun changeAnimalFamily(animalId: Long, newFamily: String)

    fun removeTicket(ticketId: Long)
}