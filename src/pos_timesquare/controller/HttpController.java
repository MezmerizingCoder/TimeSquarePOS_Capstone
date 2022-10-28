/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.controller;

import com.orsoncharts.util.json.JSONArray;
import com.orsoncharts.util.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Acer
 */
public class HttpController {
    
    public static void sendHttpGETRequest(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_OK) { 
            // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

        for (int i = 1; i <= 8; i++) {
            System.out.println(httpURLConnection.getHeaderFieldKey(i) + " = " + httpURLConnection.getHeaderField(i));
        }

    }
    public static void postHttpRequest(String url) throws IOException{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
//        String jsonInputString = "{"name": "Upendra", "job": "Programmer"}";
        JSONObject jsonInputString = new JSONObject();
        jsonInputString.put("totalSales", 50000);
        jsonInputString.put("totalOrders", 23);
        jsonInputString.put("chartURL", doughnutChart());
        

//        jsonInputString.put("job", "Programmer");
        
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.toString().getBytes("utf-8");
            os.write(input, 0, input.length);			
        }
        
        try(BufferedReader br = new BufferedReader(
        new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        System.out.println(jsonInputString.toString());
        
    }
    
    public static String doughnutChart(){
        JSONObject json = new JSONObject();
//        json.put("type", "doughnut");
//        
        JSONObject data = new JSONObject();
        JSONArray labels = new JSONArray();
        labels.add("Week 1");
        labels.add("Week 2");
        
        data.put("labels", labels);
        
        JSONArray datasets = new JSONArray();
        JSONArray datasetsData = new JSONArray();
        datasetsData.add(23);
        datasetsData.add(90);
        
        JSONObject dataObject =  new JSONObject();
        dataObject.put("data", datasetsData);
        datasets.add(dataObject);


//        data.put("datasets", datasets);
        data.put("datasets", datasets);
        
        //
        String option = "{type:'doughnut',data:{labels:"+ labels.toString().replaceAll("\"","'") +",datasets:[{data:"+ datasetsData.toString() +"}]},options:{plugins:{doughnutlabel:{labels:[{text:'550',font:{size:20}},{text:'total'}]}}}}";
        
        System.out.println(option);
        System.out.println(data.toString());
        
        return option;
        
    }
}
