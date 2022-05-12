import org.jetbrains.exposed.sql.ResultRow

object CachingInteraction : DatabaseInteraction {

    private val cache = LruBasedCache<Key, List<ResultRow>>(128)

    override fun getAllPerformances() =
        cache.getOrPut(AllPerformancesKey) { NonCachingInteraction.getAllPerformances() }

    override fun getTamer(id: Long) =
        cache.getOrPut(TamerKey(id)) { NonCachingInteraction.getTamer(id) }

    override fun getAnimalPerformer(id: Long) =
        cache.getOrPut(AnimalKey(id)) { NonCachingInteraction.getAnimalPerformer(id) }

    override fun addTamer(_name: String, _surname: String) {
        cache.remove(AllTamersKey)

        NonCachingInteraction.addTamer(_name, _surname)
    }

    override fun changeTamerName(tamerId: Long, newTamerName: String) {
        cache.remove(TamerKey(tamerId))

        NonCachingInteraction.changeTamerName(tamerId, newTamerName)
    }

    override fun changeAnimalFamily(animalId: Long, newFamily: String) {
        cache.remove(AnimalKey(animalId))

        NonCachingInteraction.changeAnimalFamily(animalId, newFamily)
    }

    override fun removeTicket(ticketId: Long) {
        cache.remove(AllTicketsKey)

        NonCachingInteraction.removeTicket(ticketId)
    }
}