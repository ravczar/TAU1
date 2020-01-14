#define CATCH_CONFIG_MAIN
#include <iostream>
#include "lib/catch.hpp"
#include "services/Iterator.h"
#include "services/Car.h"
#include "services/DataBase.h"

// https://www.sololearn.com/Play/CPlusPlus
// https://github.com/catchorg/Catch2/blob/master/docs/tutorial.md#top

/*  ITERATOR OBJECT TEST CASE */
TEST_CASE("Iterator operations", "[Iterator][constructors]") { // [Iterator][constructor of Car] present

  SECTION("The iterator object can be created") {
    REQUIRE_NOTHROW([]() { Iterator iterator; });
  }
  SECTION ("getAndIncrementValue() method is present") {
    Iterator iterator;
    REQUIRE_NOTHROW(iterator.getAndIncrementValue());
  }
  SECTION("Iterator iterates <0,1,2,3..>") {
    Iterator iterator;
    for(int i=0; i<=9; i++){
        REQUIRE( iterator.getAndIncrementValue() == i);
    }  
    REQUIRE (iterator.getInteger() == 10);
  }
}

/* CAR OBJECT TEST CASE */
TEST_CASE("Basic entity operations", "[Car][constructors]") { // [Car][constructor of Car] present

  SECTION("The entity object can be created") {
    REQUIRE_NOTHROW([]() { Car car; });
  }

  SECTION("The database object can be created with 4 arguments.") {
    REQUIRE_NOTHROW([]() { Car car(1,"500", "Fiat", 1300.00); });
  }

  SECTION("The database object can be created with 3 arguments (ommiting ID field).") {
    REQUIRE_NOTHROW([]() { Car car("500", "Fiat", 1300.00); });
  }

  SECTION("Have access to private Car fields through getters.") {
    Car car(13,"500", "Fiat", 1300.00);
    REQUIRE(car.getId() == 13);
    REQUIRE(car.getModel() == "500");
    REQUIRE(car.getBrand() == "Fiat");
    REQUIRE(car.getEngineCapacity() == 1300.00);
  }
  
  SECTION("Have access to private Car fields through setters.") {
    Car car;
    car.setId(9);
    REQUIRE(car.getId() == 9);
    car.setModel("Polonez");
    REQUIRE(car.getModel() == "Polonez");
    car.setBrand("FSO");
    REQUIRE(car.getBrand() == "FSO");
    car.setEngineCapacity(1200.00);
    REQUIRE(car.getEngineCapacity() == 1200.00);
  }
}

/* DATABASE TEST CASE */
TEST_CASE("Basic database object operations", "[DataBase][constructors]") { // [db_class][constructor of db_class] present

  SECTION("The database object can be created") {
    REQUIRE_NOTHROW([]() { DataBase db; });
  }

  SECTION ("AddCar() method is present") {
    DataBase db;
    Car car("V60", "Volvo", 2400.60);
    REQUIRE_NOTHROW( db.addCar(car) );
  }

  SECTION("You can add an object to Database (Car)") {
    Car car("126p", "Fiat", 648.00);
    DataBase db;
    REQUIRE_NOTHROW( db.addCar(car) );
    REQUIRE(db.getListSize() == 1);
  }

  SECTION("Adding Car to DB increments DB 'id'. (0->1)") {
    Car car("126p", "Fiat", 648.00);
    DataBase db;
    db.addCar(car);
    REQUIRE(db.getId() == 1);
  }

  SECTION ("GetAll cars method is present") {
    DataBase db;
    REQUIRE_NOTHROW(db.getAllCars());
  }

  SECTION("GetAll cars method works.") {
    Car car1("126p", "Fiat", 648.00);
    Car car2("Berlingo", "Citroen", 1998.40);
    DataBase db;
    db.addCar(car1);
    db.addCar(car2);
    REQUIRE( db.getAllCars().size() == 2 );
  }

  SECTION ("GetListSize db_list method is present") {
    DataBase db;
    REQUIRE_NOTHROW(db.getListSize());
  }

  SECTION("getListSize method works and return number of cars.") {
    Car car1("126p", "Fiat", 648.00);
    Car car2("Berlingo", "Citroen", 1998.40);
    DataBase db;
    db.addCar(car1);
    db.addCar(car2);
    REQUIRE( db.getAllCars().size() == db.getListSize() );
    REQUIRE( db.getListSize() == 2 );
  }

  SECTION ("getCarById(id) method is present and throws 404") {
    DataBase db;
    REQUIRE_THROWS(db.getCarById(0));
  }

  SECTION("getCarById(id) returns found object when object actually found in DB") {
    Car car("126p", "Fiat", 648.00); 
    DataBase db;
    db.addCar(car);
    REQUIRE( db.getCarById(0).getId() == 0 );
  }

  SECTION("getCarById(id) returns 'Throw 404' when object not found in DB") {
    Car car("126p", "Fiat", 648.00); 
    DataBase db;
    db.addCar(car);
    CHECK_THROWS( db.getCarById(999) );
  }

  SECTION ("deleteCarById(id) method is present and throws 404") {
    DataBase db;
    REQUIRE_THROWS(db.deleteCarById(0));
  }

  SECTION("Yet another section to be checked") {}
}


