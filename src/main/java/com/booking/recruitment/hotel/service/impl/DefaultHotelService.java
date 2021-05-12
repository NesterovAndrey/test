package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import com.booking.recruitment.utils.Haversine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  public List<Hotel> getAllHotels() {
    return hotelRepository.findAllByDeleted(false);
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return hotelRepository.findAll().stream()
            .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
            .collect(Collectors.toList());
  }

  @Override
  public List<Hotel> findNearestTo(City city) {
    return this.hotelRepository.findAllByDeleted(false).stream().sorted(Comparator.comparingDouble(o -> Haversine.distance(
            o.getLatitude(), o.getLongitude(),
            city.getCityCentreLatitude(), city.getCityCentreLongitude())))
            .limit(3) // Would have been to move it to application.yml
            .collect(Collectors.toList());
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  @Override
  public void deleteHotel(Hotel hotel) {
    if(hotel==null) throw new IllegalArgumentException("Hotel must not be null");
    if(hotel.getId()==null) throw new IllegalArgumentException("Hotel id must be provided when deleting a Hotel");

    hotel.setDeleted(true);
    hotelRepository.save(hotel);
  }
}
