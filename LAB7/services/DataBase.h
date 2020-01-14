#ifndef DATABASE_H
#define DATABASE_H
#include "Car.h"
#include "Iterator.h"
#include <string>
#include <iostream>
#include <list>
using namespace std;

class DataBase{
    private:
        Iterator iterator;
        list<Car> carList; //http://cpp0x.pl/dokumentacja/standard-C++/list/45
    
    public:
        DataBase(){
            setId(0);
        }

    // Methods
    public:
        void showDetails(){
            cout<<"LOL"<<endl;
        }
        list<Car> getAllCars(){
            return carList;
        }
        void addCarToList( Car car ){
            car.setId(iterator.getAndIncrementValue());
            setId(iterator.getInteger());
            carList.push_back(car);
        }
        int getListSize(){
            return carList.size();
        }

    // Getters and setters
    public:
        void setId(unsigned int ID) {
            iterator.setInteger(ID);
        }
        unsigned int getId() {
            return iterator.getInteger();
        }
        

};

#endif // DATABASE_H