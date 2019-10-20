package cars.s16281.tau.labone.services;

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

}
