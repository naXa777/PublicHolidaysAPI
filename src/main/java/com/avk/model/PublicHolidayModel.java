package com.avk.model;

public class PublicHolidayModel {
    private String name;
    private String holidayDate;
    private Boolean nationalHoliday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public Boolean getNationalHoliday() {
        return nationalHoliday;
    }

    public void setNationalHoliday(Boolean nationalHoliday) {
        this.nationalHoliday = nationalHoliday;
    }
}
