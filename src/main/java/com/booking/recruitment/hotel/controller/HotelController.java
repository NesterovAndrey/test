package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.controller.exceptions.HotelNotFoundException;
import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
  private final HotelService hotelService;

  @Autowired
  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getAllHotels() {
    return hotelService.getAllHotels();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Hotel createHotel(@RequestBody Hotel hotel) {
    return hotelService.createNewHotel(hotel);
  }
  @GetMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Hotel findOne(@PathVariable(name = "id") Hotel hotel)
  {
    if(hotel == null || hotel.isDeleted()) throw new HotelNotFoundException();
    return hotel;
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOne(@PathVariable(name = "id") Hotel hotel)
  {
    if(hotel == null) throw new HotelNotFoundException();
    hotelService.deleteHotel(hotel);
  }

}
