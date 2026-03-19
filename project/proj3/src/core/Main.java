package core;

import edu.princeton.cs.algs4.StdDraw;
import gameengine.GameMode;
import gameengine.FpsRender;
import gameengine.Player;
import tileengine.TERenderer;

public class Main {
    public static void main(String[] args) {
        //window size
        int width = 60;
        int height = 30;
        //generate gameData
        Player player = new Player();
        player.initialize();
        GameMode gm = new GameMode();

        //create walkWorld
        World.WalkWorld wwd = new World.WalkWorld();
        wwd.generateForest();
        //create fpsWorld
        World.FpsWorld fwd = new World.FpsWorld();
        fwd.generateRoom();

        //generate walkWindowRender
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        //generate fpsWindowRender
        FpsRender fer = new FpsRender(width, height, player, fwd.world);

        while (true) {
            //check if game need to change mode
            if (StdDraw.hasNextKeyTyped()) {
                swichMode(gm, player);
            }

            //render frame according to mode
            switch (gm.getMode()) {
                //pause
                case -1:
                    break;
                //mainMenu
                case 0:
                    break;
                //worldMode
                case 1:
                    ter.renderFrame(wwd.world);
                    break;
                //fpsMode
                case 2:
                    fer.renderFrame();
                    break;
            }
        }
    }

    private static void swichMode(GameMode gm, Player player) {
        char input = Character.toLowerCase(StdDraw.nextKeyTyped());
        switch (input) {
            case 'z' -> gm.changeMode(-1);
            case 'x' -> gm.changeMode(0);
            case 'c' -> gm.changeMode(1);
            case 'v' -> gm.changeMode(2);
        }

        switch (input) {
            case 'w':
                player.x += 0.1 * Math.cos(player.angle);
                player.y += 0.1 * Math.sin(player.angle);
                break;
            case 's':
                player.x -= 0.1 * Math.cos(player.angle);
                player.y -= 0.1 * Math.sin(player.angle);
                break;
            case 'a':
                player.angle -= 0.1; // 左转
                break;
            case 'd':
                player.angle += 0.1; // 右转
                break;
        }
    }
}
