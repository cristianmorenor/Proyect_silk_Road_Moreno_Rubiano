/**
 * Test sencillo para demostrar el error del threshold
 */
public class TestThreshold {
    public static void main(String[] args) {
        System.out.println("Probando threshold de 10 píxeles...");

        try {
            SpiralPath spiral = new SpiralPath(50, 50, 2);
            spiral.calcSpiral(10000); // Length muy grande que causará el error
            System.out.println("No debería llegar aquí");
        } catch (RuntimeException e) {
            System.out.println("¡ERROR CAPTURADO!: " + e.getMessage());
        }
    }
}