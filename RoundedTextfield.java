import sas.*;
import java.awt.Color;

public class RoundedTextfield {

    public Sprite sprite;

    private Rectangle center, left, right;
    private Circle tl, tr, bl, br;
    private Text label;

    private double x, y, width, height, radius;
    private int fontSize;
    private int maxChars;

    private boolean activated = false;

    private String text = "";
    private String placeholder;

    private Color normalColor;
    private Color offColor;
    private Color textColor;
    private Color placeholderColor = new Color (220,220,220);

    public RoundedTextfield(double xPos, double yPos,
                            double width, double height,
                            int fontSize,
                            String placeholder,
                            Color fieldColor,
                            Color textColor,int roundness) {

        this.x = xPos;
        this.y = yPos;
        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
        this.placeholder = placeholder;
        this.normalColor = fieldColor;
        this.textColor = textColor;
        this.radius = roundness;
        
        offColor = makeOffColor(normalColor);

        maxChars = (int) (width / (fontSize * 0.6)) - 1;

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

        label = new Text(0, 0, "", placeholderColor);
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
        setColor(offColor);
        updateDisplay();
    }
    
    
        private Color makeOffColor(Color c) {
        return new Color(
            (int)(c.getRed() * 0.75),
            (int)(c.getGreen() * 0.75),
            (int)(c.getBlue() * 0.75)
        );
    }

        private void updateDisplay() {
        if (text.length() == 0) {
            if (!activated) {
                label.setText(placeholder);
                label.setFontColor(placeholderColor);
            } else {
                label.setText("");
            }
        } else {
            label.setText(text);
            label.setFontColor(textColor);
        }
        centerText();
    }


    // ===== Textposition =====
    private void centerText() {
        double textWidth = label.getShapeWidth();
        double textHeight = label.getShapeHeight();
        double textX = x + (width - textWidth) / 2;
        double textY = y + (height - textHeight) / 2;
        label.moveTo(textX, textY);
    }

    // ===== Aktivierung =====
    public void setActivated(boolean state) {
        activated = state;
        if (state) setColor(normalColor);
        else setColor(offColor);
        updateDisplay();
    }

    public boolean getActivated() {
        return activated;
    }

    // ===== Klick =====
    public boolean clicked() {
        return sprite.mouseClicked();
    }

    // ===== Texteingabe =====
    public void textInput(char c) {
        if (!activated) return;

        if (c == '\b') {
            if (text.length() > 0)
                text = text.substring(0, text.length() - 1);
                setColor(normalColor);
        } else {
            if (text.length() < maxChars && c >= 32) {
                text += c;
            }
            if (text.length() == maxChars){
                setColor(Color.ORANGE);
            }
        }
        updateDisplay();
    }

    // ===== Textzugriff =====
    public String getText() {
        return text;
    }

    // ===== Farbe setzen =====
    public void setNewColor(Color c){
        normalColor = c;
        offColor = makeOffColor(normalColor);
        if(getActivated()){
            setColor(normalColor);
        }
        else{
            setColor(offColor);
        }
        
    }
    private void setColor(Color c) {
        center.setColor(c);
        left.setColor(c);
        right.setColor(c);
        tl.setColor(c);
        tr.setColor(c);
        bl.setColor(c);
        br.setColor(c);
    }

    // ===== Sichtbarkeit =====
    public void setHidden(boolean hidden) {
        sprite.setHidden(hidden);
    }
}
