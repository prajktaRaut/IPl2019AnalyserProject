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
    public double wicket;
    public double bowlingAverage;

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
        this.bowlingAverage=iplWktsDataCSV.average;
        this.strikingRate=iplWktsDataCSV.strikingRate;
        this.fiveWicket=iplWktsDataCSV.fiveWicket;
        this.fourWicket=iplWktsDataCSV.fourWicket;
        this.econ=iplWktsDataCSV.econ;
        this.wicket=iplWktsDataCSV.wickets;
    }

    public Object getIPLDTO(IPLAnalyser.IplDataType type)
    {
        if (type.equals(IPLAnalyser.IplDataType.RUNS))
            return new IPLRunsDataCSV(player,String.valueOf(average),strikingRate,fours,runs,six);
        return new IPLWktsDataCSV(player,average,strikingRate,fiveWicket,fourWicket,econ,wicket);
    }
}
