import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * La clase SpiralPath representa un camino en espiral verdadero dibujado en un
 * canvas.
 * La espiral se construye como una línea continua que se enrolla hacia el
 * centro,
 * usando rectángulos pequeños que forman un perímetro total igual al length
 * especificado.
 * 
 * @author MorenoRubiano
 * @version 3.0
 */
public class SpiralPath {
    /** Umbral mínimo para height y width de los segmentos */
    private static final int MIN_DIMENSION_THRESHOLD = 10;
    /** Número total de píxeles del perímetro de la espiral */
    private int totalLength;
    /** Coordenada X del punto de origen */
    private int originX;
    /** Coordenada Y del punto de origen */
    private int originY;
    /** Grosor de los segmentos del camino (más delgado) */
    private int thickness;
    /** Estado de visibilidad de la espiral */
    private boolean visible;
    /** Lista de rectángulos que forman los segmentos del camino */
    private final List<Rectangle> segments;

    /**
     * Constructor de la clase SpiralPath.
     * 
     * @param originX   Coordenada X del punto de origen
     * @param originY   Coordenada Y del punto de origen
     * @param thickness Grosor en píxeles del camino (se hará más delgado)
     */
    public SpiralPath(int originX, int originY, int thickness) {
        this.originX = originX;
        this.originY = originY;
        this.thickness = Math.max(2, thickness / 2); // Hacer más delgado
        this.visible = false;
        this.segments = new ArrayList<>();
        this.totalLength = 0;
    }

    public void makeVisible() {
        if (visible)
            return;
        visible = true;
        draw();
    }

    public void makeInvisible() {
        if (!visible)
            return;
        erase();
        visible = false;
    }

    private void draw() {
        if (visible) {
            for (Rectangle segment : segments) {
                segment.makeVisible();
            }
        }
    }

    private void erase() {
        for (Rectangle segment : segments) {
            segment.makeInvisible();
        }
    }

    public void redraw() {
        if (visible) {
            erase();
            draw();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * Calcula y genera una espiral verdadera con el perímetro especificado.
     * La espiral se forma siguiendo un patrón continuo que se enrolla hacia el
     * centro.
     * 
     * @param length Perímetro total de la espiral en píxeles
     */
    public void calcSpiral(int length) {
        if (length <= 0)
            return;

        clearSegments();
        this.totalLength = length;

        // Parámetros de la espiral
        int currentX = originX;
        int currentY = originY;
        int remainingLength = length;

        // Direcciones: 0=derecha, 1=abajo, 2=izquierda, 3=arriba
        int direction = 0;

        // Calcular el tamaño inicial de forma inteligente
        // Debe caber dentro del canvas considerando el origen
        int availableWidth = Canvas.CANVAS_WIDTH - originX - 20; // Margen de 20px
        int availableHeight = Canvas.CANVAS_HEIGHT - originY - 40;

        // Longitud inicial del primer segmento - proporcional al espacio disponible
        int maxInitialSegment = Math.min(availableWidth, availableHeight);
        int segmentLength = Math.min(maxInitialSegment, Math.max(60, length / 5));
        int minSegmentLength = Math.max(thickness * 3, MIN_DIMENSION_THRESHOLD);

        // Factor de reducción GRADUAL para una espiral más grande
        double reductionFactor = 0.88;

        while (remainingLength > minSegmentLength && segmentLength >= minSegmentLength) {

            // Calcular la longitud real del segmento actual
            int actualLength = Math.min(segmentLength, remainingLength);

            // Validar dimensiones mínimas antes de crear el segmento
            int segmentHeight = 0, segmentWidth = 0;
            switch (direction) {
                case 0: // Derecha (horizontal)
                case 2: // Izquierda (horizontal)
                    segmentHeight = thickness;
                    segmentWidth = actualLength;
                    break;
                case 1: // Abajo (vertical)
                case 3: // Arriba (vertical)
                    segmentHeight = actualLength;
                    segmentWidth = thickness;
                    break;
            }

            // Verificar threshold simple - lanzar error si es menor a 10
            if (segmentHeight < MIN_DIMENSION_THRESHOLD || segmentWidth < MIN_DIMENSION_THRESHOLD) {
                JOptionPane.showMessageDialog(null,
                        "El camino en espiral no puede continuar.\n" +
                                "Dimensiones del siguiente segmento: " + segmentWidth + "x" + segmentHeight + " px (< "
                                + MIN_DIMENSION_THRESHOLD + ").\n" +
                                "Sugerencia: use un length menor o aumente el grosor inicial.",
                        "SpiralPath - Límite alcanzado",
                        JOptionPane.WARNING_MESSAGE);
                break; // Detener generación sin lanzar excepción
            }

            Rectangle segment = null;
            String color = (direction % 2 == 0) ? "blue" : "red"; // Horizontal=azul, Vertical=rojo

            switch (direction) {
                case 0: // Derecha (horizontal)
                    segment = new Rectangle(
                            thickness, // height
                            actualLength, // width
                            currentX, // x
                            currentY, // y
                            color);
                    currentX += actualLength;
                    break;

                case 1: // Abajo (vertical)
                    segment = new Rectangle(
                            actualLength, // height
                            thickness, // width
                            currentX, // x
                            currentY, // y
                            color);
                    currentY += actualLength;
                    break;

                case 2: // Izquierda (horizontal)
                    segment = new Rectangle(
                            thickness, // height
                            actualLength, // width
                            currentX - actualLength, // x
                            currentY, // y
                            color);
                    currentX -= actualLength;
                    break;

                case 3: // Arriba (vertical)
                    segment = new Rectangle(
                            actualLength, // height
                            thickness, // width
                            currentX, // x
                            currentY - actualLength, // y
                            color);
                    currentY -= actualLength;
                    break;
            }

            if (segment != null) {
                segments.add(segment);
                remainingLength -= actualLength;
            }

            // Cambiar dirección (girar en sentido horario)
            direction = (direction + 1) % 4;

            // Reducir gradualmente la longitud del segmento para crear el efecto espiral
            // Solo reducir cada dos cambios de dirección para mantener la simetría
            if (direction % 2 == 0) {
                segmentLength = (int) (segmentLength * reductionFactor);
            }

            // Verificar límites del canvas
            if (currentX < originX - 10 || currentX > Canvas.CANVAS_WIDTH - 10 ||
                    currentY < originY - 10 || currentY > Canvas.CANVAS_HEIGHT - 10) {
                break;
            }
        }

        if (visible) {
            redraw();
        }
    }

    private void clearSegments() {
        if (visible) {
            erase();
        }
        segments.clear();
        totalLength = 0;
    }

    public int getSegmentCount() {
        return segments.size();
    }

    public int getTotalLength() {
        return totalLength;
    }

    public String getStatus() {
        return String.format("SpiralPath: %d segments, %d px total, thickness: %d, visible: %s",
                segments.size(), totalLength, thickness, visible);
    }

}