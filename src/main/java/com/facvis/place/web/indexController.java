package com.facvis.place.web;

import com.facvis.place.api.PlaceApiClient;
import com.facvis.place.service.place.PlaceService;
import com.facvis.place.web.dto.PlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class indexController {

    private final PlaceApiClient placeApiClient;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/place/result/{search}")
    public String listResult(@PathVariable String search, Model model) throws Exception {
        List<PlaceResponseDto> dto = placeApiClient.getPlaceJson(search);
        model.addAttribute("places",dto);

        return "place-result";
    }
}
