package com.example.homsi.psf;

/**
 * Created by Jonnel on 10/30/2017.
 */

public class Vehicle
{
    private String make;
    private String model;
    private String color;
    private String manf;
    private String plateNum;

    public Vehicle(String make, String model, String color, String manf, String plateNum) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.manf = manf;
        this.plateNum = plateNum;
    }

    public String getMake()
    {
        return make;
    }

    public String getModel()
    {
        return model;
    }

    public String getColor()
    {
        return color;
    }

    public String getManf()
    {
        return manf;
    }

    public String getPlateNum()
    {
        return plateNum;
    }


}
