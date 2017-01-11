package com.example

class HomeController {

    def index() {
        respond([name: session.name ?: 'User', vehicleList: Vehicle.list(), vehicleTotal: Vehicle.count()])
    }

    def updateName(String name) {
        session.name = name

        flash.message = "Name has been updated"

        redirect action: 'index'
    }

}
