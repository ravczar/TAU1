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
        void addCar( Car car ){
            car.setId(iterator.getAndIncrementValue());
            setId(iterator.getInteger());
            carList.push_back(car);
        }
        int getListSize(){
            return carList.size();
        }
        Car getCarById( unsigned int id ){ // https://www.w3schools.com/cpp/cpp_exceptions.asp 
            std::list<Car>::iterator it = carList.begin();
            for(it; it != carList.end(); ++it){
                if(it->getId() == id){
                    return *it;
                }
            }
            throw 404;
        }

        Car deleteCarById( unsigned int id ){
            Car carToBeDeleted;
            std::list<Car>::iterator it = carList.begin();
            for(it; it != carList.end(); ++it){
                if(it->getId() == id){
                    carToBeDeleted = *it;
                    carList.erase(it);
                    return carToBeDeleted;
                }
            }
            throw 404;
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