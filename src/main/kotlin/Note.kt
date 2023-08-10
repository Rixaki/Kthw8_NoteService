data class Note(
    var id: Int = 0,
    //var ownerId: Int = 0,
    var title: String = "none title",
    var text: String = "none text",
    var date: Int = 1,
    var comments: Int = 0,
    var readComments: Int = 0,
    var viewUrl: String = "none view of URL",
    var privacyView: String = "none privacy view",
    var canComment: Boolean = true,
    var textWiki: String = "none Wiki links"
) {
    var commentsList = mutableListOf<Comment>()

    private fun isHasComment(noteId: Int): Boolean {
        return (null != commentsList.find { it.id == id })
    }

    override fun toString(): String {
        return "id: $id, title: $title, text: $text"
    }

    fun deleteComment(commentId: Int): Int {
        if (isHasComment(commentId)) {
            val comment: Comment? = commentsList.find { it.id == commentId }
            if (comment != null) {
                if (!comment.isDeleted) {
                    comment.isDeleted = true
                    return 1
                } else {
                    throw Comment.StatusCommentException("Deleted comment was attempted to delete.")
                }
            } else {
                return -1
                //throw NullPointerException("none note with id#$commentId")
                //not work
            }
        } else return -1
    }

    fun editComment(
        commentId: Int,
        message: String,
    ): Int {
        if (isHasComment(commentId)) {
            val comment: Comment? = commentsList.find { it.id == commentId }
            if (comment != null) {
                if (!comment.isDeleted) {
                    comment.message = message
                    return 1
                } else {
                    throw Comment.StatusCommentException("Deleted comment was attempted to edit.")
                }
            } else {
                return -1
                //throw NullPointerException("none note with id#$commentId")
                //not work
            }
        } else return -1
    }

    fun getComments(): MutableList<Comment> {
        val resultList = mutableListOf<Comment>()
        for (comment in commentsList) {
            if (!comment.isDeleted) {
                //println(comment)
                resultList += comment
            }
        }
        return resultList
    }

    fun restoreComment(commentId: Int): Int {
        if (isHasComment(commentId)) {
            val comment: Comment? = commentsList.find { it.id == commentId }
            if (comment != null) {
                if (comment.isDeleted) {
                    comment.isDeleted = false
                    return 1
                } else {
                    throw Comment.StatusCommentException("Not deleted comment was attempted to restore.")
                }
            } else {
                return -1
                //throw NullPointerException("none note with id#$commentId")
                //not work
            }
        } else return -1
    }
}

class Comment(
    var id: Int = 0,
    var message: String = "none message",
    var isDeleted: Boolean = false
) {
    init {
        var idInWall: Int = 1
    }

    companion object { //static count
        @JvmField
        var idInWall: Int = 1
    }

    override fun toString(): String {
        return "idCom: ${this.id}, message: $message"
    }

    class StatusCommentException(msg: String) : RuntimeException(msg)
}