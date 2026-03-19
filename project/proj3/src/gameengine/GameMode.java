package gameengine;

public class GameMode {
    //menuMode
    private static final int pause = -1;
    private static final int mainMenu = 0;
    //gameMode
    private static final int worldMode = 1;
    private static final int fpsMode = 2;

    private int mode;

    public GameMode() {
        mode = 0;
    }

    public void changeMode(int i) {
        mode = i;
    }

    public int getMode() {
        return mode;
    }
}
