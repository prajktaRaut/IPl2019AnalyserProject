package com.iplanalyser;

import com.opencsv.bean.CsvBindByName;

public class IPLCensusCSV {

    @CsvBindByName(column = "POS")
    public int pos;

    @CsvBindByName(column = "PLAYER")
    public String player;

    @CsvBindByName(column = "Mat")
    public int mat;

    @CsvBindByName(column = "Inns")
    public int inns;

    @CsvBindByName(column = "NO")
    public int no;

    @CsvBindByName(column = "Runs")
    public String runs;

    @CsvBindByName(column = "HS")
    public String hs;

    @CsvBindByName(column = "Avg")
    public double avg;

    @CsvBindByName(column = "BF")
    public String bf;

    @CsvBindByName(column = "SR")
    public String sr;

    @CsvBindByName(column = "100")
    public int hundred;

    @CsvBindByName(column = "50")
    public int fifty;

    @CsvBindByName(column = "4s")
    public int fours;

    @CsvBindByName(column = "6s")
    public int six;

    public IPLCensusCSV() {
    }

    public double getAvg() {
        return avg;
    }

    @Override
    public String toString() {
        return "IPLCensusCSV{" +
                "pos=" + pos +
                ", player='" + player + '\'' +
                ", mat=" + mat +
                ", inns=" + inns +
                ", no=" + no +
                ", runs='" + runs + '\'' +
                ", hs=" + hs +
                ", avg=" + avg +
                ", bf=" + bf +
                ", sr=" + sr +
                ", hundred=" + hundred +
                ", fifty=" + fifty +
                ", fours=" + fours +
                ", six=" + six +
                '}';
    }
}
