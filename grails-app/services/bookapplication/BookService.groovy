package bookapplication

import grails.gorm.transactions.Transactional

@Transactional
class BookService {

    def save(Books book) {
        book.save(flush: true)
        return book
    }

    def get(String title){
        def book = Books.findByTitle(title)
        return book
    }

    def getAll(){
        def books = Books.list()
        return books
    }

    def update(String title, Integer yearPublished, String genre){
        def existingBook = Books.findByTitle(title)
        existingBook.title = title
        existingBook.yearPublished = yearPublished
        existingBook.genre = genre
        existingBook.lastModifiedDate = new Date()
        existingBook.save(flush: true) // Save the updated book
        return existingBook
    }

    def delete(Books book){
        book.delete(flush: true)
        return book
    }

}

