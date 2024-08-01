
package table;

import Until.SuperEllipse2D;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import javaswingdev.picturebox.DefaultPictureBoxRender;
import model.ModelEmployee;

public class TableCellProfile extends javax.swing.JPanel {

    public TableCellProfile(ModelEmployee data, Font font) {
        initComponents();
        lbName.setFont(font);
        lbLocation.setFont(font);
        lbName.setText(data.getName());
        lbLocation.setText(data.getLocation());
        lbLocation.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$Label.disabledForeground");
        if (data.getProfile().getIcon() != null) {
            pic.setImage(data.getProfile().getIcon());
        }
        pic.setPictureBoxRender(new DefaultPictureBoxRender() {
            @Override 
            public Shape render (Rectangle rec) {
            return super.render(rec);//new SuperEllipse2D(rec.x, rec.y, rec.width, rec.height, 4f).getShape();
            }
         });
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new javaswingdev.picturebox.PictureBox();
        lbName = new javax.swing.JLabel();
        lbLocation = new javax.swing.JLabel();

        lbName.setText("Name");

        lbLocation.setText("Location");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pic, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addComponent(lbLocation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pic, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbLocation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbLocation;
    private javax.swing.JLabel lbName;
    private javaswingdev.picturebox.PictureBox pic;
    // End of variables declaration//GEN-END:variables
}
