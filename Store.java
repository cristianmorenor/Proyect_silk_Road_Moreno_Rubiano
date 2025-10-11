public class Store {
    private int location;
    private String color;
    private boolean isVisible;
    private boolean isEmpty;
    private int initialTenges;
    private int actualTenges;
    private final int initialLocation;
    private Triangle shape;

    // Referencia estática al SpiralPath actual para posicionar visualmente las tiendas
    private static SpiralPath spiralPathRef;

    /**
     * Permite conectar el Store con el SpiralPath activo en SilkRoad.
     */
    public static void setSpiralPath(SpiralPath path) {
        spiralPathRef = path;
    }

    /**
     * Constructor de la tienda (Store)
     * @param location posición de la tienda en el camino
     * @param initialTenges cantidad inicial de tenges
     */
    public Store(int location, int initialTenges) {
        this.location = location;
        this.color = "yellow";
        this.isVisible = false;
        this.isEmpty = (initialTenges <= 0);
        this.initialTenges = initialTenges;
        this.actualTenges = this.initialTenges;
        this.initialLocation = location;

        this.shape = new Triangle();
        this.shape.changeSize(40, 30);
        this.shape.changeColor(this.isEmpty ? "gray" : this.color);

        // 🔹 Nueva lógica visual: colocar la tienda sobre el camino espiral
        int targetX = 140;
        int targetY = 150;

        if (spiralPathRef != null) {
            int[] pos = spiralPathRef.getPositionOnPath(location);
            targetX = pos[0];
            targetY = pos[1];
        }

        // Mover la figura a la posición calculada
        this.shape.moveHorizontal(targetX - 140);
        this.shape.moveVertical(targetY - 15);
    }

    /**
     * Reabastece la tienda a su cantidad inicial de tenges.
     */
    public void resupply() {
        this.actualTenges = this.initialTenges;
        this.isEmpty = (this.actualTenges == 0);
        if (isVisible) {
            shape.changeColor(isEmpty ? "gray" : this.color);
        }
    }

    /**
     * Vacía la tienda y retorna los tenges recolectados.
     */
    public int emptyStore() {
        int temp = this.actualTenges;
        this.actualTenges = 0;
        this.isEmpty = true;
        if (isVisible) {
            shape.changeColor("gray");
        }
        return temp;
    }

    /**
     * Hace visible la tienda.
     */
    public void makeVisible() {
        if (!isVisible) {
            isVisible = true;
            shape.makeVisible();
        }
    }

    /**
     * Oculta la tienda.
     */
    public void makeInvisible() {
        if (isVisible) {
            isVisible = false;
            shape.makeInvisible();
        }
    }

    /**
     * Indica si la tienda está vacía.
     */
    public boolean isEmpty() {
        return this.actualTenges == 0;
    }

    /**
     * Devuelve la ubicación de la tienda.
     */
    public int getLocation() {
        return location;
    }

    /**
     * Devuelve la cantidad de tenges actual.
     */
    public int getTenges() {
        return actualTenges;
    }
}
