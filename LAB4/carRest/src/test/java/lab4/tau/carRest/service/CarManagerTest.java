package lab4.tau.carRest.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lab4.tau.carRest.domain.Car;

public class CarManagerTest {

	lab4.tau.carRest.service.CarManager carManager;

	private final static String MODEL_1 = "Multipla";
	private final static String BRAND_1 = "Fiat";
	private final static String BODY_1 = "MULTIVAN";
	private final static int DATE_1 = 2005;

	private final static String MODEL_2 = "Fiesta";
	private final static String BRAND_2 = "Ford";
	private final static String BODY_2 = "Hatchback";
	private final static int DATE_2 = 1999;

	@Before
	public void setup() throws SQLException {
		carManager = new lab4.tau.carRest.service.CarManagerImpl();
	}

	@After
	public void cleanup() throws SQLException {
	}

	@Test
	public void verify_established_Connection() {
		assertNotNull("Not connection with database established, check if HSQLDB working and named properly 'workdb'!",carManager.getConnection());
	}

	@Test
	public void verify_addCar_methodAddsProperCarToDataBase() throws SQLException {
		Car car = new Car(MODEL_1, BRAND_1, BODY_1, DATE_1);
		// DB to be empty when testing single record adding
		carManager.deleteAllCars();
		assertEquals("Number of records in DB and added mismatch. Should be 1!",
				1, carManager.addCar(car));

		List<Car> cars = carManager.getAllCars();
		Car carRetrieved = cars.get(0);

		assertEquals("Expected Model_name != received Model_name",
				MODEL_1, carRetrieved.getModel()
		);
		assertEquals("Expected Brand_name != received Brand_name",
				BRAND_1, carRetrieved.getBrand()
		);
		assertEquals("Expected Body_type != received Body_type",
				BODY_1, carRetrieved.getBody()
		);
		assertEquals("Expected Date_of_prod. != received Date_of_prod.",
				DATE_1, carRetrieved.getDateOfProduction()
		);
	}

	@Test
	public void verify_deleteAllCars_methodRemovesAllRecordsFromDatabase() throws SQLException {
		// Making sure DB is empty at time of test.
		Car car = new Car(MODEL_1, BRAND_1, BODY_1, DATE_1);
		carManager.addCar(car);
		carManager.deleteAllCars();
		Integer sizeOfDb = carManager.getAllCars().size();

		assertSame("Should return Zero, but it does not! hint: Check if database is empty at time of test run!", 0 , sizeOfDb );
	}

	@Test
	public void verify_getAllCars_methodReturnsZeroWhenDatabaseIsEmpty() throws SQLException {
		// Making sure DB is empty at time of test.
		carManager.deleteAllCars();
		Integer shouldReturnZero = 0;
		Integer sizeOfDatabase = carManager.getAllCars().size();
		assertSame("Should return Zero, but it does not! hint: Check if database is empty at time of test run!", shouldReturnZero , sizeOfDatabase );
	}

	@Test
	public void verify_getCar_methodReturnsProperId() throws SQLException {
		Car car = new Car(MODEL_1, BRAND_1, BODY_1, DATE_1);
		long lastIndexInDataBaseBeforeAddingNewRecord = carManager.getAllCars().size() -1;
		if (lastIndexInDataBaseBeforeAddingNewRecord == -1){
			assertEquals("DB is not empty at time of test",carManager.getAllCars().size(), 0);
		}
		else if ( lastIndexInDataBaseBeforeAddingNewRecord >= 0){
			long idOfLastRecordInDataBaseBeforeAddingRecord = carManager.getAllCars().get((int) lastIndexInDataBaseBeforeAddingNewRecord).getId();
			System.out.println("lastIndexInDataBase : " + lastIndexInDataBaseBeforeAddingNewRecord);
			System.out.println("idOfLastRecordInDataBase : " + idOfLastRecordInDataBaseBeforeAddingRecord);
			carManager.addCar(car);
			long lastIndexInDataBaseAfterAddingRecord = carManager.getAllCars().size() -1;
			long idOfLastRecordInDataBaseAfterAddingRecord = carManager.getAllCars().get((int) lastIndexInDataBaseAfterAddingRecord).getId();
			assertNotEquals("Id value of last record pre addition of new record should be different than id of last record after addition ",
					idOfLastRecordInDataBaseBeforeAddingRecord, idOfLastRecordInDataBaseAfterAddingRecord
			);
			assertEquals("ID of last record after adding new record should be equal to ID of last record Before adding this record, but + 1 ",
					++idOfLastRecordInDataBaseBeforeAddingRecord, idOfLastRecordInDataBaseAfterAddingRecord
			);
		}
	}

	@Test
	public void verify_deleteCar_methodIsDeletingSingleRecord() throws SQLException {
		Car car = new Car(MODEL_1, BRAND_1, BODY_1, DATE_1);
		long sizeOfDatabaseBeforeAddingNewCar = carManager.getAllCars().size();
		carManager.addCar(car);
		carManager.deleteCar(car);
		long sizeOfDatabaseAfterDeletingPreviouslyAddedCar = carManager.getAllCars().size();
		assertEquals("Number of records before adding new record to DB should equal sieze of DB after removing record ",
				sizeOfDatabaseBeforeAddingNewCar, sizeOfDatabaseAfterDeletingPreviouslyAddedCar
		);

	}

	@Test
	public void verify_updateCar_methodIsUpdatingAllFieldsWithProperData() throws SQLException {
		carManager.deleteAllCars();
		Car car = new Car(MODEL_1, BRAND_1, BODY_1, DATE_1);

		Integer howManyJustAdded = carManager.addCar(car);
		Integer currentSizeOfDb = carManager.getAllCars().size();
		Integer lasIndexInDb = currentSizeOfDb -1;
		long IdOfCarBeforeAnUpdate = carManager.getAllCars().get(lasIndexInDb).getId();
		assertSame("Database received different amount of records! Wrong.", 1, howManyJustAdded);
		assertSame("Database size is different than 1", 1, currentSizeOfDb);

		car.updateMultipleFields(MODEL_2, BRAND_2, BODY_2, DATE_2);
		carManager.updateCar(car);
		long idOfUpdatedCar = carManager.getAllCars().get(lasIndexInDb).getId();
		String updatedModelName = carManager.getAllCars().get(lasIndexInDb).getModel();
		String updatedBrandName = carManager.getAllCars().get(lasIndexInDb).getBrand();
		String updatedBodyType = carManager.getAllCars().get(lasIndexInDb).getBody();
		int updatedDateOfProduction = carManager.getAllCars().get(lasIndexInDb).getDateOfProduction();

		assertEquals("Id of car before update and after and update differ, but should Not! ", IdOfCarBeforeAnUpdate, idOfUpdatedCar);
		assertEquals("Model name should be Fiesta, but its not", updatedModelName, MODEL_2);
		assertEquals("Brand name should be Ford, but its not", updatedBrandName, BRAND_2);
		assertEquals("Body type should be Hatchback, but its not", updatedBodyType, BODY_2);
		assertEquals("DateOfProduction should be 1999 but its not", updatedDateOfProduction, DATE_2);

	}

	@Test
	public void verify_updateCar_methodIsUpdatingOnlyBrandNameWithValueOfSpecific_field_BRAND2() throws SQLException {
		carManager.deleteAllCars();
		Car car = new Car(MODEL_1, BRAND_1, BODY_1, DATE_1);

		Integer howManyJustAdded = carManager.addCar(car);
		Integer currentSizeOfDb = carManager.getAllCars().size();
		Integer lasIndexInDb = currentSizeOfDb -1;
		long IdOfCarBeforeAnUpdate = carManager.getAllCars().get(lasIndexInDb).getId();
		assertSame("Database received different amount of records! Wrong.", 1, howManyJustAdded);
		assertSame("Database size is different than 1", 1, currentSizeOfDb);
		// Only changing Brand Name
		car.setBrand(BRAND_2);
		carManager.updateCar(car);
		long idOfUpdatedCar = carManager.getAllCars().get(lasIndexInDb).getId();
		String updatedModelName = carManager.getAllCars().get(lasIndexInDb).getModel();
		String updatedBrandName = carManager.getAllCars().get(lasIndexInDb).getBrand();
		String updatedBodyType = carManager.getAllCars().get(lasIndexInDb).getBody();
		int updatedDateOfProduction = carManager.getAllCars().get(lasIndexInDb).getDateOfProduction();

		assertEquals("Id of car before update and after and update differ, but should Not! ", IdOfCarBeforeAnUpdate, idOfUpdatedCar);
		assertEquals("Model name should be Fiesta, but its not", updatedModelName, MODEL_1);
		assertEquals("Brand name should be Ford, but its not", updatedBrandName, BRAND_2);
		assertEquals("Body type should be Hatchback, but its not", updatedBodyType, BODY_1);
		assertEquals("DateOfProduction should be 1999 but its not", updatedDateOfProduction, DATE_1);

	}


}
