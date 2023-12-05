package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private Image mapImage;

    public int[][] getMapTiles() {
        return mapTiles;
    }

    private int[][] mapTiles = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0},
            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0},
            {0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0},
            {0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0},
            {0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private List<Position> cookies = new ArrayList<>(); // List to store cookie positions
    private BufferedImage cookieImage; // Cookie image
    private BufferedImage homeImage; // Home image
    private BufferedImage breadhouse; // SugarHome image
    private BufferedImage heartImage; // HeartImage
    private BufferedImage intro;
    private BufferedImage lifeWord;
    private BufferedImage picket;
    private boolean goalReached = false; // Flag to track if the goal has been reached
    private BufferedImage goalBannerImage; // Store the goal banner image
    private BufferedImage gameoverImage; // Store the over banner image


    public GameMap(String mapImagePath) {
        // Load the map image from the provided path
        mapImage = Toolkit.getDefaultToolkit().getImage(mapImagePath);
        initializeCookies();

        try {
            cookieImage = ImageIO.read(new File("src/main/resources/cookie.png")); // Replace with your cookie image file path
//            homeImage = ImageIO.read(new File("src/main/resources/home.png"));
            homeImage = ImageIO.read(new File("src/main/resources/home2.png"));
            breadhouse = ImageIO.read(new File("src/main/resources/breadhouse.png"));
            heartImage = ImageIO.read(new File("src/main/resources/life.png"));
            intro = ImageIO.read(new File("src/main/resources/intro.png"));
            lifeWord = ImageIO.read(new File("src/main/resources/lifetext.png"));
//            picket = ImageIO.read(new File("src/main/resources/picket.png"));
            goalBannerImage = ImageIO.read(new File("src/main/resources/goal.png"));
            gameoverImage = ImageIO.read(new File("src/main/resources/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        // Draw the map image
//        g.drawImage(mapImage, 0, 30, null);
        g.drawImage(mapImage, 0, 0, null);
    }

    public void drawCookies(Graphics g) {
        for (Position cookiePosition : cookies) {
            int x = cookiePosition.x * 32; // Adjust for tile size
            int y = cookiePosition.y * 32; // Adjust for tile size

            if (cookieImage != null) {
                g.drawImage(cookieImage, x + 5, y + 7, null);
            }
        }
    }

    public void drawHome(Graphics g) {
        int x = 550; // Adjust the X position as needed
        int y = 550; // Adjust the Y position as needed
        int sx = 21;
        int sy = 15;
//        int px = 560;
//        int py = 560;
        if (homeImage != null) {
            g.drawImage(homeImage, x, y, null);
            g.drawImage(breadhouse, sx, sy, null);
//            g.drawImage(picket, px, py, null);
        }
    }

    public void drawStatusBar(Graphics g, int life) {
        int statusBarHeight = 50; // Height of the status bar
//        int screenWidth = map[0].length * tileSize; // Adjust for your screen width
        int screenWidth = 640; // Adjust for your screen width

        // Draw the status bar background
        g.setColor(Color.BLACK); // Set the background color
        g.fillRect(0, 690 - statusBarHeight, screenWidth, statusBarHeight);

        // Draw heart images indicating player's life
        int heartWidth = 30; // Adjust the width of each heart image
        int heartSpacing = 15; // Adjust the spacing between heart images

        for (int i = 0; i < life; i++) {
            int x = screenWidth - (i + 1) * (heartWidth + heartSpacing); // Adjust the X position
            int y = 640 + 50 - statusBarHeight + (statusBarHeight - heartWidth) / 2; // Adjust the Y position

            if (heartImage != null) {
                g.drawImage(heartImage, x, y, null);
            }
        }

        int x = 65;
        int y = 652;
        int sx = 460;
        int sy = 654;
        g.drawImage(intro, x, y, null);
        g.drawImage(lifeWord, sx, sy, null);

//        // Draw the text "Life: "
//        g.setColor(Color.WHITE); // Set the text color
//        Font customFont = new Font("Nanum Gothic", Font.PLAIN, 24);
//        g.setFont(customFont);
//        g.drawString("Life: ", 450, 640 + 50 - statusBarHeight + (statusBarHeight - heartWidth) / 2 + heartWidth- 10);
//
//        // Draw the text "Collect all the cookies and go to the sugar house !!!"
//        customFont = new Font("Arial", Font.PLAIN, 16);
//        g.setFont(customFont);
//        String statusText = "Collect all the cookies and move to the Sugar House ! ! !";
//        g.drawString(statusText, 10, 640 + 50 - statusBarHeight / 2 + 5);

    }

    public int getTileAt(int x, int y) {
        // Retrieve the tile type at the given (x, y) position
        if (x >= 0 && x < mapTiles[0].length && y >= 0 && y < mapTiles.length) {
            return mapTiles[y][x];
        }
        return -1; // Invalid position
    }

    private void initializeCookies() {
        for (int y = 0; y < mapTiles.length; y++) {
            for (int x = 0; x < mapTiles[y].length; x++) {
                if (mapTiles[y][x] == 1) {
                    cookies.add(new Position(x, y));
                }
            }
        }
    }

    public List<Position> getCookies() {
        return cookies;
    }

    public boolean goalReached() {
        return goalReached;
    }

    public void setGoalReached(boolean reached) {
        goalReached = reached;
    }

    public void clearCookies() {
        this.cookies.clear();
    }

    public void drawGoalBanner(Graphics g) {
        if (goalReached) {
            int bannerWidth = goalBannerImage.getWidth();
            int bannerHeight = goalBannerImage.getHeight();
            int x = (640 - bannerWidth) / 2;
            int y = (690 - bannerHeight) / 2;

            g.drawImage(goalBannerImage, x, y, null);
        }
    }

    public BufferedImage getGameoverImage() {
        return gameoverImage;
    }
}

