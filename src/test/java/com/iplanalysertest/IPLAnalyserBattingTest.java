package com.iplanalysertest;

import com.bridgelabzs.CSVBuilderException;
import com.google.gson.Gson;
import com.iplanalyser.FieldNameForSorting;
import com.iplanalyser.IPLAnalyser;
import com.iplanalyser.IPLRunsDataCSV;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IPLAnalyserBattingTest {

    private static final String WRONG_IPL_FILE_TYPE = "./src/test/resources/IPL2019FactsheetMostRuns.txt";
    private final String IPL_MOST_RUNS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    private final String WRONG_IPL_FILE_PATH = "./src/main/resources/IPL2019FactsheetMostRuns.csv";
    private final String INCORRECT_IPL_FILE = "./src/test/resources/IPL2019Data.csv";
    private final String EMPTY_IPL_FILE = "./src/test/resources/EmptyIPLFile.csv";
    private final String IPL_FILE_FOR_WRONG_DELIMITER_OR_HEADER_POSITION = "./src/test/resources/NewIPLDataFile.csv";

    IPLAnalyser iplAnalyser = new IPLAnalyser();

    @Test
    public void method_ToCheck_IPLFileExist_ShouldReturnExist() {
        String result = iplAnalyser.checkILLFilePresence(IPL_MOST_RUNS_FILE_PATH);
        Assert.assertEquals("EXIST", result);
    }

    @Test
    public void method_ToCheck_IPLFileExist_ShouldReturnDoesNotExist() {
        String result = iplAnalyser.checkILLFilePresence(INCORRECT_IPL_FILE);
        Assert.assertEquals("DOES NOT EXIST", result);
    }

    @Test
    public void method_ToCheck_EmptyIPLFile_ShouldReturnTrue() {
        boolean result = iplAnalyser.checkIPLFileEmptyOrNot(EMPTY_IPL_FILE);
        Assert.assertEquals(true, result);
    }

    @Test
    public void method_ToCheck_NOtEmptyIPLFile_ShouldReturnFalse() {
        boolean result = iplAnalyser.checkIPLFileEmptyOrNot(IPL_MOST_RUNS_FILE_PATH);
        Assert.assertEquals(false, result);
    }

    @Test
    public void loadIPLData_ShouldReturnCorrectRecords() {
        int result = 0;
        try {
            result = iplAnalyser.loadIPLRunsData(IPL_MOST_RUNS_FILE_PATH);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void loadIPLData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.loadIPLRunsData(WRONG_IPL_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void loadIPLData_WithWrongFileType_ShouldThrowException_() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.loadIPLRunsData(WRONG_IPL_FILE_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void loadIPLFileData_WithWrongDelimiterPosition_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.loadIPLRunsData(IPL_FILE_FOR_WRONG_DELIMITER_OR_HEADER_POSITION);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.HEADER_OR_DELIMITER_PROBLEM, e.type);
        }
    }

    @Test
    public void loadIPLFileData_WithWrongHeader_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.loadIPLRunsData(IPL_FILE_FOR_WRONG_DELIMITER_OR_HEADER_POSITION);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.HEADER_OR_DELIMITER_PROBLEM, e.type);
        }
    }

    @Test
    public void sortIPLFileData_OnBattingAverage_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.loadIPLRunsData(IPL_MOST_RUNS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.Average);
            IPLRunsDataCSV[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLRunsDataCSV[].class);
            Assert.assertEquals("MS Dhoni", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnStrikingRate_ShouldReturnSortedRecords() {
        String iplCensusCSV = null;
        try {
            iplAnalyser.loadIPLRunsData(IPL_MOST_RUNS_FILE_PATH);
            iplCensusCSV = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.Striking_Rate);
            IPLRunsDataCSV[] censusCSV = new Gson().fromJson(iplCensusCSV, IPLRunsDataCSV[].class);
            Assert.assertEquals("Ishant Sharma", censusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnSixesAndFours_ShouldReturnSortedResult() {
        String iplCensusCsv = null;
        try {
            iplAnalyser.loadIPLRunsData(IPL_MOST_RUNS_FILE_PATH);
            iplCensusCsv = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.ResultOfSixesWithFours);
            IPLRunsDataCSV[] censusCSV = new Gson().fromJson(iplCensusCsv, IPLRunsDataCSV[].class);
            Assert.assertEquals("Andre Russell", censusCSV[0].player);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sortIPLFileData_OnBestStrikingRateAndSixesFours_ShouldReturnSortedResult() {
        String iplCensusCsv=null;
        try {
            iplAnalyser.loadIPLRunsData(IPL_MOST_RUNS_FILE_PATH);
            iplCensusCsv=iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.StrikingRateWithSixesAndFours);
            IPLRunsDataCSV[] censusCSV=new Gson().fromJson(iplCensusCsv, IPLRunsDataCSV[].class);
            Assert.assertEquals("Andre Russell",censusCSV[0].player);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sortIPLFileData_OnAverageWithStrikingRate_ShouldReturnSortedResult() {
        String iplCensusCsv=null;
        try {
            iplAnalyser.loadIPLRunsData(IPL_MOST_RUNS_FILE_PATH);
            iplCensusCsv=iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.AverageWithStrikingRate);
            IPLRunsDataCSV[] censusCSV=new Gson().fromJson(iplCensusCsv, IPLRunsDataCSV[].class);
            Assert.assertEquals("MS Dhoni",censusCSV[0].player);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sortIPLFileData_OnRunsWithAverage_ShouldReturnSortedResult() {
        String iplCensusCsv = null;
        try {
            iplAnalyser.loadIPLRunsData(IPL_MOST_RUNS_FILE_PATH);
            iplCensusCsv = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.RunsWithAverage);
            IPLRunsDataCSV[] censusCSV = new Gson().fromJson(iplCensusCsv, IPLRunsDataCSV[].class);
            Assert.assertEquals("David Warner ", censusCSV[0].player);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }
}
