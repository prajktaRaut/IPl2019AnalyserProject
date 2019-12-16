package com.iplanalyser;

import com.opencsv.bean.CsvBindByName;

public class IPLCensusCSV {

    @CsvBindByName(column = "PLAYER")
    public String player;

    @CsvBindByName(column = "Avg")
    public String avg;

    @CsvBindByName(column = "SR")
    public double sr;

    @CsvBindByName(column = "4s")
    public int fours;

    @CsvBindByName(column = "6s")
    public int six;

    @CsvBindByName(column = "Runs")
    public int runs;
    public IPLCensusCSV() {
    }

    public IPLCensusCSV(String player, String avg, double sr, int fours, int six, int runs) {
        this.player = player;
        this.avg = avg;
        this.sr = sr;
        this.fours = fours;
        this.six = six;
        this.runs = runs;
    }

    @Override
    public String toString() {
        return "IPLCensusCSV{" +
                "player='" + player + '\'' +
                ", avg='" + avg + '\'' +
                ", sr=" + sr +
                ", fours=" + fours +
                ", six=" + six +
                ", runs=" + runs +
                '}';
    }
}
