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
    Sprite vokabelGesamt, vokabelAdder;
    String textFremd, textDeutsch;
    
    RoundedButton btnVokabelAbfrage, btnAbfrageRichtung, btnVokabelnEingeben;
    
    RoundedTextfield txNewVokFremd, txNewVokDeutsch;
    RoundedButton btnNewVokabel, btnBack;
    RoundedRectangle vokabelAddBackground;
    boolean vokabelIsCorrect,nachDeutsch;
    boolean inMenu, inAbfrage, inHinzufuegen;
    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main(){
        fenster = new View(1280,720,"Vokabeltrainer - Menü");
        l_b1 = new Color(238,238,238);
        l_m1 = new Color (245,245,247);
        l_a1 = new Color (0,136,204);
        
        fenster.setBackgroundColor(l_b1);
        sMove = new smoothMove();
        
        inMenu = true;
        inAbfrage = false;
        inHinzufuegen = false;
        
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
        
        loadMenu();
    }
    static void main()
    {
        Main app = new Main();
        app.runtime();
    }
    private void runtime() {
        boolean next = false;
        boolean enterPressed;
        while (true) {
            while(inMenu){
                if(btnVokabelAbfrage.clicked()){
                    fenster.remove(btnVokabelAbfrage.sprite);
                    fenster.remove(btnAbfrageRichtung.sprite);
                    fenster.remove(btnVokabelnEingeben.sprite);
                    inMenu = false;
                    inAbfrage = true;
                    fenster.setName("Vokabeltrainer - Abfrage");
                    list.toFirst();
                    vokabelIntroAnimation();
                }
                if(btnAbfrageRichtung.clicked()){
                    changeDirection();
                    if(nachDeutsch){
                        btnAbfrageRichtung.setText("Spanisch -> Deutsch");
                    }
                    else{
                        btnAbfrageRichtung.setText("Deutsch -> Spanisch");
                    }
                }
                if(btnVokabelnEingeben.clicked()){
                    fenster.remove(btnVokabelAbfrage.sprite);
                    fenster.remove(btnAbfrageRichtung.sprite);
                    fenster.remove(btnVokabelnEingeben.sprite);
                    inMenu = false;
                    inHinzufuegen = true;
                    fenster.setName("Vokabeltrainer - Hinzufügen");
                    vokabelAdderIntroAnimation();
                    btnBack = new RoundedButton(450,640,378,64,"Fertig",32,l_a1,Color.WHITE,25);
                }
                fenster.wait(1);
            }
            
            while(inAbfrage){
                enterPressed = fenster.keyEnterPressed();
    
                if (tfVokabel.clicked()) {
                    tfVokabel.setActivated(true);
                }
                if (tfVokabel.getActivated() && fenster.keyPressed()) {
                    char c = fenster.keyGetChar();
                    if (c == '\n') {
                        tfVokabel.setActivated(false);
                    } else {
                        tfVokabel.textInput(c);
                    }
                }
                if (btnVokabelCheck.clicked() || (enterPressed && !next)) {
                    enterPressed = false;   
                    vokabelIsCorrect = checkVokabel();
                    if (vokabelIsCorrect) {
                        tfVokabel.setNewColor(Color.GREEN);
                    } 
                    else {
                        tfVokabel.setNewColor(Color.RED);
                        addTextCorrection();
                        Vokabel v = list.getContent();
                        list.remove();
                        list.append(v);
                    }
                    fenster.remove(btnVokabelCheck.sprite);
                    btnNextVokabel.moveTo(450,500);
                    next = true;
                }
                if (btnNextVokabel.clicked() || enterPressed && next) {
                    enterPressed = false;
                    next = false;
                    vokabelExitAnimation();
                }
                fenster.keyBufferDelete();
                fenster.wait(1);
            }
            
            while(inHinzufuegen){
                enterPressed = fenster.keyEnterPressed();
    
                if (txNewVokFremd.clicked()) {
                    txNewVokFremd.setActivated(true);
                    txNewVokDeutsch.setActivated(false);
                }
                if (txNewVokFremd.getActivated() && fenster.keyPressed()) {
                    char c = fenster.keyGetChar();
                    if (c == '\n') {
                        txNewVokFremd.setActivated(false);
                    } else {
                        txNewVokFremd.textInput(c);
                    }
                }
                if (txNewVokDeutsch.clicked()) {
                    txNewVokDeutsch.setActivated(true);
                    txNewVokFremd.setActivated(false);
                }
                if (txNewVokDeutsch.getActivated() && fenster.keyPressed()) {
                    char c = fenster.keyGetChar();
                    if (c == '\n') {
                        txNewVokDeutsch.setActivated(false);
                    } else {
                        txNewVokDeutsch.textInput(c);
                    }
                }                
                if (btnNewVokabel.clicked() || enterPressed ) {
                    enterPressed = false;
                    
                    Vokabel vokabel = new Vokabel(txNewVokDeutsch.getText(), txNewVokFremd.getText());
                    list.append(vokabel);
                    
                    vokabelAdderExitAnimation();
                }
                if (btnBack.clicked()) {
                    inMenu = true;
                    inHinzufuegen = false;
                    fenster.remove(txNewVokFremd.sprite);
                    fenster.remove(txNewVokDeutsch.sprite);
                    fenster.remove(btnNewVokabel.sprite);
                    fenster.remove(btnBack.sprite);
                    fenster.remove(vokabelAddBackground.sprite);
                    loadMenu();
                    fenster.setName("Vokabeltrainer - Menü");
                }
                fenster.keyBufferDelete();
                fenster.wait(1);
            }
        }
    }
    
    
    private void loadVokabel()
    {
        vokabelBackground = new RoundedRectangle(426,100,426,520,l_m1,25);
        originalSprache = new RoundedRectangle(450,124,378,64,l_b1,25);
        if(nachDeutsch){
            txWort = new Text(468,138,textFremd);
        }
        else{
            txWort = new Text(468,138,textDeutsch);
        } 
        txWort.setFontSansSerif(true,32);
        txWort.move((350 - txWort.getShapeWidth() ) / 2);
        tfVokabel = new RoundedTextfield(450,200,378,64,32,"Übersetzung",l_a1,Color.WHITE,25);
        btnVokabelCheck = new RoundedButton(450,500,378,64,"Überprüfen",32,l_a1,Color.WHITE,25);
        btnNextVokabel = new RoundedButton(1450,500,378,64,"Weiter",32,l_a1,Color.WHITE,25);
        //btnVokabelCheck.setActivated(true);
    }
    private void loadVokabelAnimation()
    {
        vokabelBackground = new RoundedRectangle(1426,100,426,520,l_m1,25);
        originalSprache = new RoundedRectangle(1450,124,378,64,l_b1,25);
        if(nachDeutsch){
            txWort = new Text(1468,138,textFremd);
        }
        else{
            txWort = new Text(1468,138,textDeutsch);
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
            else{
                inMenu = true;
                inAbfrage = false;
                loadMenu();
                fenster.setName("Vokabeltrainer - Menü");
            }
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
    
    private void loadMenu(){
         btnVokabelAbfrage = new RoundedButton(450,200,378,64,"Vokabel Abfrage",32,l_a1,Color.WHITE,25);
         btnAbfrageRichtung = new RoundedButton(450,300,378,64,"Spanisch -> Deutsch",32,l_a1,Color.WHITE,25);
         btnVokabelnEingeben = new RoundedButton(450,400,378,64,"Vokabeln Hinzufügen",32,l_a1,Color.WHITE,25);   
    }
    
    private void loadAddVokabel(){
        vokabelAddBackground = new RoundedRectangle(426,100,426,520,l_m1,25);
        txNewVokFremd = new RoundedTextfield(450,124,378,64,32,"Fremdwort",l_b1,Color.BLACK,25);
        txNewVokDeutsch = new RoundedTextfield(450,200,378,64,32,"Übersetzung",l_a1,Color.WHITE,25);
        btnNewVokabel = new RoundedButton(450,500,378,64,"Nächste Vokabel",32,l_a1,Color.WHITE,25);
    }
    private void loadAddVokabelAnimation(){
        vokabelAddBackground = new RoundedRectangle(1426,100,426,520,l_m1,25);
        txNewVokFremd = new RoundedTextfield(1450,124,378,64,32,"Fremdwort",l_b1,Color.BLACK,25);
        txNewVokDeutsch = new RoundedTextfield(1450,200,378,64,32,"Übersetzung",l_a1,Color.WHITE,25);
        btnNewVokabel = new RoundedButton(1450,500,378,64,"Nächste Vokabel",32,l_a1,Color.WHITE,25);
        
        vokabelAdder = new Sprite();
        vokabelAdder.add(vokabelAddBackground.sprite);
        vokabelAdder.add(txNewVokFremd.sprite);
        vokabelAdder.add(txNewVokDeutsch.sprite);
        vokabelAdder.add(btnNewVokabel.sprite);
    }
    private void vokabelAdderIntroAnimation()
    {
        loadAddVokabelAnimation();
        sMove.moveRotateFromTo(-440,160,426,100,80,90,220,vokabelAdder,fenster,4);
        fenster.remove(vokabelAdder);
        loadAddVokabel();
    }
    private void composeVokabelAdderAnimationEnd()
    {
        vokabelAdder = new Sprite();
        vokabelAdder.add(vokabelAddBackground.sprite);
        vokabelAdder.add(txNewVokFremd.sprite);
        vokabelAdder.add(txNewVokDeutsch.sprite);
        vokabelAdder.add(btnNewVokabel.sprite);
    }
    private void vokabelAdderExitAnimation()
    {
        composeVokabelAdderAnimationEnd();
        fenster.wait(60);
        sMove.moveRotateFromTo(426,100,1292,160,90,100,220,vokabelAdder,fenster,4);
        fenster.remove(vokabelAdder);
        
        vokabelAdderIntroAnimation(); 
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