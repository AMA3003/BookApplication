package bookapplication

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import javassist.NotFoundException

@Transactional
class BooksController {
    static allowedMethods = [saveBook: "POST", UpdateBook: "PUT", deleteBook: "DELETE", getAllBooks:"GET"]

    BookService bookService
    def saveBook() {
        def bookData = request.JSON
        def authorName = bookData.author.name
        def author = Author.findByName(authorName)
        if (!author) {
            render status: 400, text: "Author with Name '$authorName' not found"
            return
        }
        def book = new Books(
                title: bookData.title,
                yearPublished: bookData.yearPublished,
                genre: bookData.genre,
                createdDate: new Date(),
                lastModifiedDate: new Date()
        )
        book.author = author
        if (book.save(flush: true)) {
            render status: 201, text: 'Book created successfully'
        } else {
            render status: 400, text: 'Failed to create book'
        }
    }

    def getAllBooks() {
        try {
            log.info("Fetching books")
            def books = bookService.getAll()
            render books as JSON
        } catch (Exception e) {
            log.error("Error fetching books", e)
            render(status: 400, text: "Error while fetching Books: ${e.message}")
        }
    }

    def getBook(String title) {
        try {
            log.info("Fetching book: $title")
            def book = bookService.get(title)
            render book as JSON
        } catch (Exception e) {
            log.error("Error fetching book: $title")
            render(status: 400, text: "Error while fetching Book: ${e.message}")
        }
    }

    def UpdateBook(){
        def json = request.JSON
        def title = json.title
        def bookToUpdate = Books.findByTitle(title)
        if (!bookToUpdate) {
            render(status: 404, text: "Book with title '$title' not found")
            return
        }
        try {
            def updatedBook = bookService.update(title, json.yearPublished as Integer, json.genre)
            log.info("Updating book: ${updatedBook.title}")
            render(status: 201, text: "Book Updated successfully: ${updatedBook.title}")
        } catch (Exception e) {
            log.error("Error updating the book")
            render(status: 400, text: e.message)
        }
    }

    def deleteBook(String title) {
        try {
            def book = Books.findByTitle(title)
            if (book) {
                def deletedBook = bookService.delete(book)
                log.info("Book with title $title successfully deleted.")
                render(status: 201, text: "Book Deleted successfully: ${deletedBook.title}")
            } else {
                throw new NotFoundException("Book with title $title not found.")
            }
        } catch (Exception e) {
            log.error("An error occurred: ${e.message}")
            render(status: 400, text: "Error while Deleting the book: ${e.message}")
        }
    }
}
