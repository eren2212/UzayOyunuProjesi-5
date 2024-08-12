
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


class Ates{
    private int x;
    private int y;

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

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}

public class Oyun extends JPanel implements KeyListener,ActionListener{
    Timer timer = new Timer(5, this);
    private int gecenSure = 0;
    private int harcananAtes = 0;
    
    private BufferedImage image;
    
    ArrayList<Ates> atesler = new ArrayList<Ates>();
    
    private int atesdirY=5;//ateşi y ekseninde hareket için
    
    private int topX =0;//topun başlanğıc konumu
    private int topdirX=4;//topu sağa sola  gitmesi için
    
    private int uzayGemisiX=0;//uzay gemisinin nerden başladğını
    private int dirUzayGemisi=20;//her seferinde kayması için
    
    public boolean kontrolEt() {
    for (Ates ates : atesler) {
        // Topun x ve y koordinatlarını ve boyutlarını belirtin
        if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 20, 20))) {
            return true;
        }
    }
    return false;
}

    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.BLACK);
        timer.start();
        
    }

    @Override
    public void paint(Graphics g) {
         super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.red);
        gecenSure += 5;
        g.fillOval(topX, 0, 20, 20);//topu oluşturduk.
        
        g.drawImage(image, uzayGemisiX, 490,image.getWidth()/10,image.getHeight()/10,this);
        //burada ımage ayarladık ve projeye ekledik
        
        g.drawLine(370, 250, 370, 350);  // Sol dikey çizgi
        g.drawLine(370, 250, 390, 250);  // Yukarı yatay çizgi
        g.drawLine(370, 300, 385, 300);  // Orta yatay çizgi
        g.drawLine(370, 350, 390, 350);  // Aşağı yatay çizgi

        // L harfi
        g.drawLine(400, 250, 400, 350);  // Sol dikey çizgi
        g.drawLine(400, 350, 420, 350);  // Aşağı yatay çizgi

        // İ harfi
        g.drawLine(430, 250, 430, 350);  // Dikey çizgi
        g.fillOval(427, 240, 6, 6);      // Nokta (küçük bir çember olarak)

        // F harfi
        g.drawLine(450, 250, 450, 350);  // Sol dikey çizgi
        g.drawLine(450, 250, 470, 250);  // Yukarı yatay çizgi
        g.drawLine(450, 300, 465, 300);  // Orta yatay çizgi
        
        
        for (Ates ates : atesler){
            if(ates.getY()<0){
                atesler.remove(ates);
            }
        }
        g.setColor(Color.blue);
        
        for(Ates ates : atesler){
            g.fillRect(ates.getX(), ates.getY(), 10, 20 );
        }
        
        if(kontrolEt()){
            timer.stop();
            String message = "Kazandınız..\n"
                    + "Gecen Süre :"+gecenSure/1000.0+
                    "\nAtes Sayisi:"+harcananAtes;
            
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    
    @Override
    public void repaint() {//oyunlar için gerekli
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        
        if(c == KeyEvent.VK_LEFT){
            if(uzayGemisiX<=0){
                uzayGemisiX =0;
            }
            else{
                uzayGemisiX -= dirUzayGemisi;
            }
        }
        else if(c == KeyEvent.VK_RIGHT){
            if(uzayGemisiX > 725){
                uzayGemisiX=725;
            }
            else{
                uzayGemisiX += dirUzayGemisi;
            }
        }
        
        else if(c == KeyEvent.VK_CONTROL){
            atesler.add(new Ates(uzayGemisiX+15, 470));
            harcananAtes++;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        for(Ates ates :atesler){
            ates.setY(ates.getY()-atesdirY);
        }
        
        
        topX += topdirX;
        
        if(topX>=750){
            topdirX = -topdirX;
        }
        if(topX<=0){
            topdirX = -topdirX;
        }
        repaint();
    }
    
}
