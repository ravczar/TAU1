package cars.s16281.tau.labone;

/*
    Helpful sites:
        http://shebang.pl/artykuly/java-8-przewodnik/
        https://www.baeldung.com/java-optional
        https://www.samouczekprogramisty.pl/testy-jednostkowe-z-uzyciem-mock-i-stub/
        https://www.javadoc.io/doc/org.mockito/mockito-core/1.10.19/org/mockito/junit/MockitoRule.html
*/

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.DbObjectHolder;
import cars.s16281.tau.labone.services.DbObjectProperties;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;
import cars.s16281.tau.labone.services.DateTimeProvider;;



public class DbDatesMockTest {

    private LocalDateTime timestamp;
    private int numberOfRecordsInInitialDatabase = 10;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    @Mock
    private DateTimeProvider provider;

    @Before
    public void setup() {
        newMockTimestamp();
        DbObjectProperties.setAllParamsToTrue();
    }

    // Tworzy znacznik czasowy teraz (przed rozpoczęciem testów)
    private LocalDateTime newMockTimestamp() {
        LocalDateTime timestamp = LocalDateTime.now();
        when(provider.get()).thenReturn(timestamp);
        this.timestamp = timestamp;
        return timestamp;
    }

    // Twory baze danych z datami i samochodami
    private DbImpl createDatabase() {
        DbImpl db = new DbImpl(provider);
        db.createCar("black", "Audi", "Quattro", "Sedan", true, new EngineImpl(), new GearboxImpl());
        db.createCar("black", "Alfa", "Spider", "Coupe", true, new EngineImpl(), new GearboxImpl());
        // można użyć tu metody randomowej: SetupDatabaseWitRecords(db,numberOfRecordsInInitialDatabase);
        return db;
    }

    // Random car for DB generator
    private DbImpl SetupDatabaseWitRecords (Integer howManyRecords){
        DbImpl initialDatabase = new DbImpl(provider);
        String colorTable[] = {"Green", "Gray", "Red", "Black", "Silver", "Brown", "Yellow", "Orange", "Purple", "Blue"};
        String brandTable[] = {"Volkswagen", "Audi", "Maseratti", "Porsche", "Fiat", "Ford", "Peugeot", "Hyundai", "Citroen", "FSO"};
        String modelTable[] = {"Acura", "Siena", "Quatroporte", "911s", "126p", "Focus", "3008", "A3", "Xara", "Polonez"};
        String typeTable[] = {"Coupe", "Cabrio", "Truck", "Combi", "Sedan", "Convertable", "Hatchback", "Roadster", "Pickup", "Van"};

        Random random = new Random();
       
        for (Integer i=0; i<howManyRecords; i++){
            Integer myRandomNumber = random.nextInt(10); // Generuje liczbę int z przedziału <baud> 0-10
            initialDatabase.createCar(colorTable[myRandomNumber], brandTable[myRandomNumber], modelTable[myRandomNumber], typeTable[myRandomNumber], true, new EngineImpl(), new GearboxImpl());
        }
        return initialDatabase;
    }

    @Test
    public void setup_InitialDatabase(){
        // Baza daych powinna zostac utworzona z 10 rekordami
        DbImpl initialDatabase = SetupDatabaseWitRecords(numberOfRecordsInInitialDatabase);
        int x = initialDatabase.getNumberOfEntries();
        assertEquals("Initial database should have 'numberOfRecordsInInitialDatabase' records",numberOfRecordsInInitialDatabase, x);
    }

    @Test
    public void checkingIfCarIsAddedToDataBase(){
        // Sprawdza czy samochód dodano do bazy danych i liczba aut się zwiększyła
        DbImpl initialDatabase = SetupDatabaseWitRecords(numberOfRecordsInInitialDatabase);
        initialDatabase.createCar("green", "maserati", "quatroperte", "sedan", true, new EngineImpl(), new GearboxImpl());
        int numberOfRecords = initialDatabase.getNumberOfEntries();
        assertEquals("Initial database should have 'numberOfRecordsInInitialDatabase' records",11, numberOfRecords);
    }

    @Test 
    public void addingCarToDataBaseSetsUpCorrectDateTime(){
        DbObjectProperties.setTrackCreationDate(true);
        DbImpl dataBase = createDatabase();
        dataBase.createCar("silver", "Audi", "A8", "limo", true, new EngineImpl(), new GearboxImpl());
        // Sprawdz czy data nie jest pusta
        Boolean creationDate = dataBase.carList.get(2).getCreationDate().isPresent();
        assertTrue("Car shoudl have present creation date", creationDate);
        // Sprawdź czy data się zgadza z mockiem
        LocalDateTime creationDateTime = dataBase.carList.get(2).getCreationDate().get();
        assertEquals(timestamp, creationDateTime);
    }

    @Test
    public void addingCarToDataBaseDoesNotSetUpModificationNorAccessDate(){
        // Po dodaniu samochodów data modyfikacji nie jest pusta więc metoda Optional.isPresent() zwróci False.
        DbImpl dataBase = createDatabase();
        Boolean modificationDate = dataBase.carList.get(1).getModificationDate().isPresent();
        assertEquals(false, modificationDate);
        // Po dodaniu samochodów data dostępu do rekordu jest pusta, bo sprawdzamy ją przez listę a nie przez metode db.getCarById(id)
        Boolean accessDate = dataBase.carList.get(1).getAccessDate().isPresent();
        assertEquals(false, accessDate);
    }

    @Test
    public void updatingCarInDatabaseShouldUpdateModificationDateTime(){
        // Dodajemy nowy samochód do bazy danych.
        DbImpl dataBase = createDatabase();
        String oldColor = dataBase.readSpecificRecord(0).getCar().getColor();
        String newColor = "pink";
        dataBase.updateSpecificCarById(0, newColor, "Audi", "Quattro", "Sedan", true, new EngineImpl(), new GearboxImpl());

        // Sprawdzenie czy kolor się zmienił
        String colorAfterMod = dataBase.readSpecificRecord(0).getCar().getColor();
        assertNotEquals("Kolor powinien być różny niż poprzedni. ", oldColor, colorAfterMod);
        assertEquals("Kolor powinien być pink. ",newColor, colorAfterMod);

        // Sprawdzenie czy data Modyfikacji nie jest pusta
        Boolean updateDate = dataBase.carList.get(0).getModificationDate().isPresent();
        assertTrue("Data nie powinna być pusta", updateDate);

        // Sprawdzenie czy data Modyfikacji jest ustawiona jak w mocku.
        LocalDateTime digitalUpdateDate = dataBase.carList.get(0).getModificationDate().get();
        assertEquals(timestamp, digitalUpdateDate);
    }

    @Test
    public void readingCarFromDatabaseUsingDbMethod_readSpecificRecord_ShouldUpdateAccessDateTime(){
        //Sprawdzam czy odczyt z bazy używając metody np. db.readSpecificRecord(Integer Id) zmienia datę i czas odczytu
        DbImpl dataBase = createDatabase();

        // Przed odczytem data powinna być pusta
        Boolean accessDate = dataBase.carList.get(1).getAccessDate().isPresent();
        Boolean modDate = dataBase.carList.get(1).getModificationDate().isPresent();
        assertFalse("Data akcesu musi być pusta", accessDate);
        assertFalse("Data modyfikacji być pusta", modDate);

        // Następuje odczyt
        dataBase.readSpecificRecord(1);

        // Sprawdzenie czy data powstała po odczycie
        accessDate = dataBase.carList.get(1).getAccessDate().isPresent();
        assertTrue("Data nie powinna być pusta", accessDate);
        
        // Sprawdzenie czy data posiada spodziewaną wartość
        LocalDateTime accessDateTime = dataBase.carList.get(0).getAccessDate().get();
        assertEquals(timestamp, accessDateTime);
    }

    @Test
    public void readingCarFromDatabaseUsing_readAllRecords_ShouldUpdateAccessDateTime(){
        //Sprawdzam czy odczyt z bazy używając metody np. db.readAllRecords() zmienia datę i czas odczytu 'AccessDate'
        DbImpl dataBase = createDatabase();

        // Przed odczytem data powinna być pusta
        Boolean accessDate = dataBase.carList.get(1).getAccessDate().isPresent();
        Boolean modDate = dataBase.carList.get(1).getModificationDate().isPresent();
        assertFalse("Data akcesu musi być pusta", accessDate);
        assertFalse("Data modyfikacji być pusta", modDate);

        //  Następuje odczyt
        ArrayList<DbObjectHolder> listOfDbObjects = dataBase.readAllRecords();

        //  Sprawdzenie wyrywkowo czy data powstała po odczycie
        accessDate = dataBase.carList.get(1).getAccessDate().isPresent();
        assertTrue("Data nie powinna być pusta", accessDate);

        // Sprawdzenie czy data posiada spodziewaną wartość
        for (DbObjectHolder _holder : listOfDbObjects){
            LocalDateTime accessTime =  _holder.getAccessDate().get();
            assertEquals(timestamp, accessTime);
            //System.out.println(_holder.getCar().getBrand());
        }
    }

    @Test
    public void deletingCarWillAffectModificationDateOfDeletedUnit(){
        DbImpl dataBase = createDatabase();
        // Przed odczytem data powinna być pusta
        Boolean modDate = dataBase.carList.get(1).getModificationDate().isPresent();
        assertFalse("Data modyfikacji być pusta", modDate);
        // Po skasowaniu data modyfikacji rekordu powinna być widoczna
        DbObjectHolder carDeleted = dataBase.deleteCar(1);
        Boolean deleteDate = carDeleted.getModificationDate().isPresent();
        assertTrue("Data modyfikacji być pusta", deleteDate);
        // Po skasowaniu data modyfikacji sie zgadza z mock: timestamp
        LocalDateTime dateOfDeletion = carDeleted.getModificationDate().get();
        assertEquals(timestamp, dateOfDeletion);
    }

    @Test
    public void DB_does_not_track_CreateDateTime_when_method_setTrackCreationDate_get_param_false(){
        // Database will not record createDateTime when we pass 'false' parameter to method setTrackCreationDate(false)
        DbObjectProperties.setTrackCreationDate(false);
        DbImpl dataBase = createDatabase();
        DbObjectHolder myHolder = dataBase.carList.get(0);
        assertEquals(myHolder.getCreationDate(), Optional.empty());
    }

    @Test
    public void DB_does_not_track_ModifyDateTime_when_method_setTrackModificationDate_get_param_false(){
        // Database will not record modificationDateTime when we pass 'false' parameter to method setTrackModificationDate(false)
        DbObjectProperties.setTrackModificationDate(false);
        DbImpl dataBase = createDatabase();
        dataBase.updateSpecificCarById(0, "Brown", "VW", "T-Roc", "SUV", true, new EngineImpl(), new GearboxImpl());
        DbObjectHolder myHolder = dataBase.carList.get(0);
        assertEquals(myHolder.getModificationDate(), Optional.empty());
    }

    @Test
    public void DB_does_not_track_AccessDateTime_when_method_setTrackAccessDate_get_param_false(){
        // Database will not record AccessDateTime when we pass 'false' parameter to method setTrackAccessDate(false)
        DbObjectProperties.setTrackAccessDate(false);
        DbImpl dataBase = createDatabase();

        DbObjectHolder myHolder = dataBase.readSpecificRecord(0);
        assertEquals(myHolder.getAccessDate(), Optional.empty());
    }
    
    @Test
    public void DB_will_start_track_AccessDateTime_when_method_setTrackAccessDate_get_param_true(){
        // Database will regain possibility to record AccessDateTime once we pass 'true' parameter to method setTrackAccessDate(true)
        DbObjectProperties.setTrackAccessDate(false);
        DbImpl dataBase = SetupDatabaseWitRecords(10);
        DbObjectHolder myHolder = dataBase.readSpecificRecord(9);
        assertEquals(myHolder.getAccessDate(), Optional.empty());

        DbObjectProperties.setTrackAccessDate(true);
        DbObjectHolder myHolder2 = dataBase.readSpecificRecord(9);
        LocalDateTime accessDateRegained = myHolder2.getAccessDate().get();
        assertEquals(accessDateRegained, timestamp);
    }
 
    @Test
    public void DB_will_start_track_ModifyDateTime_when_method_setTrackModificationDate_get_param_true(){
        // Database will regain possibility to record ModifyDateTime once we pass 'true' parameter to method setTrackModificationDate(true)
        DbObjectProperties.setTrackModificationDate(false);
        DbImpl dataBase = SetupDatabaseWitRecords(10);
        DbObjectHolder modifiedCar = dataBase.updateSpecificCarById(0, "pink", "Audi", "A2", "Sedan", true, new EngineImpl(), new GearboxImpl());
        assertEquals("Obiekt @DoNotTrack nie jest pusty!", modifiedCar.getModificationDate(), Optional.empty());
        
        DbObjectProperties.setTrackModificationDate(true);
        LocalDateTime currentTimeStamp = newMockTimestamp();
        DbObjectHolder modifiedCarPrim = dataBase.updateSpecificCarById(1, "green", "Audi", "A3", "hathback", true, new EngineImpl(), new GearboxImpl());
        assertEquals("Obiekt @ PleaseTrackModification date jest różny od porównywanego", modifiedCarPrim.getModificationDate().get(), currentTimeStamp);
    }
    
    @Test
    public void DB_will_start_track_CreateDateTime_when_method_setTrackCreationnDate_get_param_true(){
        // Database will regain possibility to record CreateDateTime once we pass 'true' parameter to method  setCreationDate(true)
        DbObjectProperties.setTrackCreationDate(false);
        DbImpl dataBase = SetupDatabaseWitRecords(10);
        DbObjectHolder myHolder = dataBase.readSpecificRecord(9);
        assertEquals(myHolder.getCreationDate(), Optional.empty());
        assertEquals(false, DbObjectProperties.isTrackCreationDate());

        DbObjectProperties.setTrackCreationDate(true);
        LocalDateTime currentTimeStamp = newMockTimestamp();
        dataBase.createCar("blue", "Renault", "Kajar", "SUV", false, new EngineImpl(), new GearboxImpl());
        DbObjectHolder myHolderPrim = dataBase.readSpecificRecord(10);
        assertEquals(myHolderPrim.getCreationDate().get(), currentTimeStamp);
        assertEquals(true, DbObjectProperties.isTrackCreationDate());
    }
}