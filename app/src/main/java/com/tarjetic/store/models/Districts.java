package com.tarjetic.store.models;

import java.util.ArrayList;
import java.util.List;

public class Districts {

    private int district_id;
    private String district;

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "Districts{" +
                "district_id=" + district_id +
                ", district='" + district + '\'' +
                '}';
    }

    public static List<Districts> getDistricts(){

        List<Districts> listDistricts = new ArrayList<>();

        Districts districts0 = new Districts();
        districts0.setDistrict_id(0);
        districts0.setDistrict("Seleccionar Distrito");
        listDistricts.add(districts0);

        Districts districts = new Districts();
        districts.setDistrict_id(1);
        districts.setDistrict("JESUS MARIA");
        listDistricts.add(districts);

        Districts district2 = new Districts();
        district2.setDistrict_id(2);
        district2.setDistrict("SAN ISIDRO");
        listDistricts.add(district2);

        Districts district3 = new Districts();
        district3.setDistrict_id(3);
        district3.setDistrict("PUEBLO LIBRE");
        listDistricts.add(district3);

        Districts district4 = new Districts();
        district4.setDistrict_id(4);
        district4.setDistrict("LINCE");
        listDistricts.add(district4);

        return listDistricts;

    }
}
