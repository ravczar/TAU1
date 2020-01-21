package cars.s16281.tau.labone;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Optional;

import cars.s16281.tau.labone.services.CarImpl;
import cars.s16281.tau.labone.services.DbObjectHolder;
import cars.s16281.tau.labone.services.DbObjectProperties;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;
import cars.s16281.tau.labone.services.DateTimeProvider;;


public class DateMockTest {

    private LocalDateTime timestamp;
    
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    @Mock
    private DateTimeProvider provider;

    @Before
    public void setup() {
        newMockTimestamp();
        DbObjectProperties.setAllParamsToTrue();
    }

    // Tworzy znacznik czasowy teraz (przed rozpoczęciem testów)
    private LocalDateTime newMockTimestamp() {
        LocalDateTime timestamp = LocalDateTime.now();
        when(provider.get()).thenReturn(timestamp);
        this.timestamp = timestamp;
        return timestamp;
    }

    // Tworzy opakowanie z datami oraz naszym obiektem bazy danych
    private DbObjectHolder createHolder() {
        CarImpl car = new CarImpl(0, "black", "Audi", "Quattro", "Sedan", true, new EngineImpl(), new GearboxImpl() );
        return new DbObjectHolder(provider, car);
    }

    @Test
    public void dateTimeProviderObjectCanBeCreated(){
        // Object on creation memorizes it's creation date time.
        DateTimeProvider provider = new DateTimeProvider();
        LocalDateTime creationDate =  provider.getObjectCreationTime();
        //System.out.println(creationDate + "   " + LocalDateTime.now());
        assertTrue("Coś poszło nie tak, to te same obiekty", creationDate != LocalDateTime.now());
    }

    @Test
    public void dateTimeProvider_returns_NOW_when_asked(){
        // Object when asked returns a LocalDateTime.now()
        DateTimeProvider provider = new DateTimeProvider();
        assertTrue("To te same obiekty, coś poczło nie tak", provider.get() != LocalDateTime.now()); 
    }

    @Test
    public void dbObjectHolder_getCar_method_should_return_a_car(){
        DbObjectHolder myHolder = createHolder();
        CarImpl car = myHolder.getCar();
        assertEquals("Powinien zwrócić brand: Audi ", car.getBrand(), "Audi");
    }

    @Test
    public void dbObjectHolder_getCreationDate_method_should_return_creationDateTime(){
        // Database records creation date at init
        DbObjectProperties.setTrackCreationDate(true);
        DbObjectHolder myHolder = createHolder();
        LocalDateTime creationDate = myHolder.getCreationDate().get(); // z get() zwróci typ: LocalDateTime
        //System.out.println("Creationd date time: "+creationDate + "   " +timestamp);
        assertEquals(creationDate, timestamp);
    }

    @Test
    public void dbObjectHolder_getModificationDate_method_should_return_modificationDateTime(){
        // Database records modification date time on update (as LocalDateTime)
        DbObjectHolder myHolder = createHolder();
        LocalDateTime currentTimeStamp = newMockTimestamp();
        CarImpl modifiedCar = new CarImpl(0, "pink", "Audi", "A2", "Sedan", true, new EngineImpl(), new GearboxImpl());
        myHolder.setCar(modifiedCar);
        LocalDateTime modificationDate = myHolder.getModificationDate().get();
        assertEquals(modificationDate, currentTimeStamp);
    }

    @Test
    public void dbObjectHolder_getAccesDate_method_should_return_recordAccesDateTime(){
        // Database records read record date time on access (as LocalDateTime)
        DbObjectProperties.setTrackAccessDate(true);
        DbObjectHolder myHolder = createHolder();
        LocalDateTime currentTimeStamp = newMockTimestamp();
        myHolder.getCar();
        LocalDateTime accessDate = myHolder.getAccessDate().get();
        assertEquals(accessDate, currentTimeStamp);
    }

    @Test
    public void dbObjectHolder_does_not_track_CreateDateTime_when_method_setTrackCreationDate_get_param_false(){
        // Database will not record createDateTime when we pass 'false' parameter to method setTrackCreationDate(false)
        DbObjectProperties.setTrackCreationDate(false);
        DbObjectHolder myHolder = createHolder();
        assertEquals(myHolder.getCreationDate(), Optional.empty());
    }

    @Test
    public void dbObjectHolder_does_not_track_ModifyDateTime_when_method_setTrackModificationDate_get_param_false(){
        // Database will not record modificationDateTime when we pass 'false' parameter to method setTrackModificationDate(false)
        DbObjectProperties.setTrackModificationDate(false);
        DbObjectHolder myHolder = createHolder();
        CarImpl modifiedCar = new CarImpl(0, "pink", "Audi", "A2", "Sedan", true, new EngineImpl(), new GearboxImpl());
        myHolder.setCar(modifiedCar);
        assertEquals(myHolder.getModificationDate(), Optional.empty());
    }

    @Test
    public void dbObjectHolder_does_not_track_AccessDateTime_when_method_setTrackAccessDate_get_param_false(){
        // Database will not record AccessDateTime when we pass 'false' parameter to method setTrackAccessDate(false)
        DbObjectProperties.setTrackAccessDate(false);
        DbObjectHolder myHolder = createHolder();
        myHolder.getCar();
        assertEquals(myHolder.getAccessDate(), Optional.empty());
    }

    @Test
    public void dbObjectHolder_will_start_track_AccessDateTime_when_method_setTrackAccessDate_get_param_true(){
        // Database will regain possibility to record AccessDateTime once we pass 'true' parameter to method setTrackAccessDate(true)
        DbObjectProperties.setTrackAccessDate(false);
        DbObjectHolder myHolder = createHolder();
        myHolder.getCar();
        assertEquals(myHolder.getAccessDate(), Optional.empty());

        DbObjectProperties.setTrackAccessDate(true);
        myHolder.getCar();
        LocalDateTime accessDateRegained = myHolder.getAccessDate().get();
        assertEquals(accessDateRegained, timestamp);
    }

    @Test
    public void dbObjectHolder_will_start_track_ModifyDateTime_when_method_setTrackModificationDate_get_param_true(){
        // Database will regain possibility to record ModifyDateTime once we pass 'true' parameter to method setTrackModificationDate(true)
        DbObjectProperties.setTrackModificationDate(false);
        DbObjectHolder myHolder = createHolder();
        CarImpl modifiedCar = new CarImpl(0, "pink", "Audi", "A2", "Sedan", true, new EngineImpl(), new GearboxImpl());
        myHolder.setCar(modifiedCar);
        assertEquals("Obiekt @DoNotTrack nie jest pusty!", myHolder.getModificationDate(), Optional.empty());

        DbObjectProperties.setTrackModificationDate(true);
        LocalDateTime currentTimeStamp = newMockTimestamp();
        CarImpl modifiedCarPrim = new CarImpl(0, "green", "Audi", "A3", "hathback", true, new EngineImpl(), new GearboxImpl());
        myHolder.setCar(modifiedCarPrim);
        LocalDateTime modificationDate = myHolder.getModificationDate().get();
        assertEquals("Obiekt @ PleaseTrackModification date jest różny od porównywanego", modificationDate, currentTimeStamp);
    }

    @Test
    public void dbObjectHolder_will_start_track_CreateDateTime_when_method_setTrackCreationnDate_get_param_true(){
        // Database will regain possibility to record CreateDateTime once we pass 'true' parameter to method  setCreationDate(true)
        DbObjectProperties.setTrackCreationDate(false);
        DbObjectHolder myHolder = createHolder();
        assertEquals(myHolder.getCreationDate(), Optional.empty());
        assertEquals(false, DbObjectProperties.isTrackCreationDate());

        DbObjectProperties.setTrackCreationDate(true);
        DbObjectHolder myHolder2 = createHolder();
        assertEquals(myHolder2.getCreationDate().get(), timestamp);
        assertEquals(true, DbObjectProperties.isTrackCreationDate());
    }


}