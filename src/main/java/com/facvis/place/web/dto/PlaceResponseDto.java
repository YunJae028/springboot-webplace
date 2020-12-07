package com.facvis.place.web.dto;

import lombok.Data;

@Data
public class PlaceResponseDto {
    private int display;
    private Item[] items;

    @Data
    static class Item{
        public String title;
        public String link;
        public String description;
        public String telephone;
        public String address;
        public String roadAddress;
        public int mapx;
        public int mapy;
    }

}
