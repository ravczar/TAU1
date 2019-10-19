package cars.s16281.tau.labone.services;

import java.util.ArrayList;

public class DbImpl 
{
    public Integer numberOfEntries;
    public ArrayList<CarImpl> carList;

    public DbImpl(){
        this.numberOfEntries = 0;
        this.carList = new ArrayList<CarImpl>(); 
    }

    public void createCar (String color, String brand, String model, String type, Boolean hasAlloyRims, EngineImpl engine, GearboxImpl gearbox ){
        this.carList.add( new CarImpl(this.numberOfEntries, color, brand, model, type, hasAlloyRims, engine, gearbox ) );
        this.numberOfEntries++;
    }
    

}
