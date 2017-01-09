package com.example

class Vehicle {

    String name

    String make
    String model


    static constraints = {
        name maxSize: 255
        make inList: ['Ford', 'Chevrolet', 'Nissan']
        model nullable: true
    }
}
