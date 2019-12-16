package com.iplanalyser;

import com.bridgelabzs.CSVBuilderException;
import com.bridgelabzs.CSVBuilderFactory;
import com.bridgelabzs.ICSVBuilder;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IPLAnalyser {

    Map<String, IPLCensusDAO> censusIPLMap = new HashMap<>();
    Map<FieldNameForSorting, Comparator<IPLCensusDAO>> fieldComparatorMap = new HashMap<>();

    public IPLAnalyser() {
        this.fieldComparatorMap.put(FieldNameForSorting.Average, Comparator.comparing(field -> field.average, Comparator.reverseOrder()));
        this.fieldComparatorMap.put(FieldNameForSorting.Striking_Rate, Comparator.comparing(fields -> fields.strikingRate, Comparator.reverseOrder()));
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

    public int loadIPLData(String csvPath) throws CSVBuilderException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvPath));
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createOpenCSVBuilder();
            Iterator<IPLCensusCSV> iterator = icsvBuilder.getCSVFileIterator(reader, IPLCensusCSV.class);
            Iterable<IPLCensusCSV> iterable = () -> iterator;
            StreamSupport.stream(iterable.spliterator(), false)
                    .map(IPLCensusCSV.class::cast)
                    .forEach(censusCSV -> censusIPLMap.put(censusCSV.player, new IPLCensusDAO(censusCSV)));
            return this.censusIPLMap.size();

        } catch (CSVBuilderException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.HEADER_OR_DELIMITER_PROBLEM);
        }
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
