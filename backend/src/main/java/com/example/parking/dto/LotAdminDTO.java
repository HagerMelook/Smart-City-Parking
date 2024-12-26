package com.example.parking.dto;

public class LotAdminDTO {
    private int lot_admin_id;
    private int lot_id;
    private String name;

    public int getLot_admin_id() {
        return lot_admin_id;
    }

    public void setLot_admin_id(int lot_admin_id) {
        this.lot_admin_id = lot_admin_id;
    }

    public int getLot_id() {
        return lot_id;
    }

    public void setLot_id(int lot_id) {
        this.lot_id = lot_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
