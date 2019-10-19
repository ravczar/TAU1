package cars.s16281.tau.labone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;


@RunWith(JUnit4.class)
public class DbTest {

    /*
    * Database Implementation and object creation only
    */
    @Test
    public void DbIsImplementedTest() {
        assertNotNull(new DbImpl());
    }
    @Test
    public void DbWhenCreatedContainsCounterThatIsNotNull() {
        DbImpl database = new DbImpl();
        assertNotNull(database.numberOfEntries);
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
        assertNotNull(database.numberOfEntries);
        assertSame(0, database.numberOfEntries);
    }
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
        assertSame(1, database.numberOfEntries);

        database.createCar("Gray", "Volkswagen", "Patheon", "Sedan",
         true, new EngineImpl(), new GearboxImpl());
        assertSame(2, database.numberOfEntries);
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
    * Database CRUD methods only
    */





}