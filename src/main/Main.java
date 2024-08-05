
package main;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.SimplePopupBorder;
import raven.toast.Notifications;
import connection.DatabaseConnection;
import model.ModelEmployee;
import Services.ServiceEmployee;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import raven.modal.ModalDialog;
import raven.modal.Toast;
import raven.modal.component.SimpleModalBorder;
import table.CheckBoxTableHeaderRenderer;
import table.TableHeaderAlignment;
import table.TablecallprofilRender;




public class Main extends javax.swing.JFrame {
    private ServiceEmployee service = new ServiceEmployee();
    private Animator animatorLogin;
    private Animator animatorBody;
    private boolean signIn;

   public Main() {
        initComponents();
        init();
        initBasic();
        getContentPane().setBackground(new Color(245, 245, 245));
        TimingTarget targetLogin = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (signIn) {
                    background1.setAnimate(fraction);
                } else {
                    background1.setAnimate(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (signIn) {
                    paneLogin.setVisible(false);
                    background1.setShowPaint(true);
                    panelBody.setAlpha(0);
                    panelBody.setVisible(true);
                    animatorBody.start();
                } else {
                    enableLogin(true);
                    txtUser.grabFocus();
                }
            }
        };
        TimingTarget targetBody = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (signIn) {
                    panelBody.setAlpha(fraction);
                } else {
                    panelBody.setAlpha(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (signIn == false) {
                    panelBody.setVisible(false);
                    background1.setShowPaint(false);
                    background1.setAnimate(1);
                    paneLogin.setVisible(true);
                    animatorLogin.start();
                }
            }
        };
        animatorLogin = new Animator(1500, targetLogin);
        animatorBody = new Animator(500, targetBody);
        animatorLogin.setResolution(0);
        animatorBody.setResolution(0);
        Scroll.getViewport().setOpaque(false);
        Scroll.setViewportBorder(null);
    }
    
    private void init() {
        GlassPanePopup.install(this);
        Notifications.getInstance().setJFrame(this);
        Panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Table.background");

        Table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background;"
                + "font:bold;");

        Table.putClientProperty(FlatClientProperties.STYLE, ""
                + "rowHeight:70;"
                + "showHorizontalLines:true;"
                + "intercellSpacing:0,1;"
                + "cellFocusColor:$TableHeader.hoverBackground;"
                + "selectionBackground:$TableHeader.hoverBackground;"
                + "selectionForeground:$Table.foreground;");

        Scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Table.background;");

        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +5;");

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("assets/search.svg"));
        txtSearch.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:15;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "margin:5,20,5,20;"
                + "background:$Panel.background");

        Table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(Table, 0));
        Table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(Table));
        Table.getColumnModel().getColumn(2).setCellRenderer(new TablecallprofilRender(Table));
        
        try {
             DatabaseConnection.getInstance().connectToDatabase();
             loadData();
        }catch(Exception e){
           e.printStackTrace();
        }
     }
    private void initBasic(){
        GlassPanePopup.install(this);
        Notifications.getInstance().setJFrame(this);
        Panel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:25;"
                + "background:$Table.background");

        basicTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "height:30;"
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background;"
                + "font:bold;");

        basicTable.putClientProperty(FlatClientProperties.STYLE, ""
                + "rowHeight:30;"
                + "showHorizontalLines:true;"
                + "intercellSpacing:0,1;"
                + "cellFocusColor:$TableHeader.hoverBackground;"
                + "selectionBackground:$TableHeader.hoverBackground;"
                + "selectionForeground:$Table.foreground;");

        Scroll1.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "trackInsets:3,3,3,3;"
                + "thumbInsets:3,3,3,3;"
                + "background:$Table.background;");
        basicTable.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(basicTable, 0));
        basicTable.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(basicTable));
        try {
             DatabaseConnection.getInstance().connectToDatabase();
             loadData1();
        }catch(Exception e){
           e.printStackTrace();
        }
    }
     private void loadData1() {
        try {
            DefaultTableModel model = (DefaultTableModel) basicTable.getModel();
            if (basicTable.isEditing()) {
                basicTable.getCellEditor().stopCellEditing();
            }
            model.setRowCount(0);
            List<ModelEmployee> list = service.getAll();
            for (ModelEmployee d:list) {
                model.addRow(d.toTableRow(basicTable.getRowCount()+1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadData() {
        try {
            DefaultTableModel model = (DefaultTableModel) Table.getModel();
            if (Table.isEditing()) {
                Table.getCellEditor().stopCellEditing();
            }
            if (basicTable.isEditing()){
                basicTable.getCellEditor().stopCellEditing();
            }
            model.setRowCount(0);
            List<ModelEmployee> list = service.getAll();
            for (ModelEmployee d:list) {
                model.addRow(d.toTableRow(Table.getRowCount()+1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     private void SearchData(String search) {
        try {
            DefaultTableModel model = (DefaultTableModel) Table.getModel();
            if (Table.isEditing()) {
                Table.getCellEditor().stopCellEditing();
            }
            
            model.setRowCount(0);
            List<ModelEmployee> list = service.search(search);
            for (ModelEmployee d:list) {
                model.addRow(d.toTableRow(Table.getRowCount()+1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new notherSwing.Background();
        paneLogin = new javax.swing.JPanel();
        panelRound1 = new swing.PanelRound();
        imageAvatar1 = new notherSwing.ImageAvatar();
        txtUser = new swing.MyTextField();
        txtpass = new swing.MyPasswordField();
        cmdSignin = new swing.ButtonAction();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelBody = new notherSwing.PanelTransparent();
        Tabbed1 = new swing.MaterialTabbed();
        jPanel1 = new javax.swing.JPanel();
        Panel1 = new javax.swing.JPanel();
        Scroll1 = new javax.swing.JScrollPane();
        basicTable = new javax.swing.JTable();
        Panel = new javax.swing.JPanel();
        Scroll = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        txtSearch = new javax.swing.JTextField();
        lbTitle = new javax.swing.JLabel();
        cmdEdite = new swing.ButtonAction();
        cmdDelete = new swing.ButtonAction();
        cmdNew = new swing.ButtonAction();
        jPanel2 = new javax.swing.JPanel();
        imageAvatar2 = new notherSwing.ImageAvatar();
        buttonAction2 = new swing.ButtonAction();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        background1.setLayout(new java.awt.CardLayout());

        imageAvatar1.setBorderSize(1);
        imageAvatar1.setBorderSpace(0);
        imageAvatar1.setImage(new javax.swing.ImageIcon(getClass().getResource("/assets/RUPP_logo.png"))); // NOI18N

        cmdSignin.setBackground(new java.awt.Color(0, 255, 204));
        cmdSignin.setText("Sign in ");
        cmdSignin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSigninActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Username");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Password");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtpass, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)))
                .addGap(75, 75, 75))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(cmdSignin, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(cmdSignin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout paneLoginLayout = new javax.swing.GroupLayout(paneLogin);
        paneLogin.setLayout(paneLoginLayout);
        paneLoginLayout.setHorizontalGroup(
            paneLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneLoginLayout.createSequentialGroup()
                .addContainerGap(467, Short.MAX_VALUE)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(452, 452, 452))
        );
        paneLoginLayout.setVerticalGroup(
            paneLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneLoginLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        background1.add(paneLogin, "card2");

        basicTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        basicTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Select", "#", "NAME", "LOCATION", "DATE", "SALARY", "POSITION", "DESCRIPTION"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        basicTable.setPreferredSize(new java.awt.Dimension(700, 850));
        Scroll1.setViewportView(basicTable);
        if (basicTable.getColumnModel().getColumnCount() > 0) {
            basicTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            basicTable.getColumnModel().getColumn(1).setMaxWidth(40);
            basicTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            basicTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            basicTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        }

        javax.swing.GroupLayout Panel1Layout = new javax.swing.GroupLayout(Panel1);
        Panel1.setLayout(Panel1Layout);
        Panel1Layout.setHorizontalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Scroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 1192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        Panel1Layout.setVerticalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Scroll1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Tabbed1.addTab("Basic table", jPanel1);

        Panel.setBackground(new java.awt.Color(204, 204, 204));

        Table.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "SELECT", "#", "NAME", "DATE", "SALARY", "POSITION", "DESCRIPTION"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setPreferredSize(new java.awt.Dimension(700, 850));
        Scroll.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setPreferredWidth(150);
            Table.getColumnModel().getColumn(0).setMaxWidth(50);
            Table.getColumnModel().getColumn(0).setHeaderValue("SELECT");
            Table.getColumnModel().getColumn(1).setMaxWidth(40);
            Table.getColumnModel().getColumn(2).setPreferredWidth(100);
            Table.getColumnModel().getColumn(6).setPreferredWidth(150);
        }

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        lbTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle.setForeground(new java.awt.Color(51, 51, 51));
        lbTitle.setText("Student E6 - Group6");
        lbTitle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cmdEdite.setText("Edite");
        cmdEdite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditeActionPerformed(evt);
            }
        });

        cmdDelete.setText("Delete");
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });

        cmdNew.setText("New");
        cmdNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addComponent(Scroll)
                        .addContainerGap())
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 730, Short.MAX_VALUE)
                        .addComponent(cmdNew, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdEdite, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1061, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdEdite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        Tabbed1.addTab("Coustom table", Panel);

        jPanel2.setBackground(new java.awt.Color(157, 153, 255));

        imageAvatar2.setToolTipText("");
        imageAvatar2.setBorderSize(1);
        imageAvatar2.setBorderSpace(0);
        imageAvatar2.setImage(new javax.swing.ImageIcon(getClass().getResource("/assets/profile.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imageAvatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(imageAvatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        buttonAction2.setBackground(new java.awt.Color(157, 153, 255));
        buttonAction2.setText("Sign Out");
        buttonAction2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
        panelBody.setLayout(panelBodyLayout);
        panelBodyLayout.setHorizontalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelBodyLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(Tabbed1, javax.swing.GroupLayout.PREFERRED_SIZE, 1254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBodyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        panelBodyLayout.setVerticalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBodyLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tabbed1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        background1.add(panelBody, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSigninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSigninActionPerformed
        // TODO add your handling code here:
          if (!animatorLogin.isRunning()) {
            signIn = true;
            String user = txtUser.getText().trim();
            String pass = String.valueOf(txtpass.getPassword());
            boolean action = true;
            if (user.equals("")) {
                txtUser.setHelperText("Please input user name");
                txtUser.grabFocus();
                action = false;
            }
            if (pass.equals("")) {
                txtpass.setHelperText("Please input password");
                if (action) {
                    txtpass.grabFocus();
                }
                action = false;
            }
            if (action) {
                animatorLogin.start();
                enableLogin(false);
            }
        }
    }//GEN-LAST:event_cmdSigninActionPerformed

    private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed

        Create create = new Create();
        create.loadData(service, null);
        DefaultOption option = new DefaultOption() {
            @Override
            public boolean closeWhenClickOutside() {
                return true;
            }
        };
        String actions[] = new String[]{"Cancel", "Save"};
        GlassPanePopup.showPopup(new SimplePopupBorder(create, "Create Employee", actions, (pc, i) -> {
            if (i == 1) {
                // save
                try {
                    service.create(create.getData());
                    pc.closePopup();
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Employee has been created");
                    loadData();
                    loadData1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pc.closePopup();
            }
        }), option);
    }//GEN-LAST:event_cmdNewActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        List<ModelEmployee> list = getSelectedData();
        if (!list.isEmpty()) {
            SimpleModalBorder.Option[] options = new SimpleModalBorder.Option[]{
                new SimpleModalBorder.Option("Cancel", SimpleModalBorder.CANCEL_OPTION),
                new SimpleModalBorder.Option("Delete", SimpleModalBorder.OK_OPTION)
            };

            JLabel label = new JLabel("Are you  sure to delete " + list.size() + " employee ?");
            label.setBorder(new EmptyBorder(0, 25, 0, 25));
            ModalDialog.showModal(this, new SimpleModalBorder(label, "Confirm Delete", options, (mc, i) -> {
                if (i == SimpleModalBorder.OK_OPTION) {
                    // delete
                    try {
                        for (ModelEmployee d : list) {
                            service.delete(d.getEmployeeId());
                        }
                        Toast.show(this, Toast.Type.SUCCESS, "Employee has been deleted");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loadData();
                    loadData1();
                }
            }));
        } else {
            Toast.show(this, Toast.Type.WARNING, "Please select employee to delete");
        }
    }//GEN-LAST:event_cmdDeleteActionPerformed

    private void cmdEditeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditeActionPerformed
        List<ModelEmployee> list = getSelectedData();
        if(!list.isEmpty()){
            if(list.size() == 1){
                ModelEmployee data = list.get(0);
                Create create = new Create();
                create.loadData(service, data);
                DefaultOption option = new DefaultOption() {
                    @Override
                    public boolean closeWhenClickOutside() {
                        return true;
                    }
                };
                String actions[] = new String[]{"Cancel", "Update"};
                GlassPanePopup.showPopup(new SimplePopupBorder(create, "Edit Employee [" + data.getName() +"]", actions, (pc, i) -> {
                    if (i == 1) {
                        // edite
                        try {
                            ModelEmployee dataEdit=create.getData();
                            dataEdit.setEmployeeId(data.getEmployeeId());
                            service.edit(dataEdit);
                            pc.closePopup();
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Employee has been update");
                            loadData();
                            loadData1();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        pc.closePopup();
                    }
                }), option);
            }else {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Please select only one employee");
            }
        }
        else{
            Notifications.getInstance().show(Notifications.Type.WARNING, "Please select employee to edite");
        }
    }//GEN-LAST:event_cmdEditeActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        SearchData(txtSearch.getText().trim());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void buttonAction2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction2ActionPerformed
        signIn = false;
        clearLogin();
        animatorBody.start();
    }//GEN-LAST:event_buttonAction2ActionPerformed
      private List<ModelEmployee> getSelectedData() {
        List<ModelEmployee> list = new ArrayList<>();
        for (int i = 0; i < Table.getRowCount(); i++) {
            if ((boolean) Table.getValueAt(i, 0)) {
                ModelEmployee data = (ModelEmployee) Table.getValueAt(i, 2);
                list.add(data);
            }
        }
        return list;
    }
    

    private void enableLogin(boolean action) {
        txtUser.setEditable(action);
        txtpass.setEditable(action);
        cmdSignin.setEnabled(action);
    }

    public void clearLogin() {
        txtUser.setText("");
        txtpass.setText("");
        txtUser.setHelperText("");
        txtpass.setHelperText("");
    }
   
    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new splashscreen.Loadingscren(null , true).setVisible(true);
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private javax.swing.JPanel Panel1;
    private javax.swing.JScrollPane Scroll;
    private javax.swing.JScrollPane Scroll1;
    private swing.MaterialTabbed Tabbed1;
    private javax.swing.JTable Table;
    private notherSwing.Background background1;
    private javax.swing.JTable basicTable;
    private swing.ButtonAction buttonAction2;
    private swing.ButtonAction cmdDelete;
    private swing.ButtonAction cmdEdite;
    private swing.ButtonAction cmdNew;
    private swing.ButtonAction cmdSignin;
    private notherSwing.ImageAvatar imageAvatar1;
    private notherSwing.ImageAvatar imageAvatar2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPanel paneLogin;
    private notherSwing.PanelTransparent panelBody;
    private swing.PanelRound panelRound1;
    private javax.swing.JTextField txtSearch;
    private swing.MyTextField txtUser;
    private swing.MyPasswordField txtpass;
    // End of variables declaration//GEN-END:variables
}
