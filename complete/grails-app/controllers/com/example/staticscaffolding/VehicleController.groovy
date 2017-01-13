package com.example.staticscaffolding

import com.example.Vehicle
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


@Transactional(readOnly = true) // <1>
class VehicleController {

    static namespace = 'staticscaffolding'

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100) // <2>
        respond Vehicle.list(params), model:[vehicleCount: Vehicle.count()] // <3>
    }

    def show(Vehicle vehicle) {
        respond vehicle //<3>
    }

    def create() {
        respond new Vehicle(params) // <3>
    }

    @Transactional //<1>
    def save(Vehicle vehicle) {
        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vehicle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vehicle.errors, view:'create'  // <3>
            return
        }

        vehicle.save flush:true

        request.withFormat {  //<4>
            form multipartForm {
                //<5>
                flash.message = message(code: 'default.created.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect vehicle
            }
            '*' { respond vehicle, [status: CREATED] } // <3>
        }
    }

    def edit(Vehicle vehicle) {
        respond vehicle //<3>
    }

    @Transactional //<1>
    def update(Vehicle vehicle) {
        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vehicle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vehicle.errors, view:'edit' //<3>
            return
        }

        vehicle.save flush:true

        request.withFormat {
            form multipartForm {
                //<5>
                flash.message = message(code: 'default.updated.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect vehicle // <6>
            }
            '*'{ respond vehicle, [status: OK] } // <3>
        }
    }

    @Transactional //<1>
    def delete(Vehicle vehicle) {

        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        vehicle.delete flush:true

        request.withFormat {
            form multipartForm {
                //<5>
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect action:"index", method:"GET" // <6>
            }
            '*'{ render status: NO_CONTENT } // <7>
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                //<5>
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), params.id])
                redirect action: "index", method: "GET" //<6>
            }
            '*'{ render status: NOT_FOUND }  // <7>
        }
    }
}
