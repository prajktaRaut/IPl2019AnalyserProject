package com.iplanalyser;

import com.bridgelabzs.CSVBuilderException;
import com.bridgelabzs.CSVBuilderFactory;
import com.bridgelabzs.ICSVBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class IPLAdapter {

    Map<String, IPLCensusDAO> censusIPLMap = new HashMap<>();

    public abstract Map<String,IPLCensusDAO> loadIPLData(IPLAnalyser.IplDataType type, String... iplFilePath) throws CSVBuilderException;

    public <E> Map<String,IPLCensusDAO> loadIPLData(Class<E> iplClass,String... iplFilePath) throws CSVBuilderException {
        try {
            Reader reader=Files.newBufferedReader(Paths.get(iplFilePath[0]));
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createOpenCSVBuilder();
            Iterator<E> iplCsvIterator=icsvBuilder.getCSVFileIterator(reader,iplClass);
            Iterable<E> csvIterable = ()-> iplCsvIterator;
            if (iplClass.getName().equals("com.iplanalyser.IPLRunsDataCSV"))
            {
                StreamSupport.stream(csvIterable.spliterator(),false)
                        .map(IPLRunsDataCSV.class::cast)
                        .forEach(iplCensusCsv->censusIPLMap.put(iplCensusCsv.player,new IPLCensusDAO(iplCensusCsv)));
            }
            else if (iplClass.getName().equals("com.iplanalyser.IPLWktsDataCSV"))
            {
                StreamSupport.stream(csvIterable.spliterator(),false)
                        .map(IPLWktsDataCSV.class::cast)
                        .forEach(iplCensusCsv->censusIPLMap.put(iplCensusCsv.player,new IPLCensusDAO(iplCensusCsv)));
            }
            return censusIPLMap;

        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),CSVBuilderException.ExceptionType.FILE_PROBLEM);
        } catch (CSVBuilderException| RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.HEADER_OR_DELIMITER_PROBLEM);
        }
    }

}
