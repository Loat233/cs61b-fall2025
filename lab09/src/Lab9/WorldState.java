package Lab9;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import tileengine.TETile;

import java.io.File;
import java.util.Random;

public class WorldState {
    private final String fileName = "world_save.txt";

    int squareNum;
    int seed;
    int addTimes;

    Random rand;
    TETile[][] world;


    public WorldState(int n, int seed, TETile[][] world) {
        this.squareNum = n;
        this.seed = seed;
        this.addTimes = 0;
        this.rand = new Random(seed);
        this.world = world;
    }

    public void saveState() {
        Out saveFile = new Out(fileName);
        saveFile.println("squareNum, seed, addTimes");
        String data = String.format("%d,%d,%d", squareNum, seed, addTimes);
        saveFile.println(data);
        saveFile.close();
    }

    public boolean haveSaved() {
        File file = new File(fileName);
        return file.exists() || file.length() != 0;
    }

    public void loadState() {
        if (haveSaved()) {
            In in = new In(fileName);
            in.readLine();
            String[] data = in.readLine().split(",");

            //修改当前的世界状态
            squareNum = Integer.parseInt(data[0]);
            seed = Integer.parseInt(data[1]);
            addTimes = Integer.parseInt(data[2]);
        }
    }

    public void resetRandom() {
        rand = new Random(seed);
    }

}
