#define CATCH_CONFIG_MAIN
#include "lib/catch.hpp"

// https://www.sololearn.com/Play/CPlusPlus
// https://github.com/catchorg/Catch2/blob/master/docs/tutorial.md#top

// SILNIA
unsigned int Factorial( unsigned int number ) {
    return number <= 1 ? number : Factorial(number-1)*number; 
}

TEST_CASE("Factorials are computed", "[factorial]" ){
    REQUIRE( Factorial(1)==1 );
    REQUIRE( Factorial(2)==2 );
    REQUIRE( Factorial(3)==6 );
    REQUIRE( Factorial(4)==24 ); 
    REQUIRE_FALSE( Factorial(0)==1 );
}