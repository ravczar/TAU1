package cars.s16281.tau.labone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    /* SETTING UP AN INITIAL DATABASE */
    private DbImpl initialDatabase = new DbImpl();
    
    /* END */

    @Mock private DbImpl mockedDatabase;

 
    @Before
    public void initMocks() {      
        MockitoAnnotations.initMocks(this);
        SetupMockedDatabaseWitRecords(this.initialDatabase, this.numberOfRecordsInInitialDatabase, Clock.systemDefaultZone() );
        //when(mockedDatabase.readAllRecords()).thenReturn(initialDatabase.readAllRecords());
        //when(mockedDatabase.readSpecificRecord(0)).thenReturn(initialDatabase.readSpecificRecord(0));
        when(mockedDatabase.getNumberOfEntries()).thenReturn(initialDatabase.getNumberOfEntries());
        when(mockedDatabase.getCarList() ).thenReturn(initialDatabase.getCarList());
        //when(mockedDatabase.updateSpecificCarById( anyInt(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), eq(new EngineImpl()), eq(new GearboxImpl())))
            //.thenReturn(initialDatabase.updateSpecificCarById( 0, "newColor", "newBrand", "newModel", "newType", true, new EngineImpl(), new GearboxImpl()));
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
        
        for (CarImpl _car : initialDatabase.getCarList()){
            if  ( _car.getLastReadDateTime().equals(null) ){
                    throw new IllegalArgumentException("Some car in DB does not have updated _lastReadDate field, example id: " + _car.getId());   
                }    
        }

        System.out.println("Data _lastReadDateTime PRZED odczytem wszystkich: " + valueBeforeReading);
        System.out.println("Data _lastReadDateTime PO odczycie wszystkich: " + valueAfterReading);

        assertEquals(null, valueBeforeReading);
        assertNotEquals(null, valueAfterReading);
        assertNotEquals(valueBeforeReading, valueAfterReading);   
    }

    ////////////////////////////////////////
    // JESTEM TUTAJ NA ROBOCIE

    @Test
    public void CheckIfCreationOfNewRecordAdd_creationDateTime_ToCarImpl() {
        // our mockedDatabase receives 10 records.
        //mockedDatabase = initialDatabase;
        mockedDatabase = new DbImpl();
        mockedDatabase.createCar("color", "brand", "model", "type", true, new EngineImpl(), new GearboxImpl());

        LocalDateTime creationDateWeExpect = mockedDatabase.readSpecificRecord(0).getCreationDateTime();
        System.out.println(creationDateWeExpect);
        System.out.println("Ile rekordow w bazie: " + mockedDatabase.getNumberOfEntries());
        assertNotNull(creationDateWeExpect);
        
    }

  



}