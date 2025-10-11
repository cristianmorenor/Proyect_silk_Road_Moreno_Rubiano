/**
 * La clase Robot representa al jugador robot en el juego Silk Road.
 * 
 * El robot puede moverse por diferentes posiciones del mapa, recolectar tenges
 * (moneda del juego) de las tiendas, y cambiar su estado de visibilidad.
 * El robot mantiene una posición inicial fija a la cual puede regresar,
 * así como una posición actual que cambia durante el juego.
 * 
 * El robot se representa visualmente con color rojo y puede ser visible
 * o invisible según las mecánicas del juego.
 * 
 * @author MorenoRubiano
 * @version 2.0
 */
public class Robot {

    /** Cantidad actual de tenges que ha recolectado el robot */
    private int actualTenges;

    /** Color del robot (por defecto "black") */
    private String color;

    /** Estado de visibilidad del robot en el canvas */
    private boolean isVisible;

    /** Ubicación inicial del robot (inmutable) */
    private final int initialLocation;

    /** Posición actual del robot en el mapa */
    private int position;

    private Circle shape;

    /**
     * Constructor de la clase Robot.
     * 
     * Crea un nuevo robot en una ubicación inicial específica.
     * El robot se inicializa con color rojo, sin tenges, y en estado invisible.
     * 
     * @param location La ubicación inicial donde se posicionará el robot
     */
    public Robot(int location) {
        this.initialLocation = location;
        this.position = location;
        this.color = "black";
        this.actualTenges = 0;
        this.isVisible = false;
        this.shape = new Circle();
        this.shape.changeSize(20);
        this.shape.changeColor(color);
        this.shape.makeVisible();
        this.shape.moveHorizontal(150);
        this.shape.moveVertical(150);
    }

    /**
     * Hace invisible al robot en el canvas del juego.
     * 
     * Cambia el estado de visibilidad del robot a falso,
     * ocultándolo de la interfaz gráfica.
     */
    public void makeInvisible() {
        if (isVisible) {
            isVisible = false;
            shape.makeInvisible();
        }
    }

    /**
     * Obtiene la cantidad de tenges (ganancias) que ha recolectado el robot.
     * 
     * @return La cantidad actual de tenges que posee el robot
     */
    public int getProfit() {
        return actualTenges;
    }

    /**
     * Mueve el robot a su posición inicial.
     * 
     * Restablece la posición actual del robot a la ubicación inicial
     * que fue establecida durante la construcción del objeto.
     */
    public void goInitialPosition() {
        this.position = this.initialLocation;
        if (isVisible) {
            shape.makeInvisible();
            shape.moveHorizontal(initialLocation * 30);
            shape.makeVisible();
        }
    }

    /**
     * Obtiene la posición actual del robot en el mapa.
     * 
     * @return La posición actual donde se encuentra el robot
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Mueve el robot a una nueva posición específica.
     * 
     * @param newPosition La nueva posición donde se moverá el robot
     */
    public void moveTo(int newPosition) {
        this.position = newPosition;
        if (isVisible) {
            shape.makeInvisible();
            shape.moveHorizontal(newPosition * 30);
            shape.makeVisible();
        }
    }

    /**
     * Hace visible al robot en el canvas del juego.
     * 
     * Cambia el estado de visibilidad del robot a verdadero,
     * permitiendo que sea mostrado en la interfaz gráfica.
     */
    public void makeVisible() {
        if (!isVisible) {
            isVisible = true;
            shape.makeVisible();
        }
    }

    /**
     * Verifica si el robot está en una ubicación específica.
     * 
     * Compara la ubicación actual del robot con la ubicación proporcionada
     * para determinar si coinciden.
     * 
     * @param location La ubicación a verificar
     * @return true si el robot está en la ubicación especificada, false en caso
     *         contrario
     */
    public boolean isAtLocation(int location) {
        return this.position == location;
    }

    /**
     * Permite al robot recolectar una cantidad específica de tenges.
     * 
     * Establece la cantidad actual de tenges del robot al valor proporcionado.
     * Este método simula la acción de recoger tenges de una tienda o ubicación.
     * 
     * @param actualTenges La cantidad de tenges que el robot recolectará
     */
    public void pickTenges(int actualTenges) {
        this.actualTenges = actualTenges;
    }

    /**
     * Verifica si el robot está visible en el canvas.
     * 
     * @return true si el robot es visible, false si está oculto
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Establece el estado de visibilidad del robot.
     * 
     * Permite cambiar manualmente el estado de visibilidad del robot
     * sin usar los métodos makeVisible() o makeInvisible().
     * 
     * @param visible true para hacer visible al robot, false para ocultarlo
     */
    public void setVisible(boolean visible) {
        this.isVisible = visible;
        if (visible)
            shape.makeVisible();
        else
            shape.makeInvisible();
    }

    /**
     * Obtiene la ubicación inicial del robot.
     * 
     * Devuelve la ubicación inicial inmutable que fue establecida
     * durante la construcción del robot.
     * 
     * @return La ubicación inicial del robot
     */
    public int getInitialLocation() {
        return initialLocation;
    }
}
