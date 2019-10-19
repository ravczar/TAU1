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
        DbImpl MYdatabase = new DbImpl();
        System.out.println("NUMBER OF ENTRIES: " + MYdatabase.getNumberOfEntries());
        System.out.println(MYdatabase.carList.size());

        CarImpl nowy = new CarImpl(1 ,"Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl() );
        //MYdatabase.carList.add(nowy);

        System.out.println("CAR ID:" + nowy.getId());
        System.out.println(MYdatabase.readSpecificRecord(0));
        
    }
}
