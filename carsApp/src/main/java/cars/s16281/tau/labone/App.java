package cars.s16281.tau.labone;

import cars.s16281.tau.labone.services.CarImpl;
import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World! This is your first TAU lab with mr. Puźniakowski" );
        DbImpl database = new DbImpl();
        System.out.println("NUMBER OF ENTRIES: " + database.numberOfEntries);
        System.out.println(database.carList.size());

        CarImpl nowy = new CarImpl(1 ,"Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl() );

        System.out.println(nowy.getHasAlloyRims());
        
    }
}
