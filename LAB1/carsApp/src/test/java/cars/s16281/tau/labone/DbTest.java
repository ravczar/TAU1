package cars.s16281.tau.labone;

import static org.junit.Assert.assertNotEquals;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import cars.s16281.tau.labone.services.CarImpl;
import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;
import junit.framework.TestCase;


@RunWith(JUnit4.class)
public class DbTest extends TestCase {
    // Generic test for Junit to prevent this error from compilation @ mvn test : warning(junit.framework.TestSuite$1): No tests found in carsApp
    public void testPass(){
        assertEquals("matching 2 with 2", 2,2);
    }
    
     //Database Implementation ONY 
     //https://www.guru99.com/junit-assert.html very good site to get a grip on JUNIT TDD
     //SimpleDateFormat @ java https://www.tutorialspoint.com/java/java_date_time.htm

    @Test
    public void DbIsImplementedTest() {
        assertNotNull(new DbImpl());
    }
    @Test
    public void DbWhenCreatedContainsCounterThatIsNotNull() {
        DbImpl database = new DbImpl();
        assertNotNull(database.getNumberOfEntries());
    }
    @Test
    public void DbWhenCreatedContainsArrayListThatIsNotNull() {
        DbImpl database = new DbImpl();
        assertNotNull(database.carList);
    }
    @Test
    public void DbConstructorCreatesArrayListWithZeroRecords() {
        DbImpl database = new DbImpl();
        assertEquals(0, database.carList.size());
    }
    @Test
    public void DbField_numberOfEntries_EqualsZeroOnCreation() {
        DbImpl database = new DbImpl();
        assertNotNull(database.getNumberOfEntries());
        assertSame(0, database.getNumberOfEntries());
    }


    
    
    /////// Database *C*RUD methods ONY
    
    @Test
    public void DbField_CreateCar_ShouldAddNewCarToOurArrayList() {
        DbImpl database = new DbImpl();
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan", true, new EngineImpl(), new GearboxImpl() );
        assertEquals(1, database.carList.size());
    }
    @Test
    public void DbField_WhenCreatingNewCarNumberOfEntriesRisesByOneWithEachNewCarAddedToTheList() {
        DbImpl database = new DbImpl();
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        assertSame(1, database.getNumberOfEntries());

        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        assertSame(2, database.getNumberOfEntries());
    }

    @Test
    public void AddingNewCarActuallyCreatesNewObjectThatIsNotNull() {
        DbImpl database = new DbImpl();

        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

         CarImpl RecentlyAddedObjectToTheList = database.carList.get(0);
        
        assertNotNull(RecentlyAddedObjectToTheList);
    }

    @Test
    public void DbField_WhenAddingNewCarToList_CarsIdIsPostIncremented() {
        DbImpl database = new DbImpl();

        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        assertSame(0, database.carList.get(0).getId());

        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        assertSame(1, database.carList.get(1).getId());
    }
    @Test
    public void DbField_WhenAddingNewCarToListAndValueOfField_numberOfEntries_SomehowWillNotBeinCremented_ShouldGetException() throws IllegalArgumentException{
        DbImpl database = new DbImpl();

        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        assertSame(0, database.carList.get(0).getId());

        //Number of entries is incrmented automaticaly when we add new Car and it goes as an Id for a #CarList and id for a #CarImpl Class.
        //We simulate abbreviation, that it somehow "got stuck at previous value"
        database.setNumberOfEntries(0);
        
        // Expect to get en error
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Car with this ID already exists,cannot create");
        
        // Cause an error by adding new car with same id (id in Car comes from field NumberOfEntries in DBImplementation class.)
        database.createCar("Green", "Fiat", "Ducato", "Van",
         false, new EngineImpl(), new GearboxImpl());

        // Because we have only one record, and the other one was forced stop from adding to DB, below is TRUE (DB size =1, as it contains only one car.)
        assertEquals(1, database.carList.size());

    }


    
    //// Database C*R*UD methods ONY 
    
    //ReadAllRecords
    @Test
    public void MethodGetNumberOfEntriesIsImplemented() {
        assertNotNull(new DbImpl().readAllRecords());
    }
    @Test
    public void MethodGetNumberOfEntriesReturnesProperRecordAmountAfterAddingThreeNewCars() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        
        assertEquals(3, database.readAllRecords().size());
        assertSame(3, database.getNumberOfEntries());
        assertTrue(database.readAllRecords().size() == database.getNumberOfEntries() );       
    }
    //ReadSpecificRecord
    @Test
    public void readSpecificRecord_metheod_ReturnsAnObjectThatIsNotNull() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        Integer exactIdOfRecentlyAddedCar = database.carList.size()-1;

        assertNotNull(database.readSpecificRecord(exactIdOfRecentlyAddedCar));   
    }
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Test
    public void readSpecificRecord_metheod_ThrowsExceptionWhenObjectIsNotOntheList() throws NoSuchElementException {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        Integer idOfRecentlyAddedCarIncrementedByOne = database.carList.size();
        exception.expect(NoSuchElementException.class);
        exception.expectMessage("No such car in DB");
        
        assertNotNull( database.readSpecificRecord(idOfRecentlyAddedCarIncrementedByOne) );
    }

    @Test
    public void readSpecificRecord_metheod_ReturnDifferentObjectsWhenGivenDifferentIDsAsParameter() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        database.createCar("Black", "Audi", "A4", "Kombi",
         false, new EngineImpl(), new GearboxImpl());

        CarImpl x = database.readSpecificRecord(0);
        CarImpl y = database.readSpecificRecord(1);

        assertNotEquals("Objects are not equal", x, y);
    }
    @Test
    public void readSpecificRecord_metheod_ReturnExactlySameObjectWhenAskedForSameID_Twice() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        CarImpl x = database.readSpecificRecord(0);
        CarImpl y = database.readSpecificRecord(0);

        assertSame(x, y);
    }
    
    //// Database CR*U*D methods ONY 
    
    @Test
    public void updateSpecificCarById_metheod_ReturnsAnObjectThatIsNotNull() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        Integer exactIdOfRecentlyAddedCar = database.carList.size()-1;

        assertNotNull(database.updateSpecificCarById(
            exactIdOfRecentlyAddedCar,"Blue", "Maseratti", "Quatroporte", "Coupe", false, new EngineImpl(), new GearboxImpl())
             );  
    }
    
    @Test
    public void updateSpecificCarById_metheod_ThrowsExceptionWhenObjectIsNotOntheList() throws NoSuchElementException {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        Integer idOfRecentlyAddedCarIncrementedByOne = database.carList.size();
        exception.expect(NoSuchElementException.class);
        exception.expectMessage("No such car in DB, record not updated.");
        
        assertNotNull( database.updateSpecificCarById(
            idOfRecentlyAddedCarIncrementedByOne,"Blue", "Maseratti", "Quatroporte", "Coupe", false, new EngineImpl(), new GearboxImpl())
            );    
    }
    
    @Test
    public void updateSpecificCarById_metheod_UpdateSuccessDoesNotChangeTheIdOfAnObject() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        
        CarImpl carBeforeAnUpdate = database.readSpecificRecord(0);
        Integer oldId = carBeforeAnUpdate.getId();
        
        //data now updated to new values, id remains the same
        database.updateSpecificCarById(0 ,"Blue", "Maseratti", "Quatroporte", "Coupe", false, new EngineImpl(), new GearboxImpl());
        CarImpl carAfterAnUpdate = database.readSpecificRecord(0);

        //Id remains the same! 0 -> 0
        assertSame(oldId,                  carAfterAnUpdate.getId());

    }
    @Test
    public void updateSpecificCarById_metheod_RecordsBeforeUpdateAndAfterUpdateDiffer_case_update_SUCCESS() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        //Data set without copy of an object :-(
        CarImpl carBeforeAnUpdate = database.readSpecificRecord(0);
        Integer oldId = carBeforeAnUpdate.getId();
        String oldColor = carBeforeAnUpdate.getColor();
        String oldBrand = carBeforeAnUpdate.getBrand();
        String oldModel = carBeforeAnUpdate.getModel();
        String oldType = carBeforeAnUpdate.getType();
        Boolean oldHasAlloyRims = carBeforeAnUpdate.getHasAlloyRims();
        EngineImpl oldEngine = carBeforeAnUpdate.getEngine();
        GearboxImpl oldGearbox = carBeforeAnUpdate.getGearbox();
        
        //data now updated to new values, id remains the same
        database.updateSpecificCarById(0 ,"Blue", "Maseratti", "Quatroporte", "Coupe", false, new EngineImpl(), new GearboxImpl());
        CarImpl carAfterAnUpdate = database.readSpecificRecord(0);

        //Id remains the same! 0 -> 0
        assertSame(oldId,                  carAfterAnUpdate.getId());
        assertNotEquals(oldColor,               carAfterAnUpdate.getColor());
        assertNotEquals(oldBrand,          carAfterAnUpdate.getBrand());    
        assertNotEquals(oldModel,          carAfterAnUpdate.getModel());    
        assertNotEquals(oldType,           carAfterAnUpdate.getType()); 
        assertNotEquals(oldHasAlloyRims,   carAfterAnUpdate.getHasAlloyRims()); 
        assertNotEquals(oldEngine,         carAfterAnUpdate.getEngine()); 
        assertNotEquals(oldGearbox,        carAfterAnUpdate.getGearbox());

    }
    
    ///// Database CRU*D* methods ONY 
    
    @Test
    public void deleteCar_metheod_ReturnsAnObjectThatIsNotNull() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        Integer exactIdOfRecentlyAddedCar = database.carList.size()-1;

        assertNotNull(database.deleteCar( exactIdOfRecentlyAddedCar ));  
    }

    @Test
    public void deleteCar_metheod_DeletesCarFromOurListOfCarsSucessfully() {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        Integer exactIdOfRecentlyAddedCar = database.carList.size()-1;

        assertNotNull(database.deleteCar( exactIdOfRecentlyAddedCar )); 
        assertSame(0, database.carList.size());
        assertTrue(database.carList.isEmpty());
    }

    @Test
    public void deleteCar_metheod_DeleteOneCarOutOfOneAndCheckIfDatabaseIsEmpty() {
        DbImpl database = new DbImpl();
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan", true, new EngineImpl(), new GearboxImpl());
        database.deleteCar( 0 ); 
        assertTrue(database.carList.isEmpty());
    }
    @Test
    public void deleteCar_metheod_DeleteCertainCarAndCheckIfStillCanBefound() throws NoSuchElementException {
        DbImpl database = new DbImpl();
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan", true, new EngineImpl(), new GearboxImpl());
        database.deleteCar( 0 ); 
        exception.expect(NoSuchElementException.class);
        exception.expectMessage("No such car in DB");
        assertTrue(database.carList.isEmpty());
        assertTrue(database.readSpecificRecord(0) == null);
    }

    @Test
    public void deleteCar_metheod_WhenCarToBeDeletedIsNotFoundExeptionToBeThrown() throws NoSuchElementException {
        DbImpl database = new DbImpl();
        
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        exception.expect(NoSuchElementException.class);
        exception.expectMessage("No such car in DB");

        Integer indexOutOfRange = 99;

        assertNotNull(database.deleteCar( indexOutOfRange )); 
        assertSame(0, database.carList.size());
        assertTrue(database.carList.isEmpty());
    }
    
    /*
    *
    *   **  LAB 2 dodaj daty <createDate, modifyDate, readDate> do bazy danych.
    *
    */

    /// Field exist in Class CarImpl and can be read.
    @Test
    public void Class_CarImpl_ContainsReadableField_creationDateTime_andItIsNotNull() {
        DbImpl database = new DbImpl();

        database.setWhatDatesDatabaseRecord(true, false, false);

        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        //System.out.println("Date of creation in database : " + database.readSpecificRecord(0).getCreationDateTime());
        assertNotNull("Field creationDateTime contains null, check value it is set with at init.", database.readSpecificRecord(0).getCreationDateTime()); 
    }

    @Test
    public void Class_CarImpl_ContainsReadableField_modificationDateTime_andItContainsNullAtCreation() {
        DbImpl database = new DbImpl();
               
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        //System.out.println("Data modyfikacji obiektu: " + database.readSpecificRecord(0).getModificationDateTime());
        assertNull("Field creationDateTime does not exist", database.readSpecificRecord(0).getModificationDateTime()); 
    }

    @Test
    public void Class_CarImpl_ContainsReadableField_lastReadDateTime_andItContainsNullAtCreation() {
        DbImpl database = new DbImpl();
               
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        database.readSpecificRecord(0);

        //System.out.println("Data odczytania obiektu: " + database.readSpecificRecord(0).getLastReadDateTime());
        assertNotNull("Field: lastReadDateTime does not exist", database.readSpecificRecord(0).getLastReadDateTime()); 
    }

    // Modification field is updated when we call for an update (stops beeing null - as on initial object creation).

    @Test
    public void setModificationDateTimeMethodChangeInitialNullValueOfField_modificationDateTime_To_Now() {
        DbImpl database = new DbImpl();
               
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());

        database.readSpecificRecord(0).setModificationDateTime(true, mockClock);

        //System.out.println("Data modyfikacji: " + database.readSpecificRecord(0).getModificationDateTime() );
        assertNotNull("Field creationDateTime does not exist", database.readSpecificRecord(0).getModificationDateTime() ); 
    }

    // LastReadDateTime field is updated when we call for an update (stops beeing null - as on initial object creation).

    @Test
    public void setLastReadDateTimeMethodChangeInitialNullValueOfField_lastReadDateTime_To_Now() {
        DbImpl database = new DbImpl();
                   
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
    
        database.readSpecificRecord(0).setLastReadDateTime(true, mockClock);
    
        //System.out.println("Data ostatniego odczytu: " + database.readSpecificRecord(0).getLastReadDateTime() );
        assertNotNull("Field lastReadDateTime value is Null", database.readSpecificRecord(0).getLastReadDateTime() ); 
    }

    // Mock Clock
    private int year = 2017;  // Be this a specific 
    private int month = 2;    // date we need 
    private int day = 3; 
    Clock mockClock =
      Clock.fixed(
        LocalDateTime.of(year, month, day, 10, 20, 33).toInstant(OffsetDateTime.now().getOffset()),
        Clock.systemDefaultZone().getZone()
      );







    /*
    @Mock
    private DbImpl mockedDatabase;

    @Before
    public void Setup(){
        mockedDatabase.setClockForMock(this.mockClock);
        SetupMockedDatabaseWitRecords(mockedDatabase, 10);
        when(mockedDatabase.getNumberOfEntries()).thenReturn(10);
    }
    @Before
    public void initMocks() {      
        MockitoAnnotations.initMocks(this);  
    }



    @Test
    public void DbImpl_objectMockedAndIsNotNull() {
        assertNotNull("Error mockTest 1: database object is not created", mockedDatabase);
    }

    @Test
    public void DbImpl_listOfCarsIsAccessibleAndHasZeroRecordsAtStart() {

        assertSame("Error mockTest 2: database has some records ", 0, mockedDatabase.getNumberOfEntries() );
        verify(mockedDatabase).getNumberOfEntries();
        
    }

    @Test
    public void checkIfField_numberOfEntries_incrementsWhenAddingNewCar(){
        EngineImpl fakeEngine = new EngineImpl();
        GearboxImpl fakeGearbox = new GearboxImpl();
        mockedDatabase.createCar( "color", "brand", "model", "type", true, fakeEngine, fakeGearbox );
        
        // Co ma zwracać w takiej sytuacji po dodaniu rekordu
        when(mockedDatabase.getNumberOfEntries()).thenReturn(1);

        //Sprawdzamy, czy mamy dokladnie jeden rekord.
        assertSame(1, mockedDatabase.getNumberOfEntries());

        verify(mockedDatabase).createCar("color", "brand", "model", "type", true, fakeEngine, fakeGearbox);

    }

    @Test
    public void checkIf_DbImpl_SetsProperDateAndTimeWhenWeReadGivenRecord(){
        DbImpl database = new DbImpl();
        
        // Create new object
        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        // Read given object to setup time of modification
        database.readSpecificRecord(0);
        // Modify object to get date and time
        database.updateSpecificCarById(0, "gay", "Volvo", "xc90", "suv", false, new EngineImpl(), new GearboxImpl());

        LocalDateTime utworzenie = database.readSpecificRecord(0).getCreationDateTime();
        LocalDateTime odczytanie = database.readSpecificRecord(0).getLastReadDateTime();
        LocalDateTime modyfikacja = database.readSpecificRecord(0).getModificationDateTime();

        System.out.println("Data utworzenia : " +  utworzenie);
        System.out.println("Data odczytu : " +  odczytanie);
        System.out.println("Data modyfikacji : " +  modyfikacja);

        assertNotEquals("Daty są takie same!",utworzenie, odczytanie);
        assertNotEquals("Daty są takie same!",utworzenie, modyfikacja);
        // i tak dalej kolejne porównania.

    }

    // ***** poważne testowanie na mocku
    @Test
    public void CheckingIf_CarImpl_ClassHasMethodToSetCreatDatTimeField(){
       //when(mockedDatabase.readSpecificRecord(0).getCreationDateTime()).thenReturn(LocalDateTime.now(mockedClock)); 
       //System.out.println("Zegar mocka:"  + LocalDateTime.now(mockClock));
       //verify(mockedDatabase, atLeastOnce()).readSpecificRecord(anyInt()).getCreationDateTime();
       DbImpl database = new DbImpl();
       database.setClockForMock(this.mockClock); 
       database.createCar("xxx", "xxx", "xxx", "xxx", anyBoolean(), new EngineImpl(), new GearboxImpl());
       //System.out.println("Czas utworzenia rekordu: " + database.readSpecificRecord(0).getCreationDateTime());

       assertEquals(LocalDateTime.now(mockClock), database.readSpecificRecord(0).getCreationDateTime());

       System.out.println("Ile mamy rokordów  bazie danych: " + mockedDatabase.getNumberOfEntries());


    }
    @Test
    public void CheckingIf_checkingIfFillingWithRandomRecordsWork(){
       
       DbImpl database = new DbImpl();
       database.setClockForMock(this.mockClock); 
       SetupMockedDatabaseWitRecords(database, 10);
       //System.out.println("Czas utworzenia rekordu: " + database.readSpecificRecord(0).getCreationDateTime());
       assertEquals(LocalDateTime.now(mockClock), database.readSpecificRecord(0).getCreationDateTime());

       System.out.println("ILE TEGO W KONCU JEST? " + mockedDatabase.getNumberOfEntries() );

    }*/
    
    

}
