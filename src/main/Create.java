
package main;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.File;
import java.sql.Date;
import javaswingdev.picturebox.DefaultPictureBoxRender;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import jnafilechooser.api.JnaFileChooser;
import model.ModelEmployee;
import model.ModelPositions;
//import model.other.ModelProfile;
import Services.ServiceEmployee;
import model.other.ModelProfile;


public class Create extends javax.swing.JPanel {

    public Create() {
        initComponents();
         datePicker.setCloseAfterSelected(true);
         datePicker.setEditor(txtDate);
         pic.setPictureBoxRender(new DefaultPictureBoxRender() {
            @Override
            public Shape render(Rectangle rectangle) {
                return createRound(rectangle, UIScale.scale(10));
            }
        });
        pic.setImage(new FlatSVGIcon("assets/profile.svg", 3f));
        Panelpic.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0,$Component.borderColor,,10;"
                + "background:$TextArea.background");
    }
    public void loadData(ServiceEmployee service, ModelEmployee data){
       try {
       
           for(ModelPositions pos:service.getServicePositions().getAll()){
                comboPosition.addItem(pos);
                if (data != null && data.getPositions().getPositionsId() == pos.getPositionsId()) {
                    comboPosition.setSelectedItem(pos);
                }  
           }
       }catch (Exception e){
            e.printStackTrace();
       }
         if (data != null) {
            txtName.setText(data.getName());
            txtLocation.setText(data.getLocation());
            if (data.getDate() != null) {
                datePicker.setSelectedDate(data.getDate().toLocalDate());
            }
            txtSalary.setValue(data.getSalary());
            txtDescription.setText(data.getDescription());
            profile = new ModelProfile(data.getProfile().getIcon());
            if  (profile.getIcon() != null){
                pic.setImage(profile.getIcon());
            }
        }
    } 
    

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        datePicker = new raven.datetime.component.date.DatePicker();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSalary = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        comboPosition = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        Panelpic = new javax.swing.JPanel();
        pic = new javaswingdev.picturebox.PictureBox();
        jToolBar1 = new javax.swing.JToolBar();
        cmdBrows = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Name");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Location");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Date");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Salary");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtSalary.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Position");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Description");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtDescription.setColumns(20);
        txtDescription.setLineWrap(true);
        txtDescription.setRows(5);
        txtDescription.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDescription);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Picture");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Panelpic.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        cmdBrows.setText("Brows");
        cmdBrows.setFocusable(false);
        cmdBrows.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdBrows.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdBrows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBrowsActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdBrows);

        cmdDelete.setForeground(new java.awt.Color(255, 102, 102));
        cmdDelete.setText("Delete");
        cmdDelete.setFocusable(false);
        cmdDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdDelete);

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, picLayout.createSequentialGroup()
                .addGap(0, 141, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        picLayout.setVerticalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        Panelpic.add(pic, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Panelpic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(67, 67, 67))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtName, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSalary, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboPosition, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                    .addComponent(txtDate, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLocation, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(Panelpic, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed

    }//GEN-LAST:event_txtDateActionPerformed
    
    private void cmdBrowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBrowsActionPerformed
        JnaFileChooser ch = new JnaFileChooser();
        ch.addFilter("Image", "png", "jpg");
        boolean act = ch.showOpenDialog(SwingUtilities.getWindowAncestor(this));
        if (act) {
            File file = ch.getSelectedFile();
            pic.setImage(new ImageIcon(file.getAbsolutePath()));
            profile = new ModelProfile(file);
        }
            
    }//GEN-LAST:event_cmdBrowsActionPerformed
    private ModelProfile profile;
    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        pic.setImage(new FlatSVGIcon("assets/profile.svg", 5f));
        profile = null;
    }//GEN-LAST:event_cmdDeleteActionPerformed
 public ModelEmployee getData() {
        String name = txtName.getText().trim();
        String location = txtLocation.getText().trim();
        Date date = datePicker.isDateSelected() ? Date.valueOf(datePicker.getSelectedDate()) : null;
        double salary = Double.parseDouble(txtSalary.getValue().toString());
        String description = txtDescription.getText().trim();
        ModelPositions positions = (ModelPositions) comboPosition.getSelectedItem();
        return new ModelEmployee(0, name, location, date, salary, description, profile, positions);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panelpic;
    private javax.swing.JButton cmdBrows;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JComboBox<Object> comboPosition;
    private raven.datetime.component.date.DatePicker datePicker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javaswingdev.picturebox.PictureBox pic;
    private javax.swing.JFormattedTextField txtDate;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtName;
    private javax.swing.JFormattedTextField txtSalary;
    // End of variables declaration//GEN-END:variables
}
