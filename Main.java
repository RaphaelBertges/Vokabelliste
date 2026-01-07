import sas.*;
import sasio.*;
import java.awt.Color;
public class Main
{
    Vokabel vokabel;
    List list;
    View fenster;
    
    Color l_b1, l_m1, l_a1;
    Color d_b1, d_m1, d_a1;
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
    }
}
