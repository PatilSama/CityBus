package com.example.citybus.ModelClases;

public class SearchBusNumberModel {
    String nameOfBus,busNumber,busFrom,busTo;
    public SearchBusNumberModel(String nameOfBus,String busNumber,String busFrom,String busTo)
    {
        this.nameOfBus = nameOfBus;
        this.busNumber=busNumber;
        this.busFrom=busFrom;
        this.busTo=busTo;
    }

    public String getNameOfBus()
    {
        return nameOfBus;
    }

    public void setNameOfBus(String nameOfBus)
    {
        this.nameOfBus=nameOfBus;
    }

    public String getBusNumber()
    {
        return busNumber;
    }
    public void setBusNumber(String busNumber)
    {
        this.busNumber=busNumber;
    }

    public String getBusFrom()
    {
        return busFrom;
    }

    public void setBusFrom(String busFrom)
    {
        this.busFrom=busFrom;
    }
    public String getBusTo()
    {
        return busTo;
    }
    public void setBusTo(String busTo)
    {
        this.busTo=busTo;
    }
}
