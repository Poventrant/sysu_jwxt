package com.pwq.httpclient;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by 枫叶 on 2016/1/20.
 */
public class JsonUtil {

    public static HashMap<String,Object> jsonToMap(String str) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
        HashMap<String,Object> o = null;
        try {
            o = mapper.readValue(str, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }


}
