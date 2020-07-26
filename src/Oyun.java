
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {
    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}

public class Oyun extends JPanel implements KeyListener,ActionListener
{
    //topları ve uzay mekiniğini hareket ettirmek için actionlistenerı imp ediyoruz
    //timer her çalıştığında actionperformed harekete geçicek
    
    //oyunda kullancağımız değişkenler ve özellikler
    Timer timer = new Timer(5,this);
    private int gecen_sure = 0 ;
    private int harcanan_ates = 0;
    
    private BufferedImage image;
    
    //atılan ateşleri tutmak için bir arraylist yazıcaz
    private ArrayList<Ates> atesler = new ArrayList<Ates>();
    
    //ateşleri her timer çalıştığında bir ileri götürmemiz lazım
    private int atesdirY = 1;
    
    //topun hareketi
    private int topX = 0;
    
    private int topdirX = 2;
    
    //uzay gemisinin nerden başlıcağını söylicek
    private int uzayGemisiX = 0; 
    public boolean kontrolEt ()
    {
        for (Ates ates : atesler)
        {
            if(new Rectangle(ates.getX(), ates.getY(), 10,20).intersects(new Rectangle(topX,0,20,20)))
            {
                return true;
            }
        }
        return false;
    }
    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzayaraci.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.BLACK);
        
        //oyun başladığı anda timerın başlamasını istediğimiz için;
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        gecen_sure +=5;
        //top çizme
        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);
        
        g.drawImage(image, uzayGemisiX, 490,image.getWidth()/8 , image.getHeight() /10, this);
        
        for (Ates ates : atesler)
        {
            //Jframe den çıkan ateşleri direk silmek için;
            if(ates.getY() <0)
            {
                atesler.remove(ates);
            }
        }
        
        //ates çizmek
        g.setColor(Color.blue);
        for (Ates ates : atesler)
        {
            g.fillRect(ates.getX(), ates.getY(), 10, 20);
        }
        if(kontrolEt())
        {
            timer.stop();
            String message ="Kazandınız..\n" +
                    "Harcanan Ateş: " + harcanan_ates +
                    "\nGeçen Süre :" + gecen_sure /1000.0 + "saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    
    }

    @Override //tekrar paint çağırılıp şekillerimiz oluşturuluyor
    public void repaint() {
        super.repaint(); 
    }
    
    //sağa sola basıldığında 20 milim kayması 
    private int dirUzayX = 20;
            
    
    
    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        //uzay mek hareketi
        int c = e.getKeyCode();
        if ( c == KeyEvent.VK_LEFT)
        {
            if (uzayGemisiX <= 0)
            {
                uzayGemisiX = 0;
            }
            else
            {
                uzayGemisiX -= dirUzayX;
            }
        }
        else if ( c == KeyEvent.VK_RIGHT)
        {
            if(uzayGemisiX >= 750)
            {
                uzayGemisiX = 750;
            }
            else
            {
                uzayGemisiX += dirUzayX;
            }
            
        }
        else if (c == KeyEvent.VK_CONTROL)
        {
            atesler.add(new Ates(uzayGemisiX +15,470));
            harcanan_ates ++;
            
                   
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //ateşler
        for(Ates ates : atesler)
        {
            ates.setY(ates.getY() - atesdirY);
            
        }
        //topun hareketini sağlamak için
        topX += topdirX;
        
        //topun panelden çıkma durumunu kontrol etmek için;
        if (topX >= 750)
        {
            topdirX = - topdirX;
        }
        if (topX <= 0)
        {
            topdirX = - topdirX;
        }
        repaint();
    }
    
    
    

    
    
}
