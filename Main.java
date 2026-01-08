import sas.*;
import sasio.*;
import java.awt.Color;
public class Main
{
    List list;
    View fenster;
    
    Color l_b1, l_m1, l_a1;
    Color d_b1, d_m1, d_a1;
    Rectangle r;
    Sprite sT;
    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main(){
       fenster = new View(1920,1080,"Vokabeltrainer");
       l_b1 = new Color(238,238,238);
       l_m1 = new Color (245,245,247);
       l_a1 = new Color (0,136,204);
       d_b1 = new Color(29,29,31);
       d_m1 = new Color (245,245,247);
       d_a1 = new Color (0,102,204);
       
       fenster.setBackgroundColor(l_b1);
       
       r = new Rectangle(100,100,50,50, Color.RED);
       sT = new Sprite();
       sT.add(r);

    }
    public void testSmoothMove()
    {
        smoothMoveFromTo(100,100,400,300,500,sT,fenster,1);

        // Ease-in + Ease-out
        smoothMoveTo(100,100,500,sT,fenster,2);

        // Kreisartige Bewegung
        smoothMove(400,400,500,sT,fenster,5);
    }
    private double[] calcProgress(int type, int step, int totalSteps) {
        double t = (double) step / totalSteps; // normalisierte Zeit 0..1
        double x = 0, y = 0;

        switch(type) {
            case 1: // linear
                x = t;
                y = t;
                break;
            case 2: // ease-in + ease-out (sinusförmig)
                x = 0.5 - 0.5 * Math.cos(Math.PI * t);
                y = x;
                break;
            case 3: // quadratisch (ease-in)
                x = t * t;
                y = t * t;
                break;
            case 4: // quadratisch rückwärts (ease-out)
                x = 1 - (1 - t) * (1 - t);
                y = 1 - (1 - t) * (1 - t);
                break;
            case 5: // Kreis: x quadratisch, y quadratisch rückwärts
                x = t * t;
                y = 1 - (1 - t) * (1 - t);
                break;
            case 6: // Kreis 2: x quadratisch rückwärts, y quadratisch
                x = 1 - (1 - t) * (1 - t);
                y = t * t;
                break;
            default: // fallback linear
                x = t;
                y = t;
                break;
        }

        return new double[]{x, y};
    }

    // ===== smoothMoveFromTo =====
    public void smoothMoveFromTo(double xStart, double yStart,
                                 double xEnd, double yEnd,
                                 int steps,
                                 Sprite obj, View win,
                                 int type) {
        obj.moveTo(xStart, yStart);

        for (int i = 1; i <= steps; i++) {
            double[] prog = calcProgress(type, i, steps);
            double dx = xStart + (xEnd - xStart) * prog[0];
            double dy = yStart + (yEnd - yStart) * prog[1];
            obj.moveTo(dx, dy);
            win.wait(1); // sichtbare Animation
        }
    }

    // ===== smoothMoveTo =====
    public void smoothMoveTo(double xEnd, double yEnd,
                             int steps,
                             Sprite obj, View win,
                             int type) {
        double xStart = obj.getShapeX();
        double yStart = obj.getShapeY();
        smoothMoveFromTo(xStart, yStart, xEnd, yEnd, steps, obj, win, type);
    }

    // ===== smoothMoveFrom =====
    public void smoothMoveFrom(double xStart, double yStart,
                               double xChange, double yChange,
                               int steps,
                               Sprite obj, View win,
                               int type) {
        smoothMoveFromTo(xStart, yStart, xStart + xChange, yStart + yChange,
                         steps, obj, win, type);
    }

    // ===== smoothMove =====
    public void smoothMove(double xChange, double yChange,
                           int steps,
                           Sprite obj, View win,
                           int type) {
        double xStart = obj.getShapeX();
        double yStart = obj.getShapeY();
        smoothMoveFromTo(xStart, yStart, xStart + xChange, yStart + yChange,
                         steps, obj, win, type);
    }

}
