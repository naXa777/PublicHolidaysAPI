package com.avk.model;

public class PublicHolidayModelBuilder {
    public static PublicHolidayModel buildModel(String name, String holidayDate, boolean nationalHoliday) {
        PublicHolidayModel model = new PublicHolidayModel();

        model.setName(name);
        model.setHolidayDate(holidayDate);
        model.setNationalHoliday(nationalHoliday);

        return model;
    }
}
