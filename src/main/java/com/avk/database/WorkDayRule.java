package com.avk.database;

import java.time.LocalDate;

public interface WorkDayRule {
    boolean isBusinessDay(LocalDate localDate);
}
