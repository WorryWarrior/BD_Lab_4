sealed interface Key

data class TamerKey(private val id: Long) : Key

data class AnimalKey(private val id: Long) : Key

object AllUserNotesKey : Key
