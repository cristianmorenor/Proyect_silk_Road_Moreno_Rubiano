public class Store {
    private int location;
    private String color;
    private boolean isVisible;
    private boolean isEmpty;
    private int initialTenges;
    private int actualTenges;
    private final int initialLocation;
    private Triangle shape;
    private int timesEmptied;

    /**
     * Constructor de la tienda (Store)
     * 
     * @param location      posición de la tienda en el camino
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
        this.timesEmptied = 0; // inicializo el contador de las tiendas

        this.shape = new Triangle();
        this.shape.changeSize(15, 15);
        this.shape.changeColor(this.isEmpty ? "gray" : this.color);
    }

    /**
     * Reabastece la tienda a su cantidad inicial de tenges y cambia su color segun
     * el requisito de usabilidad
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
        if (temp > 0) {
            this.timesEmptied++;// incremento si y solo si la tienda tenia tenges
        }
        if (isVisible) {
            shape.changeColor("gray");
        }
        return temp;
    }

    public int getTimesEmptied() {
        return timesEmptied;
    }

    public void changeColor(String newColor) {
        this.color = newColor;
        if (!isEmpty && isVisible) {
            shape.changeColor(this.color);
        }
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
     * Actualiza visualmente la posición de la tienda en el canvas.
     * 
     * @param x coordenada X destino
     * @param y coordenada Y destino
     */
    public void updateVisualPosition(int x, int y) {
        this.shape.setXY(x, y);
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
