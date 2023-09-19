package bookapplication

import grails.gorm.transactions.Transactional

@Transactional
class AuthorService {

    def save(Author author) {
        author.save(flush: true)
        return author
    }

    def update(String name, String emailId){
        def existingAuthor = Author.findByName(name)
        def author=new Author(name: name,emailId: emailId)
        existingAuthor.properties= author.properties
        existingAuthor.save(flush: true)
        return existingAuthor
    }

    def getAll(){
        def authors = Author.list()
        return authors
    }

    def delete(Author author){
        author.delete(flush: true)
        return author
    }
}

