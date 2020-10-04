import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball implements Runnable{
    
    //Global Variables
    int x, y, xDirection, yDirection;
    
    //Difficulty level
    int difficulty = 5;
    
    //Score
    int p1Score, p2Score;
     
    //Paddle
    Paddle p1 = new Paddle(15, 230, 2);
    Paddle p2 = new Paddle(475, 230, 2);
    
    Rectangle ball;
    
    public Ball(int x, int y){
        p1Score = p2Score = 0;
        this.x = x;
        this.y = y;
        
        //set ball moving randomly
        Random r = new Random();
        int xrDir = r.nextInt(1);
        if(xrDir == 0)
            xrDir--;
        setXDirection(xrDir);
        int yrDir = r.nextInt(1);
        if(yrDir == 0)
            yrDir--;
        setYDirection(yrDir);
        
        //Create 'ball'
        ball = new Rectangle(this.x, this.y, 13, 13);

    }
    
    public void setXDirection(int xdir){
        xDirection = xdir;
    }
    public void setYDirection(int ydir){
        yDirection = ydir;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.fillOval(ball.x, ball.y, ball.width, ball.height);
    }
    
    public void collision(){
        if(ball.intersects(p1.paddle))
            setXDirection(+1);
        if(ball.intersects(p2.paddle))
            setXDirection(-1);
    }
    
    public void move(){
        collision();
        ball.x += xDirection;
        ball.y += yDirection;
        
        //Bounce the ball at edges
        if(ball.x <= 5){
            setXDirection(+1);
            p2Score++;
        }            
        if(ball.x>= 480){
            setXDirection(-1);   
            p1Score++;
        }
        if(ball.y <= 59)
            setYDirection(+1);
        
        if(ball.y >= 480)
            setYDirection(-1);
    }

    @Override
    public void run() {
        try{
            while(true){
                move();
                Thread.sleep(difficulty);
            }            
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public void setDifficulty(int diff){
        difficulty = diff;
    }
}
