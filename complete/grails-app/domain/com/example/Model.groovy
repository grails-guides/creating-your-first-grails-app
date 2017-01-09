package com.example

class Model {

    String name

    static belongsTo = [ make: Make ]

    static constraints = {
    }
}
