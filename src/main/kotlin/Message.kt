data class Message (
    val id: Long = 0L,
    val chatId: Long,
    val ownerId: Long,
    val recipientId: Long,
    val text: String
) {
    var read: Boolean = false
    var deleted: Boolean = false
}