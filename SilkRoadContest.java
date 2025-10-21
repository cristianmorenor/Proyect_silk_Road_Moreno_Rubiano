/**
 * Interfaz para soluciones de la Ruta de la Seda.
 *
 * Define la API mínima para resolver y simular instancias.
 *
 * Autor: MorenoRubiano
 * Versión: 1.0
 */
public interface SilkRoadContest {

    int[] solve(int[][] days);

    /**
     * Simula la solución visualmente.
     */
    void simulate(int[][] days, boolean slow);

    /**
     * Valida que los datos de entrada sean correctos.
     * 
     * Todas las implementaciones pueden usar esta validación.
     * Si una implementación necesita validación diferente, puede sobrescribirlo.
     */
    default boolean validateInput(int[][] days) {
        if (days == null || days.length == 0) {
            System.err.println("Error: datos de entrada vacíos");
            return false;
        }

        for (int i = 0; i < days.length; i++) {
            if (days[i] == null || days[i].length < 1) {
                System.err.println("Error: día " + i + " tiene formato inválido");
                return false;
            }

            int numStores = days[i][0];
            if (numStores < 0) {
                System.err.println("Error: número de tiendas negativo en día " + i);
                return false;
            }

            if (days[i].length < numStores + 1) {
                System.err.println("Error: faltan datos de tiendas en día " + i);
                return false;
            }
        }

        return true;
    }

    /**
     * Calcula estadísticas básicas de la solución.
     * 
     * Método helper
     */
    default void printStatistics(int[] results) {
        if (results == null || results.length == 0) {
            System.out.println("No hay resultados para mostrar");
            return;
        }

        int total = 0;
        int max = results[0];
        int min = results[0];

        for (int profit : results) {
            total += profit;
            max = Math.max(max, profit);
            min = Math.min(min, profit);
        }

        double average = (double) total / results.length;

        System.out.println("========== ESTADÍSTICAS ==========");
        System.out.println("Días simulados: " + results.length);
        System.out.println("Ganancia total: " + total);
        System.out.println("Ganancia promedio: " + String.format("%.2f", average));
        System.out.println("Mejor día: " + max);
        System.out.println("Peor día: " + min);
        System.out.println("==================================");
    }

    /**
     * Calcula la longitud del camino basado en el número de tiendas.
     * 
     */
    default int calculatePathLength(int[][] days) {
        int maxStores = 0;

        for (int[] day : days) {
            if (day != null && day.length > 0) {
                maxStores = Math.max(maxStores, day[0]);
            }
        }

        return maxStores * 15; // 15 píxeles por tienda
    }

    /**
     * Pausa la ejecución (simulador)
     */
    default void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Simulación interrumpida");
        }
    }

    /**
     * Método que valida, resuelve y muestra estadísticas.
     */
    default int[] solveWithValidation(int[][] days) {
        // Validar entrada
        if (!validateInput(days)) {
            return new int[0];
        }

        // Resolver
        int[] results = solve(days);

        // Mostrar estadísticas
        printStatistics(results);

        return results;
    }
}