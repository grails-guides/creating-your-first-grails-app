package creating.a.grails.application

import com.example.Make
import com.example.Model
import com.example.Vehicle

class BootStrap {

    def init = { servletContext ->

        def nissan = new Make(name: "Nissan").save()
        def ford = new Make(name: "Ford").save()

        def titan = new Model(name: "Titan", make: nissan).save()
        def leaf = new Model(name: "Leaf", make: nissan).save()
        def windstar = new Model(name: "Windstar", make: ford).save()

        new Vehicle(name: "Pickup",  model: titan).save()
        new Vehicle(name: "Economy", model: leaf).save()
        new Vehicle(name: "Minivan", model: windstar).save()
    }
    def destroy = {
    }
}
