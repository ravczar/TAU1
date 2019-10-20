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

        CarImpl nowy = new CarImpl(0 ,"Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl() );
        MYdatabase.carList.add(nowy);
        String kolor1 = MYdatabase.readSpecificRecord(0).getColor();
        Integer id1 = MYdatabase.readSpecificRecord(0).getId();
        System.out.println("To moj stary kolor auta: " + kolor1 + " ID STARE: " + id1 );

        MYdatabase.updateSpecificCarById(0 ,"Blue", "Maseratti", "Quatroporte", "Coupe", false, new EngineImpl(), new GearboxImpl());
        Integer id2 = MYdatabase.readSpecificRecord(0).getId();
        String kolor2 = MYdatabase.readSpecificRecord(0).getColor();
        System.out.println("To moj nowy kolor auta: " + kolor2 + " ID STARE: " + id2);
    
        System.out.println("SKASOWANO SAMOCHOD: ->>>"+ MYdatabase.deleteCar(0).getModel());
        System.out.println(MYdatabase.getNumberOfEntries() );
        System.out.println(MYdatabase.carList.size() );
        

    }
}
