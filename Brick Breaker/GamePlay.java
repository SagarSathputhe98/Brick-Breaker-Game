import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false; // Indicates if the game is currently being played
    private int score = 0; // The player's score
    private int totalbricks = 21; // Total number of bricks in the game
    private Timer Timer; // Timer to control the game's animation
    private int delay = 8; // Delay between each frame in the animation
    private int playerX = 310; // X-coordinate of the player's paddle
    private int ballposX = 350; // X-coordinate of the ball
    private int ballposY = 530; // Y-coordinate of the ball
    private int ballXdir = -1; // X-axis direction of the ball (-1 for left, 1 for right)
    private int ballYdir = -2; // Y-axis direction of the ball (-1 for up, 1 for down)
    private MapGenerator map; // Object to generate and manage the game's bricks

    public GamePlay() {
        map = new MapGenerator(3, 7); // Create a new MapGenerator object with 3 rows and 7 columns of bricks
        addKeyListener(this); // Add KeyListener to the JPanel to handle keyboard events
        setFocusable(true); // Set the focusable property of the JPanel to true
        setFocusTraversalKeysEnabled(false); // Disable focus traversal keys
        Timer = new Timer(delay, this); // Create a Timer object that fires an event every 'delay' milliseconds
        Timer.start(); // Start the timer
    }

    public void paint(Graphics g) {
        super.paint(g); // Call the parent class's paint method

        // Background Color black
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // Draw the bricks
        map.draw((Graphics2D) g);

        // Borders Color yellow
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Score Color white, Font Serif with bold size 25
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // Paddle Color green
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // Ball Color red
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);

        // Game over condition
        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.orange);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }

        // Winning condition
        if (totalbricks == 0) {
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.orange);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over: " + score, 190, 300);
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }

        g.dispose(); // Release system resources used by the Graphics object
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) {
            // Handle ball and paddle collision
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            // Check collision with bricks
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;

                        // Handle ball and brick collision
                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalbricks--;
                            score += 5;
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            // Move the ball
            ballposX += ballXdir;
            ballposY += ballYdir;

            // Handle ball collisions with walls
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint(); // Request a repaint of the JPanel
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this implementation
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used in this implementation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle key presses
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Move the paddle to the right
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Move the paddle to the left
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Restart the game when Enter key is pressed
            if (!play) {
                ballposX = 350;
                ballposY = 530;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);
                repaint();
            }
        }
    }

    public void moveRight() {
        // Move the paddle to the right
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        // Move the paddle to the left
        play = true;
        playerX -= 20;
    }
}
