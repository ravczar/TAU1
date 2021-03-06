package cars.s16281.tau.labone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mock;

import cars.s16281.tau.labone.services.CarImpl;
import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;

@RunWith(MockitoJUnitRunner.class)
public class DbTestMock {

    /* RANDOM DB CAR RECORD GENRATOR */
    public void SetupMockedDatabaseWitRecords (DbImpl dbToBeFilledWithRecords, Integer howManyRecords, Clock whatClock){
        String colorTable[] = {"Green", "Gray", "Red", "Black", "Silver", "Brown", "Yellow", "Orange", "Purple", "Blue"};
        String brandTable[] = {"Volkswagen", "Audi", "Maseratti", "Porsche", "Fiat", "Ford", "Peugeot", "Hyundai", "Citroen", "FSO"};
        String modelTable[] = {"Acura", "Siena", "Quatroporte", "911s", "126p", "Focus", "3008", "A3", "Xara", "Polonez"};
        String typeTable[] = {"Coupe", "Cabrio", "Truck", "Combi", "Sedan", "Convertable", "Hatchback", "Roadster", "Pickup", "Van"};

        Random random = new Random();
       
        for (Integer i=0; i<howManyRecords; i++){
            Integer myRandomNumber = random.nextInt(10); // Generuje liczbę int z przedziału <baud> 0-10
            dbToBeFilledWithRecords.setClockForMock(whatClock);
            dbToBeFilledWithRecords.createCar(colorTable[myRandomNumber], brandTable[myRandomNumber], modelTable[myRandomNumber], typeTable[myRandomNumber], true, new EngineImpl(), new GearboxImpl());
        }
        //System.out.println("Rozmiar bazy danych po kroeaniu randomowym: " + mockedDatabase.getNumberOfEntries());
    }
     /* END*/

     /* SETTING FIXED TIME CLOCK FOR MOCKS */
    private int numberOfRecordsInInitialDatabase = 10;
    private int year = 2017;  // Be this a specific 
    private int month = 2;    // date we need 
    private int day = 3; 
    Clock mockClock =
      Clock.fixed(
        LocalDateTime.of(year, month, day, 10, 20, 33).toInstant(OffsetDateTime.now().getOffset()),
        Clock.systemDefaultZone().getZone()
      );
    /* END*/

    /* Mocking the clock for test purposes */
    public Clock getNewFakeClockValue(Integer howManyMinutes){
        Clock testClock = Clock.systemDefaultZone();
        Duration offsetDuration = Duration.ofMinutes(howManyMinutes);
        Clock createElementClock = Clock.offset(testClock, offsetDuration);
        return createElementClock;
    }
    /* END */

    /* SETTING UP AN INITIAL DATABASE */
    private DbImpl initialDatabase = new DbImpl();
    
    /* END */

    @Mock private DbImpl mockedDatabase;

 
    @Before
    public void initMocks() {      
        MockitoAnnotations.initMocks(this);
        SetupMockedDatabaseWitRecords(this.initialDatabase, this.numberOfRecordsInInitialDatabase, Clock.systemDefaultZone() );
        when(mockedDatabase.getNumberOfEntries()).thenReturn(initialDatabase.getNumberOfEntries());
        when(mockedDatabase.getCarList() ).thenReturn(initialDatabase.getCarList());
    }

   
    @Test
    public void MockedDatabaseReadsSameNumberOfRecordsAsStatedInField_numberOfRecordsInInitialDatabase(){
        // Baza danych z mocka bedzie miec tyle rekordów co daliśmy w generatorze
        assertSame(this.numberOfRecordsInInitialDatabase, mockedDatabase.getNumberOfEntries());   
        verify(mockedDatabase, times(1)).getNumberOfEntries();
    }

    @Test
    public void WhenCarAddedToDatabase_lastModificationDateTime_ShouldEqualNull(){
        //State of lastModificationDateTime before modification is null
        when(mockedDatabase.readSpecificRecord(0)).thenReturn(initialDatabase.readSpecificRecord(0));
        assertSame(null, mockedDatabase.readSpecificRecord(0).getModificationDateTime());
        verify(mockedDatabase, times(1)).readSpecificRecord(0);
        
    }

    @Test 
    public void WhenCarAddedToDatabase_lastReadDateTime_ShouldEqualNull(){
        //State of lastReadDateTime before reading record is null
        LocalDateTime valueBeforeReading = initialDatabase.getCarList().get(0).getLastReadDateTime();
        assertNull("Date before reading given car is not null, thus it may contain an actual date", valueBeforeReading);
    }

    @Test
    public void WhenCarIsReadFromDatabaseUsingMethod_readSpecificRecord_Field_lastReadDateTime_ShouldContainDate(){
        //State of lastReadDateTime before reading record is null
        LocalDateTime valueBeforeReading = initialDatabase.getCarList().get(0).getLastReadDateTime();
        when(mockedDatabase.readSpecificRecord(0)).thenReturn(initialDatabase.readSpecificRecord(0));
        verify(mockedDatabase, never()).readSpecificRecord(0);
        LocalDateTime valueAfterReading = mockedDatabase.readSpecificRecord(0).getLastReadDateTime();

        verify(mockedDatabase, times(1)).readSpecificRecord(anyInt());
        assertEquals(null, valueBeforeReading);
        assertNotEquals(null, valueAfterReading);
        assertNotEquals(valueBeforeReading, valueAfterReading);    
    }

    @Test
    public void WhenCarsAreReadFromDatabaseUsingMethod_readAllRecordRecords_Field_lastReadDateTime_ShouldContainDateInEachCarObject(){
        //State of lastReadDateTime before reading record is null
        LocalDateTime valueBeforeReading = initialDatabase.getCarList().get(0).getLastReadDateTime();
        
        for (CarImpl _car : initialDatabase.getCarList()){
            if  ( _car.getLastReadDateTime() != null ){
                throw new IllegalArgumentException("Some car already has lastReadDate that is not null, example id: " + _car.getId());   
            }    
        }

        when(mockedDatabase.readAllRecords()).thenReturn(initialDatabase.readAllRecords());

        LocalDateTime valueAfterReading = initialDatabase.getCarList().get(0).getLastReadDateTime();
        verify(mockedDatabase, never()).readAllRecords();
        mockedDatabase.readAllRecords();
        
        for (CarImpl _car : mockedDatabase.getCarList()){
            if  ( _car.getLastReadDateTime().equals(null) ){
                    throw new IllegalArgumentException("Some car in DB does not have updated _lastReadDate field, example id: " + _car.getId());   
                }    
        }

        System.out.println("Data _lastReadDateTime PRZED odczytem wszystkich: " + valueBeforeReading);
        System.out.println("Data _lastReadDateTime PO odczycie wszystkich: " + valueAfterReading);

        verify(mockedDatabase, times(1)).readAllRecords();
        assertEquals(null, valueBeforeReading);
        assertNotEquals(null, valueAfterReading);
        assertNotEquals(valueBeforeReading, valueAfterReading);   
    }

    @Test
    public void CheckIfCreationOfNewRecordAdd_creationDateTime_ThatIsNotNull() {

        mockedDatabase = new DbImpl();
        mockedDatabase.createCar("color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl());
        
        assertNotNull(mockedDatabase.readSpecificRecord(0).getCreationDateTime());
        
    }

    @Test
    public void CheckIfCreationOfNewRecordAdd_creationDateTime_ToCarObject() {
        LocalDateTime timeOfTestStart = LocalDateTime.now();
        initialDatabase.setClockForMock(getNewFakeClockValue(+5));
        when(
            mockedDatabase.createCar(
                "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl() 
                )
            )
            .thenReturn(initialDatabase.createCar(
                "Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl() 
            )
        );

        when(mockedDatabase.readSpecificRecord(10)).thenReturn(initialDatabase.readSpecificRecord(10));
        mockedDatabase.createCar("Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl());
        LocalDateTime creationDateReadFromRecentlyCreatedObject = initialDatabase.readSpecificRecord(10).getCreationDateTime();

        System.out.println("timestam before creation:  " + timeOfTestStart+"    cd:  "+creationDateReadFromRecentlyCreatedObject );
        assertNotEquals(timeOfTestStart, creationDateReadFromRecentlyCreatedObject);
        assertTrue(timeOfTestStart.isBefore(creationDateReadFromRecentlyCreatedObject));
    }

    @Test
    public void TestIfField_modiytDateTime_IsUpdatedWhenMethod_updateSpecificCarById_IsRun(){
        when(mockedDatabase.readSpecificRecord(0)).thenReturn(initialDatabase.readSpecificRecord(0));
        LocalDateTime fieldValueBeforeUpdate = mockedDatabase.readSpecificRecord(0).getModificationDateTime();
        System.out.println("fieldValueBeforeUpdate : "+ fieldValueBeforeUpdate);

        when(
            mockedDatabase.updateSpecificCarById(
                0, "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl() 
                )
            )
            .thenReturn(initialDatabase.updateSpecificCarById(
                0, "Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl() 
            )
        );

        mockedDatabase.updateSpecificCarById(0, "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl());
        LocalDateTime fieldValueAfterUpdate = mockedDatabase.readSpecificRecord(0).getModificationDateTime();
        System.out.println("fieldValueAfterUpdate : "+ fieldValueAfterUpdate);

        verify(mockedDatabase, times(2)).readSpecificRecord(0);
        assertNull("fieldValueBeforeUpdate is not null, but it should be before update applied", fieldValueBeforeUpdate);
        assertNotNull("fieldValueAfterUpdate is null, but it should contain a date of a update already", fieldValueAfterUpdate);
        assertNotSame("One should contain null and other a date. Should be Different", fieldValueBeforeUpdate, fieldValueAfterUpdate);
    }

    @Test
    public void TestCarObjectMethod_showAllDateTimeFields_willReturnAsFollows_DateNullNull_ifThereWasNoUpdateOrReadOnObject(){

        ArrayList<CarImpl> cars = mockedDatabase.getCarList();

        assertNotNull("Created car must have a creation date at init", cars.get(0).getAllDateTimeFields().get(0));
        assertNull("Created car must have null in lastReadDate at init", cars.get(0).getAllDateTimeFields().get(1));
        assertNull("Created car must have null in modificationDate at init", cars.get(0).getAllDateTimeFields().get(2));
        
        verify(mockedDatabase, times(1)).getCarList();
        
    }

    @Test
    public void TestCarObjectMethod_showAllDateTimeFields_willReturnAsFollows_DateDateNull_ifThereWasNoUpdateOrReadOnObject(){
        initialDatabase.setClockForMock(getNewFakeClockValue(+5));
        when(mockedDatabase.readSpecificRecord(0)).thenReturn(initialDatabase.readSpecificRecord(0));
        mockedDatabase.readSpecificRecord(0);
        ArrayList<CarImpl> cars = mockedDatabase.getCarList();

        LocalDateTime createDate = cars.get(0).getAllDateTimeFields().get(0);
        LocalDateTime readDate = cars.get(0).getAllDateTimeFields().get(1);
        LocalDateTime modifDate = cars.get(0).getAllDateTimeFields().get(2);

        System.out.println("CD  "+createDate +"  RD  "+readDate+ "  MD  "+ modifDate);

        assertNotNull("Created car must have a creation date at init", createDate);
        assertNotNull("Created car must have null in lastReadDate at init", readDate);
        assertNull("Created car must have null in modificationDate at init", modifDate);
        assertTrue(createDate.isBefore(readDate));

        verify(mockedDatabase, times(1)).getCarList();
        
    }

    @Test
    public void TestCarObjectMethod_showAllDateTimeFields_willReturnAsFollows_DateNullDate_ifThereWasNoReadActionOnObjectButWasUpdateObjectApplied(){

        initialDatabase.setClockForMock(getNewFakeClockValue(+5));
        when(
            mockedDatabase.updateSpecificCarById(
                0, "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl() 
                )
            )
            .thenReturn(initialDatabase.updateSpecificCarById(
                0, "Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl() 
            )
        );

        ArrayList<CarImpl> cars = mockedDatabase.getCarList();

        LocalDateTime createDate = cars.get(0).getAllDateTimeFields().get(0);
        LocalDateTime readDate = cars.get(0).getAllDateTimeFields().get(1);
        LocalDateTime modifDate = cars.get(0).getAllDateTimeFields().get(2);

        //System.out.println("CD  "+createDate +"  RD  "+readDate+ "  MD  "+ modifDate);

        assertNotNull("Created car must have a creation date at init", createDate);
        assertNull("Created car must have null in lastReadDate at init", readDate);
        assertNotNull("Created car must have null in modificationDate at init", modifDate);
        assertTrue(modifDate.isAfter(createDate));
        verify(mockedDatabase, times(1)).getCarList();
        
    }
     
    @Test
    public void TestCarObjectMethod_showAllDateTimeFields_willReturnAsFollows_DateDateDate_ifObjectWasReadAndObjectWasUpdated(){
       
        initialDatabase.setClockForMock(getNewFakeClockValue(5));
        when(mockedDatabase.readSpecificRecord(0)).thenReturn(initialDatabase.readSpecificRecord(0));
        initialDatabase.setClockForMock(getNewFakeClockValue(10));
        when(
            mockedDatabase.updateSpecificCarById(
                0, "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl() 
                )
            )
            .thenReturn(initialDatabase.updateSpecificCarById(
                0, "Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl() 
            )
        );
        

        ArrayList<CarImpl> cars = mockedDatabase.getCarList();
        
        LocalDateTime createDate = cars.get(0).getAllDateTimeFields().get(0);
        LocalDateTime readDate = cars.get(0).getAllDateTimeFields().get(1);
        LocalDateTime modifDate = cars.get(0).getAllDateTimeFields().get(2);
        
        //System.out.println("CD  "+createDate +"  RD  "+readDate+ "  MD  "+ modifDate);
        assertNotNull("Created car must have a creation date at init", createDate);
        assertNotNull("Created car must have null in lastReadDate at init", readDate);
        assertNotNull("Created car must have null in modificationDate at init", modifDate);

        assertTrue(readDate.isAfter(createDate));
        assertTrue(modifDate.isAfter(createDate));
        assertTrue(modifDate.isAfter(readDate));
        verify(mockedDatabase, times(1)).getCarList();
        
    }
  
    @Test
    public void TestIfSwitchingOfAllDateTimeFieldsWillNotAllowToAdd_crateDateTime_WhenAddingNewRecordToDB(){
        
        initialDatabase.setWhatDatesDatabaseRecord(false, false, false);
        when(
            mockedDatabase.createCar(
                "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl() 
                )
            )
            .thenReturn(initialDatabase.createCar(
                "Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl() 
            )
        );

        ArrayList<CarImpl> cars = mockedDatabase.getCarList();

        LocalDateTime createDate = cars.get(10).getAllDateTimeFields().get(0);

        assertNull("createDate must equal Null when adding new database", createDate);
        //System.out.println("CD  "+createDate +"  RD  "+readDate+ "  MD  "+ modifDate + "  Ile rekordów  " + initialDatabase.getNumberOfEntries());
    }


    @Test
    public void TestIfSwitchingOfAllDateTimeFieldsWillNotAllowToAdd_modifyDateTime_WhenAddingNewRecordToDB(){
        
        initialDatabase.setWhatDatesDatabaseRecord(true, false, false);
        
        when(
            mockedDatabase.createCar(
                "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl() 
                )
            )
            .thenReturn(initialDatabase.createCar(
                "Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl() 
            )
        );

        when(
            mockedDatabase.updateSpecificCarById(
                10, "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl() 
                )
            )
            .thenReturn(initialDatabase.updateSpecificCarById(
                10, "Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl() 
            )
        );

    
        ArrayList<CarImpl> cars = mockedDatabase.getCarList();

        LocalDateTime modifDate = cars.get(10).getAllDateTimeFields().get(2);

        assertNull("createDate must equal Null when adding new database", modifDate);
        //System.out.println("CD  "+createDate +"  RD  "+readDate+ "  MD  "+ modifDate + "  Ile rekordów  " + initialDatabase.getNumberOfEntries());
    }

    @Test
    public void TestIfSwitchingOfAllDateTimeFieldsWillNotAllowToAdd_readDateTime_WhenAddingNewRecordToDB(){
        
        initialDatabase.setWhatDatesDatabaseRecord(true, false, false);
        
        when(
            mockedDatabase.createCar(
                "color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl() 
                )
            )
            .thenReturn(initialDatabase.createCar(
                "Gray", "Volvo", "XC90", "SUV", true, new EngineImpl(), new GearboxImpl() 
            )
        );

        when(mockedDatabase.readSpecificRecord(10)).thenReturn(initialDatabase.readSpecificRecord(10));

    
        ArrayList<CarImpl> cars = mockedDatabase.getCarList();

        //LocalDateTime createDate = cars.get(10).getAllDateTimeFields().get(0);
        LocalDateTime readDate = cars.get(10).getAllDateTimeFields().get(1);
        //LocalDateTime modifDate = cars.get(10).getAllDateTimeFields().get(2);

        assertNull("createDate must equal Null when adding new database", readDate);
        //System.out.println("CD  "+createDate +"  RD  "+readDate+ "  MD  "+ modifDate + "  Ile rekordów  " + initialDatabase.getNumberOfEntries());
    }

    @Test
    public void TestIfSwitchingOfAllDateTimeFieldsWillNotAllowToAdd_readDateTime_WhenReadingAllRecords(){
        
        initialDatabase.setWhatDatesDatabaseRecord(true, false, false);
        
        mockedDatabase.readAllRecords();

        when(mockedDatabase.readSpecificRecord(9)).thenReturn(initialDatabase.readSpecificRecord(9));

    
        ArrayList<CarImpl> cars = mockedDatabase.getCarList();

        LocalDateTime createDate = cars.get(9).getAllDateTimeFields().get(0);
        LocalDateTime readDate = cars.get(9).getAllDateTimeFields().get(1);
        LocalDateTime modifDate = cars.get(9).getAllDateTimeFields().get(2);

        assertNull("createDate must equal Null when adding new database", readDate);
        System.out.println("CD  "+createDate +"  RD  "+readDate+ "  MD  "+ modifDate + "  Ile rekordów  " + initialDatabase.getNumberOfEntries());
    }




}