#define CATCH_CONFIG_MAIN
#include "lib/catch.hpp"
#include "services/Iterator.h"
#include "services/Car.h"
#include "services/DataBase.h"

// https://www.sololearn.com/Play/CPlusPlus
// https://github.com/catchorg/Catch2/blob/master/docs/tutorial.md#top
TEST_CASE("Iterator operations", "[Iterator][constructors]") { // [Iterator][constructor of Car] present

  SECTION("The iterator object can be created") {
    REQUIRE_NOTHROW([]() { Iterator iterator; });
  }
  SECTION("Iterator iterates <0,1,2,3..>") {
    Iterator iterator;
    for(int i = 0; i <= 9; i++){
        REQUIRE( iterator.getAndIncrementValue() == i);
    }  
  }
}


