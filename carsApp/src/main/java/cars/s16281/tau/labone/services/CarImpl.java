package cars.s16281.tau.labone.services;

import java.text.SimpleDateFormat;

public class CarImpl 
{
    public Integer id;
    public String color;
    public String brand;
    public String model;
    public String type;
    public Boolean hasAlloyRims;
    public SimpleDateFormat dateOfProduction;
    public EngineImpl engine;
    public GearboxImpl gearbox;

    public CarImpl(){}

}
