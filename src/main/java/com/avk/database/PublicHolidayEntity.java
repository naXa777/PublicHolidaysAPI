package com.avk.database;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "public_holiday")
public class PublicHolidayEntity implements Comparable<PublicHolidayEntity> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private CountryEntity countryEntity;

    @Column
    private String name;

    @Column(name = "holiday_date")
    private Date holidayDate;

    @Column(name = "holiday_date_value")
    private String holidayDateValue;

    @Column(name = "national_holiday")
    private Boolean nationalHoliday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity country) {
        this.countryEntity = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date date) {
        this.holidayDate = date;
    }

    public String getHolidayDateValue() {
        return holidayDateValue;
    }

    public void setHolidayDateValue(String value) {
        this.holidayDateValue = value;
    }

    public Boolean getNationalHoliday() {
        return nationalHoliday;
    }

    public void setNationalHoliday(Boolean nationalHoliday) {
        this.nationalHoliday = nationalHoliday;
    }

    @Override
    public int compareTo(PublicHolidayEntity o) {
        return getHolidayDate().compareTo(o.getHolidayDate());
    }
}
