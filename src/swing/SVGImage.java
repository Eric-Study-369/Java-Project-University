
package swing;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;
import javax.swing.JLabel;
import javax.swing.Timer;


public class SVGImage extends JLabel {
    
    private FlatSVGIcon svgicon;
    private Timer timer;
    private int iconColor = 0;
    
    public void setSvgImage(String image , int width, int height){
        svgicon = new FlatSVGIcon(image , width , height);
        setIcon(svgicon);
    }
    public void animation(){
        if (svgicon!= null){
            svgicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
        public Color apply(Color t){
           iconColor += 1;
           iconColor %= 255;
           return Color.getHSBColor(iconColor / 255f, 1 , 1);
        } 
   }));
    timer = new Timer(30, new ActionListener(){
      @Override
       public void actionPerformed(ActionEvent e){
             SVGImage.this.repaint();
            }
        }); 
        timer.start();
        }
   
    }
}