package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

public class World {
    private static final int LENGTH = 30;
    private static final int WIDTH = 30;

    public static class WalkWorld{
        //world size
        private static int length;
        private static int width;
        //world
        public TETile[][] world;

        public WalkWorld() {
            length = World.LENGTH;
            width = World.WIDTH;
            world = new TETile[length][width];
            initialize();
        }

        //initialize a blank world
        private void initialize() {
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < width; y++) {
                    world[x][y] = Tileset.NOTHING;
                }
            }
        }

        public void generateForest() {
           for (int x = 0; x < length; x++) {
                for (int y = 0; y < width; y++) {
                    if (x == 0 || x == length - 1 || y == 0 || y == width - 1) {
                        world[x][y] = Tileset.TREE;
                    }
                }
            }
        }

        public void showText() {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(1, 3, "hello world");
            StdDraw.show();
        }
    }

    public static class FpsWorld{
        //world size
        private static int length;
        private static int width;
        //world
        public int[][] world;

        public FpsWorld() {
            length = World.LENGTH;
            width = World.WIDTH;
            world = new int[length][width];
            initialize();
        }

        //initialize a blank world
        private void initialize() {
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < width; y++) {
                    world[x][y] = 0;
                }
            }
        }

        public void generateRoom() {
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < width; y++) {
                    if (x == 0 || x == length - 1 || y == 0 || y == width - 1) {
                        world[x][y] = 1;
                    }
                }
            }
            for (int y = 0; y < width; y++) {
                for (int x = 0; x < length; x++) {
                    System.out.print(world[x][y] + " ");
                }
                System.out.println();
            }
        }
    }
}
