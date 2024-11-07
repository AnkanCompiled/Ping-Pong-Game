import java.awt.Insets;
import javax.swing.JFrame;

class GameFrame extends JFrame {
    GameFrame() {
        Game game = new Game();
        this.add(game);
        this.setTitle("Ping Pong");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Insets insets = this.getInsets();
        this.setSize(game.getPreferredSize().width + insets.left + insets.right,
        game.getPreferredSize().height + insets.top + insets.bottom);
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

