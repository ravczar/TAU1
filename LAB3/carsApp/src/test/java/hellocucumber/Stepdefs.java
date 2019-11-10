package hellocucumber;

import cars.s16281.tau.labone.services.CarImpl;
import cucumber.api.java.en.*;
import io.cucumber.datatable.DataTable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;

public class Stepdefs {
    
    private DbImpl database;
    private ArrayList<CarImpl> searchResult;
    private String fieldOfClassCar;
    private Exception exception;

    // Scenario 1
    @Given("Database is filled with data")
    public void database_is_filled_with_data(DataTable dataTable) {  
        /* dataTable is passed from scenario.feature file : training @
         https://github.com/cucumber/cucumber/tree/master/datatable */
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        database = new DbImpl();

        for (Map<String, String> row : list) {
            database.createCar( 
                row.get("color"), 
                row.get("brand"),
                row.get("model"),
                row.get("type"),
                Boolean.parseBoolean(row.get("Boolean")),
                new EngineImpl(),
                new GearboxImpl()
                ); 
        }
        Integer records_from_scenario = list.size();
        Integer records_in_database = database.getNumberOfEntries();
        assertSame("Amount of Cars in scenario mismatch amount of cars in Your mock database",records_from_scenario, records_in_database);
    }

    @When("the searched Car field is set to model")
    public void I_search_repository_by_regex(){
        this.fieldOfClassCar = "model";
        assertTrue(this.fieldOfClassCar.equals("model"));
    }


    @And("I search repository by regex {string}")
    public void I_search_repository_by_regex(String regex) {
        searchResult = new ArrayList<>();
        this.searchResult = database.searchWithRegex(fieldOfClassCar,regex);
    }

    @Then("result count is {int}")
    public void result_count_is(Integer count) {
        assertEquals("Count of found values do not match the prediction..",count.intValue(), searchResult.size());
    }

    // Scenario 2
    @And("Database contains exactly {int} records")
    public void Database_contains_exactly_records( Integer amountOfRecords) {
        Integer records_from_scenario = amountOfRecords;
        Integer records_in_database = database.getNumberOfEntries();
        assertSame("Database does not contain exactly: " +amountOfRecords+" cars",records_from_scenario, records_in_database);
    }

    @When("I delete record with ID {int}")
    public void I_delete_record_with_ID( Integer id ){
        CarImpl carThatHasBeenDeleted = this.database.deleteCar(id);
        assertEquals("Deleted car expected to have id: " +id+ " but it has:" + carThatHasBeenDeleted,id, carThatHasBeenDeleted.getId());
    }

    @Then("record with ID {int} is deleted")
    public void record_with_ID_is_deleted( Integer id ) {
        try {
            database.readSpecificRecord(id);
        } catch(Exception e) {
            this.exception = e;
        }
        assertNotNull(this.exception);
    }

    @And("Database contains only {int} elements")
    public void database_contains_only_elements(Integer amount){
        assertSame("Amount of records mismatch:", amount, database.getDataBaseSize());
    }

    @But("Id counter remains unchanged: {int}")
    public void id_counter_remains_unchanged(Integer idCounter){
        // NumberOfEntries field in DbImpl.class is a field, on which CarImpl.class adjust new Car's ID on creation od new Object.
        // So when we have (id=0 -> car1, id=1 -> car2, id=2 -> car3) _numberOfEntries returns Int: 3.
        // if than delete any of records, next added/created car will still receive ID 3
        Integer idCounterForNewCarImplInstance = database.getNumberOfEntries();
        assertSame("", idCounter, idCounterForNewCarImplInstance);
        assertNotSame("", idCounter, database.getDataBaseSize());
    }


}
