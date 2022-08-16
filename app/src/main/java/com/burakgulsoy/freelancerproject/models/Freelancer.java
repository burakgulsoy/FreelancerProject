package com.burakgulsoy.freelancerproject.models;


public class Freelancer {

    private int freelancer_id;
    private String name;
    private String surname;
    private String email;
    private String phone_number;
    private String password;
    private int role_id;
    private int is_validated;
    private String freelancer_description;

    public Freelancer() {

    }

    public Freelancer(int freelancer_id, String name, String surname, String email, String phone_number, String password, int role_id, int is_validated, String freelancer_description) {
        this.freelancer_id = freelancer_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.role_id = role_id;
        this.is_validated = is_validated;
        this.freelancer_description = freelancer_description;
    }

    public Freelancer(String name, String surname, String email, String phone_number, String password, int role_id, int is_validated, String freelancer_description) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.role_id = role_id;
        this.is_validated = is_validated;
        this.freelancer_description = freelancer_description;
    }

    public int getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(int freelancer_id) {
        this.freelancer_id = freelancer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getIs_validated() {
        return is_validated;
    }

    public void setIs_validated(int is_validated) {
        this.is_validated = is_validated;
    }

    public String getFreelancer_description() {
        return freelancer_description;
    }

    public void setFreelancer_description(String freelancer_description) {
        this.freelancer_description = freelancer_description;
    }
}

