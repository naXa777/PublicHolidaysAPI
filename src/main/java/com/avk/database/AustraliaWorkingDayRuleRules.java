package com.avk.database;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AustraliaWorkingDayRuleRules implements WorkDayRule
{
    @Override
    public boolean isBusinessDay(LocalDate localDate)
    {
        return !(localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY));
    }
}
