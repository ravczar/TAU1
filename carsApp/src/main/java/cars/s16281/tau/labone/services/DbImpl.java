package cars.s16281.tau.labone.services;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DbImpl 
{
    private Integer numberOfEntries;
    public ArrayList<CarImpl> carList;

    public DbImpl(){
        this.numberOfEntries = 0;
        this.carList = new ArrayList<CarImpl>(); 
    }

    public void createCar (String color, String brand, String model, String type, Boolean hasAlloyRims, EngineImpl engine, GearboxImpl gearbox ){
        this.carList.add( new CarImpl(this.numberOfEntries, color, brand, model, type, hasAlloyRims, engine, gearbox ) );
        this.numberOfEntries++;
    }

    public ArrayList<CarImpl> readAllRecords() {
        return this.carList;
    }
    
    public CarImpl readSpecificRecord(Integer Id) throws NoSuchElementException {
        System.out.println("Baza jest długości: " + this.carList.size() );
        for (CarImpl _car : this.carList){
            if  ( _car.getId().equals(Id) ){
                System.out.println("Znaleziono rekord!: " + _car.getBrand());
                return _car;               
            }       
        }
        throw new NoSuchElementException("No such car in DB");
    }


    /*
    * GETTERS
    */
    public Integer getNumberOfEntries(){
        return this.numberOfEntries;
    }



}
