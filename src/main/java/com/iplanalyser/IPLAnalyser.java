package com.iplanalyser;

import com.bridgelabzs.CSVBuilderException;
import com.google.gson.Gson;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class IPLAnalyser {

    Map<String, IPLCensusDAO> censusIPLMap = new HashMap<>();
    Map<FieldNameForSorting, Comparator<IPLCensusDAO>> fieldComparatorMap = new HashMap<>();

    public IPLAdapter iplAdapter;

    public void setIplAdapter(IPLAdapter iplAdapter) {
        this.iplAdapter = iplAdapter;
    }

    public enum IplDataType
    {
        RUNS,WICKET
    }

    IplDataType type;

    public IPLAnalyser() {
        this.fieldComparatorMap.put(FieldNameForSorting.BattingAverage, Comparator.comparing(field -> field.average, Comparator.reverseOrder()));
        this.fieldComparatorMap.put(FieldNameForSorting.BowlingAverage, Comparator.comparing(field -> field.bowlingAverage, Comparator.reverseOrder()));
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

        this.fieldComparatorMap.put(FieldNameForSorting.EconomyRate, Comparator.comparing(field -> field.econ));
        this.fieldComparatorMap.put(FieldNameForSorting.ResultOfFiveAndFourWkts, new SortedOnFiveWktsWithFourWkts().reversed());
        this.fieldComparatorMap.put(FieldNameForSorting.StrikingRateWith5WAnd4W, new SortedOnFiveWktsWithFourWkts().reversed().thenComparing((fields -> fields.strikingRate)));

        Comparator<IPLCensusDAO> wicketComparator = Comparator.comparing(field -> field.wicket);
        this.fieldComparatorMap.put(FieldNameForSorting.MaximumWicketWithAverage, wicketComparator.reversed().thenComparing(averageComparator));

        Comparator<IPLCensusDAO> battingAvgComparator = Comparator.comparing(field -> field.average);
        Comparator<IPLCensusDAO> bowlingAvgComparator = Comparator.comparing(field -> field.bowlingAverage);
        this.fieldComparatorMap.put(FieldNameForSorting.BestBallingAndBattingAverage,battingAvgComparator.thenComparing(bowlingAvgComparator).reversed());
        Comparator<IPLCensusDAO> resultOfAverage=battingAvgComparator.thenComparing(bowlingAvgComparator);
        this.fieldComparatorMap.put(FieldNameForSorting.MostRunsWithWickets,resultOfAverage.reversed());
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

    public int loadIPLData(IplDataType type,String... csvPath) throws CSVBuilderException {
        this.type=type;
        censusIPLMap = iplAdapter.loadIPLData(type, csvPath);
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



