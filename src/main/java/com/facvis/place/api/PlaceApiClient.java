package com.facvis.place.api;

import com.facvis.place.web.dto.PlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class PlaceApiClient {
    RestTemplate restTemplate = new RestTemplate();

    private final String CLIENT_ID = "qGC0TfZb3XxmbWyhFi_x";
    private final String CLIENT_SECRET = "xPyTCeZLrd";
    private final String kind_key = "애견동반 ";
    private final String OpenNaverPlaceUrl_getPlace = "https://openapi.naver.com/v1/search/local.json?query="+kind_key+"{search}&display=30";

    public PlaceResponseDto requestPlace(String keyword){
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(OpenNaverPlaceUrl_getPlace, HttpMethod.GET, entity, PlaceResponseDto.class, keyword).getBody();
    }


}
