import java.awt.Dimension;
import javax.swing.JFrame;

class GameFrame extends JFrame {
    GameFrame() {
        Game game = new Game();
        this.add(game);
        this.setTitle("Ping Pong");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(game.SCREEN_WIDTH, game.SCREEN_HEIGHT));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);  
    }
}

public class Main {
    @SuppressWarnings("unused")

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
    }
}

