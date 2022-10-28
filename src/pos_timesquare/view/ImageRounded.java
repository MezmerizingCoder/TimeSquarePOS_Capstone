/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static pos_timesquare.view.MainFrame.darkRB;
import static pos_timesquare.view.MainFrame.productImage;

/**
 *
 * @author Acer
 */
public class ImageRounded extends JLabel{
    
    BufferedImage selectedImage = null;
    public Boolean isToDraw = false;
    
    ImageRounded(){
        
    }
    
    ImageRounded(BufferedImage image, int cornerRadius){
        
        selectedImage = makeRoundedCorner(image, cornerRadius);
        
        
//        Image scaledImage = selectedImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);

//        ImageIcon imageIcon = new ImageIcon(selectedImage);
        
//        setIcon(imageIcon);
        
        System.out.println("image save");
//        this.paintImmediately(this.getBounds());
        revalidate();
        repaint();
    }
    
    public JLabel setImage(BufferedImage image, int cornerRadius){
        selectedImage = makeRoundedCorner(image, cornerRadius);
        return new JLabel();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(selectedImage == null){
            final BufferedImage image;
            try {
                image = ImageIO.read(getClass().getResource("/img/product/no-img.png"));
                BufferedImage rounded = makeRoundedCorner(image, 30);
                Image scaledImage = rounded.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
                g2.drawImage(scaledImage, 0, 0, this.getWidth(), this.getHeight(), null);
                System.out.println(selectedImage);
                System.out.println("no paint");

            } catch (IOException ex) {
                Logger.getLogger(ImageRounded.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            g2.drawImage(selectedImage, 0, 0, this.getWidth(), this.getHeight(), null);
            System.out.println(selectedImage);
        }
        
//        if(selectedImage != null){
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g.create();
//
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            
//            g2.drawImage(selectedImage, 0, 0, null);
//        }

//        if(darkRB.isSelected()){
//            g2.setColor(new Color(70, 70, 80));
//        }else{
//            g2.setColor(new Color(205,205,205));
//        }
//
//        g2.setStroke(new BasicStroke(1));
//        g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
    
    }
    
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }
}
