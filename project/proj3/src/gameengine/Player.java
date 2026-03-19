package gameengine;

public class Player {
    public double x;
    public double y;
    public double z;

    public double angle;
    public double fov;

    public void initialize(double posX, double posY, double posZ, double facingAngle, double fov) {
        this.x = posX;
        this.y = posY;
        this.z = posZ;
        this.angle = facingAngle;
        this.fov = fov * (Math.PI / 180);
    }

    public int gridX() {
        return (int) x;
    }

    public int gridY() {
        return (int) y;
    }

    public void initialize() {
        initialize(15, 15, 0, 0, 60);
    }
}
