package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@org.springframework.stereotype.Repository
public class Repository {
    HashMap<String,Hotel> hotelDB=new HashMap<>();
    HashMap<Integer,User> userDB=new HashMap<>();
    HashMap<String, Booking> bookingsMap=new HashMap<>();
    HashMap<Integer,List<Booking>> personBookings = new HashMap<>();
    public String addHotel(Hotel hotel){
        if(hotel == null || hotel.getHotelName() == null || hotelDB.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }else{
            hotelDB.put(hotel.getHotelName(),hotel);
            return "SUCCESS";
        }

    }
    public Integer addUser(User user){
        userDB.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();

    }
    public String getHotelWithMostFacilities(){
        Integer max=null;
        String HotelName="";
        TreeMap<String,Hotel> Map = new TreeMap<>();
        Map.putAll(hotelDB);

        for(String hotelName : Map.keySet()) {
            int numOfFacilities = hotelDB.get(hotelName).getFacilities().size();
            if(numOfFacilities > max) {
                max = numOfFacilities;
                HotelName = hotelName;
            }
        }
        return HotelName;

    }
    public int bookARoom(Booking booking) {
        String primaryKey = booking.getBookingId();
        bookingsMap.put(primaryKey,booking);

        int availableRooms = hotelDB.get(booking.getHotelName()).getAvailableRooms();
        if(availableRooms < booking.getNoOfRooms()) {
            return -1;
        }else {
            List<Booking> bookingsTillNow = personBookings.getOrDefault(booking.getBookingAadharCard(),new ArrayList<>());
            bookingsTillNow.add(booking);
            personBookings.put(booking.getBookingAadharCard(),bookingsTillNow);
            int priceToBePaid = booking.getNoOfRooms() * hotelDB.get(booking.getHotelName()).getPricePerNight();
            booking.setAmountToBePaid(priceToBePaid);
            return priceToBePaid;
        }

    }
    public int getBookings(int aadharCard) {
        List<Booking> bookingList = personBookings.get(aadharCard);
        return bookingList.size();

    }


    public boolean containsAlready(List<Facility> facilities, Facility newFacility) {
        for(Facility f : facilities){
            if(f == newFacility)
                return true;
        }
        return false;

    }
    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDB.get(hotelName);
        List<Facility> availableFacilities = hotel.getFacilities();
        for(Facility f : newFacilities){
            if(!containsAlready(availableFacilities, f))
                availableFacilities.add(f);
        }
        hotel.setFacilities(availableFacilities);
        return hotel;
    }

}
