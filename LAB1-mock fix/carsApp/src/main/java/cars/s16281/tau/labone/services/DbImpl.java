package cars.s16281.tau.labone.services;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DbImpl 
{
    private Integer numberOfEntries;
    private DateTimeProvider provider;
    public ArrayList<DbObjectHolder> carList;
    
    public DbImpl(){
        this.numberOfEntries = 0;
        this.carList = new ArrayList<DbObjectHolder>(); 
    }

    public DbImpl(DateTimeProvider dateTimeProvider){
        this.numberOfEntries = 0;
        this.carList = new ArrayList<DbObjectHolder>(); 
        this.provider = dateTimeProvider;
    }

    public DbObjectHolder createCar (String color, String brand, String model, String type, Boolean hasAlloyRims, EngineImpl engineImpl, GearboxImpl gearboxImpl ) throws IllegalArgumentException{
        for (DbObjectHolder _holder : this.carList){
            if  ( _holder.getCar().getId().equals(this.numberOfEntries) ){
                    throw new IllegalArgumentException("Car with this ID already exists,cannot create");   
                }       
        }// (DateTimeProvider provider, CarImpl car)
        DbObjectHolder carHolder = new DbObjectHolder( provider, new CarImpl(this.numberOfEntries, color, brand, model, type, hasAlloyRims, engineImpl, gearboxImpl ) );
        this.carList.add(carHolder);
        this.numberOfEntries++;         
        return carHolder;      
        
    }

    public ArrayList<DbObjectHolder> readAllRecords() {
        // Ustawienie accessDate przy doczycie wszystkich
        for (DbObjectHolder _holder : this.carList){
            _holder.updateAccessDate();
        }
        return this.carList;
    }
    
    public DbObjectHolder readSpecificRecord(Integer Id) throws NoSuchElementException {
        //System.out.println("Baza jest długości: " + this.carList.size() );
        for (DbObjectHolder _holder : this.carList){
            if  ( _holder.getCar().getId().equals(Id) ){
                //System.out.println("Znaleziono rekord w readSpecificRecords!: " + _holder.getCar().getBrand());
                // Update Access Date of a dbObject.
                _holder.updateAccessDate();
                return _holder;               
            }       
        }
        throw new NoSuchElementException("No such car in DB");
    }

    public DbObjectHolder updateSpecificCarById(Integer IdOfUpdatedObject, String newColor, String newBrand, String newModel, String newType, Boolean newHasAlloyRims, EngineImpl newEngine, GearboxImpl newGearbox ) throws NoSuchElementException {
        for (DbObjectHolder _holder : this.carList){
            if  ( _holder.getCar().getId().equals(IdOfUpdatedObject) ){
                //System.out.println("Znaleziono rekord do updejtu!: " + _holder.getCar().getBrand());
                _holder.getCar().setColor(newColor);
                _holder.getCar().setBrand(newBrand);
                _holder.getCar().setModel(newModel);
                _holder.getCar().setType(newType);
                _holder.getCar().setHasAlloyRims(newHasAlloyRims);
                _holder.getCar().setEngine(newEngine);
                _holder.getCar().setGearbox(newGearbox);
                //System.out.println("Car succesfully updated : " + _holder.getCar().getModel() + "id remains same: " +  _holder.getCar().getId() );
                //update creation date for specific car with given id.
                _holder.updateModificationDate();
                return _holder;               
            }       
        }
        throw new NoSuchElementException("No such car in DB, record not updated.");

    }

    public DbObjectHolder deleteCar(int Id){
        CarImpl _carFound = readSpecificRecord(Id).getCar();
        DbObjectHolder _objectDeleted = new DbObjectHolder(provider, _carFound);
        if ( _carFound != null){
            // update last access date (removal date)
            this.carList.get(Id).updateModificationDate();
            _objectDeleted = this.carList.get(Id);
            this.carList.remove(Id);
            //System.out.println("Car with id: "+ Id +"  ..has been removed succesfully");
        }
        return _objectDeleted;
    }


    /*
    * GETTERS
    */
    public Integer getNumberOfEntries(){
        return this.numberOfEntries;
    }

    public ArrayList<DbObjectHolder> getCarList(){
        return this.carList;
    }

    /*
    * SETTERS
    */
    public void setNumberOfEntries(Integer number){
        this.numberOfEntries = number;
    }

}
