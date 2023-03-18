package com.example.lab4.entities;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

public class MessageFormat {
    public SimpleStringProperty m1;
    public SimpleStringProperty m2;

    public MessageFormat(String m1, String m2) {
        this.m1 = new SimpleStringProperty(m1);
        this.m2 = new SimpleStringProperty(m2);
    }

    public String getM1() {
        return m1.get();
    }

    public void setM1(String m1) {
        this.m1 = new SimpleStringProperty(m1);
    }

    public String getM2() {
        return m2.get();
    }

    public void setM2(String m2) {
        this.m2 = new SimpleStringProperty(m2);
    }
}
