import javax.swing.JFrame;

public class Game {
    public static void main(String[] args) {
        // Create a JFrame object
        JFrame obj = new JFrame();

        // Create a GamePlay object
        GamePlay gameplay = new GamePlay();

        // Set the size and position of the JFrame
        obj.setBounds(10, 10, 700, 600);

        // Set the JFrame to not be resizable
        obj.setResizable(false);

        // Set the JFrame to be visible
        obj.setVisible(true);

        // Set the default close operation of the JFrame
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the title of the JFrame
        obj.setTitle("BrickBreaker");

        // Add the GamePlay object to the JFrame
        obj.add(gameplay);
    }
}
