package ru.netology

//import WallNotes
import Comment.*
import WallNotes
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainKtTest {

    @Before
    fun clearBeforeTest() {
        WallNotes().clear()
    }

    @Test
    fun addNoteTest() {
        val service = WallNotes()
        assertTrue(service.add("1st note", "hello world") != -1)
    }

    @Test
    fun createCommentCorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        assertTrue(service.createComment(2, "what about you?") != -1)
    }

    @Test
    fun createCommentIncorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        assertTrue(service.createComment(12, "what about you?") == -1)
    }

    @Test()
    fun deleteNoteCorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.add("day 3", "another day - another joy")
        assertTrue(service.delete(3) == 1)
    }

    @Test()
    fun deleteNoteInCorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.add("day 3", "another day - another joy")
        assertTrue(service.delete(13) == -1)
    }

    @Test()
    fun editNoteCorrectStatusTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.add("day 3", "another day - another joy")
        assertTrue(service.edit(2, "day 2 and 3", "all is good and nice") == 1)
    }

    @Test()
    fun editNoteCorrectFunctionTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.add("day 3", "another day - another joy")
        service.edit(2, "day 2 and 3", "all is good and nice")
        assertTrue(service.getById(2)?.title == "day 2 and 3")
    }

    @Test()
    fun editNoteIncorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.add("day 3", "another day - another joy")
        assertTrue(service.edit(12, "day 2 and 3", "all is good and nice") == -1)
    }

    @Test()
    fun getByIdCorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.add("day 3", "another day - another joy")
        assertTrue(
            (service.getById(1)?.title == "1st note") &&
                    (service.getById(1)?.text == "hello world")
        )
    }

    @Test()
    fun getByIdIncorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.add("day 3", "another day - another joy")
        assertTrue(service.getById(11) == null)
    }

    @Test
    fun deleteCommentCorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        assertTrue(service.getById(2)?.deleteComment(1) == 1)
    }

    @Test
    fun deleteCommentIncorrectTestByNoteId() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        assertTrue(service.getById(12)?.deleteComment(1) == null)
    }

    @Test(expected = StatusCommentException::class)
    fun deleteCommentIncorrectTestByStatus() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        service.getById(2)?.deleteComment(1)
        service.getById(2)?.deleteComment(1)
    }

    @Test
    fun editCommentCorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        assertTrue(service.getById(2)?.editComment(2, "my day ... nice") == 1)
    }

    @Test
    fun editCommentIncorrectTestByNoteId() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        assertTrue(service.getById(12)?.editComment(2, "my day ... nice") == null)
    }

    @Test
    fun editCommentIncorrectTestByCommentId() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        assertTrue(service.getById(2)?.editComment(12, "my day ... nice") == -1)
    }

    @Test(expected = StatusCommentException::class)
    fun editCommentIncorrectTestByStatus() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        service.getById(2)?.deleteComment(1)
        service.getById(2)?.editComment(1, "my day ... nice")
    }

    @Test
    fun restoreCorrectTest() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        service.getById(2)?.deleteComment(3)
        assertTrue(service.getById(2)?.restoreComment(3) == 1)
    }

    @Test
    fun restoreIncorrectTestByNoteId() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        service.getById(2)?.deleteComment(1)
        assertTrue(service.getById(112)?.restoreComment(3) == null)
    }

    @Test
    fun restoreIncorrectTestByCommentId() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        service.getById(3)?.deleteComment(1)
        assertTrue(service.getById(2)?.restoreComment(103) == -1)
    }

    @Test(expected = StatusCommentException::class)
    fun restoreIncorrectTestByStatus() {
        val service = WallNotes()
        service.add("1st note", "hello world")
        service.add("day 2", "all is good")
        service.createComment(2, "what about you?")
        service.createComment(2, "my day is good too")
        service.createComment(2, "i saw nice sunrise")
        service.getById(2)?.restoreComment(3)
    }
}