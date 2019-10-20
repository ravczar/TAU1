package cars.s16281.tau.labone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
    /*
    * Database CR*U*D methods ONY 
    */
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
    /*
    * Database CRU*D* methods ONY 
    */

    

}