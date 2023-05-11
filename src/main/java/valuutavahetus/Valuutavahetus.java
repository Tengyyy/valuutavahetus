package valuutavahetus;

import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Valuutavahetus {

    private final List<Valuuta> valuutad = new ArrayList<>();

    public Valuutavahetus() throws IOException, URISyntaxException {
        List<String> read = Files.readAllLines(Path.of(Main.class.getResource("valuutad.txt").toURI()));
        for(String rida : read){

            String[] argumendid = rida.split(";");

            String nimi = argumendid[0];
            String lyhend = argumendid[1];
            String symbol = argumendid[2];
            double kurssEuroSuhtes = Double.parseDouble(argumendid[3]);
            boolean onMadalVahetustasu = Boolean.parseBoolean(argumendid[4]);

            valuutad.add(new Valuuta(nimi, lyhend, symbol, kurssEuroSuhtes, onMadalVahetustasu));
        }
    }

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
