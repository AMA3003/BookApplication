package bookapplication

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/authors"(resources: "author")
        "/books"(resources: "book") {
            "/associateWithAuthor"(controller: "book", action: "associateWithAuthor", method: "POST")
        }
        "/"(view: "/saveBook")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
