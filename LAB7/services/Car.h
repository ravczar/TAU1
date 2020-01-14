#ifndef CAR_H
#define CAR_H
#include <string>
#include <iostream>
using namespace std;

class Car{
    private:
      unsigned int id;
      string model;
      string brand;
      double engineCapacity;
    
    public:
      Car(){}
      Car( string MODEL, string BRAND, double ENGINECAPACITY){
        setModel(MODEL);
        setBrand(BRAND);
        setEngineCapacity(ENGINECAPACITY);
      }
      Car(unsigned int ID, string MODEL, string BRAND, double ENGINECAPACITY){
        setId(ID);
        setModel(MODEL);
        setBrand(BRAND);
        setEngineCapacity(ENGINECAPACITY);
      }

    // Getters and setters
    public:
      void setId(unsigned int ID) {
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
      unsigned int getId() {
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