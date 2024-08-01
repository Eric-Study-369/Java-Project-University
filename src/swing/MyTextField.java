package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class MyTextField extends JTextField {

    private Icon prefixIcon;
    private Icon suffixIcon;
    private String hint = "";
    private String labelText = "Label";
    private String helperText = "";
    private int spaceHelperText = 15;

    public MyTextField() {
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.decode("#7A8C8D"));
        setFont(new java.awt.Font("sansserif", 0, 13));
        setSelectionColor(new Color(75, 175, 152));
    }

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
        repaint();
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

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(230, 245, 241));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        paintIcon(g);
        super.paintComponent(g);
        paintHint(g);
        paintHelperText(g2); // Call to paint helper text
    }

    private void paintHint(Graphics g) {
        if (getText().length() == 0) {
            int h = getHeight();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            g2.setColor(new Color(200, 200, 200));
            g2.drawString(hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }

    private void paintHelperText(Graphics2D g2) {
        if (helperText != null && !helperText.equals("")) {
            Insets in = getInsets();
            int height = getHeight() - 15;
            g2.setColor(new Color(255, 76, 76));
            Font font = getFont();
            g2.setFont(font.deriveFont(0, font.getSize() - 1));
            FontMetrics ft = g2.getFontMetrics();
            Rectangle2D r2 = ft.getStringBounds(helperText, g2);
            double textY = (15 - r2.getHeight()) / 2f;
            g2.drawString(helperText, in.left, (int) (height + ft.getAscent() - textY));
        }
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