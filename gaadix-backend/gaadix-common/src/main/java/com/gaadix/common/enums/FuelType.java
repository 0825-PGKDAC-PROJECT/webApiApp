package com.gaadix.common.enums;

import lombok.Getter;

@Getter
public enum FuelType {
    PETROL("Petrol", "ICE", true, 40.0, 50.0),
    DIESEL("Diesel", "ICE", true, 50.0, 60.0),
    CNG("CNG", "ICE", true, 20.0, 25.0),
    LPG("LPG", "ICE", true, 15.0, 20.0),
    ELECTRIC("Electric", "EV", false, 0.0, 0.0),
    HYBRID("Hybrid", "HYBRID", true, 25.0, 30.0),
    PHEV("Plug-in Hybrid", "PHEV", true, 30.0, 35.0),
    HYDROGEN("Hydrogen", "FCEV", false, 0.0, 0.0),
    ETHANOL_BLEND("Ethanol Blend E20", "ICE", true, 35.0, 45.0),
    BIO_CNG("Bio-CNG", "ICE", true, 18.0, 23.0),
    FLEX_FUEL("Flex Fuel E85", "ICE", true, 30.0, 40.0);

    private final String displayName;
    private final String category;
    private final boolean requiresFuelTank;
    private final Double minTankCapacity;
    private final Double maxTankCapacity;

    FuelType(String displayName, String category, boolean requiresFuelTank, 
             Double minTankCapacity, Double maxTankCapacity) {
        this.displayName = displayName;
        this.category = category;
        this.requiresFuelTank = requiresFuelTank;
        this.minTankCapacity = minTankCapacity;
        this.maxTankCapacity = maxTankCapacity;
    }
}
