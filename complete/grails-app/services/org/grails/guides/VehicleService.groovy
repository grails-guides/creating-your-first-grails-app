package org.grails.guides

import grails.gorm.services.Service

@Service(Vehicle)
interface VehicleService {
    Vehicle save(String name, Make make, Model model, Integer year)
}
