package com.mediconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "service_categories")
public class ServiceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nameEn;

    @Column(nullable = false)
    private String nameUr;

    @Column(length = 1000)
    private String descriptionEn;

    @Column(length = 1000)
    private String descriptionUr;

    private String icon; // "Bandage", "Syringe", "Droplets", "Activity", "Scissors", "HeartPulse"

    private Double suggestedPricePkr;

    private Integer estimatedMinutes;

    private Boolean isActive = true;

    public ServiceCategory() {}

    public ServiceCategory(String nameEn, String nameUr, String descriptionEn, String descriptionUr, String icon, Double suggestedPricePkr, Integer estimatedMinutes) {
        this.nameEn = nameEn;
        this.nameUr = nameUr;
        this.descriptionEn = descriptionEn;
        this.descriptionUr = descriptionUr;
        this.icon = icon;
        this.suggestedPricePkr = suggestedPricePkr;
        this.estimatedMinutes = estimatedMinutes;
        this.isActive = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }

    public String getNameUr() { return nameUr; }
    public void setNameUr(String nameUr) { this.nameUr = nameUr; }

    public String getDescriptionEn() { return descriptionEn; }
    public void setDescriptionEn(String descriptionEn) { this.descriptionEn = descriptionEn; }

    public String getDescriptionUr() { return descriptionUr; }
    public void setDescriptionUr(String descriptionUr) { this.descriptionUr = descriptionUr; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public Double getSuggestedPricePkr() { return suggestedPricePkr; }
    public void setSuggestedPricePkr(Double suggestedPricePkr) { this.suggestedPricePkr = suggestedPricePkr; }

    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Integer estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
