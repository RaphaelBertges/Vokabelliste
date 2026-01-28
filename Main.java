import sas.*;
import sasio.*;
import java.awt.Color;
public class Main
{
    List<Vokabel> list;
    View fenster;
    smoothMove sMove;
    
    Color l_b1, l_m1, l_a1;
    
    
    RoundedRectangle vokabelBackground, originalSprache, correction;
    RoundedButton btnVokabelCheck, btnNextVokabel;
    Text txWort, txCorrection;
    RoundedTextfield tfVokabel;
    Sprite vokabelGesamt;
    String textFremd, textDeutsch;
    boolean vokabelIsCorrect;
    boolean nachDeutsch;
    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main(){
        fenster = new View(1280,720,"Vokabeltrainer");
        l_b1 = new Color(238,238,238);
        l_m1 = new Color (245,245,247);
        l_a1 = new Color (0,136,204);
        
        list = new List<Vokabel>();
        list.toFirst();
        
        Vokabel vokabel = new Vokabel("Hallo", "Hola");
        list.append(vokabel);
        
        vokabel = new Vokabel("Danke", "Gracias");
        list.append(vokabel);
        
        vokabel = new Vokabel("Informatik", "Informática");
        list.append(vokabel);
        
        vokabel = new Vokabel("Bier", "Cerveza");
        list.append(vokabel);
        
        vokabel = new Vokabel("Traum", "Sueño");
        list.append(vokabel);
        
        nachDeutsch = true;
        list.toFirst();
        Vokabel v = list.getContent();
        textDeutsch = v.getDeutsch();
        textFremd = v.getFremd();
        
        fenster.setBackgroundColor(l_b1);
        sMove = new smoothMove();
        
        fenster.wait(100);
        
        vokabelIntroAnimation();
        

    }
    static void main() {
        Main app = new Main();
        boolean next = false;
        boolean enterPressed;
        while (true) {
            enterPressed = app.fenster.keyEnterPressed();

            if (app.tfVokabel.clicked()) {
                app.tfVokabel.setActivated(true);
            }
            if (app.tfVokabel.getActivated() && app.fenster.keyPressed()) {
                char c = app.fenster.keyGetChar();
                if (c == '\n') {
                    app.tfVokabel.setActivated(false);
                } else {
                    app.tfVokabel.textInput(c);
                }
            }
            if (app.btnVokabelCheck.clicked() || (enterPressed && !next)) {
                enterPressed = false;   
                app.vokabelIsCorrect = app.checkVokabel();
                if (app.vokabelIsCorrect) {
                    app.tfVokabel.setNewColor(Color.GREEN);
                } 
                else {
                    app.tfVokabel.setNewColor(Color.RED);
                    app.addTextCorrection();
                    Vokabel v = app.list.getContent();
                    app.list.remove();
                    app.list.append(v);
                }
                app.fenster.remove(app.btnVokabelCheck.sprite);
                app.btnNextVokabel.moveTo(450,500);
                next = true;
            }
            if (app.btnNextVokabel.clicked() || enterPressed && next) {
                enterPressed = false;
                next = false;
                app.vokabelExitAnimation();
            }
            app.fenster.keyBufferDelete();
            app.fenster.wait(1);
        }
    }
    private void addTextCorrection()
    {
        correction = new RoundedRectangle(450,300,378,64,Color.RED,25);
        if(nachDeutsch){
            txCorrection = new Text(468,312,textDeutsch);
        }
        else{
            txCorrection = new Text(468,312,textFremd);
        }
        txCorrection.setFontSansSerif(true,32);
        txCorrection.move((350 - txWort.getShapeWidth() ) / 2);
    }
    private void loadVokabelAnimation()
    {
        vokabelBackground = new RoundedRectangle(1426,100,426,520,l_m1,25);
        originalSprache = new RoundedRectangle(1450,124,378,64,l_b1,25);
        if(nachDeutsch){
            txWort = new Text(1468,132,textFremd);
        }
        else{
            txWort = new Text(1468,132,textDeutsch);
        } 
        txWort.setFontSansSerif(true,32);
        txWort.move((350 - txWort.getShapeWidth() ) / 2);
        tfVokabel = new RoundedTextfield(1450,200,378,64,32,"Übersetzung",l_a1,Color.WHITE,25);
        btnVokabelCheck = new RoundedButton(1450,500,378,64,"Überprüfen",32,l_a1,Color.WHITE,25);
        
        vokabelGesamt = new Sprite();
        vokabelGesamt.add(vokabelBackground.sprite);
        vokabelGesamt.add(originalSprache.sprite);
        vokabelGesamt.add(txWort);
        vokabelGesamt.add(tfVokabel.sprite);
        vokabelGesamt.add(btnVokabelCheck.sprite);
    
    }
    private void composeVokabelAnimationEnd()
    {
        vokabelGesamt = new Sprite();
        vokabelGesamt.add(vokabelBackground.sprite);
        vokabelGesamt.add(originalSprache.sprite);
        vokabelGesamt.add(txWort);
        vokabelGesamt.add(tfVokabel.sprite);
        vokabelGesamt.add(btnNextVokabel.sprite);
        if(!vokabelIsCorrect)
        {
            vokabelGesamt.add(correction.sprite);
            vokabelGesamt.add(txCorrection);
        }
    }
    private void vokabelIntroAnimation()
    {
        loadVokabelAnimation();
        sMove.moveRotateFromTo(-440,160,426,100,80,90,220,vokabelGesamt,fenster,4);
        fenster.remove(vokabelGesamt);
        loadVokabel();
    }
    private void vokabelExitAnimation()
    {
        composeVokabelAnimationEnd();
        fenster.wait(60);
        sMove.moveRotateFromTo(426,100,1292,160,90,100,220,vokabelGesamt,fenster,4);
        fenster.remove(vokabelGesamt);
        
        if(!list.isEmpty())
        {
            if(vokabelIsCorrect){
                list.next();
            }
            if(list.hasAccess()){                
                Vokabel v = list.getContent();
                textDeutsch = v.getDeutsch();
                textFremd = v.getFremd();
            
                vokabelIntroAnimation();
            }
        }
        
    }
    private void loadVokabel()
    {
        vokabelBackground = new RoundedRectangle(426,100,426,520,l_m1,25);
        originalSprache = new RoundedRectangle(450,124,378,64,l_b1,25);
        if(nachDeutsch){
            txWort = new Text(468,132,textFremd);
        }
        else{
            txWort = new Text(468,132,textDeutsch);
        } 
        txWort.setFontSansSerif(true,32);
        txWort.move((350 - txWort.getShapeWidth() ) / 2);
        tfVokabel = new RoundedTextfield(450,200,378,64,32,"Übersetzung",l_a1,Color.WHITE,25);
        btnVokabelCheck = new RoundedButton(450,500,378,64,"Überprüfen",32,l_a1,Color.WHITE,25);
        btnNextVokabel = new RoundedButton(1450,500,378,64,"Weiter",32,l_a1,Color.WHITE,25);
        //btnVokabelCheck.setActivated(true);
    }
    private boolean checkVokabel()
    {
        String textInput = tfVokabel.getText();
        if(nachDeutsch)
        {
            if(textInput.equals(textDeutsch)){
               return true; 
            }
            else{
                return false;
            }
        }
        else{
            if(textInput.equals(textFremd)){
               return true; 
            }
            else{
                return false;
            }
        }
    }
    private void changeDirection(){
        if(nachDeutsch)
        {
            nachDeutsch = false;
        }
        else
        {
            nachDeutsch = true;
        }
    }
    private class smoothMove{
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
                win.wait(2);
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
                win.wait(2);
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
                win.wait(2);
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
        }
    }

}
