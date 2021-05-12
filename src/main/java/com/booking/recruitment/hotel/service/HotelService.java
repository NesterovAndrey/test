package com.booking.recruitment.hotel.service;

import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;

import java.util.List;

public interface HotelService {
  List<Hotel> getAllHotels();

  List<Hotel> getHotelsByCity(Long cityId);

  List<Hotel> findNearestTo(City city);

  Hotel createNewHotel(Hotel hotel);

  void deleteHotel(Hotel hotel);
}
