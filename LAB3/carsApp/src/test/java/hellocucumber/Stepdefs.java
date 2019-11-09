package hellocucumber;

import cars.s16281.tau.labone.services.CarImpl;
import io.cucumber.datatable.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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
        //System.out.println("Transferred in datatable:\n" + dataTable);
        //System.out.println("zmienna database ma tyle rekordow: " + database.getNumberOfEntries());
    }

    @When("I search repository by regex {string}")
    public void I_search_repository_by_regex(String regex) {
        searchResult = new ArrayList<>();
        this.searchResult = database.searchWithRegex(regex);

    }

    @Then("result count is {int}")
    public void result_count_is(Integer count) {
        assertEquals(count.intValue(), searchResult.size());

    }



}
