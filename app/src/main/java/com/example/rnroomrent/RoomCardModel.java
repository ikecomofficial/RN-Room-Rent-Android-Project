package com.example.rnroomrent;

public class RoomCardModel {
    public String room_id;
    public String room_name;
    private int custom_rent;
    public String tenant_name;
    public String rent_status;
    public String unit_paid_up_to;


    public RoomCardModel() { }

    public RoomCardModel(String room_id, String room_name, int custom_rent,
                         String tenant_name, String rent_status,
                         String unit_paid_up_to) {
        this.room_id = room_id;
        this.room_name = room_name;
        this.custom_rent = custom_rent;
        this.tenant_name = tenant_name;
        this.rent_status = rent_status;
        this.unit_paid_up_to = unit_paid_up_to;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getCustom_rent() {
        return custom_rent;
    }

    public void setCustom_rent(int custom_rent) {
        this.custom_rent = custom_rent;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getRent_status() {
        return rent_status;
    }

    public void setRent_status(String rent_status) {
        this.rent_status = rent_status;
    }

    public String getUnit_paid_up_to() {
        return unit_paid_up_to;
    }

    public void setUnit_paid_up_to(String unit_paid_up_to) {
        this.unit_paid_up_to = unit_paid_up_to;
    }
}