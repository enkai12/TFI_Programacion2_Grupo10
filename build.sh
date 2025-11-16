#!/bin/bash
# Compile
javac -cp "lib/mysql-connector-j-8.4.0/*:src" src/main/java/entities/Base.java src/main/java/entities/EstadoLegajo.java src/main/java/entities/Legajo.java src/main/java/entities/Empleado.java src/main/java/config/DatabaseConnection.java src/main/java/config/TransactionManager.java src/main/java/dao/GenericDAO.java src/main/java/dao/LegajoDAO.java src/main/java/dao/EmpleadoDAO.java src/main/java/service/ServiceException.java src/main/java/service/GenericService.java src/main/java/service/LegajoServiceImpl.java src/main/java/service/EmpleadoServiceImpl.java src/main/java/main/MenuDisplay.java src/main/java/main/MenuHandler.java src/main/java/main/AppMenu.java src/main/java/main/Main.java -d out
# Run
java -cp "lib/mysql-connector-j-8.4.0/*:out" main.Main
