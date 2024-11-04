import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public final class Game extends JPanel implements ActionListener{
    public int SCREEN_WIDTH = 800;
    public int SCREEN_HEIGHT = 600;
    static int UNIT_SIZE = 10;
    static int DELAY = 40;
    int pad1_y[] = new int[SCREEN_HEIGHT/ UNIT_SIZE];
    int pad1_x[] = new int[SCREEN_WIDTH/ UNIT_SIZE];
    boolean running = false;
    Timer timer;

    public Game(){
        this.setBackground(Color.black);
        this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
        this.requestFocusInWindow();
        startGame();
    }

    public void startGame(){
        int startPos = -30;
        for(int i = 0; i<6 ;i++) {
			pad1_x[i] = 30;
			pad1_y[i] = SCREEN_HEIGHT/2 + startPos;
            startPos += 10;
		}
        running = true;
        timer = new Timer(DELAY,this);
		timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		padDraw(g);
	}
    
    public void padDraw(Graphics g){
        for (int i = 0; i<6; i++){
            g.setColor(Color.white);
			g.fillRect(pad1_x[i], pad1_y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    public void move_p1(char d){
        switch (d){
            case 'D' -> {
                if (pad1_y[5]<=540){
                    for(int i = 0; i<6 ;i++) {
                        pad1_y[i] = pad1_y[i] + 10;
                    }
                }
            }
            case 'U' -> {
                if (pad1_y[0]>=10){
                    for(int i = 0; i<6 ;i++) {
                        pad1_y[i] = pad1_y[i] - 10;
                    }
                }
            }
        }
    }

    public void move_p2(){
        //movement of player 1
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER -> {
                    if(running == false){
                        startGame();
                        System.out.println("Start Game");
                    }
                }
                case KeyEvent.VK_W -> {
                    move_p1('U');
                }
                case KeyEvent.VK_S -> {
                    move_p1('D');
                }
            }
        }
    }
}
