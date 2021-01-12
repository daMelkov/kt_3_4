data class Chat (
    val id: Long,
    val messages: List<Message>? = null
) {
    var deleted: Boolean = false
}