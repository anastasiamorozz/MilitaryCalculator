package com.example.kursova;

public class MRE {
    private int calories;
    private int protein;
    private int fat;
    private int carbs;

    public MRE(String name, int calories, int protein, int fat, int carbs) {
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getCarbs() {
        return carbs;
    }
}

