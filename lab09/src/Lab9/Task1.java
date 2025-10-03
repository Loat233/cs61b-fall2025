package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world initially full of trees.
 */
public class Task1 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static void fillWithTrees(TETile[][] world) {
        for (int w = 0; w < world.length; w++) {
            for (int h = 0; h < world[0].length; h++) {
                world[w][h] = Tileset.TREE;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int terWidth = 30;
        int terHeight = 20;
        ter.initialize(terWidth, terHeight);

        int tetWidth = 30;
        int tetHeight = 15;
        TETile[][] world = new TETile[tetWidth][tetHeight];

        fillWithTrees(world);
        ter.renderFrame(world);
    }
}