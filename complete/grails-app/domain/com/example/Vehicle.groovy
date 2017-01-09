package com.example

class Vehicle {

    String name
    Model model

    Make getMake() {
        model.make
    }

    static constraints = {
        name maxSize: 255
    }
}
