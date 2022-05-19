import org.jetbrains.exposed.sql.ResultRow

object CachingInteraction : DatabaseInteraction {

    private val cache = LruBasedCache<Key, List<ResultRow>>(512)

    override fun getPerformance(id: Long) =
        cache.getOrPut(PerformanceKey(id)) { NonCachingInteraction.getPerformance(id) }

    override fun getTamer(id: Long) =
        cache.getOrPut(TamerKey(id)) { NonCachingInteraction.getTamer(id) }

    override fun getAnimalPerformer(id: Long) =
        cache.getOrPut(AnimalKey(id)) { NonCachingInteraction.getAnimalPerformer(id) }

    override fun addTamer(_name: String, _surname: String) {
        NonCachingInteraction.addTamer(_name, _surname)

        cache.remove(AllTamersKey)
    }

    override fun changeTamerName(tamerId: Long, newTamerName: String) {
        NonCachingInteraction.changeTamerName(tamerId, newTamerName)

        cache.remove(TamerKey(tamerId))
    }

    override fun changeAnimalFamily(animalId: Long, newFamily: String) {
        NonCachingInteraction.changeAnimalFamily(animalId, newFamily)

        cache.remove(AnimalKey(animalId))
    }

    override fun removeTicket(ticketId: Long) {
        //cache.remove(AllTicketsKey)

        NonCachingInteraction.removeTicket(ticketId)
    }
}