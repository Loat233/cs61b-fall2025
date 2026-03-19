package gameengine;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class FpsRender {
    private int width;
    private int height;
    private Player player;
    private int[][] world;
    private Palette palette;
    private int[][] pixelBuffer;

    public FpsRender(int width, int height, Player player, int[][] world) {
        this.width = width;
        this.height = height;
        this.player = player;
        this.world = world;
        this.palette = new Palette();
        this.pixelBuffer = new int[width][height];

        // 初始化StdDraw
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();
    }

    public void renderFrame() {
        // 清空像素缓冲区
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelBuffer[x][y] = 3; // 默认填充黑色
            }
        }

        // 投射光线并渲染
        castRays();

        // 绘制到屏幕
        StdDraw.clear(Color.BLACK);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                drawPixel(x, y);
            }
        }
        StdDraw.show();
    }

    private void castRays() {
        // 为屏幕上的每一列投射一条光线
        for (int x = 0; x < width; x++) {
            // 计算当前列对应的光线角度
            double rayAngle = player.angle + ((double)x / width - 0.5) * player.fov;

            // 计算光线方向向量
            double rayDirX = Math.cos(rayAngle);
            double rayDirY = Math.sin(rayAngle);

            // 执行DDA算法找到墙面
            RayHit hit = performDDA(player.x, player.y, rayDirX, rayDirY);

            if (hit != null) {
                // 计算墙面高度并绘制列
                drawWallColumn(x, hit);
            }
        }
    }

    private RayHit performDDA(double startX, double startY, double rayDirX, double rayDirY) {
        // 玩家当前所在的网格坐标
        int mapX = (int)startX;
        int mapY = (int)startY;

        // 光线方向向量的长度（用于计算到下一个网格边界的距离）
        double deltaDistX = (rayDirX == 0) ? Double.MAX_VALUE : Math.abs(1 / rayDirX);
        double deltaDistY = (rayDirY == 0) ? Double.MAX_VALUE : Math.abs(1 / rayDirY);

        // 步进方向和初始侧距离
        int stepX, stepY;
        double sideDistX, sideDistY;

        // 确定步进方向和初始侧距离
        if (rayDirX < 0) {
            stepX = -1;
            sideDistX = (startX - mapX) * deltaDistX;
        } else {
            stepX = 1;
            sideDistX = (mapX + 1.0 - startX) * deltaDistX;
        }

        if (rayDirY < 0) {
            stepY = -1;
            sideDistY = (startY - mapY) * deltaDistY;
        } else {
            stepY = 1;
            sideDistY = (mapY + 1.0 - startY) * deltaDistY;
        }

        // DDA算法
        boolean hit = false;
        int side = 0; // 0表示X面碰撞，1表示Y面碰撞

        while (!hit) {
            // 跳转到下一个网格边界
            if (sideDistX < sideDistY) {
                sideDistX += deltaDistX;
                mapX += stepX;
                side = 0;
            } else {
                sideDistY += deltaDistY;
                mapY += stepY;
                side = 1;
            }

            // 检查是否超出地图边界
            if (mapX < 0 || mapX >= world.length || mapY < 0 || mapY >= world[0].length) {
                break;
            }

            // 检查是否击中墙面
            if (world[mapX][mapY] > 0) {
                hit = true;
            }
        }

        if (hit) {
            // 计算垂直距离（修正鱼眼效应）
            double perpWallDist;
            if (side == 0) {
                perpWallDist = (mapX - startX + (1 - stepX) / 2) / rayDirX;
            } else {
                perpWallDist = (mapY - startY + (1 - stepY) / 2) / rayDirY;
            }

            return new RayHit(mapX, mapY, side, perpWallDist);
        }

        return null;
    }

    private void drawWallColumn(int x, RayHit hit) {
        // 计算墙面高度
        int lineHeight = (int)(height / hit.distance);

        // 计算墙面在屏幕上的起始和结束位置
        int drawStart = -lineHeight / 2 + height / 2;
        if (drawStart < 0) drawStart = 0;

        int drawEnd = lineHeight / 2 + height / 2;
        if (drawEnd >= height) drawEnd = height - 1;

        // 根据墙面类型选择颜色
        int wallType = world[hit.mapX][hit.mapY];
        int wallColor = getWallColor(wallType, hit.side);

        // 绘制墙面列
        for (int y = drawStart; y <= drawEnd; y++) {
            pixelBuffer[x][y] = wallColor;
        }

        // 绘制天空（墙面之上）
        for (int y = 0; y < drawStart; y++) {
            pixelBuffer[x][y] = 2; // 蓝色代表天空
        }

        // 绘制地面（墙面之下）
        for (int y = drawEnd + 1; y < height; y++) {
            pixelBuffer[x][y] = 3; // 黑色代表地面
        }
    }

    private int getWallColor(int wallType, int side) {
        // 根据墙面类型和碰撞面返回不同颜色
        // 这里可以根据需要扩展为纹理映射
        if (side == 1) {
            // Y面碰撞，使用较暗的颜色
            return wallType == 1 ? 0 : 4; // 红色或黄色
        } else {
            // X面碰撞，使用正常颜色
            return wallType == 1 ? 1 : 5; // 绿色或蓝色
        }
    }

    private void drawPixel(int x, int y) {
        Color color = palette.pickColor(pixelBuffer[x][y]);
        StdDraw.setPenColor(color);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
    }

    // 内部类，用于存储光线碰撞信息
    private static class RayHit {
        int mapX;
        int mapY;
        int side; // 0=X面, 1=Y面
        double distance;

        RayHit(int mapX, int mapY, int side, double distance) {
            this.mapX = mapX;
            this.mapY = mapY;
            this.side = side;
            this.distance = distance;
        }
    }
}

