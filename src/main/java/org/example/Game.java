package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;

public class Game {
    private boolean isRunning;
    private Display display;
    private Player player;
    private Witch witch;
    private GameMap gameMap;

    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;
    private boolean upKeyPressed = false;
    private boolean downKeyPressed = false;

    private static final int FRAME_RATE = 60; // 60 frames per second
    private static final double TIME_PER_FRAME = 1.0 / FRAME_RATE;
    private long lastTime;
    private double deltaTime;

    private boolean movable = true; // Initialize as movable

    private double trackPlayerTimer = 0;
    private final double TRACK_PLAYER_INTERVAL = 50; // track seconds
    private double witchDeltaTime;

    public Game(String title, int width, int height) {
        display = new Display(title, width, height);
//        player = new Player(19 * 32, 19 * 32); // Spawn at array index (19, 19) // Subtract 30 for the top bar
        player = new Player(42, 80); // Spawn at array index (19, 19) // Subtract 30 for the top bar
        witch = new Witch(445, 490);
        gameMap = new GameMap("src/main/resources/map.png");
        isRunning = false;

        lastTime = System.nanoTime();
        deltaTime = 0;

        // Add a key listener to the canvas for handling input
        display.getCanvas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    leftKeyPressed = true;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    rightKeyPressed = true;
                } else if (keyCode == KeyEvent.VK_UP) {
                    upKeyPressed = true;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    downKeyPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    leftKeyPressed = false;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    rightKeyPressed = false;
                } else if (keyCode == KeyEvent.VK_UP) {
                    upKeyPressed = false;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    downKeyPressed = false;
                }
            }
        });

        display.getCanvas().setFocusable(true);
        display.getCanvas().requestFocus();
    }

    public void start() {
        isRunning = true;
        run();
    }

    public void stop() {
        isRunning = false;
    }

    public void handleInput() {
        // Input handling is done through the key listener
    }

    private void run() {

        while (isRunning) {
            handleInput();

            long currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / 1e9;
            witchDeltaTime += (currentTime - lastTime) / 1e9;
//            System.out.println(deltaTime);
            lastTime = currentTime;

            // Check if movable based on deltaTime
            if (deltaTime >= TIME_PER_FRAME) {
                movable = true;
                deltaTime -= TIME_PER_FRAME;
            } else {
                movable = false;
            }
//            System.out.println(deltaTime);

//            // Check if it's time to track the player
//            trackPlayerTimer += deltaTime;
////            System.out.println(trackPlayerTimer);
//            if (trackPlayerTimer >= TRACK_PLAYER_INTERVAL) {
//                witch.trackPlayer(gameMap, player);
//                trackPlayerTimer = 0; // Reset the timer
//            }

            // Check if it's time to track the player
            trackPlayerTimer += deltaTime;
//            System.out.println(trackPlayerTimer);
            if (trackPlayerTimer >= TRACK_PLAYER_INTERVAL) {
                witch.trackPlayer(gameMap, player);
                trackPlayerTimer = 0; // Reset the timer
            }

            // Get the player's current position
            int playerX = player.getX();
            int playerY = player.getY();

            // Determine the player's next position based on input and movement direction
            int nextPlayerX = playerX;
            int nextPlayerY = playerY;

            if (leftKeyPressed && movable) {
                nextPlayerX = playerX - player.getDx();
            } else if (rightKeyPressed && movable) {
                nextPlayerX = playerX + player.getDx();
            } else if (upKeyPressed && movable) {
                nextPlayerY = playerY - player.getDy(); // Subtract 30 for the top bar
            } else if (downKeyPressed && movable) {
                nextPlayerY = playerY + player.getDy(); // Subtract 30 for the top bar
            }

            // Check if the next position is within bounds and not a wall
            int tileSize = 32;
            int nextTileType = gameMap.getTileAt(nextPlayerX / tileSize, (nextPlayerY) / tileSize);
//            System.out.println("get Tile at X: "+ nextPlayerX / tileSize);
//            System.out.println("get Tile at Y: "+(nextPlayerY) / tileSize);
            if (nextTileType != 0) {
                // Update the player's position only if the next tile is not a wall
//                System.out.println(currentTime);
                player.setX(nextPlayerX);
                player.setY(nextPlayerY);
                player.eatCookies(gameMap);
            }

            if (movable) {
                witch.update(gameMap.getMapTiles(), player);
//                witch.update();
            }
//            player.update(); // You can implement player update logic if needed
//            witch.update(); // You can implement witch update logic if needed
//            display.clear();
//            gameMap.draw(display.getGraphics());
//            player.draw(display.getGraphics());
//            witch.draw(display.getGraphics());
//            display.update();


//            player.update();
//            witch.update();

            Graphics g = display.getGraphics();

            if (g != null) {
                display.clear();
                gameMap.draw(g);
//                if (leftKeyPressed && movable) {
//                    player.moveLeft();
////                    System.out.println(player.getX()+ " " + player.getY());
//                }
//
//                if (rightKeyPressed && movable) {
//                    player.moveRight();
////                    System.out.println(player.getX()+ " " + player.getY());
//                }
//
//                if (upKeyPressed && movable) {
//                    player.moveUp();
////                    System.out.println(player.getX()+ " " + player.getY());
//                }
//
//                if (downKeyPressed && movable) {
//                    player.moveDown();
////                    System.out.println(player.getX()+ " " + player.getY());
//                }

                // Check for collision between player and witch
                if (player.collidesWith(witch)) {
                    if (player.getLife() > 0 && !player.isInvincible()) {
                        player.decreaseLife(); // Deduct 1 heart
                    }
                }
                player.update(); // Update player's invincibility status

//                gameMap.clearCookies();

                // Check if the player is at (550, 550) and the cookie count is zero
                if (!gameMap.goalReached() && player.getX() >= 550 && player.getX() <= 610 && player.getY() >= 550 && player.getY() <= 610 && gameMap.getCookies().isEmpty()) {
                    gameMap.setGoalReached(true);
                }
//                g.setColor(Color.BLACK);
//                g.fillRect(550, 550, 590 - 530, 590 - 530);

//                witch.trackPlayer(gameMap, player);
                gameMap.drawCookies(g);
                gameMap.drawHome(g);
                gameMap.drawStatusBar(g, player.getLife());
                player.draw(g);
                witch.draw(g);

                // Display the "GOAL" banner if the goal has been reached
                if (gameMap.goalReached()) {
                    gameMap.drawGoalBanner(g);
                }

                // Check if the player's life is 0
                if (player.getLife() <= 0 && !gameMap.goalReached()) {
                    drawGameLostBanner(g);
                }

                display.update();
                g.dispose();
            }

        }
        // Clean up and exit
        System.out.println("Exit");
        System.exit(0);
    }

    private void drawGameLostBanner(Graphics g) {
        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, screenWidth, screenHeight); // Fill the background with black

        // Draw the "Game Lost" banner image at the center of the screen
        if (gameMap.getGameoverImage() != null) {
            int x = (640 - gameMap.getGameoverImage().getWidth()) / 2;
            int y = (690 - gameMap.getGameoverImage().getHeight()) / 2;
            g.drawImage(gameMap.getGameoverImage(), x, y, null);
        }
    }


    public static void main(String[] args) {
        Game game = new Game("Henzel & Gretel Pacman", 640, 640 + 50);
        game.start();
    }
}
