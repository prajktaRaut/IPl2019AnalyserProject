package com.iplanalyser;

public class IPLAdapterFactory {

    public static IPLAdapter getCsvType(IPLAnalyser.IplDataType type)
    {
        if (type.equals(IPLAnalyser.IplDataType.RUNS))
            return new IPLRunsAdapter();
        if (type.equals(IPLAnalyser.IplDataType.WICKET))
            return new IPLWicketsAdapter();
        return null;
    }
}
