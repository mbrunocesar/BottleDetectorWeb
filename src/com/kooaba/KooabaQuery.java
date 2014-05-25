package com.kooaba;

import global.Path;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class KooabaQuery {

	
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, IOException {
        // If you want your image to be recognized, it must be added to your reference items: https://platform.kooaba.com/items
        // Make sure that your query image is less the 2Mb in size.
        String imageFile = Path.thisPath+"images/check-confirmed/100_percent_carrot_small2079_lower.jpg";

        // Get your query API keys at: https://platform.kooaba.com/querykeys
        // Initialize a KooabaApi object with "KA" authentication over http.
        KooabaApi kooaba = new KooabaApi("bd53263d-e2dc-48b8-95ee-c3735ca69097", "cV445YCB5IvGlhWzzM67vtGa7j4AJMfyvq0DHfI7");

        // or initialize a KooabaApi object with "Token" authentication over https.
        //KooabaApi kooaba = new KooabaApi("<secret_token>");

        // Execute a simple query.
        //kooaba.query(imageFile);

        // Execute a query passing additional parameters.
        Map<String, String> params = new HashMap<String, String>();
        params.put("max_results", "1");
        params.put("user_data", "{\"user_id\": \"Master\"}");
        kooaba.query(imageFile, params);

        // Print the response to console.
        System.out.println("HTTP Status: " + kooaba.getResponseStatus());
        System.out.println("HTTP Response: " + kooaba.getResponseBody());
    }

	final String imagePath = Path.thisPath + "images/";    
    KooabaApi kooaba; 
    
    public KooabaQuery(){
    	kooaba = new KooabaApi("bd53263d-e2dc-48b8-95ee-c3735ca69097", "cV445YCB5IvGlhWzzM67vtGa7j4AJMfyvq0DHfI7");
    }
    
    public String execute(String imageFile) throws InvalidKeyException, NoSuchAlgorithmException, IOException {
        // Execute a query passing additional parameters.
        Map<String, String> params = new HashMap<String, String>();
        params.put("max_results", "2");
        params.put("user_data", "{\"user_id\": \"Master\"}");
        kooaba.query(imagePath+imageFile+".png", params);

        // Print the response to console.
        System.out.println("HTTP Status: " + kooaba.getResponseStatus());
        System.out.println("HTTP Response: " + kooaba.getResponseBody());
        
        return kooaba.getResponseBody();
    }
    
    
    
}
