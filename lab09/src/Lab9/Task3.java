package Lab9;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.Random;

/**
 * Draws a world initially full of trees.
 */
public class Task3 {
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

    private static void drawSquare(TETile[][] world, int startX, int startY, int size, TETile tile) {
        int drawWidth = Math.min(startX + 1, size);
        int drawHeight = Math.min(startY + 1, size);

        int endX = startX - drawWidth + 1;
        int endY = startY - drawHeight + 1;
        for (int x = startX; x >= endX; x--) {
            for (int y = startY; y >= endY; y--) {
                world[x][y] = tile;
            }
        }
    }

    private static void addRandomSquare(TETile[][] world, Random rand, TETile set) {
        int size = RandomUtils.uniform(rand, 3, 8);
        int randX = rand.nextInt(world.length);
        int randY = rand.nextInt(world[0].length);
        drawSquare(world, randX, randY, size, set);
    }

    private static TETile pickRandomSet(Random rand) {
        int randomNum = rand.nextInt(3);
        return switch (randomNum) {
            case 0 -> Tileset.FLOWER;
            case 1 -> Tileset.WALL;
            case 2 -> Tileset.WATER;
            default -> Tileset.NOTHING;
        };
    }

    private static void reloadWorld(WorldState ws) {
        fillWithTrees(ws.world);
        ws.resetRandom();
        for (int i = 0; i < ws.squareNum; i++) {
            addRandomSquare(ws.world, ws.rand, pickRandomSet(ws.rand));
        }
    }

    private static boolean checkKBInput(WorldState ws) {
        char input;

        while (StdDraw.hasNextKeyTyped()) {
            input = Character.toLowerCase(StdDraw.nextKeyTyped());

            switch (input) {
                case 'd':
                    if (ws.squareNum > 0) {
                        ws.squareNum--;
                        reloadWorld(ws);
                    }
                    break;
                case 'n':
                    addRandomSquare(ws.world, ws.rand, pickRandomSet(ws.rand));
                    ws.addTimes++;
                    ws.squareNum++;
                    break;
                case 's':
                    ws.saveState();
                    break;
                case 'l':
                    if (ws.haveSaved()) {
                        ws.loadState();
                        reloadWorld(ws);
                    }
                    break;
                case 'q':
                    return false;
                default:
                    break;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        //初始化TERendeer
        TERenderer ter = new TERenderer();
        ter.initialize(30, 20);
        TETile[][] world = new TETile[30][15];
        fillWithTrees(world);

        //主循环
        boolean isContinue = true;
        WorldState ws = new WorldState(0, 3456, world);
        String text;

        while (isContinue) {
            ter.renderFrame(world);
            //显示计数器
            text = String.format("Number of square: %d", ws.squareNum);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(1, 17, text);

            isContinue = checkKBInput(ws);

            StdDraw.show();
            StdDraw.pause(2);
        }
    }
}