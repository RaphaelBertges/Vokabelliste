import sas.*;
import java.awt.Color;

public class RoundedRectangle {

    private Sprite sprite;

    private Rectangle center, left, right;
    private Circle tl, tr, bl, br;

    private double x, y, width, height, radius;

    public RoundedRectangle(double xPos, double yPos, double width, double height,Color rectangleColor) 
    {
        this.x = xPos;
        this.y = yPos;
        this.width = width;
        this.height = height;

        radius = height / 2.0;

        center = new Rectangle(x + radius, y,width - 2 * radius, height,rectangleColor);

        left = new Rectangle(x, y + radius,radius, height - 2 * radius,rectangleColor);

        right = new Rectangle(x + width - radius, y + radius,radius, height - 2 * radius,rectangleColor);

        tl = new Circle(x, y, radius, rectangleColor);
        tr = new Circle(x + width - 2 * radius, y, radius, rectangleColor);
        bl = new Circle(x, y + height - 2 * radius, radius, rectangleColor);
        br = new Circle(x + width - 2 * radius, y + height - 2 * radius, radius, rectangleColor);


        sprite = new Sprite();
        sprite.add(center);
        sprite.add(left);
        sprite.add(right);
        sprite.add(tl);
        sprite.add(tr);
        sprite.add(bl);
        sprite.add(br);

    }

    public void setRectangleColor(Color color) {
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
