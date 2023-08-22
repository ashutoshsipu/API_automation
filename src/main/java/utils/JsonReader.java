package utils;

import java.io.File;
import java.io.IOException;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
    public JsonReader() {
    }

    public static String getTestData(String key) throws IOException, ParseException {
        return (String)getJsonData().get(key);
    }

    public static JSONObject getJsonData() throws IOException, ParseException {
        File filename = new File("resources//TestData//testdata.json");
        String json = FileUtils.readFileToString(filename, "UTF-8");
        Object obj = (new JSONParser()).parse(json);
        JSONObject jsonObject = (JSONObject)obj;   //We've converted JSON into JSON object & JSON obj has method called .get  which will take key give me the value
        return jsonObject;
    }


    public static JSONArray getJsonArray(String key) throws IOException, ParseException {
        JSONObject jsonObject = getJsonData();
        JSONArray jsonArray = (JSONArray)jsonObject.get(key);
        return jsonArray;
    }

    public static Object getJsonArrayData(String key, int index) throws IOException, ParseException {
        JSONArray languages = getJsonArray(key);
        return languages.get(index);
    }
}
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


