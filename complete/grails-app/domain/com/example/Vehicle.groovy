package com.example

class Vehicle {

    String name
    Model model
    Make make

    static constraints = {
        name maxSize: 255
    }
}
