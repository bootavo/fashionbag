package com.fashionbag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gtufinof on 3/12/18.
 */

public class User {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("token")
    private String token;

    @Expose
    @SerializedName("last_sale")
    private String last_sale;

    @Expose
    @SerializedName("is_director")
    private boolean is_director;

    @Expose
    @SerializedName("has_director")
    private boolean has_director;

    @Expose
    @SerializedName("last_login")
    private String last_login;

    @Expose
    @SerializedName("is_superuser")
    private boolean is_superuser;

    @Expose
    @SerializedName("created")
    private String created;

    @Expose
    @SerializedName("modified")
    private String modified;

    @Expose
    @SerializedName("reservation_days")
    private int reservation_days;

    @Expose
    @SerializedName("deactivate_account_days")
    private String deactivate_account_days;

    @Expose
    @SerializedName("min_sales_units")
    private int min_sales_units;

    @Expose
    @SerializedName("min_mount_jewels")
    private int min_mount_jewels;

    @Expose
    @SerializedName("min_shoes_units")
    private int min_shoes_units;

    @Expose
    @SerializedName("weekly_goal")
    private float weekly_goal;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("first_name")
    private String first_name;

    @Expose
    @SerializedName("last_name")
    private String last_name;

    @Expose
    @SerializedName("profile_picture")
    private String profile_picture;

    @Expose
    @SerializedName("dni")
    private String dni;

    @Expose
    @SerializedName("phone_number")
    private String phone_number;

    @Expose
    @SerializedName("mobile_phone")
    private String mobile_phone;

    @Expose
    @SerializedName("is_active")
    private boolean is_active;

    @Expose
    @SerializedName("groups")
    private List<Integer> groups;

    @Expose
    @SerializedName("client_type")
    private int client_type;

    @Expose
    @SerializedName("is_facebook_user")
    private boolean is_facebook_user;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("address")
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLast_sale() {
        return last_sale;
    }

    public void setLast_sale(String last_sale) {
        this.last_sale = last_sale;
    }

    public boolean isIs_director() {
        return is_director;
    }

    public void setIs_director(boolean is_director) {
        this.is_director = is_director;
    }

    public boolean isHas_director() {
        return has_director;
    }

    public void setHas_director(boolean has_director) {
        this.has_director = has_director;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public boolean isIs_superuser() {
        return is_superuser;
    }

    public void setIs_superuser(boolean is_superuser) {
        this.is_superuser = is_superuser;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public int getReservation_days() {
        return reservation_days;
    }

    public void setReservation_days(int reservation_days) {
        this.reservation_days = reservation_days;
    }

    public String getDeactivate_account_days() {
        return deactivate_account_days;
    }

    public void setDeactivate_account_days(String deactivate_account_days) {
        this.deactivate_account_days = deactivate_account_days;
    }

    public int getMin_sales_units() {
        return min_sales_units;
    }

    public void setMin_sales_units(int min_sales_units) {
        this.min_sales_units = min_sales_units;
    }

    public int getMin_mount_jewels() {
        return min_mount_jewels;
    }

    public void setMin_mount_jewels(int min_mount_jewels) {
        this.min_mount_jewels = min_mount_jewels;
    }

    public int getMin_shoes_units() {
        return min_shoes_units;
    }

    public void setMin_shoes_units(int min_shoes_units) {
        this.min_shoes_units = min_shoes_units;
    }

    public float getWeekly_goal() {
        return weekly_goal;
    }

    public void setWeekly_goal(float weekly_goal) {
        this.weekly_goal = weekly_goal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public void setGroups(List<Integer> groups) {
        this.groups = groups;
    }

    public int getClient_type() {
        return client_type;
    }

    public void setClient_type(int client_type) {
        this.client_type = client_type;
    }

    public boolean isIs_facebook_user() {
        return is_facebook_user;
    }

    public void setIs_facebook_user(boolean is_facebook_user) {
        this.is_facebook_user = is_facebook_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", last_sale='" + last_sale + '\'' +
                ", is_director=" + is_director +
                ", has_director=" + has_director +
                ", last_login='" + last_login + '\'' +
                ", is_superuser=" + is_superuser +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", reservation_days=" + reservation_days +
                ", deactivate_account_days='" + deactivate_account_days + '\'' +
                ", min_sales_units=" + min_sales_units +
                ", min_mount_jewels=" + min_mount_jewels +
                ", min_shoes_units=" + min_shoes_units +
                ", weekly_goal=" + weekly_goal +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                ", dni='" + dni + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", is_active=" + is_active +
                ", groups=" + groups +
                ", client_type=" + client_type +
                ", is_facebook_user=" + is_facebook_user +
                ", status='" + status + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
