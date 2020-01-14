#ifndef DATABASE_H
#define DATABASE_H
#include "Car.h"
#include <string>
#include <iostream>
#include <list>
using namespace std;

class DataBase{
    private:
        int id;
        list <Car*> carList; //http://cpp0x.pl/dokumentacja/standard-C++/list/45
    
    public:
        DataBase(){
            setId(0);
        }

    // Methods
    public:
        void showDetails(){
            cout<<"LOL"<<endl;
        }
        list <Car*> getAllCars(){
            return carList;
        }
        void addCarToList( Car* x){
            carList.push_back(x);
        }
        int showListSize(){
            return carList.size();
        }

    // Getters and setters
    public:
        void setId(int ID) {
        id = ID;
        }
        int getId() {
        return id;
        }
        

};

#endif // DATABASE_H