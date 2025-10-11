/**
 * La clase ProfitBar representa una barra de progreso visual que muestra
 * el progreso actual de las ganancias o beneficios en relación con un máximo
 * establecido.
 * 
 * La barra está compuesta por dos rectángulos superpuestos:
 * - Un rectángulo de fondo (magenta) que representa el máximo posible (100%)
 * - Un rectángulo de progreso (green) que representa el valor actual
 * 
 * La barra se posiciona automáticamente en la parte inferior del canvas
 * 
 * @author MorenoRubiano
 * @version 2.2
 */
public class ProfitBar {
    private int maxProfit;
    private int currentProfit;
    private boolean visible;

    private Rectangle backgroundBar; // Barra de fondo (total)
    private Rectangle progressBar; // Barra de progreso (actual)

    // Configuración de la barra
    private static final int BAR_WIDTH = 210; // 70% del ancho del canvas (300 * 0.7)
    private static final int BAR_HEIGHT = 15;
    private static final int MARGIN_BOTTOM = 2; // Margen desde el fondo
    private static final int MARGIN_SIDE = 45; // Centrado horizontalmente

    /**
     * Constructor de ProfitBar
     * 
     * @param x         posición X (se ignorará, se calculará para centrar)
     * @param y         posición Y (se ignorará, se posicionará en la parte
     *                  inferior)
     * @param maxProfit valor máximo de profit que puede alcanzarse
     */
    public ProfitBar(int x, int y, int maxProfit) {
        this.maxProfit = Math.max(1, maxProfit); // Evitar división por cero
        this.currentProfit = 0;
        this.visible = false;

        // Calcular posición en la parte inferior del canvas
        Canvas canvas = Canvas.getCanvas();
        int xPosition = MARGIN_SIDE; // Centrado
        int yPosition = canvas.CANVAS_HEIGHT - MARGIN_BOTTOM - BAR_HEIGHT;

        // Crear rectángulo de fondo (magenta) - representa el 100%
        this.backgroundBar = new Rectangle(
                BAR_HEIGHT, // height
                BAR_WIDTH, // width completo
                xPosition, // xPosition
                yPosition, // yPosition en la parte inferior
                "magenta" // color de fondo
        );

        // Crear rectángulo de progreso (verde) - representa el progreso actual
        this.progressBar = new Rectangle(
                BAR_HEIGHT, // height
                0, // width inicial = 0 (0% progreso)
                xPosition, // xPosition (misma que el fondo)
                yPosition, // yPosition (misma que el fondo)
                "green" // color de progreso
        );
    }

    /**
     * Hace visible la barra de progreso
     */
    public void makeVisible() {
        if (!visible) {
            visible = true;
            // Mostrar primero el fondo, luego el progreso
            backgroundBar.makeVisible();
            updateProgressBar();
        }
    }

    /**
     * Hace invisible la barra de progreso
     */
    public void makeInvisible() {
        if (visible) {
            visible = false;
            backgroundBar.makeInvisible();
            progressBar.makeInvisible();
        }
    }

    /**
     * Actualiza el progreso de la barra basado en el valor actual
     * 
     * @param currentProgress valor actual de progreso (debe ser <= maxProfit)
     */
    public void updateProgress(int currentProgress) {
        this.currentProfit = Math.max(0, Math.min(currentProgress, maxProfit));

        if (visible) {
            updateProgressBar();
        }
    }

    /**
     * Actualiza los valores de la barra de progreso (método de compatibilidad)
     * 
     * @param current     valor actual de profit
     * @param maxPossible nuevo valor máximo posible
     */
    public void updateProfitBar(int current, int maxPossible) {
        // Actualizar el máximo si es necesario
        this.maxProfit = Math.max(1, maxPossible);

        // Recrear las barras con el nuevo máximo si es diferente
        updateProgress(current);
    }

    /**
     * Obtiene el valor actual de profit
     * 
     * @return valor actual de profit
     */
    public int getCurrent() {
        return currentProfit;
    }

    /**
     * Resetea la barra de progreso a 0
     */
    public void reset() {
        this.currentProfit = 0;
        if (visible) {
            updateProgressBar();
        }
    }

    /**
     * Obtiene el progreso como porcentaje (0.0 a 1.0)
     * 
     * @return porcentaje de progreso
     */
    public double getProgressPercentage() {
        return (double) currentProfit / maxProfit;
    }

    /**
     * Actualiza visualmente la barra de progreso
     */
    private void updateProgressBar() {
        if (!visible)
            return;

        // Calcular el ancho proporcional basado en el porcentaje
        double percentage = getProgressPercentage();
        percentage = Math.min(1.0, Math.max(0.0, percentage)); // Clamping entre 0 y 1

        int progressWidth = (int) (BAR_WIDTH * percentage);

        // Si el progreso es 0, ocultar la barra de progreso
        if (progressWidth == 0) {
            progressBar.makeInvisible();
        } else {
            // Ocultar la barra anterior
            progressBar.makeInvisible();

            // Actualizar tamaño y mostrar nueva barra de progreso
            progressBar.changeSize(BAR_HEIGHT, progressWidth);
            progressBar.makeVisible();
        }
    }

    /**
     * Verifica si la barra está visible
     * 
     * @return true si está visible, false en caso contrario
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Obtiene el valor máximo de profit
     * 
     * @return valor máximo de profit
     */
    public int getMaxProfit() {
        return maxProfit;
    }

    /**
     * Obtiene información de estado para depuración
     * 
     * @return string con el estado actual
     */
    public String getStatus() {
        return String.format("ProfitBar: %d/%d (%.1f%%)",
                currentProfit, maxProfit, getProgressPercentage() * 100);
    }
}