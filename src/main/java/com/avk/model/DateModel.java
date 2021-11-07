package com.avk.model;

public class DateModel {
    private Boolean isHoliday;
    private PublicHolidayModel publicHoliday;

    public Boolean getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    public PublicHolidayModel getPublicHoliday() {
        return publicHoliday;
    }

    public void setPublicHoliday(PublicHolidayModel publicHoliday) {
        this.publicHoliday = publicHoliday;
    }
}
