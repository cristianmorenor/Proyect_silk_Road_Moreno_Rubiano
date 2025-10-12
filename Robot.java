import java.util.Random;

/**
 * Representa un robot que puede moverse a lo largo del camino de SilkRoad
 * y recolectar tenges desde las tiendas.
 * 
 * Cada robot tiene una ubicación, una posición inicial, y un contador de
 * ganancias.
 * También cuenta con una representación visual (círculo) que se dibuja en el
 * canvas.
 * 
 * @author
 * @version 1.0
 */
public class Robot {
    private int position;
    private int profit;
    private int initialPosition;
    private Circle shape;
    private boolean isVisible;

    // Coordenadas actuales del dibujo (para mover visualmente)
    private int currentX;
    private int currentY;

    // Referencia estática al SpiralPath para posicionar el robot en el camino
    private static SpiralPath spiralPathRef;

    /**
     * Conecta la clase Robot con el SpiralPath activo en SilkRoad.
     */
    public static void setSpiralPath(SpiralPath path) {
        spiralPathRef = path;
    }

    /**
     * Crea un nuevo robot en una ubicación específica.
     * 
     * @param position ubicación inicial del robot.
     */
    public Robot(int position) {
        this.position = position;
        this.initialPosition = position;
        this.profit = 0;
        this.isVisible = false;

        this.shape = new Circle();
        this.shape.changeSize(20);
        this.shape.changeColor("black");

        // Nueva lógica: ubicar el robot sobre el camino espiral
        int targetX = 140;
        int targetY = 150;

        if (spiralPathRef != null) {
            int[] pos = spiralPathRef.getPositionOnPath(position);
            targetX = pos[0];
            targetY = pos[1];
        }

        // Mover la figura a la posición visual calculada
        this.shape.moveHorizontal(targetX - 140);
        this.shape.moveVertical(targetY - 15);
        this.currentX = targetX;
        this.currentY = targetY;
    }

    /**
     * Hace visible el robot.
     */
    public void makeVisible() {
        if (!isVisible) {
            isVisible = true;
            shape.makeVisible();
        }
    }

    /**
     * Hace invisible el robot.
     */
    public void makeInvisible() {
        if (isVisible) {
            isVisible = false;
            shape.makeInvisible();
        }
    }

    /**
     * Devuelve la posición actual del robot.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Devuelve las ganancias actuales del robot.
     */
    public int getProfit() {
        return profit;
    }

    /**
     * Mueve el robot a una nueva ubicación.
     * 
     * @param newPosition nueva posición del robot
     */
    public void moveTo(int newPosition) {
        this.position = newPosition;

        // Actualizar posición visual según el camino espiral
        if (spiralPathRef != null) {
            int[] pos = spiralPathRef.getPositionOnPath(newPosition);
            updateVisualPosition(pos[0], pos[1]);
        }
    }

    /**
     * Recolecta una cantidad de tenges y la suma a las ganancias del robot.
     */
    public void pickTenges(int tenges) {
        this.profit += tenges;
    }

    /**
     * Regresa el robot a su posición inicial.
     */
    public void goInitialPosition() {
        this.position = initialPosition;

        if (spiralPathRef != null) {
            int[] pos = spiralPathRef.getPositionOnPath(initialPosition);
            updateVisualPosition(pos[0], pos[1]);
        }
    }

    /**
     * Actualiza visualmente la posición del robot en el canvas.
     * 
     * @param x coordenada X destino
     * @param y coordenada Y destino
     */
    public void updateVisualPosition(int x, int y) {
        int deltaX = x - this.currentX;
        int deltaY = y - this.currentY;
        this.shape.moveHorizontal(deltaX);
        this.shape.moveVertical(deltaY);
        this.currentX = x;
        this.currentY = y;
    }

    public void changeColor(String newColor) {
        this.shape.changeColor(newColor);
    }
}
