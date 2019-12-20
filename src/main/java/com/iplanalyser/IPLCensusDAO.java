package com.iplanalyser;

public class IPLCensusDAO {

    public String player;
    public double average;
    public double strikingRate;
    public int fours;
    public int six;
    public int runs;
    public double econ;
    public int fourWicket;
    public int fiveWicket;

    public IPLCensusDAO(IPLRunsDataCSV iplRunsDataCSV) {
        this.player= iplRunsDataCSV.player;
        this.average=Double.parseDouble(iplRunsDataCSV.avg);
        this.strikingRate= iplRunsDataCSV.sr;
        this.fours= iplRunsDataCSV.fours;
        this.runs= iplRunsDataCSV.runs;
        this.six= iplRunsDataCSV.six;
    }

    public IPLCensusDAO(IPLWktsDataCSV iplWktsDataCSV) {
        this.player=iplWktsDataCSV.player;
        this.average=iplWktsDataCSV.average;
        this.strikingRate=iplWktsDataCSV.strikingRate;
        this.fiveWicket=iplWktsDataCSV.fiveWicket;
        this.fourWicket=iplWktsDataCSV.fourWicket;
        this.econ=iplWktsDataCSV.econ;
    }
}
