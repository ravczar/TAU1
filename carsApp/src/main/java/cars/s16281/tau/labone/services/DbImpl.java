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
        //System.out.println("Baza jest długości: " + this.carList.size() );
        for (CarImpl _car : this.carList){
            if  ( _car.getId().equals(Id) ){
                System.out.println("Znaleziono rekord!: " + _car.getBrand());
                return _car;               
            }       
        }
        throw new NoSuchElementException("No such car in DB");
    }

    public CarImpl updateSpecificCarById(Integer IdOfUpdatedObject, String newColor, String newBrand, String newModel, String newType, Boolean newHasAlloyRims, EngineImpl newEngine, GearboxImpl newGearbox ) throws NoSuchElementException {
        for (CarImpl _car : this.carList){
            if  ( _car.getId().equals(IdOfUpdatedObject) ){
                System.out.println("Znaleziono rekord do udejtu!: " + _car.getBrand());
                _car.setColor(newColor);
                _car.setBrand(newBrand);
                _car.setModel(newModel);
                _car.setType(newType);
                _car.setHasAlloyRims(newHasAlloyRims);
                _car.setEngine(newEngine);
                _car.setGearbox(newGearbox);
                System.out.println("Car succesfully updated : " + _car.getModel() + "id remains same: " +  _car.getId() );
                return _car;               
            }       
        }
        throw new NoSuchElementException("No such car in DB, record not updated.");

    }

    public CarImpl deleteCar(int Id){
        //CarImpl _myCar = null;
        CarImpl _carFound = readSpecificRecord(Id);
        if ( _carFound != null){
            this.carList.remove(Id);
            System.out.println("Car with id: "+ Id +"  ..has been removed succesfully");
        }
        return _carFound;
    }


    /*
    * GETTERS
    */
    public Integer getNumberOfEntries(){
        return this.numberOfEntries;
    }



}
