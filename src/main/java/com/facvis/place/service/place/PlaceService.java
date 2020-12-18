package com.facvis.place.service.place;

import com.facvis.place.api.PlaceApiClient;
import com.facvis.place.web.dto.PlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceApiClient placeApiClient;

    @Transactional(readOnly = true)
    public PlaceResponseDto findByKeyword(String search){
        return placeApiClient.requestPlace(search);
    }
}
