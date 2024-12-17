import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.withSettings;

public class LoadBalancerTest {

    private LoadBalancer loadBalancer;
    private Random randomMock = Mockito.mock(Random.class);

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        loadBalancer = new LoadBalancer();
//        Field randomField = LoadBalancer.class.getDeclaredField("random");
//        randomField.setAccessible(true);
//        randomField.set(loadBalancer, randomMock);
        loadBalancer.random = randomMock;
    }

    @Test
    public void testContainsInstanceEmptyInstanceList() throws MalformedURLException {
        boolean result = loadBalancer.containsInstance(new URL("https://facebook.com"));
        assertFalse(result);
    }

    @Test
    public void testContainsInstanceDuplicateInstance() throws MalformedURLException {
        loadBalancer.addServerInstance(new URL("https://facebook.com"));
        boolean result = loadBalancer.containsInstance(new URL("https://facebook.com"));
        assertTrue(result);
    }

    @Test
    public void testAddNewServerInstances() throws MalformedURLException {
        loadBalancer.addServerInstance(new URL("https://facebook.com"));
        boolean result = loadBalancer.containsInstance(new URL("https://facebook.com"));
        assertTrue(result);
    }

    @Test
    public void testCannotAddDuplicate() throws MalformedURLException {
        loadBalancer.addServerInstance(new URL("https://facebook.com"));
        try {
            loadBalancer.addServerInstance(new URL("https://facebook.com"));
            fail("Expected Exception here");
        } catch (IllegalArgumentException e) {
//            Deliberately left empty
        }
    }

    @Test
    public void testRetrieveRandomInstance() throws MalformedURLException {
        loadBalancer.addServerInstance(new URL("https://facebook.com"));
        loadBalancer.addServerInstance(new URL("https://revolut.com"));
        Mockito.when(randomMock.nextInt()).thenReturn(1);
        ServerInstance result = loadBalancer.retrieveServerInstance();

        assertEquals(new URL("https://revolut.com"), result.getBackendURL());
        Mockito.verify(randomMock).nextInt(2);

    }


}
