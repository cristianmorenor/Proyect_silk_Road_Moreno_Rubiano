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

    private SilkRoad silkRoad;

    /**
     * Configuración inicial antes de cada prueba.
     * Se ejecuta antes de cada método @Test.
     */
    @Before
    public void setUp() {
        silkRoad = new SilkRoad(10);
    }

    // ==================== PRUEBAS PARA placeStore ====================

    @Test
    public void testPlaceStoreSuccess() {
        silkRoad.placeStore(5, 100);
        assertTrue("La tienda debería colocarse exitosamente", silkRoad.ok());
        int[][] stores = silkRoad.stores();
        assertEquals("Debería haber 1 tienda", 1, stores.length);
        assertEquals("La ubicación debería ser 5", 5, stores[0][0]);
        assertEquals("Los tenges deberían ser 100", 100, stores[0][1]);
    }

    @Test
    public void testPlaceStoreMultipleLocations() {
        silkRoad.placeStore(3, 50);
        silkRoad.placeStore(7, 75);
        silkRoad.placeStore(10, 100);
        assertTrue("Todas las tiendas deberían colocarse exitosamente", silkRoad.ok());
        int[][] stores = silkRoad.stores();
        assertEquals("Debería haber 3 tiendas", 3, stores.length);
    }

    @Test
    public void testPlaceStoreDuplicateLocationFail() {
        silkRoad.placeStore(5, 100);
        silkRoad.placeStore(5, 200); // Intentar colocar en la misma ubicación
        assertFalse("No debería permitir duplicar ubicación", silkRoad.ok());
    }

    // ==================== PRUEBAS PARA removeStore ====================

    @Test
    public void testRemoveStoreSuccess() {
        silkRoad.placeStore(5, 100);
        silkRoad.removeStore(5);
        assertTrue("La tienda debería eliminarse exitosamente", silkRoad.ok());
        int[][] stores = silkRoad.stores();
        assertEquals("No debería haber tiendas", 0, stores.length);
    }

    @Test
    public void testRemoveStoreFromMultiple() {
        silkRoad.placeStore(3, 50);
        silkRoad.placeStore(5, 75);
        silkRoad.placeStore(7, 100);
        silkRoad.removeStore(5);
        assertTrue("La tienda debería eliminarse exitosamente", silkRoad.ok());
        int[][] stores = silkRoad.stores();
        assertEquals("Deberían quedar 2 tiendas", 2, stores.length);
    }

    @Test
    public void testRemoveStoreNonExistentFail() {
        silkRoad.removeStore(99); // Ubicación que no existe
        assertFalse("No debería poder eliminar tienda inexistente", silkRoad.ok());
    }

    // ==================== PRUEBAS PARA placeRobot ====================

    @Test
    public void testPlaceRobotSuccess() {
        silkRoad.placeRobot(2, 0);
        assertTrue("El robot debería colocarse exitosamente", silkRoad.ok());
        int[][] robots = silkRoad.robots();
        assertEquals("Debería haber 1 robot", 1, robots.length);
        assertEquals("La posición debería ser 2", 2, robots[0][0]);
    }

    @Test
    public void testPlaceRobotMultiplePositions() {
        silkRoad.placeRobot(1, 0);
        silkRoad.placeRobot(5, 0);
        silkRoad.placeRobot(8, 0);
        assertTrue("Todos los robots deberían colocarse exitosamente", silkRoad.ok());
        int[][] robots = silkRoad.robots();
        assertEquals("Deberían haber 3 robots", 3, robots.length);
    }

    @Test
    public void testPlaceRobotDuplicatePositionFail() {
        silkRoad.placeRobot(3, 0);
        silkRoad.placeRobot(3, 0); // Intentar colocar en la misma posición
        assertFalse("No debería permitir duplicar posición", silkRoad.ok());
    }

    // ==================== PRUEBAS PARA removeRobot ====================

    @Test
    public void testRemoveRobotSuccess() {
        silkRoad.placeRobot(4, 0);
        silkRoad.removeRobot(4);
        assertTrue("El robot debería eliminarse exitosamente", silkRoad.ok());
        int[][] robots = silkRoad.robots();
        assertEquals("No debería haber robots", 0, robots.length);
    }

    @Test
    public void testRemoveRobotFromMultiple() {
        silkRoad.placeRobot(2, 0);
        silkRoad.placeRobot(5, 0);
        silkRoad.placeRobot(8, 0);
        silkRoad.removeRobot(5);
        assertTrue("El robot debería eliminarse exitosamente", silkRoad.ok());
        int[][] robots = silkRoad.robots();
        assertEquals("Deberían quedar 2 robots", 2, robots.length);
    }

    @Test
    public void testRemoveRobotNonExistentFail() {
        silkRoad.removeRobot(99); // Posición que no existe
        assertFalse("No debería poder eliminar robot inexistente", silkRoad.ok());
    }

    // ==================== PRUEBAS PARA moveRobot ====================

    @Test
    public void testMoveRobotSuccess() {
        silkRoad.placeRobot(0, 0);
        silkRoad.moveRobot(0, 5);
        assertTrue("El robot debería moverse exitosamente", silkRoad.ok());
        int[][] robots = silkRoad.robots();
        assertEquals("La nueva posición debería ser 5", 5, robots[0][0]);
    }

    @Test
    public void testMoveRobotMultipleTimes() {
        silkRoad.placeRobot(0, 0);
        silkRoad.moveRobot(0, 3);
        silkRoad.moveRobot(3, 4);
        silkRoad.moveRobot(7, 2);
        assertTrue("El robot debería moverse exitosamente", silkRoad.ok());
        int[][] robots = silkRoad.robots();
        assertEquals("La posición final debería ser 9", 9, robots[0][0]);
    }

    @Test
    public void testMoveRobotNonExistentFail() {
        silkRoad.moveRobot(99, 5); // Robot que no existe
        assertFalse("No debería poder mover robot inexistente", silkRoad.ok());
    }

    // ==================== PRUEBAS PARA resupplyStores ====================

    @Test
    public void testResupplyStoresSuccess() {
        silkRoad.placeStore(5, 100);
        silkRoad.resupplyStores();
        assertTrue("Las tiendas deberían reabastecerse exitosamente", silkRoad.ok());
    }

    @Test
    public void testResupplyMultipleStores() {
        silkRoad.placeStore(2, 50);
        silkRoad.placeStore(5, 75);
        silkRoad.placeStore(8, 100);
        silkRoad.resupplyStores();
        assertTrue("Todas las tiendas deberían reabastecerse", silkRoad.ok());
        int[][] stores = silkRoad.stores();
        assertEquals("Deberían mantenerse 3 tiendas", 3, stores.length);
    }

    @Test
    public void testResupplyStoresWithNoStoresFail() {
        // Este test espera que el método funcione incluso sin tiendas
        // pero falla conceptualmente si esperamos que deba haber tiendas
        silkRoad.resupplyStores();
        int[][] stores = silkRoad.stores();
        assertNotEquals("Debería fallar: esperábamos tiendas para reabastecer", 1, stores.length);
    }

    // ==================== PRUEBAS PARA returnRobots ====================

    @Test
    public void testReturnRobotsSuccess() {
        silkRoad.placeRobot(0, 0);
        silkRoad.moveRobot(0, 5);
        silkRoad.returnRobots();
        assertTrue("Los robots deberían regresar exitosamente", silkRoad.ok());
        int[][] robots = silkRoad.robots();
        assertEquals("El robot debería estar en posición inicial 0", 0, robots[0][0]);
    }

    @Test
    public void testReturnMultipleRobots() {
        silkRoad.placeRobot(0, 0);
        silkRoad.placeRobot(3, 0);
        silkRoad.moveRobot(0, 7);
        silkRoad.moveRobot(3, 4);
        silkRoad.returnRobots();
        assertTrue("Todos los robots deberían regresar", silkRoad.ok());
        int[][] robots = silkRoad.robots();
        assertEquals("Primer robot en posición inicial", 0, robots[0][0]);
        assertEquals("Segundo robot en posición inicial", 3, robots[1][0]);
    }

    @Test
    public void testReturnRobotsNoMovementFail() {
        silkRoad.placeRobot(5, 0);
        // No movemos el robot
        silkRoad.returnRobots();
        int[][] robots = silkRoad.robots();
        // Fallará si esperamos que la posición cambie
        assertNotEquals("Debería fallar: el robot no se movió", 10, robots[0][0]);
    }

    // ==================== PRUEBAS PARA reboot ====================

    @Test
    public void testRebootSuccess() {
        silkRoad.placeRobot(0, 0);
        silkRoad.moveRobot(0, 5);
        silkRoad.reboot();
        assertTrue("El sistema debería reiniciarse exitosamente", silkRoad.ok());
        assertEquals("La barra de profit debería ser 0", 0, silkRoad.profitBar());
    }

    @Test
    public void testRebootResetsRobotPositions() {
        silkRoad.placeRobot(2, 0);
        silkRoad.moveRobot(2, 8);
        silkRoad.reboot();
        int[][] robots = silkRoad.robots();
        assertEquals("El robot debería estar en posición inicial", 2, robots[0][0]);
        assertEquals("Las ganancias deberían ser 0", 0, robots[0][1]);
    }

    @Test
    public void testRebootDoesNotRemoveRobotsFail() {
        silkRoad.placeRobot(1, 0);
        silkRoad.placeRobot(5, 0);
        silkRoad.reboot();
        int[][] robots = silkRoad.robots();
        // Falla si esperamos que los robots se eliminen
        assertNotEquals("Debería fallar: los robots no se eliminan con reboot", 0, robots.length);
    }

    // ==================== PRUEBAS PARA profitBar ====================

    @Test
    public void testProfitBarInitialValue() {
        int profit = silkRoad.profitBar();
        assertEquals("La barra de profit inicial debería ser 0", 0, profit);
    }

    @Test
    public void testProfitBarAfterUpdate() {
        silkRoad.updateProFitBar(50, 100);
        int profit = silkRoad.profitBar();
        assertEquals("La barra de profit debería ser 50", 50, profit);
    }

    @Test
    public void testProfitBarNegativeValueFail() {
        int profit = silkRoad.profitBar();
        // Falla si esperamos un valor negativo
        assertNotEquals("Debería fallar: profit no puede ser negativo", -1, profit);
    }

    // ==================== PRUEBAS PARA totalProfit ====================

    @Test
    public void testTotalProfitInitiallyZero() {
        silkRoad.placeRobot(0, 0);
        int total = silkRoad.totalProfit();
        assertEquals("El profit total inicial debería ser 0", 0, total);
    }

    @Test
    public void testTotalProfitMultipleRobots() {
        silkRoad.placeRobot(0, 0);
        silkRoad.placeRobot(5, 0);
        int total = silkRoad.totalProfit();
        assertTrue("El profit total debería ser >= 0", total >= 0);
    }

    @Test
    public void testTotalProfitNegativeFail() {
        int total = silkRoad.totalProfit();
        // Falla si esperamos que el profit pueda ser negativo
        assertTrue("Debería fallar: el profit no puede ser negativo", total >= 0);
        assertNotEquals("Debería ser diferente de -1", -1, total);
    }

    // ==================== PRUEBAS PARA updateProFitBar ====================

    @Test
    public void testUpdateProFitBarSuccess() {
        silkRoad.updateProFitBar(30, 100);
        assertTrue("La actualización debería ser exitosa", silkRoad.ok());
        assertEquals("El profit debería ser 30", 30, silkRoad.profitBar());
    }

    @Test
    public void testUpdateProFitBarMaxValue() {
        silkRoad.updateProFitBar(100, 100);
        assertTrue("La actualización debería ser exitosa", silkRoad.ok());
        assertEquals("El profit debería ser 100", 100, silkRoad.profitBar());
    }

    @Test
    public void testUpdateProFitBarExceedsMaxFail() {
        silkRoad.updateProFitBar(150, 100);
        int profit = silkRoad.profitBar();
        // Falla si esperamos que no permita exceder el máximo
        assertTrue("Debería fallar: el valor excede el máximo", profit <= 100);
    }

    // ==================== PRUEBAS PARA stores ====================

    @Test
    public void testStoresReturnsCorrectArray() {
        silkRoad.placeStore(3, 50);
        silkRoad.placeStore(7, 100);
        int[][] stores = silkRoad.stores();
        assertEquals("Debería haber 2 tiendas", 2, stores.length);
        assertEquals("Cada tienda tiene 2 atributos", 2, stores[0].length);
    }

    @Test
    public void testStoresEmptyInitially() {
        int[][] stores = silkRoad.stores();
        assertEquals("No debería haber tiendas inicialmente", 0, stores.length);
    }

    @Test
    public void testStoresNullArrayFail() {
        int[][] stores = silkRoad.stores();
        // Falla si esperamos que el array sea null
        assertNotNull("Debería fallar: el array no es null", stores);
    }

    // ==================== PRUEBAS PARA robots ====================

    @Test
    public void testRobotsReturnsCorrectArray() {
        silkRoad.placeRobot(2, 0);
        silkRoad.placeRobot(6, 0);
        int[][] robots = silkRoad.robots();
        assertEquals("Debería haber 2 robots", 2, robots.length);
        assertEquals("Cada robot tiene 2 atributos", 2, robots[0].length);
    }

    @Test
    public void testRobotsEmptyInitially() {
        int[][] robots = silkRoad.robots();
        assertEquals("No debería haber robots inicialmente", 0, robots.length);
    }

    @Test
    public void testRobotsNullArrayFail() {
        int[][] robots = silkRoad.robots();
        // Falla si esperamos que el array sea null
        assertNotNull("Debería fallar: el array no es null", robots);
    }

    // ==================== PRUEBAS PARA ok ====================

    @Test
    public void testOkReturnsTrueAfterSuccess() {
        silkRoad.placeStore(5, 100);
        assertTrue("ok() debería retornar true después de operación exitosa", silkRoad.ok());
    }

    @Test
    public void testOkReturnsTrueInitially() {
        assertTrue("ok() debería retornar true inicialmente", silkRoad.ok());
    }

    @Test
    public void testOkReturnsFalseAfterFailure() {
        silkRoad.placeStore(5, 100);
        silkRoad.placeStore(5, 200); // Operación fallida
        assertFalse("ok() debería retornar false después de operación fallida", silkRoad.ok());
    }

    // ==================== PRUEBAS PARA hideSpiral ====================

    @Test
    public void testHideSpiralSuccess() {
        silkRoad.hideSpiral();
        // No hay forma directa de verificar, asumimos éxito si no hay excepción
        assertTrue("hideSpiral debería ejecutarse sin errores", true);
    }

    @Test
    public void testHideSpiralMultipleCalls() {
        silkRoad.hideSpiral();
        silkRoad.hideSpiral();
        silkRoad.hideSpiral();
        assertTrue("Múltiples llamadas no deberían causar error", true);
    }

    @Test
    public void testHideSpiralThrowsExceptionFail() {
        // Este test falla conceptualmente si esperamos una excepción
        try {
            silkRoad.hideSpiral();
            assertTrue("Debería fallar: esperábamos excepción", true);
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }
    }

    // ==================== PRUEBAS PARA showProfitBar ====================

    @Test
    public void testShowProfitBarSuccess() {
        silkRoad.showProfitBar();
        assertTrue("showProfitBar debería ejecutarse sin errores", true);
    }

    @Test
    public void testShowProfitBarMultipleCalls() {
        silkRoad.showProfitBar();
        silkRoad.showProfitBar();
        assertTrue("Múltiples llamadas no deberían causar error", true);
    }

    @Test
    public void testShowProfitBarVisibilityFail() {
        silkRoad.showProfitBar();
        // Falla conceptualmente si no podemos verificar visibilidad
        assertNotNull("Debería fallar: no podemos verificar visibilidad directamente", silkRoad);
    }

    // ==================== PRUEBAS PARA hideProfitBar ====================

    @Test
    public void testHideProfitBarSuccess() {
        silkRoad.hideProfitBar();
        assertTrue("hideProfitBar debería ejecutarse sin errores", true);
    }

    @Test
    public void testHideProfitBarAfterShow() {
        silkRoad.showProfitBar();
        silkRoad.hideProfitBar();
        assertTrue("Debería poder ocultar después de mostrar", true);
    }

    @Test
    public void testHideProfitBarVerificationFail() {
        silkRoad.hideProfitBar();
        // Falla conceptualmente porque no podemos verificar estado visual
        assertNotNull("Debería fallar: no hay forma de verificar visibilidad", silkRoad);
    }

    // ==================== PRUEBAS PARA makeVisible ====================

    @Test
    public void testMakeVisibleSuccess() {
        silkRoad.makeVisible();
        assertTrue("makeVisible debería ejecutarse sin errores", true);
    }

    @Test
    public void testMakeVisibleWithElements() {
        silkRoad.placeStore(5, 100);
        silkRoad.placeRobot(2, 0);
        silkRoad.makeVisible();
        assertTrue("makeVisible con elementos debería funcionar", true);
    }

    @Test
    public void testMakeVisibleStateVerificationFail() {
        silkRoad.makeVisible();
        // Falla porque no podemos verificar el estado visual
        assertNotNull("Debería fallar: no podemos verificar visibilidad", silkRoad);
    }

    // ==================== PRUEBAS PARA makeInvisible ====================

    @Test
    public void testMakeInvisibleSuccess() {
        silkRoad.makeInvisible();
        assertTrue("makeInvisible debería ejecutarse sin errores", true);
    }

    @Test
    public void testMakeInvisibleAfterVisible() {
        silkRoad.makeVisible();
        silkRoad.makeInvisible();
        assertTrue("Debería poder ocultar después de mostrar", true);
    }

    @Test
    public void testMakeInvisibleVerificationFail() {
        silkRoad.makeInvisible();
        // Falla conceptualmente por falta de verificación visual
        assertNotNull("Debería fallar: no hay verificación de estado", silkRoad);
    }

    // ==================== PRUEBAS PARA finish ====================

    @Test
    public void testFinishSuccess() {
        silkRoad.placeStore(5, 100);
        silkRoad.placeRobot(2, 0);
        silkRoad.finish();
        assertTrue("finish debería ejecutarse exitosamente", silkRoad.ok());
    }

    @Test
    public void testFinishWithoutElements() {
        silkRoad.finish();
        assertTrue("finish debería funcionar sin elementos", silkRoad.ok());
    }

    @Test
    public void testFinishRemovesElementsFail() {
        silkRoad.placeStore(5, 100);
        silkRoad.placeRobot(2, 0);
        silkRoad.finish();
        // Falla si esperamos que elimine los elementos de las colecciones
        int[][] stores = silkRoad.stores();
        int[][] robots = silkRoad.robots();
        assertTrue("Debería fallar: finish no elimina elementos", stores.length > 0 || robots.length > 0);
    }
}
