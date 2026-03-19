package gameengine;



public class BarrierSet {
    public static final Barrier ground = new Barrier(0, 3, 1, "ground");
    public static final Barrier wall = new Barrier(1, 1, 10, "wall");
    public static final Barrier nothing = new Barrier(-1, 3, 1, "nothing");

    public static Barrier getBarrier(int id) {
        return switch (id) {
            case 0 -> ground;
            case 1 -> wall;
            default -> nothing;
        };
    }
}
