package com.example.kursova;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String type;
    private MRE mre;
    private double weight;
    private byte[] image;

    public Product(String name, String type, MRE mre, double weight, byte[] image) {
        this.name = name;
        this.type = type;
        this.mre = mre;
        this.weight = weight;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

