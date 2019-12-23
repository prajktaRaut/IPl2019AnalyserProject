package com.iplanalyser;

import java.util.Comparator;

public class SortedOnFiveWktsWithFourWkts implements Comparator<IPLCensusDAO> {
    @Override
    public int compare(IPLCensusDAO t1, IPLCensusDAO t2) {
        return (((t1.fiveWicket*6)+(t1.fourWicket*4))-((t2.fiveWicket*6) + (t2.fourWicket*4)));
    }
}
