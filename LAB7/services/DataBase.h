#ifndef DATABASE_H
#define DATABASE_H
#include "Car.h"
#include "Iterator.h"
#include <string>
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
            throw "Car Not Found";
        }

        list <Car> getCarsByParam(unsigned int id=-1, string model="none", string brand="none", double capacity=-1 ) {
            list<Car> cars_found;

            if( id == -1 && model =="none" && brand =="none" && capacity == -1)
                throw "Give at last one param";
            else{
                std::list<Car>::iterator it = carList.begin();
                for(it; it != carList.end(); ++it){
                    if( id != -1 && model != "none" && brand!= "none" && capacity != -1){
                        if(it->getId() == id && it->getModel() == model && it->getBrand() == brand && it->getEngineCapacity() == capacity){
                            cars_found.push_back(*it);
                        }
                    }
                    if( id == -1 && model != "none" && brand!= "none" && capacity != -1){
                        if(it->getModel() == model && it->getBrand() == brand && it->getEngineCapacity() == capacity){
                            cars_found.push_back(*it);
                        }
                    }
                    if( id == -1 && model == "none" && brand!= "none" && capacity != -1){
                        if(it->getBrand() == brand && it->getEngineCapacity() == capacity){
                            cars_found.push_back(*it);
                        }
                    }
                    if( id == -1 && model == "none" && brand == "none" && capacity != -1){
                        if(it->getEngineCapacity() == capacity){
                            cars_found.push_back(*it);
                        }
                    }
                    if( id == -1 && model == "none" && brand != "none" && capacity == -1){
                        if(it->getBrand() == brand){
                            cars_found.push_back(*it);
                        }
                    }
                    if( id == -1 && model != "none" && brand == "none" && capacity == -1){
                        if(it->getModel() == model){
                            cars_found.push_back(*it);
                        }
                    }
                    if( id != -1 && model == "none" && brand == "none" && capacity == -1){
                        if(it->getId() == id){
                            cars_found.push_back(*it);
                        }
                    }
                    if( id == -1 && model != "none" && brand != "none" && capacity == -1){
                        if(it->getModel() == model && it->getBrand() == brand ){
                            cars_found.push_back(*it);
                        }
                    }
                    if( id == -1 && model != "none" && brand == "none" && capacity != -1){
                        if(it->getModel() == model && it->getEngineCapacity() == capacity ){
                            cars_found.push_back(*it);
                        }
                    }         
                }
                if (cars_found.size() != 0 )
                    return cars_found;
                else
                    throw "Car Not Found";
            }

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
            throw "Car Not Found";
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