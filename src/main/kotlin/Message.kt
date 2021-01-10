data class Message (
    val id: Long,
    val ownerId: Long,
    val recipientId: Long,
    val text: String,
    val read: Boolean
)