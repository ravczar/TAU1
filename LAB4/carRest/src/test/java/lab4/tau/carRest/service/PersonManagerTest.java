package lab4.tau.carRest.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lab4.tau.carRest.domain.Car;

public class PersonManagerTest {

	lab4.tau.carRest.service.CarManager carManager;

	private final static String MODEL_1 = "Multipla";
	private final static String BRAND_1 = "Fiat";
	private final static String BODY_1 = "MULTIVAN";
	private final static int DATE_1 = 2005;

	@Before
	public void setup() throws SQLException {
		carManager = new lab4.tau.carRest.service.CarManagerImpl();
	}

	@After
	public void cleanup() throws SQLException {
	}

	@Test
	public void checkConnection() {
		assertNotNull("Not connection with database established, check if HSQLDB working and named properly 'workdb'!",carManager.getConnection());
	}

	@Test
	public void verifyAddingSingleCarMethod() throws SQLException {
		Car car = new Car(MODEL_1, BRAND_1, BODY_1, DATE_1);
		// DB to be empty when testing single record adding
		carManager.deleteAll();
		assertEquals("Number of records in DB and added mismatch. Should be 1!",1, carManager.addCar(car));

		List<Car> cars = carManager.getAllCars();
		Car carRetrieved = cars.get(0);

		assertEquals("Expected Model_name != received Model_name", MODEL_1, carRetrieved.getModel());
		assertEquals("Expected Brand_name != received Brand_name", BRAND_1, carRetrieved.getBrand());
		assertEquals("Expected Body_type != received Body_type", BODY_1, carRetrieved.getBody());
		assertEquals("Expected Date_of_prod. != received Date_of_prod.", DATE_1, carRetrieved.getDateOfProduction());
	}



}
