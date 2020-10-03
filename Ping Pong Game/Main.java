import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class Main extends JFrame {
    
    //Double buffering
    Image dbImage;
    Graphics dbg, g;
    
    //Ball Objects
    static Ball b = new Ball(242, 250);
    
    Thread ball = new Thread(b);
    Thread p1 = new Thread(b.p1);
    Thread p2 = new Thread(b.p2);
    

    //difficulty level(toggle)
    boolean hardDifficulty = false;
    
    //game menu
    boolean gameStarted = false;
    boolean gamePause = false;
    
    //Hover
    boolean startHover;
    boolean difficultyHover;
    boolean resumeHover;
    boolean restartHover;
    boolean quitHover;

    //Conditions 
    boolean start;
    boolean winner;
    boolean esc;

    
    //Start Menu Buttons
    Rectangle startButton = new Rectangle(200, 225, 100, 25);
    Rectangle difficultyButton = new Rectangle(175, 275, 150, 25 );

    //Pause Menu Buttons
    Rectangle resumeButton = new Rectangle(50, 200, 75, 25);
    Rectangle restartButton = new Rectangle(50, 250, 75, 25 );
    Rectangle quitButton = new Rectangle(50, 300, 75, 25 );
    
    //Screensize
    int GWIDTH = 500, GHEIGHT = 500;
    
    //Dimensions of width*height
    Dimension screenSize = new Dimension(GWIDTH, GHEIGHT);
    
    public Main(){
        setSize(screenSize);
        setTitle("Ping Pong");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(new AL());
        addMouseListener(new ML());
        addMouseMotionListener(new ML());
    }
    
    public void startGame(){
        gameStarted = true;
        start = true;
        hardDifficulty = false;
    }

    public static void main(String[] args) {
         Main m = new Main();
       
    }
    
    @Override
    public void paint(Graphics g){
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        draw(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
    public void draw(Graphics g){
        
        //Menu
        if(!gameStarted)
        {
            Image img = Toolkit.getDefaultToolkit().getImage
                (this.getClass().getResource("pong-bg 3.png"));
                             
            //Background
            g.drawImage(img, -40, 25, this);
            g.setColor(Color.YELLOW);
            g.fillRect(2,25,495,3);
            g.fillRect(495,26,2,490);
            g.fillRect(2,495,495,2);
            g.fillRect(3,27,2,493);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.WHITE);
            g.drawString("Ping Pong!", 170, 200);
            if(!startHover)
                g.setColor(Color.CYAN);
            else
                g.setColor(Color.WHITE);
            g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);
            g.setFont(new Font("Arial", Font.BOLD, 17));
            g.setColor(Color.BLACK);
            g.drawString("Start Game", startButton.x+5, startButton.y+18);
            if(!difficultyHover)
                g.setColor(Color.CYAN);
            else
                g.setColor(Color.WHITE);
            g.fillRect(difficultyButton.x, difficultyButton.y, difficultyButton.width, difficultyButton.height);
            g.setFont(new Font("Arial", Font.BOLD, 17));
            g.setColor(Color.BLACK);
            g.drawString("Difficulty: ", difficultyButton.x+10, difficultyButton.y+18);
            if(!hardDifficulty){
                g.setColor(Color.BLUE);
                g.drawString("Easy", difficultyButton.x+95, difficultyButton.y+18);
            }
            else{
                g.setColor(Color.RED);
                g.drawString("Hard", difficultyButton.x+95, difficultyButton.y+18);
            }
        }
        else if(gamePause)
        {
            drawPause(g);
            start = false;
        }
        else if(b.p1Score == 5)
        {
            Image img = Toolkit.getDefaultToolkit().getImage
                (this.getClass().getResource("Celebration 1.png"));
            g.drawImage(img, -50, 0, this);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.WHITE);
            g.drawString("Player 1 Wins!!!", 150, 230);
            ball.suspend();
            p1.suspend();
            p2.suspend(); 
            winner = true;
            esc = false;
        }
        else if(b.p2Score == 5)
        {
            Image img = Toolkit.getDefaultToolkit().getImage
                (this.getClass().getResource("Celebration 1.png"));
            g.drawImage(img, -50, 0, this);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.WHITE);
            g.drawString("Player 2 Wins!!!", 150, 230);
            ball.suspend();
            p1.suspend();
            p2.suspend();
            winner = true;
            esc = false;
        }
        else
        {
            Image img = Toolkit.getDefaultToolkit().getImage
                (this.getClass().getResource("pong-bg 1.png"));
                             
            //Background
            g.drawImage(img, 0, 0, this);
            g.setColor(Color.YELLOW);
            g.fillRect(3,53,493,1);
            g.fillRect(495,53,2,490);
            g.fillRect(3,495,493,2);
            g.fillRect(3,53,2,490);
                                             
            b.draw(g);
            b.p1.draw(g);
            b.p2.draw(g);

            //Scores
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Player 1 ", 10, 45);
            g.drawString(""+ b.p1Score, 200, 50);
            g.drawString("Player 2 ", 410, 45);
            g.drawString(""+ b.p2Score, 292, 50); 

            esc = true;
        }
        repaint();
    }
    
    public void drawPause(Graphics g){
                Image img = Toolkit.getDefaultToolkit().getImage
                (this.getClass().getResource("pong-bg 2.png"));
                             
            //Background
            g.drawImage(img, 0, 0, this);
            g.setColor(Color.YELLOW);
            g.fillRect(2,25,495,2);
            g.fillRect(495,26,2,490);
            g.fillRect(2,495,495,2);
            g.fillRect(3,27,2,493);
            
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.WHITE);
            g.drawString("Options!!", 25, 150);
            if(!resumeHover)
                g.setColor(Color.CYAN);
            else
                g.setColor(Color.WHITE);
            g.fillRect(resumeButton.x, resumeButton.y, resumeButton.width, resumeButton.height);
            g.setFont(new Font("Arial", Font.BOLD, 17));
            g.setColor(Color.BLACK);
            g.drawString("Resume", resumeButton.x+5, resumeButton.y+18);
            if(!restartHover)
                g.setColor(Color.CYAN);
            else
                g.setColor(Color.WHITE);
            g.fillRect(restartButton.x, restartButton.y, restartButton.width, restartButton.height);
            g.setFont(new Font("Arial", Font.BOLD, 17));
            g.setColor(Color.BLACK);
            g.drawString("Restart", restartButton.x+9, restartButton.y+18);
            if(!quitHover)
                g.setColor(Color.CYAN);
            else
                g.setColor(Color.WHITE);
            g.fillRect(quitButton.x, quitButton.y, quitButton.width, quitButton.height);
            g.setFont(new Font("Arial", Font.BOLD, 17));
            g.setColor(Color.BLACK);
            g.drawString("Quit", quitButton.x+20, quitButton.y+18);
	    
            
    }
    //Event Listener
    public class AL extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if(start)
                if(e.getKeyCode() == e.VK_SPACE)
                {
	   	    start = false;
                    ball.start();
                    p1.start();
                    p2.start();
                  
                }
             if(esc)
            	if(e.getKeyCode()== e.VK_ESCAPE)
            	{
	    	    gamePause = true;
                    ball.suspend();
                    p1.suspend();
                    p2.suspend();
            	}
		if(e.getKeyCode() == e.VK_P)
		{
                    ball.suspend();
                    p1.suspend();
                    p2.suspend();
		}
             if(e.getKeyCode() == e.VK_R)
	     {
                     ball.resume();
                     p1.resume();
                     p2.resume();
	     }
             if(winner)
        	if(e.getKeyCode() == e.VK_ENTER)
                {
                    gameStarted = false;
                    ball.stop();
                    p1.stop();
                    p2.stop();
                    b = new Ball(242,250);
                    ball = new Thread(b);
                    p1 =new Thread(b.p1);
                    p2 = new Thread(b.p2);
                    winner = false;
                }
            b.p1.keyPressed(e);
            b.p2.keyPressed(e);    
        }
        @Override
        public void keyReleased(KeyEvent e){
            b.p1.keyReleased(e);
            b.p2.keyReleased(e);
        }
    }
    public class ML extends MouseAdapter{
        
        public void mouseMoved(MouseEvent e){
            int mx = e.getX();
            int my = e.getY();
            if(mx > startButton.x && mx < startButton.x+startButton.width &&
               my > startButton.y && my < startButton.y+startButton.height)
                startHover = true;
            else
                startHover = false;
            if(mx > difficultyButton.x && mx < difficultyButton.x+difficultyButton.width &&
               my > difficultyButton.y && my < difficultyButton.y+difficultyButton.height)
                difficultyHover = true;
            else
                difficultyHover = false;
            if(mx > resumeButton.x && mx < resumeButton.x+resumeButton.width &&
               my > resumeButton.y && my < resumeButton.y+resumeButton.height)
                resumeHover = true;
            else
                resumeHover = false;
            if(mx > restartButton.x && mx < restartButton.x+restartButton.width &&
               my > restartButton.y && my < restartButton.y+restartButton.height)
                restartHover = true;
            else
                restartHover = false;
            if(mx > quitButton.x && mx < quitButton.x+quitButton.width &&
               my > quitButton.y && my < quitButton.y+quitButton.height)
                quitHover = true;
            else
                quitHover = false;
        }
        public void mousePressed(MouseEvent e){
            int mx = e.getX();
            int my = e.getY();
            if(mx > startButton.x && mx < startButton.x+startButton.width &&
               my > startButton.y && my < startButton.y+startButton.height)
            startGame();
            else if(mx > difficultyButton.x && mx < difficultyButton.x+difficultyButton.width &&
               my > difficultyButton.y && my < difficultyButton.y+difficultyButton.height)
                if(!hardDifficulty){
                    b.setDifficulty(3);
                    hardDifficulty = true;
                }
                else{
                    b.setDifficulty(5);
                    hardDifficulty = false;
                }
                
            if(mx > resumeButton.x && mx < resumeButton.x+resumeButton.width &&
               my > resumeButton.y && my < resumeButton.y+resumeButton.height){
                    start = true;
                    ball.resume();
                    p1.resume();
                    p2.resume();
                    gameStarted = true;
                    gamePause = false;
		}
            
            if(mx > restartButton.x && mx < restartButton.x+restartButton.width &&
               my > restartButton.y && my < restartButton.y+restartButton.height)
            {   
                ball.stop();
                p1.stop();
                p2.stop();
                b = new Ball(242, 250);
                ball = new Thread(b);
                p1 = new Thread(b.p1);
                p2 = new Thread(b.p2);
                start = true;
                gameStarted = true;
                gamePause = false;
            }
             if(mx > quitButton.x && mx < quitButton.x+quitButton.width &&
               my > quitButton.y && my < quitButton.y+quitButton.height)
                 System.exit(0);
        }
    }
}