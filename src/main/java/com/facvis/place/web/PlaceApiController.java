package com.facvis.place.web;

import com.facvis.place.service.place.PlaceService;
import com.facvis.place.web.dto.PlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlaceApiController {
    private final PlaceService placeService;

    @GetMapping("/api/v1/place/{search}")
    public PlaceResponseDto get(@PathVariable String search){
        return placeService.findByKeyword(search);
    }
}
