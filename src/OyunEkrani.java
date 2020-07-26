
import java.awt.HeadlessException;
import javax.swing.JFrame;


public class OyunEkrani extends JFrame 
{
    public static void main(String[] args)
    {
        OyunEkrani ekran = new OyunEkrani("Uzay Oyunu");
        ekran.setResizable(false);
        ekran.setFocusable(false);
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Oyun oyun = new Oyun();
        //klavye işlerini anlaması için;
        oyun.requestFocus();
        
        //interface imp ederek kşavye işlemlerinin anlamasını sağlıcaz
        oyun.addKeyListener(oyun);
        oyun.setFocusable(true);
        oyun.setFocusTraversalKeysEnabled(false);
        
        //Jpaneli ekrana eklemek için;
        ekran.add(oyun);
        ekran.setVisible(true);
        
        
    }

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
        
        
    }
    
}
