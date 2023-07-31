package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private Repository RepositoryObj;
    public String addHotel(Hotel hotel){
        return RepositoryObj.addHotel(hotel);

    }
    public Integer addUser(User user){
        return RepositoryObj.addUser(user);

    }
    public String getHotelWithMostFacilities(){
        return RepositoryObj.getHotelWithMostFacilities();

    }
    public int bookARoom(Booking booking){
        UUID uuid = UUID.randomUUID();
        booking.setBookingId(uuid.toString());
        return RepositoryObj.bookARoom(booking);
    }

    public int getBookings(int aadharCard) {
        return RepositoryObj.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return RepositoryObj.updateFacilities(newFacilities, hotelName);
    }
}
