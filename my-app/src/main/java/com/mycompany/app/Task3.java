package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;

public class Task3 {
    public static void getWeatherForecast() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Knight\\Desktop\\chromedriver-win64\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&timezone=Europe%2FMoscow&forecast_days=1";
            webDriver.get(url);
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject hourly = (JSONObject) obj.get("hourly");
            JSONArray time = (JSONArray) hourly.get("time");
            JSONArray temperature = (JSONArray) hourly.get("temperature_2m");
            JSONArray rain = (JSONArray) hourly.get("rain");

            FileWriter writer = new FileWriter("result/forecast.txt");
            writer.write("№\tДата/время\t\tТемпература (°C)\tОсадки (мм)\n");
            System.out.println("№\tДата/время\t\tТемпература (°C)\tОсадки (мм)");
            for (int i = 0; i < time.size(); i++) {
                String row = String.format("%d\t%s\t%.1f\t\t%.2f\n", (i + 1), time.get(i), ((Number) temperature.get(i)).doubleValue(), ((Number) rain.get(i)).doubleValue());
                System.out.print(row);
                writer.write(row);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error in Task3");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
}