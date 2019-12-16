package com.iplanalyser;

public class IPLCensusDAO {

    public String player;
    public double average;
    public double strikingRate;
    public int fours;
    public int six;
    public int runs;


    public IPLCensusDAO(IPLCensusCSV iplCensusCSV) {

        this.player=iplCensusCSV.player;
        this.average=Double.parseDouble(iplCensusCSV.avg);
        this.strikingRate=iplCensusCSV.sr;
        this.fours=iplCensusCSV.fours;
        this.runs=iplCensusCSV.runs;

    }
}
