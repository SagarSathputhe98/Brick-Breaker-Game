import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    public int map[][];
    public int bricksWidth;
    public int bricksHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];

        // Initialize all bricks in the map to 1 (indicating they are active)
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        // Calculate the width and height of each brick based on the row and column count
        bricksWidth = 540 / col;
        bricksHeight = 150 / row;
    }

    public void draw(Graphics2D g) {
        // Loop through the map and draw each brick
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    // Set brick color to cyan and fill the rectangle
                    g.setColor(Color.cyan);
                    g.fillRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);

                    // Set stroke width to 3 and color to black, then draw the rectangle border
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);
                }
            }
        }
    }

    public void setBricksValue(int value, int row, int col) {
        // Set the value of a brick in the map (0 for inactive, 1 for active)
        map[row][col] = value;
    }
}
