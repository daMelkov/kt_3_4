object MessageService: CrudService<Message> {
    private var messages = ArrayList<Message>()

    override fun create(entity: Message): Long {
        val id = (messages.size + 1).toLong()
        messages.add(entity.copy(id = id))

        return id
    }

    override fun read(): List<Message> {
        return messages.filter { !it.deleted }
    }

    override fun update(entity: Message) {
        val message = entity.copy(text = entity.text)

        messages.remove(entity)
        messages.add(message)
    }

    override fun delete(id: Long) {
        messages.filter { id == id }[0].deleted = true
    }

    override fun restore(id: Long) {
        messages.filter { id == id }[0].deleted = false
    }

    override fun markRead(id: Long) {
        messages.filter { it.id == id }[0].read = true
    }

    fun read(chatId: Long, messageId: Long, offset: Int): List<Message> {
        // TODO: если offset = 0, возвращать все сообщения
        return messages
            .filter { it.chatId == chatId && it.id >= messageId }
            .take(offset)
    }

    fun markRead(chatId: Long, messageId: Long, offset: Int) {
        for(message in read(chatId = chatId, messageId = messageId, offset = offset)) {
                markRead(message.id)
        }
    }
}