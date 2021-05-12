package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.controller.exceptions.CityNotFoundException;
import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final HotelService hotelService;

    @Autowired
    public SearchController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> getNearest(@PathVariable("id") City city,
                                  @RequestParam(value = "sortBy", required = true) String sort)
    {
        //Just some fast solution
        if(!sort.equals("distance")) throw new WrongSortType();
        if(city==null) throw new CityNotFoundException();

        return this.hotelService.findNearestTo(city);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Wrong sort type")
    private static class WrongSortType extends RuntimeException {
    }
}
