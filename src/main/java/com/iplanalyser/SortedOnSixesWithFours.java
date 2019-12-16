package com.iplanalyser;

import java.util.Comparator;

public class SortedOnSixesWithFours implements Comparator<IPLCensusDAO> {
    @Override
    public int compare(IPLCensusDAO o1, IPLCensusDAO o2) {
        return (((o1.six*6)+(o1.fours*4))-((o2.six*6) + (o2.fours*4)));
    }
}
