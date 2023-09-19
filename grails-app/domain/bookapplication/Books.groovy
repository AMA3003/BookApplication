package bookapplication

class Books {

    String title
    Integer yearPublished
    String genre
    Date createdDate
    Date lastModifiedDate

    static belongsTo = [author: Author]

    static mapWith = "mongo"
    static constraints = {
        title(blank: false)
        yearPublished(nullable: true, min: 0)
        genre(blank: false)
    }

}
