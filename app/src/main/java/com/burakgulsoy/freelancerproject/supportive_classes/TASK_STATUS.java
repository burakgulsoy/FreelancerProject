package com.burakgulsoy.freelancerproject.supportive_classes;

public enum TASK_STATUS {

    TO_DO("TO_DO"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    String name;

    private TASK_STATUS(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }


}
