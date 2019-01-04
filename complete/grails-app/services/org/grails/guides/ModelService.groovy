package org.grails.guides

import grails.gorm.services.Service

@Service(Model)
interface ModelService {
    Model save(String name, Make make)
}