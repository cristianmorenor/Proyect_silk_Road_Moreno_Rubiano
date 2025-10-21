import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase de prueba para el Ciclo 2 de SilkRoad.
 * 
 * Verificar la correcta implementación de los métodos:
 * - moveRobots()
 * - findBestStore()
 * - highlightBestRobot()
 * - profitPerMove()
 * - emptiedStores()
 * @Autores: CristianMoreno-JuanitaRubiano
 * @version Ciclo2_2025
 */

public class SilkRoadCC2Test {

    private SilkRoad road;

    @Before
    public void setUp() {
        // Base con tiendas y robots
        road = new SilkRoad(100);
        road.placeStore(10, 20);
        road.placeStore(40, 80);
        road.placeStore(70, 50);

        road.placeRobot(0, 0);
        road.placeRobot(20, 0);
    }

    @After
    public void tearDown() {
        road = null;
    }
    
    /**
     * Prueba que verifica que los robots se mueven automáticamente hacia la mejor tienda
     * según la rentabilidad (ratio) : ganancia/distancia.
     */
    @Test
    public void accordingMRShouldMoveRobotsToBestStores() {
        road.moveRobots();
        int[][] robots = road.robots();
        assertTrue("El primer robot no se movió correctamente", robots[0][0] > 0);
    }
    
    /**
     * Verifica que el método findBestStore selecciona correctamente
     * la tienda con mejor relación tenges/distancia.
     */
    @Test
    public void accordingMRShouldFindBestStoreByRatio() {
        // para evitar tiempos largos de ejecucion de la prueba
        road.hideSpiral();
        road.hideProfitBar();
        // Crear un SilkRoad nuevo de prueba con algunas tiendas con ratios 
        SilkRoad road = new SilkRoad(100);
        road.placeStore(10, 100);   // ratio = 100/10 = 10
        road.placeStore(40, 300);   // ratio = 300/40 = 7.5
        road.placeStore(80, 200);   // ratio = 200/80 = 2.5

        // Creamos un robot para simular posición inicial y asi probar
        road.placeRobot(0, 0);

        // Invocar el método privado al mover robots
        road.moveRobots();

        // El robot debería moverse a la tienda más rentable (ubicación 10)
        int[][] robots = road.robots();
        assertEquals("El robot no se movió a la tienda con mejor ratio esperado (ubicación 10)",
    10, robots[0][0]);
    }

    
    
    
    /**
     * Verificar que el robot con mayor ganancia sea detectado y parpadee como pide el requisito funciona
     */
    @Test
    public void accordingMRShouldHighlightBestRobot() {
        road.moveRobots();
        road.highlightBestRobot();
        assertTrue("El método highlightBestRobot debe ejecutarse sin errores", road.ok());
    }
    
     /**
     * Verificar que después del movimiento, el robot con más tenges tenga mayor profit.
     * Para poder destacarlo
     */
    @Test
    public void accordingMRShouldUpdateRobotProfitAfterMoving() {
        road.moveRobots();
        int[][] robots = road.robots();

        int maxProfit = 0;
        for (int[] r : robots) {
            if (r[1] > maxProfit) maxProfit = r[1];
        }
        assertTrue("El profit no se actualizó correctamente", maxProfit > 0);
    }
    
    /**
     * Verificar que el método profitPerMove() devuelva información coherente con lo que se pide
     * Lo que se pide es  la ganancia por movimiento de cada robot
     */
    @Test
    public void accordingMRShouldReturnValidProfitPerMoveMatrix() {
        road.moveRobots();
        int[][] profitData = road.profitPerMove();

        assertNotNull("El método profitPerMove no puede retornar null", profitData);
        assertTrue("Debe contener al menos un registro de movimiento", profitData.length > 0);

        for (int[] row : profitData) {
            assertEquals("Cada registro debe tener dos columnas compuestas: (posición, profit)", 2, row.length);
        }
    }
    
    /**
     * Verificar que el método emptiedStores() reporte correctamente las tiendas vacías
     * El reporte se hace despues de cada movimiento de moveRobot()
     */
    @Test
    public void accordingMRShouldListEmptiedStoresCorrectly() {
        road.moveRobots();
        int[][] emptied = road.emptiedStores();

        assertNotNull("El método emptiedStores no puede retornar null", emptied);
        for (int[] storeInfo : emptied) {
            assertEquals("Cada registro debe tener dos columnas (ubicación, estado)", 2, storeInfo.length);
            assertTrue("Ubicación de tienda debe ser positiva", storeInfo[0] >= 0);
        }
    }
}

    



    

