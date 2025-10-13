import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * La clase SpiralPath representa un camino en espiral de Arquímedes dibujado
 * con puntos (círculos pequeños) en el canvas.
 * La espiral se calcula matemáticamente y permite obtener coordenadas (x,y)
 * dada una distancia sobre el camino.
 * 
 * @author MorenoRubiano
 * @version 4.0 - Basado en espiral de Arquímedes punto a punto
 */
public class SpiralPath {

    /** Clase interna para almacenar un punto (x, y) de la espiral */
    private static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /** Lista de puntos que forman la espiral */
    private final List<Point> points;

    /** Lista de círculos para visualizar la espiral */
    private final List<Circle> visualDots;

    /** Longitud total acumulada de la espiral */
    private double totalLength;

    /** Longitud solicitada por el usuario (para mapeo) */
    private int requestedLength;

    /** Distancias acumuladas por punto para mapeo preciso por longitud */
    private final List<Double> cumulativeDistances;

    /** Estado de visibilidad */
    private boolean visible;

    /** Ancho del canvas */
    private int canvasWidth;

    /** Alto del canvas */
    private int canvasHeight;

    /**
     * Constructor de la clase SpiralPath.
     * 
     * @param originX   Coordenada X del punto de origen (no usado, se usa centro
     *                  del canvas)
     * @param originY   Coordenada Y del punto de origen (no usado, se usa centro
     *                  del canvas)
     * @param thickness Grosor en píxeles del camino (no usado en esta versión)
     */
    public SpiralPath(int originX, int originY) {
        this.visible = false;
        this.points = new ArrayList<>();
        this.visualDots = new ArrayList<>();
        this.totalLength = 0;
        this.requestedLength = 0;
        this.cumulativeDistances = new ArrayList<>();
        this.canvasWidth = Canvas.CANVAS_WIDTH;
        this.canvasHeight = Canvas.CANVAS_HEIGHT;
    }

    /**
     * Hace visible la espiral en el canvas.
     */
    public void makeVisible() {
        if (visible)
            return;
        visible = true;
        draw();
    }

    /**
     * Hace invisible la espiral en el canvas.
     */
    public void makeInvisible() {
        if (!visible)
            return;
        erase();
        visible = false;
    }

    /**
     * Dibuja todos los puntos de la espiral.
     */
    private void draw() {
        if (visible) {
            for (Circle dot : visualDots) {
                dot.makeVisible();
            }
        }
    }

    /**
     * Borra todos los puntos de la espiral.
     */
    private void erase() {
        for (Circle dot : visualDots) {
            dot.makeInvisible();
        }
    }

    /**
     * Redibuja la espiral.
     */
    public void redraw() {
        if (visible) {
            erase();
            draw();
        }
    }

    /**
     * Verifica si la espiral es visible.
     */
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
        this.requestedLength = length;

        // Centro del canvas
        double cx = canvasWidth / 2.0;
        double cy = canvasHeight / 2.0;

        // === Parámetros de la espiral de Arquímedes ===
        // Margen muy pequeño para ocupar casi todo el canvas
        double margen = 10; // Margen fijo de 10px
        double radioMax = Math.min(canvasWidth, canvasHeight) / 2.0 - margen;
        int vueltasMax = 8; // Reducido para espiral menos densa (más espacio entre vueltas)

        // Radio inicial muy pequeño (empieza casi en el centro)
        double a = 5; // radio inicial pequeño
        double b = (radioMax - a) / (2 * Math.PI * vueltasMax);

        // Usar exactamente la longitud solicitada para que el mapeo sea 1:1
        int effectiveLength = length;

        // === Construcción de la espiral ===
        double theta = 0;
        this.totalLength = 0;

        double prevX = cx + a * Math.cos(theta);
        double prevY = cy + a * Math.sin(theta);
        points.add(new Point(prevX, prevY));
        cumulativeDistances.add(0.0);

        double maxTheta = 2 * Math.PI * vueltasMax;
        double deltaTheta = 0.02; // más pequeño = más puntos (más suave)

        while (theta < maxTheta && this.totalLength < effectiveLength) {
            theta += deltaTheta;
            double r = a + b * theta;
            double x = cx + r * Math.cos(theta);
            double y = cy + r * Math.sin(theta);

            double dx = x - prevX;
            double dy = y - prevY;
            double seg = Math.sqrt(dx * dx + dy * dy);
            this.totalLength += seg;

            if (this.totalLength <= effectiveLength) {
                points.add(new Point(x, y));
                cumulativeDistances.add(this.totalLength);
                prevX = x;
                prevY = y;
            } else {
                break;
            }
        }

        // === Crear círculos visuales (puntos) con distribución uniforme ===
        // Objetivo: ~200-250 puntos distribuidos uniformemente por DISTANCIA
        // (no por índice) para evitar exceso en el centro

        int targetDots = 300;
        double dotSpacing = this.totalLength / targetDots; // Espacio entre puntos

        double accumulatedDist = 0;
        double nextDotAt = 0;

        prevX = cx + a * Math.cos(0);
        prevY = cy + a * Math.sin(0);

        for (Point p : points) {
            // Calcular distancia desde el punto anterior
            double dx = p.x - prevX;
            double dy = p.y - prevY;
            accumulatedDist += Math.sqrt(dx * dx + dy * dy);

            // ¿Es momento de colocar un punto visual?
            if (accumulatedDist >= nextDotAt) {
                Circle dot = new Circle();
                dot.changeSize(3);
                dot.changeColor("black");

                // Posicionar el círculo (Circle tiene posición base en 20,15)
                dot.moveHorizontal((int) p.x - 20);
                dot.moveVertical((int) p.y - 15);

                visualDots.add(dot);
                nextDotAt += dotSpacing;
            }

            prevX = p.x;
            prevY = p.y;
        }

        // No redibujar aquí; la visibilidad la controla makeVisible()
    }

    private void clearSegments() {
        if (visible) {
            erase();
        }
        visualDots.clear();
        points.clear();
        cumulativeDistances.clear();
        totalLength = 0;
        requestedLength = 0;
    }

    public int getSegmentCount() {
        return points.size();
    }

    /**
     * Retorna el número de puntos visuales (círculos) dibujados.
     */
    public int getVisualDotsCount() {
        return visualDots.size();
    }

    public int getTotalLength() {
        return (int) totalLength;
    }

    public String getStatus() {
        return String.format("SpiralPath: %d points, %.1f px total, visible: %s",
                points.size(), totalLength, visible);
    }

    /**
     * Retorna las coordenadas (x, y) aproximadas sobre el camino espiral
     * para una posición dada (location).
     * Este método permite posicionar visualmente robots y tiendas
     * sobre la ruta dibujada por la espiral.
     * 
     * Equivalente a getSpiralPoint del código JavaScript.
     * 
     * @param location Distancia sobre el camino (en píxeles)
     * @return Array de 2 enteros [x, y] con las coordenadas
     */
    public int[] getPositionOnPath(int location) {
        if (points.isEmpty()) {
            // Si no hay puntos, retornar centro del canvas
            return new int[] { canvasWidth / 2, canvasHeight / 2 };
        }

        // Asegurar que location esté dentro del rango válido
        if (location < 0)
            location = 0;
        if (location > totalLength)
            location = (int) totalLength;

        // Usar mapeo por longitud acumulada para mayor precisión
        int mapLen = (requestedLength > 0) ? requestedLength : (int) Math.round(totalLength);
        if (location > mapLen)
            location = mapLen;

        // Buscar el primer punto cuya distancia acumulada sea >= location
        int idx = Collections.binarySearch(cumulativeDistances, (double) location);
        if (idx < 0)
            idx = -idx - 1; // inserción

        if (idx <= 0) {
            Point p0 = points.get(0);
            return new int[] { (int) p0.x, (int) p0.y };
        }
        if (idx >= points.size()) {
            Point plast = points.get(points.size() - 1);
            return new int[] { (int) plast.x, (int) plast.y };
        }

        // Interpolar linealmente entre los puntos idx-1 y idx
        double d0 = cumulativeDistances.get(idx - 1);
        double d1 = cumulativeDistances.get(idx);
        Point p0 = points.get(idx - 1);
        Point p1 = points.get(idx);
        double t = (d1 - d0) > 0 ? (location - d0) / (d1 - d0) : 0.0;
        double x = p0.x + t * (p1.x - p0.x);
        double y = p0.y + t * (p1.y - p0.y);
        return new int[] { (int) x, (int) y };
    }

}