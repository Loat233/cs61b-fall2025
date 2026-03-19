package gameengine;

public class Barrier {
    public final int id;
    public final int surfaceColor;
    public final int height;
    public final String description;


    public Barrier(int id,int surfaceColor, int height, String description) {
        this.id = id;
        this.surfaceColor = surfaceColor;
        this.height = height;
        this.description = description;
    }

}
