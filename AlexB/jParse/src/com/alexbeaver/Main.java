package com.alexbeaver;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;

/**
 * @author Alex Beaver
 */
public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("JSON Started");
        /**
         * Creates a new JSONObject to parse
         */
        JSONObject object = new JSONObject();
        /**
         * Puts values into JSONObject
         */
        object.put("name","test");
        object.put("value","ABC");
        System.out.println(object);
        String myJSONData = object.toString();
        System.out.println("=============================================");

        JSONParser parser = new  JSONParser();
        JSONObject myParsedData = new JSONObject();
        /**
         * Tests if data is valid otherwise throws nil object
         */
        try {myParsedData = (JSONObject) parser.parse(myJSONData);}catch(ParseException e){ e.printStackTrace(); myParsedData = null;}

        /**
         * Gets value for index "name"
         */
        System.out.println(myParsedData.get("name"));



    }
}
