package com.leader.ren.model.bigscreen.entity;

import lombok.Data;

@Data
public class BigScreenStatistics {
    private Long id;

    private String month;

    private String billCountIn;

    private String billCountOut;

    private String billCountTotal;

    private String cargoValueIn;

    private String cargoValueOut;

    private String cargoValueTotal;

    private String cargoCount;

    private String tax;

    private String peopleIn;

    private String peopleOut;

    private String peopleTotal;

    private String planeBusIn;

    private String planeCargoIn;

    private String planeInTotal;

    private String planeBusOut;

    private String planeCargoOut;

    private String planeOutTotal;

    private String planeTotal;

    private String expressIn;

    private String expressOut;

    private String expressTotal;

    private String clueTrade;

    private String clueNonTrade;

    private String clueTotal;

    private String businessCar;

    private String businessShow;

    private String businessFinanceLease;

    private String businessElectricity;

    private String businessTotal;

}