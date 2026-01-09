import sas.*;
import java.awt.Color;

public class RoundedButton 
/**Die gestamte Klasse ins Projekt importieren. Wie bei Queue,Stack,List.**/
{

    public Sprite sprite;

    private Rectangle center, left, right;
    private Circle tl, tr, bl, br;
    private Text label;

    private double x, y, width, height, radius;

    public RoundedButton(double xPos, double yPos,
                         double width, double height,
                         String text,
                         int fontSize,
                         Color buttonColor,
                         Color textColor, int roundness) {

        this.x = xPos;
        this.y = yPos;
        this.width = width;
        this.height = height;
        this.radius = roundness;

        center = new Rectangle(x + radius, y,width - 2 * radius, height,buttonColor);

        left = new Rectangle(x, y + radius,radius, height - 2 * radius,buttonColor);

        right = new Rectangle(x + width - radius, y + radius,radius, height - 2 * radius,buttonColor);

        tl = new Circle(x, y, radius, buttonColor);
        tr = new Circle(x + width - 2 * radius, y, radius, buttonColor);
        bl = new Circle(x, y + height - 2 * radius, radius, buttonColor);
        br = new Circle(x + width - 2 * radius, y + height - 2 * radius, radius, buttonColor);

        label = new Text(0, 0, text, textColor);
        label.setFontSansSerif(true, fontSize);

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

    private void centerText() {
        double textWidth = label.getText().length() * label.getShapeHeight() * 0.6;
        double textHeight = label.getShapeHeight();

        double textX = x + (width - textWidth) / 2;
        double textY = y + (height + textHeight / 2) / 2;

        label.moveTo(textX, textY);
    }

    public boolean clicked() {
        return sprite.mouseClicked();
    }

    public void setText(String newText) {
        label.setText(newText);
        centerText();
    }

    public void setTextColor(Color color) {
        label.setFontColor(color);
    }

    public void setButtonColor(Color color) {
        center.setColor(color);
        left.setColor(color);
        right.setColor(color);
        tl.setColor(color);
        tr.setColor(color);
        bl.setColor(color);
        br.setColor(color);
    }

    public void moveTo(double newX, double newY) {
        sprite.move(newX - x, newY - y);
        x = newX;
        y = newY;
    }

    public void setHidden(boolean hidden) {
        sprite.setHidden(hidden);
    }
}
