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
  SECTION("Iterator iterates <0,1,2,3..>") {
    Iterator iterator;
    for(int i=0; i<=9; i++){
        REQUIRE( iterator.getAndIncrementValue() == i);
    }  
    REQUIRE (iterator.getInteger() == 10);
    delete &iterator;
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

  SECTION("Have access to private Car fields through getters.") {
    Car car(13,"500", "Fiat", 1300.00);
    REQUIRE(car.getId() == 13);
    REQUIRE(car.getModel() == "500");
    REQUIRE(car.getBrand() == "Fiat");
    REQUIRE(car.getEngineCapacity() == 1300.00);
    delete &car;
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
    delete &car;
  }
}


