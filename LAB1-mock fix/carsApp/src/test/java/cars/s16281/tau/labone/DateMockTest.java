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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import cars.s16281.tau.labone.services.CarImpl;
import cars.s16281.tau.labone.services.DbImpl;
import cars.s16281.tau.labone.services.EngineImpl;
import cars.s16281.tau.labone.services.GearboxImpl;
import cars.s16281.tau.labone.services.DateTimeProvider;;



public class DateMockTest {

    private LocalDateTime timestamp;

    @Mock
    private DateTimeProvider provider;

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
        System.out.println(creationDate + "   " + LocalDateTime.now());
        assertTrue(creationDate != LocalDateTime.now()); // dwa różne obiekty, zbliżone czasy
    }

    @Test
    public void dateTimeProvider_returns_NOW_when_asked(){
        // Object when asked returns a LocalDateTime.now()
        DateTimeProvider provider = new DateTimeProvider();
        System.out.println(provider.get()+ "   " +LocalDateTime.now());
        assertTrue(provider.get() != LocalDateTime.now()); // dwa różne obiekty, zbliżone czasy
    }
    

}