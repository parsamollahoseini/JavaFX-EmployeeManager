// Parsa Molahosseini
// Mehrad Bayat
// Jerry-Lee Somera



package com.example.projectv3;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serial;
import java.io.Serializable;

public class Department implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private transient SimpleStringProperty name;
    private transient SimpleStringProperty manager;

    private String serializedName;
    private String serializedManager;
    private int id;
    private int serializedId;

    public Department(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.manager = new SimpleStringProperty("Default Manager");
        serialize();
    }

    public Department(int id, String name, String manager) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.manager = new SimpleStringProperty(manager);
        serialize();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        serialize();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
        serialize();
    }

    public String getManager() {
        return manager.get();
    }

    public void setManager(String manager) {
        this.manager.set(manager);
        serialize();
    }

    private void serialize() {
        this.serializedName = name.get();
        this.serializedManager = manager.get();
        this.serializedId = id;
    }

    private void deserialize() {
        this.name = new SimpleStringProperty(serializedName);
        this.manager = new SimpleStringProperty(serializedManager);
        this.id = serializedId;
    }

    private Object readResolve() {
        deserialize();
        return this;
    }

    @Override
    public String toString() {
        return name.get();
    }
}
