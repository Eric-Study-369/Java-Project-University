package swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import org.jdesktop.animation.timing.Animator;

public class MyPasswordField extends JPasswordField {
    public String getHelperText() {
        return helperText;
    }

    public void setHelperText(String helperText) {
        this.helperText = helperText;
        repaint();
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }
    
    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Icon getPrefixIcon() {
        return prefixIcon;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        initBorder();
    }

    public Icon getSuffixIcon() {
        return suffixIcon;
    }

    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        initBorder();
    }
    
    private String labelText = "Label";
    private String helperText = "";
    private int spaceHelperText = 15;
    private Icon prefixIcon;
    private Icon suffixIcon;
    private String hint = "";
    private final Image eye;
    private final Image eye_hide;
    private boolean hide = true;
    private final Animator animator = null;

   public MyPasswordField() {
    setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
    setBackground(new Color(0, 0, 0, 0));
    setForeground(Color.decode("#7A8C8D"));
    setFont(new java.awt.Font("sansserif", 0, 13));
    setSelectionColor(new Color(75, 175, 152));

    // Load eye and eye_hide images with error handling
    URL eyeURL = getClass().getResource("/assets/eye.png");
    URL eyeHideURL = getClass().getResource("/assets/eye_hide.png");
    
    if (eyeURL != null) {
        eye = new ImageIcon(eyeURL).getImage();
    } else {
        System.err.println("Failed to load eye.png");
        eye = null; // or some default image or handling code
    }

    if (eyeHideURL != null) {
        eye_hide = new ImageIcon(eyeHideURL).getImage();
    } else {
        System.err.println("Failed to load eye_hide.png");
        eye_hide = null; // or some default image or handling code
    }

    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = getWidth() - 30 + 5;
            int y = (getHeight() - 20) / 2;
            if (e.getX() >= x && e.getX() <= x + 20 && e.getY() >= y && e.getY() <= y + 20) {
                hide = !hide;
                setEchoChar(hide ? '*' : (char) 0);
                repaint();
            }
        }
    });

    addMouseMotionListener(new MouseAdapter() {
        @Override
        public void mouseMoved(MouseEvent me) {
            int x = getWidth() - 30;
            if (new Rectangle(x, 0, 30, 30).contains(me.getPoint())) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.TEXT_CURSOR));
            }
        }
    });
}

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(230, 245, 241));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        paintIcon(g);
        super.paintComponent(g);
        createHelperText(g2);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getPassword().length == 0) {
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            g.setColor(new Color(200, 200, 200));
            g.drawString(hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
        createShowHide((Graphics2D) g);
    }

    private void createShowHide(Graphics2D g) {
        int x = getWidth() - 30 + 5;
        int y = (getHeight() - 20) / 2;
        g.drawImage(hide ? eye_hide : eye, x, y, null);
    }

    private void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (prefixIcon != null) {
            Image prefix = ((ImageIcon) prefixIcon).getImage();
            int y = (getHeight() - prefixIcon.getIconHeight()) / 2;
            g2.drawImage(prefix, 10, y, this);
        }
        if (suffixIcon != null) {
            Image suffix = ((ImageIcon) suffixIcon).getImage();
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2;
            g2.drawImage(suffix, getWidth() - suffixIcon.getIconWidth() - 10, y, this);
        }
    }

    private void createHelperText(Graphics2D g2) {
        if (helperText != null && !helperText.equals("")) {
            Insets in = getInsets();
            int height = getHeight() - spaceHelperText;
            g2.setColor(new Color(255, 76, 76));
            Font font = getFont();
            g2.setFont(font.deriveFont(0, font.getSize() - 1));
            FontMetrics ft = g2.getFontMetrics();
            Rectangle2D r2 = ft.getStringBounds(labelText, g2);
            double textY = (spaceHelperText - r2.getHeight()) / 2f;
            g2.drawString(helperText, in.left, (int) (height + ft.getAscent() - textY));
        }
    }

    @Override
    public void setText(String string) {
        if (!getText().equals(string)) {
            showing(string.equals(""));
        }
        super.setText(string);
    }

    private void showing(boolean action) {
        if (animator != null && animator.isRunning()) {
            animator.stop();
        }
        float location = 1f;
        if (animator != null) {
            animator.setStartFraction(1f - location);
            location = 1f - location;
            animator.start();
        }
    }

    private void initBorder() {
        int left = 15;
        int right = 15;
        if (prefixIcon != null) {
            left = prefixIcon.getIconWidth() + 15;
        }
        if (suffixIcon != null) {
            right = suffixIcon.getIconWidth() + 15;
        }
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, left, 10, right));
    }
}