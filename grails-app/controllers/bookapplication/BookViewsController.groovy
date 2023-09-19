package bookapplication

class BookViewsController {

    BookService bookService
    def saveBook() {
        def existingBook = Books.findByTitle(params.title)

        if (existingBook) {
            log.error("Book with the same title already exists: ${params.title}")
            render(status: 400, text: "Book with the same title already exists: ${params.title}")
            return
        }
        def newBook = new Books(
                title: params.title,
                author: params.author,
                yearPublished: params.yearPublished,
                genre:params.genre,
                createdDate: new Date(),
                lastModifiedDate: new Date()
        )
        try {
            def savedBook = bookService.save(newBook)
            def bookList = Books.list()
            log.info("Book saved successfully: ${newBook.title}")
            render (view: "/listBooks", model: [bookList: bookList])
        } catch (Exception e) {
            render(view: '/error')
        }
    }

    def listBooks() {
        def bookList = Books.list()
        render (view: "/listBooks", model: [bookList: bookList])
    }

    def UpdateBook(){
        render (view: "/UpdateBook")
    }

    def UpdateBooks(){
        def title = params.title
        def bookToUpdate = Books.findByTitle(title)
        if (!bookToUpdate) {
            render "No"
            return
        }
        try {
            // log.info("Updating book: ${updatedBook.title}")
            def updatedBook = bookService.update(title, params.author, params.yearPublished as Integer, params.genre)
            log.info("Updating book: ${updatedBook.title}")
            def bookList = Books.list()
            render (view: "/listBooks", model: [bookList: bookList])
        } catch (Exception e) {
            log.error("Error updating the book")
            render (view:'/error')
        }
    }

    def deleteBook() {
        def book = Books.findByTitle(params.title)
        try {
            def deletedBook = bookService.delete(book)
            def bookList = Books.list()
            render (view: "/listBooks", model: [bookList: bookList])
        } catch (Exception e) {
            render (view:'/error')
        }
    }
}
