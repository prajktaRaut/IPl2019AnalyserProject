package com.iplanalyser;

import com.bridgelabzs.CSVBuilderException;
import com.bridgelabzs.CSVBuilderFactory;
import com.bridgelabzs.ICSVBuilder;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IPLAnalyser {

    Map<String, IPLCensusDAO> censusIPLMap = new HashMap<>();
    Map<FieldNameForSorting, Comparator<IPLCensusDAO>> fieldComparatorMap = new HashMap<>();

    public enum IplDataType
    {
        RUNS,WICKET
    }

    IplDataType type;

    public IPLAnalyser() {
        this.fieldComparatorMap.put(FieldNameForSorting.Average, Comparator.comparing(field -> field.average, Comparator.reverseOrder()));
        this.fieldComparatorMap.put(FieldNameForSorting.Striking_Rate, Comparator.comparing(fields -> fields.strikingRate, Comparator.reverseOrder()));
        this.fieldComparatorMap.put(FieldNameForSorting.ResultOfSixesWithFours, new SortedOnSixesWithFours().reversed());
        this.fieldComparatorMap.put(FieldNameForSorting.StrikingRateWithSixesAndFours, new SortedOnSixesWithFours().reversed().thenComparing((fields -> fields.strikingRate)));

        Comparator<IPLCensusDAO> averageComparator = Comparator.comparing(field -> field.average);
        Comparator<IPLCensusDAO> strikingRateComparator = Comparator.comparing(field -> field.strikingRate);
        Comparator<IPLCensusDAO> resultOfComparator = averageComparator.thenComparing(strikingRateComparator);
        this.fieldComparatorMap.put(FieldNameForSorting.AverageWithStrikingRate, resultOfComparator.reversed());

        Comparator<IPLCensusDAO> runComparator = Comparator.comparing(field -> field.runs);
        Comparator<IPLCensusDAO> avgComparator = Comparator.comparing(field -> field.average);
        Comparator<IPLCensusDAO> resultOfRunAvgComparator = runComparator.thenComparing(avgComparator);
        this.fieldComparatorMap.put(FieldNameForSorting.RunsWithAverage, resultOfRunAvgComparator.reversed());

    }

    public String checkILLFilePresence(String ipl_file_path) {
        File file = new File(ipl_file_path);
        if (file.exists())
            return "EXIST";
        return "DOES NOT EXIST";
    }

    public boolean checkIPLFileEmptyOrNot(String ipl_file_path) {
        File file = new File(ipl_file_path);
        if (file.length() == 0)
            return true;
        return false;
    }

    public int loadIPLData(IplDataType type,String csvPath) throws CSVBuilderException {
        IPLAdapter adapter = IPLAdapterFactory.getCsvType(type);
        this.type=type;
        censusIPLMap = adapter.loadIPLData(type, csvPath);
        return censusIPLMap.size();
    }

    public String sortIPLDataBasedOnFields(FieldNameForSorting fieldName) throws CSVBuilderException {
        if (censusIPLMap == null || censusIPLMap.size() == 0) {
            throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList arrayList = censusIPLMap.values().stream()
                .sorted(fieldComparatorMap.get(fieldName))
                .collect(Collectors.toCollection(ArrayList::new));

        String sortedStateCensusJson = new Gson().toJson(arrayList);
        return sortedStateCensusJson;
    }
}



