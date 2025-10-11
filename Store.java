public class Store {
    private static final int TRI_START_X = 140;
    private static final int TRI_START_Y = 15;

    private int location;
    private String color;
    private boolean isVisible;
    private boolean isEmpty;
    private int initialTenges;
    private int actualTenges;
    private final int initialLocation;

    private Triangle shape;

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

        int targetX = location * 30;
        int targetY = 200;
        this.shape.moveHorizontal(targetX - TRI_START_X);
        this.shape.moveVertical(targetY - TRI_START_Y);
    }

    public void resupply() {
        this.actualTenges = this.initialTenges;
        this.isEmpty = (this.actualTenges == 0);
        if (isVisible) {
            shape.changeColor(isEmpty ? "gray" : "yellow");
        }
    }

    public int emptyStore() {
        int temp = this.actualTenges;
        this.actualTenges = 0;
        this.isEmpty = true;
        if (isVisible) {
            shape.changeColor("gray");
        }
        return temp;
    }

    public void makeVisible() {
        if (!isVisible) {
            isVisible = true;
            shape.makeVisible();
        }
    }

    public int set(int location, int initialTenges) {
        this.location = location;
        this.initialTenges = Math.max(0, initialTenges);
        this.actualTenges = this.initialTenges;
        this.isEmpty = (this.actualTenges == 0);

        // Re-crear siempre el shape desde cero
        this.shape = new Triangle();
        this.shape.changeSize(40, 30);
        this.shape.changeColor(this.isEmpty ? "gray" : "yellow");

        int targetX = location * 30;
        int targetY = 200;
        this.shape.moveHorizontal(targetX - TRI_START_X);
        this.shape.moveVertical(targetY - TRI_START_Y);

        if (isVisible) {
            shape.makeVisible();
        }

        return this.actualTenges;
    }

    public void makeInvisible() {
        if (isVisible) {
            isVisible = false;
            shape.makeInvisible();
        }
    }

    public boolean isEmpty() {
        return this.actualTenges == 0;
    }

    public int getLocation() {
        return location;
    }

    public int getTenges() {
        return actualTenges;
    }
}
