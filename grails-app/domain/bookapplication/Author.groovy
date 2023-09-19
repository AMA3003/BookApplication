package bookapplication

class Author {

    String name
    String emailId

    static mapWith = "mongo"

    static hasMany = [books: Books]
    static constraints = {
        name(blank: false)
        emailId(blank: false)
    }

    static mapping = {
        books cascade: "all"
    }
}
