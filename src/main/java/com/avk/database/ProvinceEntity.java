package com.avk.database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "province")
public class ProvinceEntity {

    public static final String NSW = "NSW";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private CountryEntity countryEntity;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "province_name")
    private String provinceName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "public_holiday_province",
            joinColumns = @JoinColumn(name = "province_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "public_holiday_id", referencedColumnName = "id")
    )
    private Set<PublicHolidayEntity> publicHolidayEntities = new HashSet<>();

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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String name) {
        this.provinceName = name;
    }

    public Set<PublicHolidayEntity> getPublicHolidayEntities() {
        return publicHolidayEntities;
    }
}
