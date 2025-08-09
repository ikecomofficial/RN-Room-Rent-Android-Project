package com.example.rnroomrent;

public class Properties {
    public String property_name;
    public String property_address;
    public String total_rooms;
    public String total_shops;
    public String rooms_occupied;
    public String shops_occupied;

    public Properties(){

    }

    public Properties(String pname, String paddress, String totalRooms,
                       String totalShops, String occupiedRooms, String occupiedShops) {
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

    public String getTotal_rooms() {
        return total_rooms;
    }

    public void setTotal_rooms(String total_rooms) {
        this.total_rooms = total_rooms;
    }

    public String getTotal_shops() {
        return total_shops;
    }

    public void setTotal_shops(String total_shops) {
        this.total_shops = total_shops;
    }

    public String getRooms_occupied() {
        return rooms_occupied;
    }

    public void setRooms_occupied(String rooms_occupied) {
        this.rooms_occupied = rooms_occupied;
    }

    public String getShops_occupied() {
        return shops_occupied;
    }

    public void setShops_occupied(String shops_occupied) {
        this.shops_occupied = shops_occupied;
    }
}
