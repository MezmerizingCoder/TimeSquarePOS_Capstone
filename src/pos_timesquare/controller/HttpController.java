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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import pos_timesquare.model.Category;
import pos_timesquare.model.ServiceTickets;
import pos_timesquare.model.StoreInfo;
import pos_timesquare.model.TransactionHistory;
import pos_timesquare.view.CategoryThumb;
import static pos_timesquare.view.MainFrame.jComboBox7;

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

        StoreInfoService storeInfoService = new StoreInfoService();
        StoreInfo storeInfo = storeInfoService.getStoreInfoDetails();
        
        
        
        TransactionHistoryService ths = new TransactionHistoryService();
        
        List<TransactionHistory> th = ths.getAllTransactionHistoryDetails();
        
        ServiceTicketsService sts = new ServiceTicketsService();
        
        ProductService ps = new ProductService();
        CategoryService cs = new CategoryService();
        List<Category> listCategory = cs.getAllCategory();
        List<ServiceTickets> ts = sts.getAllServiceTicketsDetails();
        
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        java.sql.Date date2 = new java.sql.Date(cal.getTime().getTime());
        
        float totalSales = 0;
        float totalService = 0;
        float totalRefund = 0;
        
        JSONArray orderHistory = new JSONArray();
        
        HashMap<String, Integer> category = new HashMap<>();
        
        String reportDate = new SimpleDateFormat("MMMM d, yyyy").format(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                   
        
        switch(jComboBox7.getSelectedIndex()){
            case 0:
                
                
                return;
            case 1:
                
                
                for(int i = 0; i < ts.size() ; i++){
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(ts.get(i).getEstimateFinish());
//                    int month = cal.get(Calendar.MONTH);
//                    int day = cal.get(Calendar.DAY_OF_MONTH);
//                    int year = cal.get(Calendar.YEAR);
                

                    System.out.println(ts.get(i).getEstimateFinish());
                    if(date.toString().equals(ts.get(i).getEstimateFinish().toString())){
                        if(ts.get(i).getStatus().equals("Done")){
                            totalService += ts.get(i).getPrice();
                        }
                    }
                    
                    
                    
                    jsonInputString.put("reportDate", cal);
                }
                
                for(int i = 0; i < th.size(); i++){
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(th.get(i).getTransactionDate());
//                    int month = cal.get(Calendar.MONTH);
//                    int day = cal.get(Calendar.DAY_OF_MONTH);
//                    int year = cal.get(Calendar.YEAR);

                    System.out.println(th.get(i).getTransactionDate());
                    if(date.toString().equals(th.get(i).getTransactionDate().toString())){
                        System.out.println(th.get(i).getProductId());
                        
                        if(th.get(i).getStatus().equals("Refund")){
                            totalRefund += th.get(i).getTotalPrice();
                        }else{
                            totalSales += th.get(i).getTotalPrice();
                            
                            String name = ps.getProductById(th.get(i).getProductId()).getName();
                            int order = th.get(i).getOrders();
                            float price = th.get(i).getTotalPrice();

                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("name", name);
                            jsonObj.put("order", order);
                            jsonObj.put("price", price);
                            orderHistory.add(jsonObj);
                        }
//                        totalOrders += th.get(i).getOrders();
                        
                        
                        
                        for(int j = 0; j < listCategory.size(); j++){
//                            category.put(listCategory.get(j).getType(), th.get(i).getOrders());
                            if(listCategory.get(j).getProduct_id() == th.get(i).getProductId()){
                                if(category.isEmpty()){
                                    category.put(listCategory.get(j).getType(), th.get(i).getOrders());
                                }else{
                                    if(category.get(listCategory.get(j).getType()) != null){
                                        category.put(listCategory.get(j).getType(), category.get(listCategory.get(j).getType()) + th.get(i).getOrders());
                                    }else{
                                        category.put(listCategory.get(j).getType(), th.get(i).getOrders());
                                    }
                                }
                            }
                        }
                        
                        
                    }
                }
                
                 
                jsonInputString.put("reportDate", reportDate);
                
                break;
            case 2:
               
                cal.setTime(date);
                
//                currentDate.add(Calendar.DATE, 7);
                for(int i = 0; i < ts.size() ; i++){
                    cal.setTime(date);
                    for(int j = 1; j <= 7; j++){
                        java.sql.Date currentDate = new java.sql.Date(cal.getTime().getTime());
//                        System.out.println(ts.get(i).getEstimateFinish());
                        
                        if(currentDate.toString().equals(ts.get(i).getEstimateFinish().toString())){
                            if(ts.get(i).getStatus().equals("Done")){
                                totalService += ts.get(i).getPrice();
                            }
                        }
                        cal.add(Calendar.DATE, -1);
                    }
                }
                
                cal.setTime(date);
                date2 = new java.sql.Date(cal.getTime().getTime());
                System.out.println("first date: " + date2);
                
                
                for(int i = 0; i < th.size(); i++){
                    cal.setTime(date);
                    for(int k = 1; k <= 7; k++){
                        java.sql.Date currentDate = new java.sql.Date(cal.getTime().getTime());
                        
                        System.out.println(currentDate);
                        
                        if(currentDate.toString().equals(th.get(i).getTransactionDate().toString())){
//                            System.out.println(th.get(i).getProductId());

                            if(th.get(i).getStatus().equals("Refund")){
                                totalRefund += th.get(i).getTotalPrice();
                            }else{
                                totalSales += th.get(i).getTotalPrice();

                                String name = ps.getProductById(th.get(i).getProductId()).getName();
                                int order = th.get(i).getOrders();
                                float price = th.get(i).getTotalPrice();

                                JSONObject jsonObj = new JSONObject();
                                jsonObj.put("name", name);
                                jsonObj.put("order", order);
                                jsonObj.put("price", price);
                                orderHistory.add(jsonObj);
                            }
    
                            for(int j = 0; j < listCategory.size(); j++){
                                
                                if(listCategory.get(j).getProduct_id() == th.get(i).getProductId()){
                                    if(category.isEmpty()){
                                        category.put(listCategory.get(j).getType(), th.get(i).getOrders());
                                    }else{
                                        if(category.get(listCategory.get(j).getType()) != null){
                                            category.put(listCategory.get(j).getType(), category.get(listCategory.get(j).getType()) + th.get(i).getOrders());
                                        }else{
                                            category.put(listCategory.get(j).getType(), th.get(i).getOrders());
                                        }
                                    }
                                }
                            }
                        }
                        
                        cal.add(Calendar.DATE, -1);
                    }
                }
                break;
            case 3:
                
                cal.setTime(date);
                
//                currentDate.add(Calendar.DATE, 7);
                for(int i = 0; i < ts.size() ; i++){
                    cal.setTime(date);
                    for(int j = 1; j <= 30; j++){
                        java.sql.Date currentDate = new java.sql.Date(cal.getTime().getTime());
//                        System.out.println(ts.get(i).getEstimateFinish());
                        
                        if(currentDate.toString().equals(ts.get(i).getEstimateFinish().toString())){
                            if(ts.get(i).getStatus().equals("Done")){
                                totalService += ts.get(i).getPrice();
                            }
                        }
                        cal.add(Calendar.DATE, -1);
                    }
                }
                
                cal.setTime(date);
                date2 = new java.sql.Date(cal.getTime().getTime());
                System.out.println("first date: " + date2);
                
                
                for(int i = 0; i < th.size(); i++){
                    cal.setTime(date);
                    for(int k = 1; k <= 30; k++){
                        java.sql.Date currentDate = new java.sql.Date(cal.getTime().getTime());
                        
                        System.out.println(currentDate);
                        
                        if(currentDate.toString().equals(th.get(i).getTransactionDate().toString())){
//                            System.out.println(th.get(i).getProductId());

                            if(th.get(i).getStatus().equals("Refund")){
                                totalRefund += th.get(i).getTotalPrice();
                            }else{
                                totalSales += th.get(i).getTotalPrice();

                                String name = ps.getProductById(th.get(i).getProductId()).getName();
                                int order = th.get(i).getOrders();
                                float price = th.get(i).getTotalPrice();

                                JSONObject jsonObj = new JSONObject();
                                jsonObj.put("name", name);
                                jsonObj.put("order", order);
                                jsonObj.put("price", price);
                                orderHistory.add(jsonObj);
                            }
    
                            for(int j = 0; j < listCategory.size(); j++){
                                
                                if(listCategory.get(j).getProduct_id() == th.get(i).getProductId()){
                                    if(category.isEmpty()){
                                        category.put(listCategory.get(j).getType(), th.get(i).getOrders());
                                    }else{
                                        if(category.get(listCategory.get(j).getType()) != null){
                                            category.put(listCategory.get(j).getType(), category.get(listCategory.get(j).getType()) + th.get(i).getOrders());
                                        }else{
                                            category.put(listCategory.get(j).getType(), th.get(i).getOrders());
                                        }
                                    }
                                }
                            }
                        }
                        
                        cal.add(Calendar.DATE, -1);
                    }
                }
                break;
                    
        }

        
        if(jComboBox7.getSelectedIndex() != 0){
            jsonInputString.put("storeBranch", storeInfo.getBranch());
            jsonInputString.put("storeAddress", storeInfo.getAddress());
            jsonInputString.put("totalSales", totalSales);
            jsonInputString.put("totalService", totalService);
            jsonInputString.put("totalRefund", totalRefund);
            jsonInputString.put("chartURL", doughnutChart(category));


            jsonInputString.put("orderHistory", orderHistory);

            System.out.println("category: " + category.toString());
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
        
    }
    
    public float getTotalSales(){
        TransactionHistoryService ths = new TransactionHistoryService();
        
        List<TransactionHistory> th = ths.getAllTransactionHistoryDetails();
        
        float totalSales = 0;
        
        switch(jComboBox7.getSelectedIndex()){
            case 0:
                
                break;
            case 1:
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                th.forEach(e -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(e.getTransactionDate());
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);

                    System.out.println(e.getTransactionDate());
                    if(date.toString().equals(e.getTransactionDate().toString())){
                        System.out.println(e.getProductId());
                    }
                });
                break;
            case 2:
                break;
            case 3:
                break;
                    
        }
        
        return totalSales;
    }
    
    public static String doughnutChart(HashMap<String, Integer> category){
        JSONObject json = new JSONObject();
//        json.put("type", "doughnut");
//        
        JSONObject data = new JSONObject();
        JSONArray labels = new JSONArray();
//        labels.add("Week 1");
//        labels.add("Week 2");
//        category.forEach(e -> {
//            labels.add(e);
//        });
        
        data.put("labels", labels);
        
        JSONArray datasets = new JSONArray();
        JSONArray datasetsData = new JSONArray();
//        datasetsData.add(23);
//        datasetsData.add(90);

        int totalStock = 0;

//        category.forEach( (k,e) -> {
//            labels.add(k);
//            datasetsData.add(e);
//            totalStock += e;
//        });
        for (String i : category.keySet()) {
            labels.add(i);
            datasetsData.add(category.get(i));
            totalStock += category.get(i);
        }
        
        JSONObject dataObject =  new JSONObject();
        dataObject.put("data", datasetsData);
        datasets.add(dataObject);


//        data.put("datasets", datasets);
        data.put("datasets", datasets);
        
        //
        String option = "{type:'doughnut',data:{labels:"+ labels.toString().replaceAll("\"","'") +",datasets:[{data:"+ datasetsData.toString() +"}]},options:{plugins:{doughnutlabel:{labels:[{text:'"+ totalStock +"',font:{size:20}},{text:'Total Orders'}]}}}}";
        
        System.out.println(option);
        System.out.println(data.toString());
        
        return option;
        
    }
}
