#define CATCH_CONFIG_MAIN
#include <iostream>
#include "lib/catch.hpp"
#include "services/Iterator.h"
#include "services/Car.h"
#include "services/DataBase.h"

SCENARIO( "Items can be added, deleted, found by id", "[DataBase]" ) {
    GIVEN("Database object is filled with 3 records") {
        DataBase db;
        REQUIRE_NOTHROW(db.getId());
        Car car0("DB7", "Aston Martin", 3399.00);
        Car car1("s600", "Mercedes Benz", 4200.00);
        Car car2("Panda", "Fiat", 1211.00 );
        REQUIRE_NOTHROW(car0.getId());
        REQUIRE(db.getListSize() == 0);
        db.addCar(car0);
        db.addCar(car1);
        db.addCar(car2);
        REQUIRE(db.getListSize() == 3);
        
        WHEN( "When I add one object to database and i succeed" ) {
            Car car4("Note", "Nissan", 1400.00);
            db.addCar(car4);
            THEN("Size of DB increases to '4'."){
                REQUIRE(db.getListSize() == 4);
                REQUIRE(db.getListSize() == db.getId());
            }
        }

        WHEN( "I try to delete a record and I succeed" ) {
            // DB now is again 3 items as in 'GIVEN'
            THEN("I get a copy of it in return "){
                REQUIRE(db.deleteCarById(2).getId() == 2);
            }
            AND_THEN("Size of DB decreases to '2'."){
                db.deleteCarById(2).getId();
                CHECK(db.getListSize() == 2);
                REQUIRE_FALSE(db.getListSize() == db.getId()); // Id w klasie Iteratora będzie nadal wyższe, bo kasowanie nie zmiejsza licznika
            }
        }

        WHEN( "I try to find item by id and I succeed" ) {
            Car car4("Testarossa", "Ferrari", 3200.00);
            db.addCar(car4);
            THEN("I get a copy of object in return"){
                REQUIRE(db.getCarById(3).getBrand() == "Ferrari");
                CHECK(db.getCarById(3).getId() == 3);
            }
        }

        WHEN( "I try to find item by id and I fail" ) {
            unsigned int idToFailTest = 4;
            THEN("I get a Throw in return"){
                CHECK_THROWS( db.getCarById(idToFailTest) );
            }
        }


    }
}