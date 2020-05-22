package zad1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class Main extends Application {

    Scene scene;
    static Service service;
    static String weatherInput;
    static String city="";
    static String country="";
    static String currency="";
    Button getWeather, getRatefor, getNBPRate, getWiki;
    TextArea textInput, textOutput;
    HBox hbox;
    Tab wikiTab,restTab;
    TabPane tabPane;
    BorderPane buttonsPane, centralPane,wikiPane;
    WebView webView;
    WebEngine webEngine;

    public static void main(String[] args) {
        city="Rome";
        country="Italy";
        currency="USD";
        Service s = new Service("Italy");
        String weatherJson = s.getWeather("Rome");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();
        weatherInput=weatherJson;
        weatherFormat(weatherJson);
        service = s;
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        prepareScene(stage);
        stage.setScene(scene);
        stage.show();
    }

    private void prepareScene(Stage stage) {
        stage.setTitle("TPO2_SF_S18965");
        stage.setScene(scene);
        hbox= new HBox();

        buttonsPane = new BorderPane();
        centralPane = new BorderPane();
        wikiPane= new BorderPane();

        textOutput = new TextArea();
        textInput = new TextArea();
        textOutput.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");
        textOutput.appendText("1. W celu pobrania pogody dla danego miasta w kraju podanym w Mainie wpisz nazwę miasta po angielsku (np. Rome) i wcisnij przycisk\n" +
                "2. W celu pobrania kursu wymiany waluty kraju podanego w Mainie wobec podanej przez uzytkownika waluty wpisz kod Waluty (np. USD) i wcisnij przycisk\n" +
                "3. W celu pobrania kursu NBP złotego wobec waluty kraju podanego w Mainie wystarczy wcisnac przycisk\n" +
                "4. W celu pobrania strony wiki z opisem miasta nalezy wpisac nazwe miasta i wcisnij przycisk, po czym przejsc do drugiej zakladki");
        textOutput.setDisable(true);

        centralPane.setCenter(textOutput);
        centralPane.setTop(textInput);

        centralPane.setBottom(buttonsPane);
        tabPane = new TabPane();

        wikiTab = new Tab("Wiki page");
        restTab = new Tab("Main");

        tabPane.getTabs().addAll(restTab,wikiTab);
        restTab.setContent(centralPane);

        getWeather = new Button("Get Weather");
        getRatefor = new Button("Get currency rate");
        getNBPRate = new Button("Get NBP Rate");
        getWiki = new Button("Get Wiki page");

        hbox.getChildren().addAll(getWeather,getRatefor,getNBPRate,getWiki);
        int btnCount = hbox.getChildren().size();

        getWeather.prefWidthProperty().bind(hbox.widthProperty().divide(btnCount));
        getRatefor.prefWidthProperty().bind(hbox.widthProperty().divide(btnCount));
        getNBPRate.prefWidthProperty().bind(hbox.widthProperty().divide(btnCount));
        getWiki.prefWidthProperty().bind(hbox.widthProperty().divide(btnCount));
        buttonsPane.setCenter(hbox);

        getWeather.setOnAction( event ->{
            if(textInput.getText().length()>0){
                city=textInput.getText();
            }
            textOutput.clear();
            String newWeatherJson =service.getWeather(city);
            textOutput.appendText(weatherFormat(newWeatherJson));
            textInput.clear();
                });
        getNBPRate.setOnAction( event ->{
            textOutput.clear();
            textOutput.appendText(service.getNBPRate().toString());
            textInput.clear();
        });
        getRatefor.setOnAction( event ->{
            if(textInput.getText().length()>0){
                currency=textInput.getText();
            }
            textOutput.clear();
            textOutput.appendText(service.getRateFor(currency).toString());
            textInput.clear();
        });
        getWiki.setOnAction( event ->{
            if(textInput.getText().length()>0){
                city=textInput.getText();
            }
            textOutput.clear();
            service.getWiki(city);
            webEngine.load(service.getWiki(city));
            textInput.clear();
        });

        webView = new WebView();
        webEngine = webView.getEngine();

        wikiPane.setCenter(webView);
        wikiTab.setContent(wikiPane);

        scene = new Scene(tabPane, 1000, 700);
    }

    public static String weatherFormat(String weatherJson){
        String dane="";
        try {
            JSONObject jsonObject = new JSONObject(weatherJson);
            dane += "City: " + city + "\n";
            dane += "Temperature: " + jsonObject.getJSONObject("main").get("temp") + "\n";
            dane += "Minimal Temperature: " + jsonObject.getJSONObject("main").get("temp_min") + "\n";
            dane += "Maximal Temperature: " + jsonObject.getJSONObject("main").get("temp_max") + "\n";
            dane += "Humidity: " + jsonObject.getJSONObject("main").get("humidity") + "\n";
            dane += "Pressure: " + jsonObject.getJSONObject("main").get("pressure")+ "\n";
            dane += "Feels like: " + jsonObject.getJSONObject("main").get("feels_like")+ "\n";
            dane += "Sky: " + jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dane;
    }

}