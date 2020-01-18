package cars.s16281.tau.labone;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import cars.s16281.tau.labone.services.CarImpl;
import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.DbObjectHolder;
import cars.s16281.tau.labone.services.DbObjectProperties;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;
import cars.s16281.tau.labone.services.DateTimeProvider;;



public class DateMockTest {

    private LocalDateTime timestamp;

    @Mock
    private DateTimeProvider provider;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        newMockTimestamp();
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
    public void testPass(){
        assertEquals("matching 2 with 2", 2,2);
        System.out.println("SIEMANKO");
    }

    @Test
    public void dateTimeProviderObjectCanBeCreated(){
        // Object on creation memorizes it's creation date time.
        DateTimeProvider provider = new DateTimeProvider();
        LocalDateTime creationDate =  provider.getObjectCreationTime();
        //System.out.println(creationDate + "   " + LocalDateTime.now());
        assertTrue("Coś poszło nie tak, to te same obiekty", creationDate != LocalDateTime.now()); // dwa różne obiekty, zbliżone czasy
    }

    @Test
    public void dateTimeProvider_returns_NOW_when_asked(){
        // Object when asked returns a LocalDateTime.now()
        DateTimeProvider provider = new DateTimeProvider();
        //System.out.println(provider.get()+ "   " +LocalDateTime.now());
        assertTrue("To te same obiekty, coś poczło nie tak", provider.get() != LocalDateTime.now()); // dwa różne obiekty, zbliżone czasy
    }

    @Test
    public void dbObjectHolder_getCar_method_should_return_a_car(){
        DbObjectHolder myHolder = createHolder();
        CarImpl car = myHolder.getCar();
        //System.out.println("DANE AUTA Z HOLDERA:"+ car.getBrand() +"   "+ car.getColor());
        assertEquals("Powinien zwrócić brand: Audi ", car.getBrand(), "Audi");
    }

    @Test
    public void dbObjectHolder_getCreationDate_method_should_return_creationDateTime(){
        // Database records creation date at init
        DbObjectHolder myHolder = createHolder();
        LocalDateTime creationDate = myHolder.getCreationDate().get(); // z get() zwróci typ: LocalDateTime
        System.out.println("Creationd date time: "+creationDate + "   " +timestamp);
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
    

}