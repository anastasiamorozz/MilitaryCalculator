package com.example.kursova;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String type;
    private MRE mre;
    private double weight;
    public boolean selected;

    public Product(String name, String type, MRE mre, double weight) {
        this.name = name;
        this.type = type;
        this.mre = mre;
        this.weight = weight;
        this.selected=false;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public MRE getMre() {
        return mre;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSelected(boolean selected){ this.selected = true;}

    public boolean isChecked() {
        return selected;
    }
}

