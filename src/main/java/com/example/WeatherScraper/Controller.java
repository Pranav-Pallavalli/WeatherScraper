package com.example.WeatherScraper;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import net.minidev.json.parser.JSONParser;
import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(value = "/weatherScraper", method = RequestMethod.GET)
    public String webpageGet(ModelMap modelMap){
        return "Mainpage";
    }
    @RequestMapping(value = "/weatherScraper",method = RequestMethod.POST)
    public String apireq(@RequestParam String place, ModelMap modelMap) throws IOException, ParseException {
        if(place.isEmpty()){
            modelMap.put("error","Please enter a city name");
            modelMap.put("visibility1","true");
            modelMap.put("visibility2","false");
            return "MainPage";
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String uri = "https://api.openweathermap.org/data/2.5/weather?q="+ URLEncoder.encode(place)+"&appid=1e186d7f65344bad04966e4b5b1ad93c";
        URL url = new URL(uri);
        HttpGet httpGet = new HttpGet(String.valueOf(url));
        HttpResponse httpResponse = httpClient.execute(httpGet);
        if(httpResponse.getStatusLine().getStatusCode()==404){
            modelMap.put("error","Please enter a valid City");
            modelMap.put("visibility1","true");
            modelMap.put("visibility2","false");
            return "Mainpage";
        }
        HttpEntity entity = httpResponse.getEntity();
        String string = "";
        Scanner scanner = new Scanner(entity.getContent());
        while(scanner.hasNext()){
            string += scanner.nextLine();
        }
        scanner.close();
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(string);
        JSONArray weather = (JSONArray) object.get("weather");
        JSONObject a = (JSONObject) weather.get(0);
        String desc = a.getAsString("description");
        JSONObject main = (JSONObject) object.get("main");
        String temp = main.getAsString("temp");
        String maxtemp = main.getAsString("temp_max");
        String mintemp = main.getAsString("temp_min");
        String pressure = main.getAsString("pressure");
        String humidity = main.getAsString("humidity");
        modelMap.put("desc",desc);
        modelMap.put("temp",temp);
        modelMap.put("maxtemp",maxtemp);
        modelMap.put("mintemp",mintemp);
        modelMap.put("pressure",pressure);
        modelMap.put("humidity",humidity);
        modelMap.put("visibility1","false");
        modelMap.put("visibility2","true");
        return "Mainpage";
    }
}
