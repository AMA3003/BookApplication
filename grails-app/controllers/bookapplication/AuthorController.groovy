package bookapplication

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import javassist.NotFoundException

@Transactional
class AuthorController {
    static allowedMethods = [saveAuthor: "POST", updateAuthor: "PUT", deleteAuthor: "DELETE", getAuthors:"GET"]

    AuthorService authorService
    def saveAuthor(){
        def json = request.JSON
        def existingAuthor = Author.findByName(json.name)
        if (existingAuthor) {
            log.error("Author with the same name already exists: ${json.name}")
            render(status: 400, text: "Author with the same name already exists: ${json.name}")
            return
        }
        def newAuthor= new Author(name: json.name,emailId: json.emailId)
        try {
            def savedAuthor = authorService.save(newAuthor)
            log.info("Author saved successfully: ${json.name}")
            render(status: 201, text: "Author saved successfully: ${json.name}")
        } catch (Exception e) {
            log.error("Error while saving author: ${json.name}", e)
            render(status: 400, text: "Error while saving author: ${e.message}")
        }
    }

    def updateAuthor() {
        try {
            def json = request.JSON
            def name = json.name
            def authorToUpdate = Author.findByName(name)
            if(authorToUpdate) {
                def updatedAuthor = authorService.update(name, json.emailId)
                log.info("Updating Author: ${name}")
                render(status: 201, text: "Author Updated successfully: ${name}")
            }
            else {
                throw new NotFoundException("Author with name $name not found.")
            }
        } catch (Exception e) {
            log.error("Error updating the author")
            render(status: 400, text: "Error updating the author: ${e.message}")
        }
    }

    def getAuthors() {
        try {
            log.info("Fetching Authors")
            def authors = authorService.getAll()
            def authorNames = authors.collect { it.name }
            render authorNames as JSON
        } catch (Exception e) {
            log.error("Error while fetching Authors", e)
            render(status: 400, text: "Error while fetching Authors: ${e.message}")
        }
    }


    def deleteAuthor(String name) {
        try {
            def author = Author.findByName(name)
            if (author) {
                def deletedAuthor = authorService.delete(author)
                log.info("Author with name $name successfully deleted.")
                render(status: 201, text: "Author Deleted successfully: ${deletedAuthor.name}")
            } else {
                throw new NotFoundException("Author with name $name not found.")
            }
        } catch (Exception e) {
            log.error("An error occurred: ${e.message}")
            render(status: 400, text: "Error while Deleting the author: ${e.message}")
        }
    }


    def createAuthor() {
        def json = request.JSON
        def author = new Author(name: json.name, emailId: json.emailId)
        json.books.each { bookData ->
            def book = new Books(
                    title: bookData.title,
                    author: bookData.author,
                    yearPublished: bookData.yearPublished,
                    genre:bookData.genre,
                    createdDate: new Date(),
                    lastModifiedDate: new Date()
            )
            author.addToBooks(book)
        }
        if (author.save(flush: true)) {
            render status: 201, text: 'Author with books created successfully'
        } else {
            render status: 400, text: 'Bad Request'
        }
    }

    def booksByAuthor(String name) {
        def author = Author.findByName(name)
        if (!author) {
            render status: 404, text: "Author not found"
            return
        }
        def books = author.books
        def response = [
                author: name,
                books: books.collect { book ->
                    [
                            title: book.title,
                            yearPublished: book.yearPublished,
                            genre: book.genre,
                            createdDate: book.createdDate,
                            lastModifiedDate: book.lastModifiedDate
                    ]
                }
        ]
        render response as JSON
    }
}
