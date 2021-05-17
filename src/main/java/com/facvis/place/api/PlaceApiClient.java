package com.facvis.place.api;

import com.facvis.place.web.dto.PlaceResponseDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequiredArgsConstructor
@Service
public class PlaceApiClient {
    //네이버 지역 검색 api 받기
    public static String requestPlace(String keyword) {
        String kind_key = "애견동반 ";

        //String OpenNaverPlaceUrl_getPlace = "https://openapi.naver.com/v1/search/local.json?query="+kind_key+"서울&display=30";
        String clientId = "qGC0TfZb3XxmbWyhFi_x"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "xPyTCeZLrd"; //애플리케이션 클라이언트 시크릿값"

        String text = null;
        try {
            text = URLEncoder.encode(kind_key + keyword , "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String OpenNaverPlaceUrl_getPlace = "https://openapi.naver.com/v1/search/local.json?query="+text+"&display=30";
        String responseBody = get(OpenNaverPlaceUrl_getPlace,requestHeaders);

        System.out.println(responseBody);
        return responseBody;
    }

    private static String get(String OpenNaverPlaceUrl_getPlace, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(OpenNaverPlaceUrl_getPlace);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String OpenNaverPlaceUrl_getPlace){
        try {
            URL url = new URL(OpenNaverPlaceUrl_getPlace);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + OpenNaverPlaceUrl_getPlace, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + OpenNaverPlaceUrl_getPlace, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    public static List<PlaceResponseDto> getPlaceJson(String keyword) throws Exception{
        String result = requestPlace(keyword);

        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject)parser.parse(result);

        JSONArray j_item = (JSONArray) jsonObj.get("items");

        Gson gson = new Gson();

        //Gson라이브러리 -> json 받은 데이터를 자동으로 set
        //JsonList -> List로 변환
        List<PlaceResponseDto> list = gson.fromJson(j_item.toString(), new TypeToken<List<PlaceResponseDto>>(){}.getType());

        /*for(PlaceResponseDto placeResponseDto : list){
           // System.out.println(list);
        }
         */
        return list;
    }
}
