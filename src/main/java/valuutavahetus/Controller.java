package valuutavahetus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private VBox container, inputContainer, outputContainer;

    @FXML
    StackPane currencyContainer, confirmationButtonContainer;

    @FXML
    ComboBox<String> inputCurrencyPicker, outputCurrencyPicker;

    @FXML
    TextField inputCurrencyField, outputCurrencyField;

    @FXML
    HBox exchangeFeeContainer;

    @FXML
    Label confirmationLabel, exchangeFeeLabel, amountLabel, minimumLabel;

    @FXML
    Button confirmationButton;

    Valuutavahetus valuutavahetus;

    int miinimumkogus = Integer.MAX_VALUE;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {
            valuutavahetus = new Valuutavahetus();
        } catch (IOException | URISyntaxException e) {
            System.out.println("Valuuta infot ei õnnestunud lugeda");
        }

        inputCurrencyPicker.getItems().add("Olemasolev valuuta");
        inputCurrencyPicker.getSelectionModel().select("Olemasolev valuuta");

        outputCurrencyPicker.getItems().add("Soovitav valuuta");
        outputCurrencyPicker.getSelectionModel().select("Soovitav valuuta");

        for(Valuuta valuuta : valuutavahetus.getValuutad()){
            inputCurrencyPicker.getItems().add(valuuta.getNimi());
            outputCurrencyPicker.getItems().add(valuuta.getNimi());
        }


        inputContainer.prefWidthProperty().bind(container.widthProperty().multiply(0.5));
        inputContainer.maxWidthProperty().bind(container.widthProperty().multiply(0.5));

        outputContainer.prefWidthProperty().bind(container.widthProperty().multiply(0.5));
        outputContainer.maxWidthProperty().bind(container.widthProperty().multiply(0.5));

        minimumLabel.setVisible(false);
        confirmationLabel.setVisible(false);
        exchangeFeeContainer.setVisible(false);


        inputCurrencyPicker.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {

            if(t1.intValue() == 0){
                minimumLabel.setVisible(false);
                miinimumkogus = Integer.MAX_VALUE;
                return;
            }

            Valuuta sisendValuuta = null;
            String valuutaNimi = inputCurrencyPicker.getSelectionModel().getSelectedItem();

            for(Valuuta valuuta : valuutavahetus.getValuutad()){
                if(valuuta.getNimi().equals(valuutaNimi)){
                    sisendValuuta = valuuta;
                    break;
                }
            }

            assert sisendValuuta != null;
            miinimumkogus = (int) valuutavahetus.leiaMiinimumkogus(sisendValuuta);

            minimumLabel.setText("Min. kogus " + miinimumkogus + " " + sisendValuuta.getLyhend());
            minimumLabel.setVisible(true);

            uuendaVaadet();
        });

        outputCurrencyPicker.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            uuendaVaadet();
        });

        inputCurrencyField.textProperty().addListener((observableValue, s, t1) -> {
            uuendaVaadet();
        });
        inputCurrencyField.setPromptText("Sisesta kogus");

        outputCurrencyField.setEditable(false);
        outputCurrencyField.setPromptText("Tagastatav kogus");



        confirmationButton.setDisable(true);
        confirmationButton.setOnAction(e -> {
            confirmationLabel.setVisible(true);
            exchangeFeeContainer.setVisible(true);
        });
    }


    private void uuendaVaadet(){
        confirmationLabel.setVisible(false);
        exchangeFeeContainer.setVisible(false);

        if(inputCurrencyPicker.getSelectionModel().getSelectedIndex() == 0
            || outputCurrencyPicker.getSelectionModel().getSelectedIndex() == 0){
            outputCurrencyField.setText("");
            confirmationButton.setDisable(true);
            return;
        }

        String inputString = inputCurrencyField.getText();

        try {
            double olemasolevKogus = Double.parseDouble(inputString);

            Valuuta olemasolevValuuta = null;
            Valuuta soovitavValuuta = null;

            String olemasolevNimi = inputCurrencyPicker.getSelectionModel().getSelectedItem();
            String soovitavNimi = outputCurrencyPicker.getSelectionModel().getSelectedItem();

            for(Valuuta valuuta : valuutavahetus.getValuutad()){
                if(valuuta.getNimi().equals(olemasolevNimi)) olemasolevValuuta = valuuta;

                if(valuuta.getNimi().equals(soovitavNimi)) soovitavValuuta = valuuta;
            }

            assert olemasolevValuuta != null;
            assert soovitavValuuta != null;
            Pair<Double, Double> tehing = valuutavahetus.arvuta(olemasolevValuuta, olemasolevKogus, soovitavValuuta);

            double saadavKogus = tehing.getKey();
            double teenustasu = tehing.getValue();

            outputCurrencyField.setText(String.valueOf(String.format("%.2f", saadavKogus)));
            amountLabel.setText(String.format("%.2f", teenustasu) + " " + olemasolevValuuta.getLyhend());

            if(olemasolevKogus < miinimumkogus){
                confirmationButton.setDisable(true);
                return;
            }

            confirmationButton.setDisable(false);
        }
        catch(NumberFormatException e){
            outputCurrencyField.setText("");
            confirmationButton.setDisable(true);
        }
    }
}