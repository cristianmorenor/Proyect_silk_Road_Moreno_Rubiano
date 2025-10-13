import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import javax.swing.JOptionPane; // <- para popups

/**
 * La clase SilkRoad representa el juego principal de la Ruta de la Seda.
 * 
 * Esta clase actúa como el controlador principal del juego, gestionando:
 * - Las tiendas distribuidas a lo largo de la ruta
 * - Los robots que viajan y recolectan tenges
 * - La barra de progreso que muestra las ganancias
 * - Las soluciones y abstracciones del juego
 * 
 * El juego simula el comercio en la histórica Ruta de la Seda, donde
 * los robots representan comerciantes que viajan entre tiendas para
 * maximizar sus ganancias recolectando tenges (moneda del juego).
 * 
 * @author MorenoRubiano
 * @version 2.0
 */
public class SilkRoad {

    List<String> colorsList = java.util.Arrays.asList("red", "black", "blue", "yellow", "green", "magenta");

    /**
     * Mapa de tiendas organizadas por ubicación (clave: ubicación, valor: Store)
     */
    private final Map<Integer, Store> stores;

    /** Lista de robots que participan en el juego como comerciantes */
    private final ArrayList<Robot> robots;

    /** Objeto que representa el patrón espiral visual del juego */
    private final SpiralPath spiralPath;

    /**
     * Mapa que relaciona robots con tiendas para abstraer y almacenar soluciones
     * del juego (clave: Robot, valor: Store asociada)
     */
    private final HashMap<Robot, Store> solutionAbstraction;

    /** Barra de progreso visual que muestra las ganancias acumuladas del juego */
    private final ProfitBar profitBar;

    /**
     * Indicador del éxito de la última acción ejecutada
     * true si la última operación fue exitosa, false en caso contrario
     */
    private boolean lastActionSuccess = true;

    /** Longitud del recorrido o número de segmentos del juego */
    private int lengh;

    /**
     * Constructor del ciclo 1 - Inicializa el juego con una longitud específica.
     * 
     * Crea una nueva instancia del juego SilkRoad con las estructuras de datos
     * necesarias y configura la barra de progreso basada en la longitud
     * proporcionada.
     * El máximo de la barra de progreso se calcula como longitud * 100.
     * 
     * @param lenght La longitud del recorrido que determina el máximo de ganancias
     *               posibles
     */
    public SilkRoad(int lenght) {
        this.stores = new TreeMap<>();
        this.robots = new ArrayList<>();
        this.solutionAbstraction = new HashMap<>();
        // Ajuste: espiral depende de la longitud (espesor proporcional)
        this.spiralPath = new SpiralPath(20, 20);
        this.profitBar = new ProfitBar(300, 300, lenght * 100);
        this.lengh = lenght;
        if (!isRunningUnderJUnit()) {
            this.makeVisible();
        }
    }

    /**
     * Constructor del ciclo 2 - Inicializa el juego con una matriz de días.
     * 
     * Crea una nueva instancia del juego SilkRoad para escenarios multi-día.
     * La barra de progreso se configura basada en el número de columnas de la
     * matriz de días multiplicado por 100.
     * 
     * @param days Matriz bidimensional que representa los días del juego
     */
    public SilkRoad(int[][] days) {
        this.stores = new TreeMap<>();
        this.robots = new ArrayList<>();
        this.spiralPath = null;
        this.solutionAbstraction = new HashMap<>();
        this.profitBar = new ProfitBar(300, 300, days[0].length * 100);

    }

    /**
     * Verifica si la última acción ejecutada fue exitosa.
     * 
     * @return true si la última operación fue exitosa, false en caso contrario
     */
    public boolean ok() {
        return this.lastActionSuccess;
    }

    /**
     * Coloca una nueva tienda en una ubicación específica con una cantidad inicial
     * de tenges.
     * 
     * Crea una nueva instancia de Store con la posición y cantidad de tenges
     * especificadas,
     * y la agrega al mapa de tiendas usando la ubicación como clave.
     * 
     * @param location La posición donde se ubicará la tienda
     * @param tenges   La cantidad inicial de tenges que contendrá la tienda
     */
    public void placeStore(int location, int tenges, String color) {
        if (this.stores.containsKey(location)) {
            this.lastActionSuccess = false;
            showMessage("Ya existe una tienda en esta ubicación.");
            return;
        }
        Store store = new Store(location, tenges);
        // Posicionar en el canvas según la espiral (10px ABAJO)
        int[] xy = mapLocationToCanvas(location);
        store.updateVisualPosition(xy[0], xy[1] + 10);
        store.changeColor(color);
        this.stores.put(location, store);
        this.lastActionSuccess = true;
        this.redraw();

    }

    public void placeStore(int location, int tenges) {
        placeStore(location, tenges, this.colorsList.get(2));
    }

    /**
     * Elimina una tienda de la ubicación especificada.
     * 
     * Remueve la tienda del mapa de tiendas utilizando la ubicación como clave.
     * Si no existe una tienda en esa ubicación, el método no realiza ninguna
     * acción.
     * 
     * @param location La posición de la tienda que se desea eliminar
     */
    public void removeStore(int location) {
        if (!this.stores.containsKey(location)) {
            this.lastActionSuccess = false;
            showMessage("No hay tienda en esa ubicación.");
            return;
        }
        // Ajuste: ocultar visualmente antes de eliminar
        Store store = this.stores.get(location);
        store.makeInvisible();
        this.stores.remove(location);
        this.lastActionSuccess = true;
        this.redraw();
    }

    /**
     * Coloca un nuevo robot en una ubicación específica.
     * 
     * Crea una nueva instancia de Robot con la posición especificada y lo agrega
     * a la lista de robots. El parámetro tenges no se utiliza en la implementación
     * actual.
     * 
     * @param location La posición inicial donde se ubicará el robot
     * @param tenges   Parámetro no utilizado en la implementación actual
     */
    public void placeRobot(int location, int tenges, String robotColor) {
        if (robotColor == null) {
            robotColor = "random";
        }
        if (findRobotAtLocation(location) != null) {
            this.lastActionSuccess = false;
            showMessage("Ya existe un robot en esa ubicación.");
            return;
        }
        Robot robot = new Robot(location);
        // Posicionar en el canvas según la espiral (10px ARRIBA)
        int[] xy = mapLocationToCanvas(location);
        robot.updateVisualPosition(xy[0], xy[1] - 10);
        robot.changeColor(robotColor);
        this.robots.add(robot);
        this.lastActionSuccess = true;
        this.redraw();
    }

    // Overload for default color
    public void placeRobot(int location, int tenges) {
        placeRobot(location, tenges, this.colorsList.get((int) (Math.random() * this.colorsList.size())));
    }

    /**
     * Elimina un robot de la ubicación especificada.
     * 
     * Busca un robot en la posición indicada y, si lo encuentra, lo remueve
     * de la lista de robots. Si no hay ningún robot en esa ubicación,
     * no realiza ninguna acción.
     * 
     * @param location La posición del robot que se desea eliminar
     */
    public void removeRobot(int location) {
        Robot robot = findRobotAtLocation(location);
        if (robot == null) {
            this.lastActionSuccess = false;
            showMessage("No hay robot en esa ubicación.");
            return;
        }
        // Ajuste: ocultar visualmente antes de eliminar
        robot.makeInvisible();
        this.robots.remove(robot);
        this.lastActionSuccess = true;
        this.redraw();

    }

    /**
     * Busca un robot en la posición especificada.
     * 
     * Recorre la lista de robots para encontrar uno que esté ubicado
     * en la posición especificada.
     * 
     * @param location La posición en la que se busca el robot
     * @return El robot encontrado en esa posición, o null si no hay ninguno
     */
    private Robot findRobotAtLocation(int location) {
        for (Robot robot : this.robots) {
            if (robot.getPosition() == location) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Mueve un robot desde su ubicación actual una cantidad específica de metros.
     * 
     * Busca el robot en la ubicación especificada y lo mueve la cantidad de metros
     * indicada. Si no hay robot en esa ubicación o si los metros son <= 0,
     * no realiza ninguna acción.
     * 
     * @param location La ubicación actual del robot que se desea mover
     * @param meters   La cantidad de metros que el robot debe avanzar
     */
    public void moveRobot(int location, int meters) {
        if (meters <= 0) {
            return;
        }

        Robot robot = findRobotAtLocation(location);
        if (robot == null) {
            this.lastActionSuccess = false;
            showMessage("No existe un robot en esa ubicación.");
            return;
        }

        int newPosition = location + meters;
        robot.moveTo(newPosition);
        // Actualizar posición visual desde SilkRoad usando la espiral
        int[] xy = mapLocationToCanvas(newPosition);
        robot.updateVisualPosition(xy[0], xy[1] - 10); // 10px ARRIBA

        // Ajuste: si hay tienda en la nueva posición, vaciarla y sumar al robot
        Store store = this.stores.get(newPosition);
        if (store != null && !store.isEmpty()) {
            int taken = store.emptyStore();
            // pickTenges en Robot asigna; sumamos al total actual
            robot.pickTenges(robot.getProfit() + taken);
            // Actualizar barra tras recoger
            updateProFitBar(totalProfit(), profit());
        }

        this.redraw();
        this.lastActionSuccess = true;
    }

    /**
     * Reabastece todas las tiendas del juego.
     * 
     * Recorre todas las tiendas en el mapa y las reabastece a su cantidad
     * inicial de tenges, restaurando el estado original de cada tienda.
     */
    public void resupplyStores() {
        for (Store store : this.stores.values()) {
            store.resupply();
        }
        this.lastActionSuccess = true;
    }

    /**
     * Regresa todos los robots a sus posiciones iniciales.
     * 
     * Recorre todos los robots y los mueve de vuelta a sus ubicaciones
     * iniciales establecidas durante su creación.
     */
    public void returnRobots() {
        for (Robot robot : this.robots) {
            robot.goInitialPosition();
            int[] xy = mapLocationToCanvas(robot.getPosition());
            robot.updateVisualPosition(xy[0], xy[1] - 10);
        }
        this.lastActionSuccess = true;
    }

    /**
     * Reinicia completamente el estado del juego.
     * 
     * Este método restaura el juego a su estado inicial:
     * - Regresa todos los robots a sus posiciones iniciales
     * - Establece las ganancias de todos los robots a 0
     * - Limpia el mapa de abstracción de soluciones
     * - Resetea la barra de progreso
     */
    public void reboot() {
        for (Robot robot : this.robots) {
            robot.goInitialPosition();
            robot.pickTenges(0);
        }
        // Ajuste: también reabastecer tiendas
        for (Store store : this.stores.values()) {
            store.resupply();
        }
        this.solutionAbstraction.clear();
        this.profitBar.reset();
        this.lastActionSuccess = true;

    }

    /**
     * Obtiene el valor actual de la barra de progreso.
     * 
     * @return El valor actual de progreso de la barra de ganancias
     */
    public int profitBar() {
        return this.profitBar.getCurrent();

    }

    /**
     * Calcula el total de ganancias de todos los robots.
     * 
     * Suma las ganancias (tenges) de todos los robots en el juego
     * para obtener el profit total acumulado.
     * 
     * @return La suma total de ganancias de todos los robots
     */
    public int totalProfit() {
        int total = 0;
        for (Robot robot : this.robots) {
            total += robot.getProfit();
        }
        return total;
    }

    /**
     * Actualiza la barra de progreso con los valores de profit actuales.
     * 
     * Delega la actualización visual de la barra de progreso al objeto ProfitBar,
     * pasando los valores de profit actual y máximo posible.
     * 
     * @param currentProfit     El profit actual obtenido
     * @param maxPossibleProfit El máximo profit posible en el juego
     */
    public void updateProFitBar(int currentProfit, int maxPossibleProfit) {
        this.profitBar.updateProfitBar(currentProfit, maxPossibleProfit);
        this.lastActionSuccess = true;
    }

    /**
     * Obtiene información de todas las tiendas en formato de matriz.
     * 
     * Crea una matriz bidimensional donde cada fila representa una tienda
     * y las columnas contienen la ubicación y la cantidad de tenges
     * respectivamente.
     * 
     * @return Matriz int[][] donde [i][0] es la ubicación y [i][1] es la cantidad
     *         de tenges
     */
    public int[][] stores() {
        int[][] result = new int[this.stores.size()][2];
        int i = 0;

        for (Store store : this.stores.values()) {
            result[i][0] = store.getLocation();
            result[i][1] = store.getTenges();
            i++;
        }

        return result;
    }

    /**
     * Obtiene información de todos los robots en formato de matriz ordenada.
     * 
     * Crea una matriz bidimensional donde cada fila representa un robot
     * y las columnas contienen la posición y las ganancias respectivamente.
     * Los robots se ordenan por posición antes de generar la matriz.
     * 
     * @return Matriz int[][] donde [i][0] es la posición y [i][1] son las ganancias
     *         del robot
     */
    public int[][] robots() {
        int[][] result = new int[this.robots.size()][2];

        this.robots.sort((r1, r2) -> Integer.compare(r1.getPosition(), r2.getPosition()));

        for (int i = 0; i < this.robots.size(); i++) {
            Robot robot = this.robots.get(i);
            result[i][0] = robot.getPosition();
            result[i][1] = robot.getProfit();
        }

        return result;
    }

    /**
     * Muestra la espiral visual del juego.
     * 
     * Calcula la espiral con la longitud configurada y la hace visible
     * en el canvas. Si no hay spiralPath disponible, no hace nada.
     */
    private void showSpiral() {
        if (spiralPath != null) {
            spiralPath.calcSpiral(this.lengh);
            spiralPath.makeVisible();
        }
    }

    /**
     * Oculta la espiral visual del juego.
     * 
     * Hace invisible la espiral en el canvas. Si no hay spiralPath
     * disponible, no hace nada.
     */
    public void hideSpiral() {
        if (spiralPath != null) {
            spiralPath.makeInvisible();
        }
    }

    /**
     * Muestra la barra de progreso visual del juego.
     * 
     * Hace visible la barra de progreso que muestra las ganancias
     * acumuladas en el canvas.
     */
    public void showProfitBar() {
        profitBar.makeVisible();
    }

    /**
     * Oculta la barra de progreso visual del juego.
     * 
     * Hace invisible la barra de progreso en el canvas.
     */
    public void hideProfitBar() {
        profitBar.makeInvisible();
    }

    public void makeVisible() {
    // Primero dibujar la espiral para que quede al fondo
    showSpiral();

    //mostrar tiendas (encima del camino)
    for (Store store : this.stores.values()) {
        int[] xy = mapLocationToCanvas(store.getLocation());
        store.updateVisualPosition(xy[0], xy[1] + 10);
        store.makeVisible();
    }

    // Luego mostrar robots (encima de tiendas)
    for (Robot robot : this.robots) {
        int[] xy = mapLocationToCanvas(robot.getPosition());
        robot.updateVisualPosition(xy[0], xy[1] - 10);
        robot.makeVisible();
    }

    // Finalmente mostrar la barra (encima de todo)
    showProfitBar();
    }


    public void makeInvisible() {
        hideSpiral();
        hideProfitBar();
        for (Store store : this.stores.values()) {
            store.makeInvisible();
        }
        for (Robot robot : this.robots) {
            robot.makeInvisible();
        }
    }

    /**
     * Redibuja solo los elementos dinámicos del juego (robots y tiendas).
     * NO redibuja la espiral ni la barra de progreso, que son elementos estáticos.
     */
    public void redraw() { // Solo redibujar elementos que cambian de posición 
    for (Store store : this.stores.values()) { 
        store.makeInvisible(); store.makeVisible(); } 
    for (Robot robot : this.robots) { 
        robot.makeInvisible(); 
        robot.makeVisible(); 
    }
    }
    

    /**
     * Mapea una ubicación del camino (location) a coordenadas de canvas (x,y)
     * usando SpiralPath. Asegura que la espiral esté calculada antes de mapear.
     */
    private int[] mapLocationToCanvas(int location) {
        if (this.spiralPath == null) {
            return new int[] { 150, 150 }; // centro por defecto
        }
        // Si aún no se ha calculado, calcular sin hacer visible
        if (this.spiralPath.getTotalLength() == 0) {
            this.spiralPath.calcSpiral(this.lengh);
        }
        return this.spiralPath.getPositionOnPath(location);
    }

    /**
     * Finaliza y oculta todos los elementos visuales del juego.
     * 
     * Este método se encarga de hacer invisibles todos los componentes gráficos
     * del juego SilkRoad, incluyendo tiendas, robots, el camino espiral y la
     * barra de progreso. Es útil para limpiar completamente la interfaz visual
     * al finalizar una partida o cuando se necesita ocultar todos los elementos
     * de manera simultánea.
     * 
     * Los elementos que se ocultan son:
     * - Todas las tiendas del mapa
     * - Todos los robots de la lista
     * - El camino espiral (si existe)
     * - La barra de progreso de ganancias
     * 
     * Después de ejecutarse, la pantalla queda completamente limpia de elementos
     * del juego, aunque los datos internos se mantienen intactos.
     */
    public void finish() {
        for (Store store : this.stores.values()) {
            store.makeInvisible();
        }

        for (Robot robot : this.robots) {
            robot.makeInvisible();
        }

        if (spiralPath != null) {
            spiralPath.makeInvisible();
        }

        profitBar.makeInvisible();

        this.lastActionSuccess = true;
        // terminar ejecución del programa pero se ajusta para no cerrar bluej en modo test
        if (!isRunningUnderJUnit()) {
            System.exit(0);
        }
    }

    // TODO: ciclo 1 - retorna la ganacia maxima que se podria obtener solucion
    // analitica
    public int profit() {
        return this.lengh * 100;
    }

    // ======= Utilidad: popups solo si el simulador está visible =======
    
    private void showMessage(String message) {
    // Si se detecta que la ejecución ocurre dentro de JUnit (modo prueba),
    // no mostrar el JOptionPane para evitar bloqueos.
    if (isRunningUnderJUnit()) {
        System.out.println("[Mensaje omitido en test]: " + message);
        return;
    }

    if (this.profitBar != null && this.profitBar.isVisible()) {
        JOptionPane.showMessageDialog(null, message);
    }
    }

    /**
     * Detecta si el programa se está ejecutando bajo JUnit (por BlueJ o consola).
     * Retorna true si se detecta una clase de test en la pila de ejecución.
     */
    private boolean isRunningUnderJUnit() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            String className = element.getClassName();
            if (className.startsWith("org.junit.") || className.contains("SilkRoadTest")) {
                return true;
            }
        }
        return false;
    }
}
