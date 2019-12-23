package com.iplanalyser;

import com.bridgelabzs.CSVBuilderException;

import java.util.Map;

public class IPLBattingAdapter extends IPLAdapter {
    @Override
    public Map<String, IPLCensusDAO> loadIPLData(IPLAnalyser.IplDataType type, String... iplFilePath) throws CSVBuilderException {
        Map<String, IPLCensusDAO> iplCensusDAOMap = super.loadIPLData(IPLRunsDataCSV.class, iplFilePath[0]);
        return iplCensusDAOMap;
    }
}
