package cars.s16281.tau.labone.services;

import java.lang.reflect.Method;
import java.time.Clock;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbImpl 
{
    private Integer numberOfEntries;
    public ArrayList<CarImpl> carList;
    private Clock currentClock = Clock.systemUTC();
    private Boolean createDateTime = true;
    private Boolean modifyDateTime = true;
    private Boolean readDateTime = true;

    public DbImpl(){
        this.numberOfEntries = 0;
        this.carList = new ArrayList<>();
    }

    public Boolean createCar (String color, String brand, String model, String type, Boolean hasAlloyRims, EngineImpl engineImpl, GearboxImpl gearboxImpl ) throws IllegalArgumentException{
        for (CarImpl _car : this.carList){
            if  ( _car.getId().equals(this.numberOfEntries) ){
                    throw new IllegalArgumentException("Car with this ID already exists,cannot create");   
                }       
        }
        this.carList.add( new CarImpl(this.numberOfEntries, color, brand, model, type, hasAlloyRims, engineImpl, gearboxImpl ) );
        // adding date of creation basing on true/false argument (turned on mode or off mode)
        this.carList.get(this.numberOfEntries).setCreationDateTime(this.createDateTime, this.currentClock);
        this.numberOfEntries++;         
        return true;      
        
    }

    public ArrayList<CarImpl> readAllRecords() {
        for (CarImpl _car : this.carList){
            _car.setLastReadDateTime(this.readDateTime,this.currentClock);
        }
        return this.carList;
    }
    
    public CarImpl readSpecificRecord(Integer Id) throws NoSuchElementException {
        //System.out.println("Baza jest długości: " + this.carList.size() );
        for (CarImpl _car : this.carList){
            if  ( _car.getId().equals(Id) ){
                System.out.println("Znaleziono rekord w readSpecificRecords!: " + _car.getBrand());
                // adds new date of reading of given record.
                _car.setLastReadDateTime(this.readDateTime,this.currentClock);
                return _car;               
            }       
        }
        throw new NoSuchElementException("No such car in DB");
    }

    // LAB3 - search database by given field of class CarImpl
    public ArrayList<CarImpl> searchWithRegex(String fieldOfClassCar, String regex) {
        ArrayList<CarImpl> searchResults = new ArrayList<>();
        Pattern pattern = Pattern.compile( regex );
        String method ="";

        for (CarImpl _car : this.carList){
            switch(fieldOfClassCar){
                case "color":
                    method= _car.getColor(); break;
                case "brand":
                    method+=_car.getBrand(); break;
                case "model":
                    method+=_car.getModel(); break;
                case "type":
                    method+=_car.getType(); break;
                case "hasAlloyRims":
                    method+=_car.getHasAlloyRims(); break;
            }
            String xxx = _car.getModel();
            Matcher matcher = pattern.matcher(method);
            if(matcher.matches()){
                searchResults.add(_car);
            }
        }
        return searchResults;
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
                // once we modify details we set modification time in Car class
                _car.setModificationDateTime(this.modifyDateTime, this.currentClock);
                return _car;               
            }       
        }
        throw new NoSuchElementException("No such car in DB, record not updated.");

    }

    public CarImpl deleteCar(int Id){
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

    public Clock getClock(){
        return this.currentClock;
    }

    public ArrayList<CarImpl> getCarList(){
        return this.carList;
    }

    /*
    * SETTERS
    */
    public void setNumberOfEntries(Integer number){
        this.numberOfEntries = number;
    }

    public void setWhatDatesDatabaseRecord(Boolean createDate, Boolean modifyDate, Boolean readDate){
        this.createDateTime = createDate;
        this.modifyDateTime = modifyDate;
        this.readDateTime = readDate;
    }

    public void setClockForMock( Clock fixedClock ){
        this.currentClock = fixedClock;
    }



}
