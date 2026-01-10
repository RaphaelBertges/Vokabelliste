import sas.*;
import java.awt.Color;

public class RoundedTextfield {

    public Sprite sprite;

    private Rectangle center, left, right;
    private Circle tl, tr, bl, br;
    private Text label;

    private double x, y, width, height, radius;

    private boolean activated = false;
    private String text = "";

    private Color normalColor;
    private Color activeColor = new Color(180, 220, 255);

    public RoundedTextfield(double xPos, double yPos,
                            double width, double height,
                            int fontSize,
                            Color fieldColor,
                            Color textColor,int roundness) {

        this.x = xPos;
        this.y = yPos;
        this.width = width;
        this.height = height;
        this.normalColor = fieldColor;
        this.radius = roundness;

        center = new Rectangle(x + radius, y,
                width - 2 * radius, height, fieldColor);

        left = new Rectangle(x, y + radius,
                radius, height - 2 * radius, fieldColor);

        right = new Rectangle(x + width - radius, y + radius,
                radius, height - 2 * radius, fieldColor);

        tl = new Circle(x, y, radius, fieldColor);
        tr = new Circle(x + width - 2 * radius, y, radius, fieldColor);
        bl = new Circle(x, y + height - 2 * radius, radius, fieldColor);
        br = new Circle(x + width - 2 * radius, y + height - 2 * radius, radius, fieldColor);

        label = new Text(0, 0, "", textColor);
        label.setFontSansSerif(false, fontSize);

        centerText();
        
        sprite = new Sprite();
        sprite.add(center);
        sprite.add(left);
        sprite.add(right);
        sprite.add(tl);
        sprite.add(tr);
        sprite.add(bl);
        sprite.add(br);
        sprite.add(label);
    }

    // ===== Zentrierung =====
    private void centerText() {
        double textWidth = text.length() * label.getShapeHeight() * 0.6;
        double textX = x + 10; // leicht linksbündig
        double textY = y + height / 2 + label.getShapeHeight() / 3;
        label.moveTo(textX, textY);
    }

    // ===== Aktivierung =====
    public void setActivated(boolean state) {
        activated = state;
        if (state) setTextfieldColor(activeColor);
        else setTextfieldColor(normalColor);
    }

    public boolean getActivated() {
        return activated;
    }

    // ===== Klick =====
    public boolean clicked() {
        return sprite.mouseClicked();
    }

    // ===== Texteingabe von außen =====
    public void textInput(char c) {
        if (!activated) return;

        if (c == '\b') { // Backspace
            if (text.length() > 0)
                text = text.substring(0, text.length() - 1);
        } else {
            text += c;
        }

        setText(text);
    }

    // ===== Interne Textaktualisierung =====
    public void setText(String t) {
        label.setText(t);
        centerText();
    }

    public String getText() {
        return text;
    }

    // ===== Farbe =====
    private void setTextfieldColor(Color c) {
        center.setColor(c);
        left.setColor(c);
        right.setColor(c);
        tl.setColor(c);
        tr.setColor(c);
        bl.setColor(c);
        br.setColor(c);
    }
    public void setTextColor(Color color) {
        label.setFontColor(color);
    }
    // ===== Sichtbarkeit =====
    public void setHidden(boolean hidden) {
        sprite.setHidden(hidden);
    }
}
