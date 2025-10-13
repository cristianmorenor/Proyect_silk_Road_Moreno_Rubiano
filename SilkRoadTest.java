import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de pruebas para SilkRoad.
 * Contiene pruebas unitarias para todos los métodos públicos de la clase
 * SilkRoad.
 * Para cada método se incluyen 2 pruebas que pasan y 1 que falla
 * intencionalmente.
 * 
 * @author MorenoRubiano
 * @version 1.0
 */
public class SilkRoadTest {

    @Test
    public void testPlaceStore() {
        SilkRoad road = new SilkRoad(5);
        road.placeStore(1, 100);
        int[][] stores = road.stores();

        assertEquals(1, stores.length);
        assertEquals(1, stores[0][0]);
        assertEquals(100, stores[0][1]);
        assertTrue(road.ok());
    }

    @Test
    public void testDuplicateStore() {
        SilkRoad road = new SilkRoad(5);
        road.placeStore(1, 100);
        road.placeStore(1, 200); // intento duplicado

        // La acción debe fallar
        assertFalse(road.ok());
        // Debe seguir habiendo solo una tienda
        assertEquals(1, road.stores().length);
    }

    @Test
    public void testRemoveStore() {
        SilkRoad road = new SilkRoad(5);
        road.placeStore(2, 50);
        road.removeStore(2);
        assertTrue(road.ok());
        assertEquals(0, road.stores().length);
    }

    @Test
    public void testPlaceRobot() {
        SilkRoad road = new SilkRoad(5);
        road.placeRobot(1, 0);
        int[][] robots = road.robots();
        assertEquals(1, robots.length);
        assertEquals(1, robots[0][0]);
        assertTrue(road.ok());
    }

    @Test
    public void testDuplicateRobot() {
        SilkRoad road = new SilkRoad(5);
        road.placeRobot(1, 0);
        road.placeRobot(1, 0); // intento duplicado
        assertFalse(road.ok());
        assertEquals(1, road.robots().length);
    }

    @Test
    public void testRemoveRobot() {
        SilkRoad road = new SilkRoad(5);
        road.placeRobot(3, 0);
        road.removeRobot(3);
        assertTrue(road.ok());
        assertEquals(0, road.robots().length);
    }

    @Test
    public void testMoveRobotAndCollect() {
        SilkRoad road = new SilkRoad(10);
        road.placeStore(2, 100);
        road.placeRobot(1, 0);

        road.moveRobot(1, 1); // el robot se mueve a la tienda (2)

        int[][] robots = road.robots();
        int[][] stores = road.stores();

        // La tienda debe quedar vacía
        assertEquals(0, stores[0][1]);

        // El robot debe tener los 100 tenges recogidos
        assertEquals(100, robots[0][1]);

        // La barra de progreso debe haberse actualizado
        assertTrue(road.profitBar() > 0);
    }

    @Test
    public void testResupplyStores() {
        SilkRoad road = new SilkRoad(5);
        road.placeStore(1, 50);
        road.placeStore(2, 80);

        // Vaciar una tienda manualmente
        road.moveRobot(0, 0); // sin efecto
        Store store = new Store(1, 50);
        store.emptyStore();

        // Reabastecer todas
        road.resupplyStores();

        int[][] stores = road.stores();
        assertEquals(2, stores.length);
        // No debería haber tiendas vacías después del reabastecimiento
        for (int[] s : stores) {
            assertTrue(s[1] >= 50);
        }
    }

    @Test
    public void testReturnRobots() {
        SilkRoad road = new SilkRoad(5);
        road.placeRobot(1, 0);
        road.moveRobot(1, 2);
        road.returnRobots();
        int[][] robots = road.robots();
        assertEquals(1, robots[0][0]); // vuelve a posición inicial
    }

    @Test
    public void testReboot() {
        SilkRoad road = new SilkRoad(5);
        road.placeStore(1, 100);
        road.placeRobot(1, 0);
        road.moveRobot(1, 0); // sin efecto
        road.reboot();
        assertTrue(road.ok());
        assertEquals(0, road.totalProfit());
    }

    @Test
    public void testProfitCalculation() {
        SilkRoad road = new SilkRoad(5);
        int expected = 5 * 100; // longitud * 100
        assertEquals(expected, road.profit());
    }

    @Test
    public void testFinish() {
        // Este test solo verifica que finish() no arroje error
        SilkRoad road = new SilkRoad(3);
        try {
            road.finish();
        } catch (Exception e) {
            fail("finish() lanzó una excepción: " + e.getMessage());
        }
    }
}
