package valuutavahetus;

import java.util.Random;

public class Valuuta {

    Random random = new Random();

    private String nimi;
    private String lyhend;
    private String symbol;
    private double kurssEuroSuhtes;
    private double vahetustasu;

    boolean onMadalTeenustasu;

    public Valuuta(String nimi, String lyhend, String symbol, double kurssEuroSuhtes, boolean onMadalTeenustasu){
        this.nimi = nimi;
        this.lyhend = lyhend;
        this.symbol = symbol;
        this.kurssEuroSuhtes = kurssEuroSuhtes;
        this.onMadalTeenustasu = onMadalTeenustasu;
        if(onMadalTeenustasu) vahetustasu = random.nextDouble(0.005,0.03);
        else vahetustasu = random.nextDouble(0.03, 0.06);
    }

    public String getNimi() {
        return nimi;
    }

    public String getLyhend(){
        return lyhend;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getKurssEuroSuhtes() {
        return kurssEuroSuhtes;
    }

    public double getVahetustasu(){
        return vahetustasu;
    }

    public boolean onMadalTeenustasu(){
        return this.onMadalTeenustasu;
    }

    public String toString(){
        return this.nimi + "; Sümbol: " + this.symbol + "; Lühend: " + this.lyhend + "; Kurss euro suhtes: " + this.kurssEuroSuhtes + "; Teenustasu: " + (onMadalTeenustasu ? "madal" : "kõrge");
    }

}
