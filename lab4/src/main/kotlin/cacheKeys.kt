sealed interface Key

data class TamerKey(private val id: Long) : Key

data class AnimalKey(private val id: Long) : Key

data class TicketKey(private val id: Long) : Key

data class PerformanceKey(private val id: Long) : Key

object AllTamersKey : Key

object AllTicketsKey: Key

object AllPerformancesKey : Key
