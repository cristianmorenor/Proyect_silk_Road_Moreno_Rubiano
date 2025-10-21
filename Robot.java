
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

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private int position;
    private int profit;
    private int initialPosition;
    private Circle shape;
    private boolean isVisible;
    private List<Integer> profitHistory;// Historial de ganancias por cada movimiento que el robot hace

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

        this.profitHistory = new ArrayList<>();
        this.profitHistory.add(0);// ganancia inicial

        this.shape = new Circle();
        this.shape.changeSize(10);
        this.shape.changeColor("black");
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
        // La posición visual se actualizará desde SilkRoad usando SpiralPath
    }

    /**
     * Recolecta una cantidad de tenges y la suma a las ganancias del robot.
     */
    public void pickTenges(int tenges) {
        this.profit += tenges;
        this.profitHistory.add(this.profit);// registro l aganancia actual
    }

    public List<Integer> getProfitHistory() {
        return new ArrayList<>(profitHistory);// retorno una copia para encapsulacion
    }

    /**
     * Retorna la ganancia obtenida en un movimiento específico.
     * 
     * @param moveIndex índice del movimiento (0 = inicial)
     * @return ganancia en ese movimiento, o -1 si el índice es inválido
     */
    public int getProfitAtMove(int moveIndex) {
        if (moveIndex >= 0 && moveIndex < profitHistory.size()) {
            return profitHistory.get(moveIndex);
        }
        return -1; // Indicar índice inválido
    }

    /**
     * Regresa el robot a su posición inicial.
     */
    public void goInitialPosition() {
        this.position = initialPosition;
        // La posición visual se actualizará desde SilkRoad usando SpiralPath
    }

    /**
     * Actualiza visualmente la posición del robot en el canvas.
     * 
     * @param x coordenada X destino
     * @param y coordenada Y destino
     */
    public void updateVisualPosition(int x, int y) {
        this.shape.setXY(x, y);
    }

    public void changeColor(String newColor) {
        this.shape.changeColor(newColor);
    }
    
    /**
     * Requisito usabilidad del ciclo 2
     * Hace que el robot parpadee varias veces para destacarlo visualmente 
     */
    public void blink() {
        try {
            for (int i = 0; i < 5; i++) {
                this.makeInvisible();
                Thread.sleep(250); // 0.25 segundos invisible (intervalos de parpadeo)
                this.makeVisible();
                Thread.sleep(250); // 0.25 segundos visible
            }
        } catch (InterruptedException e) {

        }
    }
}
