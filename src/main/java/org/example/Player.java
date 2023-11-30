package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Player {
    private int x, y;  // Player's position on the map
    private int dx, dy; // Movement speed
    private BufferedImage playerImage; // Player's image

    public Player(int initialX, int initialY) {
        x = initialX;
        y = initialY;
        dx = 3;
        dy = 3;

        // Load the player's image from "hansel.png"
        try {
            playerImage = ImageIO.read(new File("src/main/resources/hansel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveLeft() {
        x -= dx;
//        System.out.println("moveLeft " + x);
    }

    public void moveRight() {
        x += dx;
//        System.out.println("moveRight " + x);
    }

    public void moveUp() {
        y -= dy;
//        System.out.println("moveUp " + y);
    }

    public void moveDown() {
//        System.out.println("moveDown " + y);
        y += dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void draw(Graphics g) {
        // Draw the player character using the loaded image
        if (playerImage != null) {
            g.drawImage(playerImage, x, y, null);
        }
    }

    // Implement other methods for player behavior
}
