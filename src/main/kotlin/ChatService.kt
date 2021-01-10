object ChatService: CrudService<Chat> {
    private var chats = ArrayList<Chat>()

    override fun create(entity: Chat): Long {
        val id = (chats.size + 1).toLong()
        chats.add(entity.copy(id = id))

        return id
    }

    override fun read(): List<Chat> {
        return chats.filter{!it.deleted && MessageService.read(it.id, 0L, 0).isNotEmpty() }
    }

    override fun update(entity: Chat) {
    }

    override fun delete(id: Long) {
        for(chat in chats.filter { it.id == id }) {
            chat.deleted = true
        }
    }

    override fun restore(id: Long) {
        for(chat in chats.filter { it.id == id }) {
            chat.deleted = false
        }
    }

    override fun markRead(id: Long) {
    }

    fun createMessage(chatId: Long, ownerId: Long, recipientId: Long, text: String) {
        val message = Message(chatId = chatId, ownerId = ownerId, recipientId = recipientId, text = text)
        MessageService.create(message)
    }

    fun readMessages(chatId: Long, messageId: Long, offset: Int): List<Message> {
        MessageService.markRead(chatId = chatId, messageId = messageId, offset = offset)

        return MessageService.read(chatId = chatId, messageId = messageId, offset = offset)
    }

    fun getUnreadChatsCount(): Int {
        var result = 0

        for(chat in chats) {
            if (chat.messages.any { !it.read }) {
                result++
            }
        }

        return result
    }

}