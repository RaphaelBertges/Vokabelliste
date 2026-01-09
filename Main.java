<<<<<<< HEAD
=======
import sas.*;
import sasio.*;
>>>>>>> c7745c7ee7bb4e705e8d543343cd83317eb7a859
import java.awt.Color;
public class Main
{
<<<<<<< HEAD
    
=======
    List list;
    View fenster;
    
    Color l_b1, l_m1, l_a1;
    Color d_b1, d_m1, d_a1;
    Rectangle r;
    RoundedRectangle rr;
    Sprite sT;
>>>>>>> c7745c7ee7bb4e705e8d543343cd83317eb7a859
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
        rr = new RoundedRectangle(600,400,200,100,Color.BLUE);
    }
    
    public void sasPlusTest()
    {
<<<<<<< HEAD
        
=======
        moveFromTo(150,100,483,532,345,sT,fenster,4);
        
        rotateTo(54,459,rr.sprite,fenster,2);
        
        moveRotateBy(234,458,34,543,sT,fenster,8);
    }
    private double calcProgress(int mode, int step, int steps) {
        double t = (double) step / steps;

        switch (mode) {
            case 1: return t;                             // linear
            case 2: return 0.5 - 0.5 * Math.cos(Math.PI * t); // ease in/out
            case 3: return t * t;                         // ease in
            case 4: return 1 - (1 - t) * (1 - t);         // ease out
            default: return t;
        }
    }
    private double[] calcCircle(double cx, double cy,
                            double radius,
                            double angleStart,
                            double angleEnd,
                            int step, int steps,
                            boolean invert) {

        double t = (double) step / steps;
        double angle = angleStart + (angleEnd - angleStart) * t;

        double x = cx + Math.cos(angle) * radius;
        double y = cy + Math.sin(angle) * radius;

            if (invert) y = cy - (y - cy);

        return new double[]{x, y};
    }
        public void moveFromTo(double xS, double yS, double xE, double yE,
                           int steps, Sprite obj, View win, int mode) {
        obj.moveTo(xS, yS);
    
        for (int i = 1; i <= steps; i++) {
            double p = calcProgress(mode, i, steps);
            obj.moveTo(
                xS + (xE - xS) * p,
                yS + (yE - yS) * p
            );
            win.wait(1);
        }
    }
    
    public void moveTo(double xE, double yE,
                       int steps, Sprite obj, View win, int mode) {
        moveFromTo(obj.getShapeX(), obj.getShapeY(), xE, yE, steps, obj, win, mode);
    }
    
    public void moveFrom(double xS, double yS,
                         double dx, double dy,
                         int steps, Sprite obj, View win, int mode) {
        moveFromTo(xS, yS, xS + dx, yS + dy, steps, obj, win, mode);
    }
    
    public void moveBy(double dx, double dy,
                       int steps, Sprite obj, View win, int mode) {
        moveFrom(obj.getShapeX(), obj.getShapeY(), dx, dy, steps, obj, win, mode);
    }
        public void rotateFromTo(double aS, double aE,
                             int steps, Sprite obj, View win, int mode) {
        obj.turnTo(aS);
    
        for (int i = 1; i <= steps; i++) {
            double p = calcProgress(mode, i, steps);
            obj.turnTo(aS + (aE - aS) * p);
            win.wait(1);
        }
    }
    
    public void rotateTo(double aE,
                         int steps, Sprite obj, View win, int mode) {
        rotateFromTo(obj.getDirection(), aE, steps, obj, win, mode);
    }
    
    public void rotateFrom(double aS, double da,
                           int steps, Sprite obj, View win, int mode) {
        rotateFromTo(aS, aS + da, steps, obj, win, mode);
    }
    
    public void rotateBy(double da,
                         int steps, Sprite obj, View win, int mode) {
        rotateFrom(obj.getDirection(), da, steps, obj, win, mode);
    }
        public void moveRotateFromTo(double xS, double yS, double xE, double yE,
                                 double aS, double aE,
                                 int steps, Sprite obj, View win, int mode) {
    
        obj.moveTo(xS, yS);
        obj.turnTo(aS);
    
        boolean circle = (mode >= 5);
        boolean invert = (mode == 6 || mode == 8);
        boolean easeRot = (mode == 7 || mode == 8);
    
        double cx = (xS + xE) / 2;
        double cy = (yS + yE) / 2;
        double radius = Math.hypot(xE - xS, yE - yS) / 2;
    
        for (int i = 1; i <= steps; i++) {
    
            double pMove = calcProgress(circle ? 1 : mode, i, steps);
            double pRot  = calcProgress(easeRot ? 2 : 1, i, steps);
    
            if (circle) {
                double[] pos = calcCircle(cx, cy, radius, 0, Math.PI, i, steps, invert);
                obj.moveTo(pos[0], pos[1]);
            } else {
                obj.moveTo(
                    xS + (xE - xS) * pMove,
                    yS + (yE - yS) * pMove
                );
            }
    
            obj.turnTo(aS + (aE - aS) * pRot);
            win.wait(1);
        }
    }
    
    public void moveRotateTo(double xE, double yE, double aE,
                             int steps, Sprite obj, View win, int mode) {
        moveRotateFromTo(
            obj.getShapeX(), obj.getShapeY(), xE, yE,
            obj.getDirection(), aE,
            steps, obj, win, mode
        );
    }
    
    public void moveRotateFrom(double xS, double yS,
                               double dx, double dy,
                               double aS, double da,
                               int steps, Sprite obj, View win, int mode) {
        moveRotateFromTo(
            xS, yS, xS + dx, yS + dy,
            aS, aS + da,
            steps, obj, win, mode
        );
    }
    
    public void moveRotateBy(double dx, double dy, double da,
                             int steps, Sprite obj, View win, int mode) {
        moveRotateFrom(
            obj.getShapeX(), obj.getShapeY(),
            dx, dy,
            obj.getDirection(), da,
            steps, obj, win, mode
        );
>>>>>>> c7745c7ee7bb4e705e8d543343cd83317eb7a859
    }

}
