package com.burakgulsoy.freelancerproject.models;

import java.io.Serializable;

public class Task  {

    private int task_id;
    private String task_description;
    private int freelancer_id;
    private String task_type;
    private String task_begin_date;
    private String task_end_date;


    public Task() {

    }

    public Task(int task_id, String task_description, int freelancer_id, String task_type, String task_begin_date, String task_end_date) {
        this.task_id = task_id;
        this.task_description = task_description;
        this.freelancer_id = freelancer_id;
        this.task_type = task_type;
        this.task_begin_date = task_begin_date;
        this.task_end_date = task_end_date;
    }

    public Task(String task_description, int freelancer_id, String task_type, String task_begin_date, String task_end_date) {
        this.task_description = task_description;
        this.freelancer_id = freelancer_id;
        this.task_type = task_type;
        this.task_begin_date = task_begin_date;
        this.task_end_date = task_end_date;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public int getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(int freelancer_id) {
        this.freelancer_id = freelancer_id;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getTask_begin_date() {
        return task_begin_date;
    }

    public void setTask_begin_date(String task_begin_date) {
        this.task_begin_date = task_begin_date;
    }

    public String getTask_end_date() {
        return task_end_date;
    }

    public void setTask_end_date(String task_end_date) {
        this.task_end_date = task_end_date;
    }
}
