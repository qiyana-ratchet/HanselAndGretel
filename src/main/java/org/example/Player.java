package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Player {
    private int x, y;  // Player's position on the map
    private int dx, dy; // Movement speed
    private BufferedImage playerImage; // Player's image
    private int cookieCount;
    private int life = 3; // Player's life count
    private boolean invincible = false; // Player's invincibility status
    private long invincibilityEndTime; // Time when invincibility ends


    public int getLife() {
        return life;
    }

    public Player(int initialX, int initialY) {
        x = initialX;
        y = initialY;
        dx = 3;
        dy = 3;

        // Load the player's image from "hansel.png"
        try {
//            playerImage = ImageIO.read(new File("src/main/resources/hansel.png"));
            playerImage = ImageIO.read(new File("src/main/resources/hansel2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        // Draw the player character using the loaded image
        if (playerImage != null) {
            g.drawImage(playerImage, x-13, y-35, null);
        }
    }

    public void eatCookies(GameMap gameMap) {
        List<Position> cookies = gameMap.getCookies();
        Iterator<Position> iterator = cookies.iterator();

        while (iterator.hasNext()) {
            Position cookie = iterator.next();
            if (x/32 == cookie.x && y/32 == cookie.y) {
                // Player has eaten a cookie
                iterator.remove(); // Remove the eaten cookie from the list
                cookieCount++; // Increment the player's cookie count
            }
        }
    }

    public boolean collidesWith(Witch witch) {
        int dx = Math.abs(x - witch.getX());
        int dy = Math.abs(y - witch.getY());

        return dx < 30 && dy < 30;
    }


    public void decreaseLife() {
        if (!invincible) {
            life--;
            setInvincibility(2000); // Set invincibility for 5 seconds (5000 milliseconds)
        }
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincibility(long durationMillis) {
        invincible = true;
        invincibilityEndTime = System.currentTimeMillis() + durationMillis;
    }

    public void update() {
        if (invincible && System.currentTimeMillis() >= invincibilityEndTime) {
            invincible = false;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }


    // Implement other methods for player behavior
}
