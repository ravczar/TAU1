package cars.s16281.tau.labone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import cars.s16281.tau.labone.services.CarImpl;
import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;


@RunWith(JUnit4.class)
public class DbTest {

    /*
    * Database Implementation ONY
    */
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



    /*
    * Database *C*RUD methods ONY
    */
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

    /*
    * Database C*R*UD methods ONY 
    */
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

        Integer idOfRecentlyAddedCar = database.carList.size()-1;

        assertNotNull(database.readSpecificRecord(idOfRecentlyAddedCar));
        

    }
    @Test
    public void readSpecificRecord_metheod_ReturnDifferentObjectsWhenGivenDifferentIDasParameter() {
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










}