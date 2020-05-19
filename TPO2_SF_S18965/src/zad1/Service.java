package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.XML;

public class Service {

    private String country;
    private String city;
    OpenWeatherMap openWeatherMap;
    String[] locale = Locale.getISOCountries();
    Map<String, String> currencies = new HashMap<>();
    String countryCode="";

    public Service(String country) {
        this.country = country;
        Locale.setDefault(Locale.ENGLISH);
        for (String string : locale) {
            Locale locale = new Locale("", string);
            currencies.put(locale.getDisplayCountry(), string);
            if(locale.getDisplayCountry().equals(country)){
            countryCode=locale.getCountry();
            }
        }
        this.openWeatherMap = new OpenWeatherMap("558702bfbb6d1964e1d6f2faa78234ae");
        openWeatherMap.setUnits(OpenWeatherMap.Units.METRIC);
    }

    public String getWeather(String city) {
        this.city=city;
        String weatherJSON = "";
        try {
            CurrentWeather currentWeather = openWeatherMap.currentWeatherByCityName(city,countryCode);
            if(currentWeather.hasBaseStation()){
                weatherJSON=currentWeather.getRawResponse();
            }
        }catch(IOException | JSONException io){
            io.printStackTrace();
        }
        return weatherJSON;
    }

    public Double getRateFor(String currency) {
        String string = "https://api.exchangeratesapi.io/latest?base="+Currency.getInstance(new Locale("", currencies.get(country)))+"&symbols="
                + currency;
        String json=toJSON(string);
        double rate=1.0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            rate=jsonObject.getJSONObject("rates").getDouble(currency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return rate;
    }

    private String toJSON(String string) {
        String json = "";
        try {
            URL url = new URL(string);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null)
                    json += line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public Double getNBPRate() {
        double kurs=1.0;
        List<String> link = Arrays.asList("http://www.nbp.pl/kursy/xml/a061z200327.xml", "http://www.nbp.pl/kursy/xml/b012z200325.xml");
        for(int i=0; i<link.size();i++){
            String strona=toJSON(link.get(i));
            JSONObject xmlJSONObj;
            try {
                xmlJSONObj = XML.toJSONObject(strona);
                JSONArray jsonArray = xmlJSONObj.getJSONObject("tabela_kursow").getJSONArray("pozycja");
                String currencyCode =Currency.getInstance(new Locale("", currencies.get(country))).toString();
                for(int ij=0; ij<jsonArray.length();ij++){
                    if(jsonArray.getJSONObject(ij).getString("kod_waluty").equals(currencyCode)){
                        kurs=Double.parseDouble(jsonArray.getJSONObject(ij).getString("kurs_sredni").replace(",","."));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return kurs;
    }

    public String getWiki(String city){
        if (city != null) {
            return "https://en.wikipedia.org/wiki/" + city;
        }
        return "";
    }
}