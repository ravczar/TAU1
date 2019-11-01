package cars.s16281.tau.labone.services;

import java.time.Clock;
import java.time.LocalDateTime;

public class CarImpl 
{
    private Integer id;
    private String color;
    private String brand;
    private String model;
    private String type;
    private Boolean hasAlloyRims;
    private EngineImpl engine;
    private GearboxImpl gearbox;

    private LocalDateTime creationDateTime;
    private LocalDateTime modificationDateTime;
    public LocalDateTime lastReadDateTime;


    /*public CarImpl(){}*/

    public CarImpl(
    int id, String color, String brand, 
    String model, String type, Boolean hasAlloyRims, 
    EngineImpl engine, GearboxImpl gearbox)
    {
        this.id = id;
        this.color = color;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.hasAlloyRims = hasAlloyRims;
        this.engine = engine;
        this.gearbox = gearbox;
    }


    /*  
        GETTERS
    */
    public Integer getId(){
        return this.id;
    }

    public String getColor(){
        return this.color;
    }

    public String getBrand(){
        return this.brand;
    }

    public String getModel(){
        return this.model;
    }

    public String getType(){
        return this.type;
    }

    public Boolean getHasAlloyRims(){
        return this.hasAlloyRims;
    }

    public EngineImpl getEngine(){
        return this.engine;
    }
    
    public GearboxImpl getGearbox(){
        return this.gearbox;
    }

    public LocalDateTime getCreationDateTime(){
        return this.creationDateTime;
    }

    public LocalDateTime getModificationDateTime(){
        return this.modificationDateTime;
    }

    public LocalDateTime getLastReadDateTime(){
        return this.lastReadDateTime;
    }

    /* 
        SETTERS 
    */
    public void setId(int Id){
        this.id = Id;
    }

    public void setColor(String Color){
        this.color = Color;
    }

    public void setBrand(String Brand){
        this.brand = Brand;
    }

    public void setModel(String Model){
        this.model = Model;
    }

    public void setType(String Type){
        this.type = Type;
    }

    public void setHasAlloyRims(Boolean HasAlloyRims){
        this.hasAlloyRims = HasAlloyRims;
    }

    public void setEngine(EngineImpl Engine){
        this.engine = Engine;
    }
    
    public void setGearbox(GearboxImpl Gearbox){
        this.gearbox = Gearbox;
    }

    public void setCreationDateTime(Boolean turnedOnOrOff, Clock databaseClock){
        if (turnedOnOrOff)
            this.creationDateTime = LocalDateTime.now(databaseClock);
        else
            this.creationDateTime = null;
    }

    public void setModificationDateTime(Boolean turnedOnOrOff, Clock databaseClock){
        if (turnedOnOrOff)
            this.modificationDateTime = LocalDateTime.now(databaseClock);
        else
            this.modificationDateTime = null;
    }

    public void setLastReadDateTime(Boolean turnedOnOrOff, Clock databaseClock){
        if (turnedOnOrOff)
            this.lastReadDateTime = LocalDateTime.now(databaseClock);
        else
            this.lastReadDateTime = null;
    }
}
