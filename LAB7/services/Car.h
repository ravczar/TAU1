#ifndef CAR_H
#define CAR_H
#include <string>
#include <iostream>
using namespace std;

class Car{
    private:
    int id;
    string model;
    string brand;
    double engineCapacity;
    
    public:
    Car(){}
    Car(int ID, string MODEL, string BRAND, double CAPACITY){
        setId(ID);
        setModel(MODEL);
        setBrand(BRAND);
        setEngineCapacity(CAPACITY);
    }
    // Methods
    public:
    void showDetails(){
        cout<<"ID: "<<id<<". Model: "<<model<<". Brand: "<<brand<<". Engine_size: "<<engineCapacity<<endl;
    }

    // Getters and setters
    public:
    void setId(int ID) {
      id = ID;
    }
    void setModel(string MODEL) {
      model = MODEL;
    }
    void setBrand(string BRAND) {
      brand = BRAND;
    }
    void setEngineCapacity(double CAPACITY) {
      engineCapacity = CAPACITY;
    }
    int getId() {
      return id;
    }
    string getModel() {
      return model;
    }
    string getBrand() {
      return brand;
    }
    double getEngineCapacity() {
      return engineCapacity;
    }

};

#endif // CAR_H