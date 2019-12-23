package com.iplanalyser;

import com.opencsv.bean.CsvBindByName;

public class IPLWktsDataCSV {

    @CsvBindByName(column = "PLAYER")
    public String player;

    @CsvBindByName(column = "Avg")
    public double average;

    @CsvBindByName(column = "SR")
    public double strikingRate;

    @CsvBindByName(column = "5w")
    public int fiveWicket;

    @CsvBindByName(column = "4w")
    public int fourWicket;

    @CsvBindByName(column = "Econ")
    public double econ;

    @CsvBindByName(column = "Wkts")
    public double wickets;

    public IPLWktsDataCSV() {
    }

    public IPLWktsDataCSV(String player, double average, double strikingRate, int fiveWicket, int fourWicket, double econ, double wickets) {
        this.player = player;
        this.average = average;
        this.strikingRate = strikingRate;
        this.fiveWicket = fiveWicket;
        this.fourWicket = fourWicket;
        this.econ = econ;
        this.wickets = wickets;
    }

    @Override
    public String toString() {
        return "IPLWktsDataCSV{" +
                "player='" + player + '\'' +
                ", average=" + average +
                ", strikingRate=" + strikingRate +
                ", fiveWicket=" + fiveWicket +
                ", fourWicket=" + fourWicket +
                ", econ=" + econ +
                ", wickets=" + wickets +
                '}';
    }
}
