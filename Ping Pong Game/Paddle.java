import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


public class Paddle implements Runnable{
    
    int x, y, yDirection, id;
    
    Rectangle paddle;
    public Paddle(int x, int y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
        paddle = new Rectangle(x, y, 10, 50);
    }
    public void keyPressed(KeyEvent e)
    {
        switch(id)
        {
            default:
                System.out.println("Enter a valid ID");
                break;
            case 1:
                if(e.getKeyCode() == e.VK_W){
                    YDirection(-1);
                }
                if(e.getKeyCode() == e.VK_S){
                    YDirection(+1);
                }
                break;
            case 2:
                if(e.getKeyCode() == e.VK_UP){
                    YDirection(-1);
                }
                if(e.getKeyCode() == e.VK_DOWN){
                    YDirection(+1);
                }
                break;
     }
   }
        public void keyReleased(KeyEvent e)
    {
        switch(id)
        {
            default:
                System.out.println("Enter a valid ID");
                break;
            case 1:
                if(e.getKeyCode() == e.VK_W){
                    YDirection(0);
                }
                if(e.getKeyCode() == e.VK_S){
                    YDirection(0);
                }
                break;
            case 2:
                if(e.getKeyCode() == e.VK_UP){
                    YDirection(0);
                }
                if(e.getKeyCode() == e.VK_DOWN){
                    YDirection(0);
                }
                break;
     }
   }
    
    public void YDirection(int ydir){
        yDirection = ydir;
    }
     public void move(){
         paddle.y += yDirection;
         if(paddle.y <= 60)
             paddle.y = 60;
         if(paddle.y >= 445)
             paddle.y = 445;
     }
    
    public void draw(Graphics g){
        switch(id){
            default:
                System.out.println("Enter a valid ID");
                break;
            case 1:
                g.setColor(Color.WHITE);
                g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
                break;
            case 2:
                g.setColor(Color.WHITE);
                g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
                break;
    }
    }

    @Override
    public void run() {
        try{
            while(true){
                move();
                Thread.sleep(2);
            }
        }
        catch(Exception e){
            
        }
    }
}