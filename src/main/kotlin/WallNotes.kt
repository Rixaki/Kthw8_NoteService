class WallNotes {
    private var notes = mutableListOf<Note>()

    init {
        idInWall = 0
        Comment.idInWall = 1
    }

    companion object { //static count
        @JvmField
        var idInWall: Int = 0
    }

    fun add(
        title: String,
        text: String
    ): Int {
        /*
        if (isHasNote(idInWall + 1)) {
            //throw RuntimeException("note with id $idInWall exists")
            return -1
        }
        */
        val note = Note(
            id = idInWall + 1,
            title = title,
            text = text
        )
        idInWall += 1
        notes += note
        return note.id
    }

    fun createComment(
        noteId: Int,
        message: String?
    ): Int {
        //if (isHasNote(idInWall)) {
        val note: Note? = notes.find { it.id == noteId }
        if (note != null) {
            if (Comment.idInWall == note.commentsList.size - 1) {
                Comment.idInWall += 1
            } else {
                Comment.idInWall = note.commentsList.size + 1
            }
            val comment = Comment(
                id = Comment.idInWall,
                message = message ?: "<none message>"
            )
            note.commentsList += comment
            note.comments += 1
            return comment.id
        } else {
            return -1
        }
        //} else return -1
    }

    fun delete(noteId: Int): Int {
        //if (isHasNote(idInWall)) {
        val note: Note? = notes.find { it.id == noteId }
        if (note != null) {
            notes.remove(note)
            return 1
        } else return -1
        //} else return -1
    }

    private fun isHasNote(id: Int): Boolean {
        return (null != notes.find { it.id == id })
    }

    fun edit(
        noteId: Int,
        title: String,
        text: String
    ): Int {
        //if (isHasNote(idInWall)) {
        val note: Note? = notes.find { it.id == noteId }
        if (note != null) {
            val indexOfEdited = notes.indexOf(note)
            if (indexOfEdited == -1) {
                return -1
            } else {
                val editedNote = note.copy(id = noteId, title = title, text = text)
                notes = (notes.slice(0 until indexOfEdited) +
                        editedNote +
                        notes.slice(indexOfEdited + 1 until notes.size)).toMutableList()
                return 1
            }
        } else return -1
        //} else return -1
    }

    fun get(): MutableList<Note> {
        val resultList = mutableListOf<Note>()
        for (note in notes) {
            //println(note)
            resultList += note
        }
        return resultList
    }

    fun getById(noteId: Int): Note? {
        val note: Note? = notes.find { it.id == noteId }
        if (note != null) {
            return note
        } else {
            return null
        }
    }

    /*
    fun clear() {
        notes = mutableListOf<Note>()
    }
     */
}