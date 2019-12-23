package com.iplanalyser;

import com.bridgelabzs.CSVBuilderException;
import com.bridgelabzs.CSVBuilderFactory;
import com.bridgelabzs.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IPLBowlingAdapter extends IPLAdapter {
    @Override
    public Map<String, IPLCensusDAO> loadIPLData(IPLAnalyser.IplDataType type, String... iplFilePath) throws CSVBuilderException {
        Map<String, IPLCensusDAO> iplCensusDAOMap = super.loadIPLData(IPLWktsDataCSV.class, iplFilePath[0]);
        if (iplFilePath.length>1)
            this.loadIPLData(iplCensusDAOMap,iplFilePath[1]);
        return iplCensusDAOMap;
    }

    private Map<String, IPLCensusDAO> loadIPLData(Map<String, IPLCensusDAO> censusIPLMap, String iplFilePath) throws CSVBuilderException {

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(iplFilePath));
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createOpenCSVBuilder();
            Iterator<IPLRunsDataCSV> iplCsvIterator = icsvBuilder.getCSVFileIterator(reader, IPLRunsDataCSV.class);
            Iterable<IPLRunsDataCSV> csvIterable = () -> iplCsvIterator;

            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState->censusIPLMap.get(csvState.player)!=null)
                    .forEach(csvState->censusIPLMap.get(csvState.player).average=Double.parseDouble(csvState.avg));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CSVBuilderException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.FILE_PROBLEM);
        }
        return censusIPLMap;
    }

}
