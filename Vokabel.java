
/**
 *@ Raphael und David 
 *07.01.2026
 */
public class Vokabel
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String deutsch,fremd;
    
    public Vokabel(String pD, String pF)
    {
        deutsch = pD;
        fremd = pF;
    
    }
    public String getDeutsch(){
        return deutsch;
    }
    public String getFremd(){
        return fremd;
    }
    public boolean checkDeutsch(String pText){
        return pText.equals(deutsch);
        
    }
      public boolean checkFremd(String pText){
        return pText.equals(fremd);
        
    }
}
