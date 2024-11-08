import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

public final class Game extends JPanel implements ActionListener{
    public int SCREEN_WIDTH = 800;
    public int SCREEN_HEIGHT = 600;
    static int UNIT_SIZE = 10;
    int ball_x = SCREEN_WIDTH/2;
    int ball_y = SCREEN_HEIGHT/2;
    static int ball_diameter = 20;
    int ballSpeed_x = 10;
    int ballSpeed_y = 10;
    public int ballSpeedTotal = 225;
    int pad1_y[] = new int[SCREEN_HEIGHT/ UNIT_SIZE];
    int pad1_x[] = new int[SCREEN_WIDTH/ UNIT_SIZE];
    int pad2_y[] = new int[SCREEN_HEIGHT/ UNIT_SIZE];
    int pad2_x[] = new int[SCREEN_WIDTH/ UNIT_SIZE];
    boolean running = false;
    boolean game_over = false;
    static int DELAY = 40;
    private final Set<Integer> keysPressed = new HashSet<>();
    Timer timer;

    public Game(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
        this.requestFocusInWindow();
        setPosition();
    }

    private void setPosition(){
        int startPos = -30;
        ball_x = SCREEN_WIDTH/2;
        ball_y = SCREEN_HEIGHT/2;
        for(int i = 0; i<6 ;i++) {
			pad1_x[i] = SCREEN_WIDTH/2 -350;
			pad1_y[i] = SCREEN_HEIGHT/2 + startPos;
            pad2_x[i] = SCREEN_WIDTH/2 +350;
            pad2_y[i] = pad1_y[i];
            startPos += 10;
		}
    }

    // starting the game with initializing pad width and height
    private void startGame(){
        ballSpeed_y = (int)(Math.random() * 21) - 10;
        ballSpeed_x = Math.random() < 0.5 ? - (int)(Math.sqrt(ballSpeedTotal -(Math.pow(ballSpeed_y, 2)))) : (int)(Math.sqrt(ballSpeedTotal -(Math.pow(ballSpeed_y, 2))));
        running = true;
        game_over = false;
        timer = new Timer(DELAY,this);
		timer.start();
        
    }

    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		padDraw(g);
        if (!running){
            gameStartPrompt(g);
        } 
        if (game_over){
            gameOverPrompt(g);
        }
	}
    
    // make paint component draw the pads
    private void padDraw(Graphics g){
        for (int i = 0; i<6; i++){
            g.setColor(Color.cyan);
            g.fillRect(pad1_x[i], pad1_y[i], UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.pink);
            g.fillRect(pad2_x[i], pad2_y[i], UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.white);
            g.fillOval(ball_x, ball_y, ball_diameter, ball_diameter);
        }
    }

    private void gameStartPrompt(Graphics g){
        g.setColor(Color.green);
        g.setFont(new Font("Helvetica",Font.BOLD, 32));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("PRESS ENTER TO START", (SCREEN_WIDTH - metrics.stringWidth("PRESS ENTER TO START"))/2, 30);
    }

    private void gameOverPrompt(Graphics g){
        String winner;
        if (ball_x >= SCREEN_WIDTH){
            winner = "BLUE";
            g.setColor(Color.cyan);
        } else {
            winner = "RED";
            g.setColor(Color.pink);
        }
        g.setFont(new Font("Helvetica",Font.BOLD, 32));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("WINNER "+winner, (SCREEN_WIDTH - metrics.stringWidth("WINNER BLUE"))/2, SCREEN_HEIGHT - 50);
    }

    // pad movement 
    private void move_p1(char d){
        switch (d){
            case 'D' -> {
                if (pad1_y[5]<590){
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

    private void move_p2(char d){
        switch (d){
            case 'D' -> {
                if (pad2_y[5]<590){
                    for(int i = 0; i<6 ;i++) {
                        pad2_y[i] = pad2_y[i] + 10;
                    }
                }
            }
            case 'U' -> {
                if (pad2_y[0]>=10){
                    for(int i = 0; i<6 ;i++) {
                        pad2_y[i] = pad2_y[i] - 10;
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            if(keysPressed.contains(KeyEvent.VK_W)){
                move_p1('U');
            }
            if(keysPressed.contains(KeyEvent.VK_S)){
                move_p1('D');
            }
            if(keysPressed.contains(KeyEvent.VK_UP)){
                move_p2('U');
            }
            if(keysPressed.contains(KeyEvent.VK_DOWN)){
                move_p2('D');
            }

            ball_x += ballSpeed_x;
            ball_y += ballSpeed_y;
            
            
            if (ball_y <= 0 ) {
                ballSpeed_y = Math.abs(ballSpeed_y);
            }
            if (ball_y >= SCREEN_HEIGHT - ball_diameter){
                ballSpeed_y = -(ballSpeed_y);
            }

            if ( ball_y >= pad1_y[0]-ball_diameter && ball_y <= pad1_y[5]+UNIT_SIZE && ball_x <= pad1_x[0]+UNIT_SIZE && ball_x >= pad1_x[0])  {
                ballSpeed_y = (int)((ball_y - (pad1_y[0] + 30)) / 3);
                ballSpeed_x = (int)(Math.sqrt(ballSpeedTotal -(Math.pow(ballSpeed_y, 2))));
                System.out.println("pad1_y[0] + 30 "+(pad1_y[0] + 30));  
            }

            if ( ball_y >= pad2_y[0]-ball_diameter && ball_y <= pad2_y[5]+UNIT_SIZE && ball_x >= pad2_x[0]-ball_diameter && ball_x <= pad2_x[0]) {
                ballSpeed_y = (int)((ball_y - (pad2_y[0] + 30)) / 3);
                ballSpeed_x = -(int)(Math.sqrt(ballSpeedTotal -(Math.pow(ballSpeed_y, 2))));
            }

            if(ball_x <= -ball_diameter || ball_x >= SCREEN_WIDTH){
                running = false;
                game_over = true;
                timer.stop();
            }
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER -> {
                    if(!running){
                        setPosition();
                        startGame();
                    }
                }
                default -> keysPressed.add(e.getKeyCode());
            }
            
        }
        // Remove the released key from the set
        @Override
        public void keyReleased(KeyEvent e) {
            keysPressed.remove(e.getKeyCode());  
        }
    }
}
