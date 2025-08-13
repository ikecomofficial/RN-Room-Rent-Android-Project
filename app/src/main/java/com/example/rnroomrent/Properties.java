package com.example.rnroomrent;

public class Properties {
    public String property_name;
    public String property_address;
    public int total_rooms;
    public int total_shops;
    public int rooms_occupied;
    public int shops_occupied;

    public Properties(){

    }

    public Properties(String pname, String paddress, int totalRooms,
                       int totalShops, int occupiedRooms, int occupiedShops) {
        this.property_name = pname;
        this.property_address = paddress;
        this.total_rooms = totalRooms;
        this.total_shops = totalShops;
        this.rooms_occupied = occupiedRooms;
        this.shops_occupied = occupiedShops;
    }

    public String getProperty_name() {
        return property_name;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public String getProperty_address() {
        return property_address;
    }

    public void setProperty_address(String property_address) {
        this.property_address = property_address;
    }

    public int getTotal_rooms() {
        return total_rooms;
    }

    public void setTotal_rooms(int total_rooms) {
        this.total_rooms = total_rooms;
    }

    public int getTotal_shops() {
        return total_shops;
    }

    public void setTotal_shops(int total_shops) {
        this.total_shops = total_shops;
    }

    public int getRooms_occupied() {
        return rooms_occupied;
    }

    public void setRooms_occupied(int rooms_occupied) {
        this.rooms_occupied = rooms_occupied;
    }

    public int getShops_occupied() {
        return shops_occupied;
    }

    public void setShops_occupied(int shops_occupied) {
        this.shops_occupied = shops_occupied;
    }
}
