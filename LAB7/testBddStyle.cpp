#define CATCH_CONFIG_MAIN
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

        WHEN( "I Try to delete a record and i fail" ){
            Car car4("Berlingo", "Citroen", 1800.00);
            THEN("I get a throw in return"){
                CHECK_THROWS(db.deleteCarById(99));
                CHECK_THROWS_WITH(db.deleteCarById(99), "Car Not Found");
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
                CHECK_THROWS_WITH(db.getCarById(idToFailTest), "Car Not Found");
            }
        }
        
        WHEN( "I try to find item giving no params to function and i FAIL" ) {
            THEN("I get a Throw in return"){
                CHECK_THROWS( db.getCarsByParam() );
                CHECK_THROWS_WITH(db.getCarsByParam(), "Give at last one param");
            }
        }

        WHEN( "I try to find item by model and I FAIL to find it by brand_name (not exist in db)" ) {
            THEN("I get a Throw in return"){
                CHECK_THROWS( db.getCarsByParam(-1,"none","Bentley",-1) );
                CHECK_THROWS_WITH(db.getCarsByParam(-1,"none","Bentley",-1), "Car Not Found");
            }
        }

        WHEN( "I try to find item by model_name and brand_name and I SUCCEED to find 2 matching cars" ) {
            Car car4("Note", "Nissan", 1400.00);
            Car car5("Note", "Nissan", 1200.00);
            db.addCar(car4);
            db.addCar(car5);
            THEN("I get a list.size() == 2"){
                CHECK( db.getCarsByParam(-1,"Note","Nissan",-1).size() == 2 );
                CHECK_NOTHROW( db.getCarsByParam(-1,"Note","Nissan",-1) );
            }
        }


    }
}