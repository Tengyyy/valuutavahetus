package valuutavahetus;

import javafx.util.Pair;

import java.util.*;

public class Valuutavahetus {

    private final List<Valuuta> valuutad = List.of(
            new Valuuta("Šveitsi frank", "CHF", "₣", 1.00301, true),
            new Valuuta("Austraalia dollar", "AUD", "$", 0.61973, true),
            new Valuuta("Brasiilia reaal", "BRL", "R$", 0.17702, false),
            new Valuuta("Bulgaaria leev", "BGN", "Лв", 0.51130, false),
            new Valuuta("Euro", "EUR", "€", 1.00000, true),
            new Valuuta("Filipiini peeso", "PHP", "₱", 0.01710, false),
            new Valuuta("Hiina jüaan", "CNY", "¥", 0.13503, false),
            new Valuuta("Hongkongi dollar", "HKD", "$", 0.11828, false),
            new Valuuta("Iisraeli seekel", "ILS", "₪", 0.25400, false),
            new Valuuta("India ruupia", "INR", "₹", 0.01123, false),
            new Valuuta("Indoneesia ruupia", "IDR", "Rp", 0.00006, false),
            new Valuuta("Islandi kroon", "ISK", "kr", 0.00666, true),
            new Valuuta("Jaapani jeen", "JPY", "¥", 0.00701, true),
            new Valuuta("Kanada dollar", "CAD", "$", 0.67907, true),
            new Valuuta("Korea vonn", "KRW", "₩", 0.00071, false),
            new Valuuta("Lõuna-Aafrika rand", "ZAR", "R", 0.05009, false),
            new Valuuta("Malaisia ringgit", "MYR", "RM", 0.20749, false),
            new Valuuta("Mehhiko peeso", "MXN", "$", 0.04964, false),
            new Valuuta("Norra kroon", "NOK", "kr", 0.08840, true),
            new Valuuta("Poola zlott", "PLN", "zł", 0.21278, true),
            new Valuuta("Rootsi kroon", "SEK", "kr", 0.09010, true),
            new Valuuta("Singapuri dollar", "SGD", "$", 0.69483, false),
            new Valuuta("Suurbritannia naelsterling", "GBP", "£", 1.13594, true),
            new Valuuta("Tšehhi kroon", "CZK", "Kč", 0.04194, true),
            new Valuuta("Taani kroon", "DKK", "kr", 0.13429, true),
            new Valuuta("Tai baat", "THB", "฿", 0.02708, false),
            new Valuuta("Türgi liir", "TRY", "₺", 0.04877, true),
            new Valuuta("USA dollar", "USD", "$", 0.92799, true),
            new Valuuta("Ungari forint", "HUF", "Ft", 0.00255, true),
            new Valuuta("Uus-Meremaa dollar", "NZD", "$", 0.57432, false),
            new Valuuta("Rumeenia leu", "RON", "L", 0.20315, false),
            new Valuuta("Lääne-Aafrika CFA frank", "XOF", "₣", 0.00152, false)
    );



    public Pair<Double, Double> arvuta(Valuuta olemasolevValuuta, double kogus, Valuuta soovitavValuuta) {

        double saadavRahaIlmaVahetustasuta = kogus * (olemasolevValuuta.getKurssEuroSuhtes()/soovitavValuuta.getKurssEuroSuhtes());
        double miinimumVahetusTasu = 0.5 / olemasolevValuuta.getKurssEuroSuhtes(); // 0.5 euro väärtuses sisendvaluutat
        double vahetusTasu = Math.max(miinimumVahetusTasu, Math.max(olemasolevValuuta.getVahetustasu(), soovitavValuuta.getVahetustasu()) * kogus);
        double saadavRaha = saadavRahaIlmaVahetustasuta - vahetusTasu;

        return new Pair<>(saadavRaha, vahetusTasu);
    }

    public double leiaMiinimumkogus(Valuuta olemasolevValuuta){
        return 20.0 / olemasolevValuuta.getKurssEuroSuhtes();
    }


    public List<Valuuta> getValuutad() {
        return valuutad;
    }

}
