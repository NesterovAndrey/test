package com.booking.recruitment.hotel.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Hotel is not found")
public class HotelNotFoundException extends RuntimeException {
}
