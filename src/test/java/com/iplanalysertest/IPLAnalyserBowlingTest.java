package com.iplanalysertest;

import com.bridgelabzs.CSVBuilderException;
import com.google.gson.Gson;
import com.iplanalyser.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IPLAnalyserBowlingTest {

    IPLAnalyser iplAnalyser=new IPLAnalyser();

    private static final String WRONG_IPL_FILE_TYPE ="./src/main/resources/IPL2019FactsheetMostWkts.txt";
    private static final String WRONG_IPL_FILE_PATH = "./src/main/resources/IPL2019FactsheetMostWkts.csv";
    private static final String EMPTY_IPL_FILE = "./src/test/resources/EmptyIPLFile.csv";
    private static final String INCORRECT_IPL_FILE = "./src/test/resources/IPL2019Runs.csv";
    private final String IPL_MOST_WKTS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";
    private static final String IPL_FILE_FOR_WRONG_DELIMITER_OR_HEADER_POSITION ="./src/test/resources/NewIPLWktsDataFile.csv";
    private final String IPL_MOST_RUNS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";

    @Test
    public void method_ToCheck_IPLFileExist_ShouldReturnExist() {
        String result = iplAnalyser.checkILLFilePresence(IPL_MOST_WKTS_FILE_PATH);
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
        boolean result = iplAnalyser.checkIPLFileEmptyOrNot(IPL_MOST_WKTS_FILE_PATH);
        Assert.assertEquals(false, result);
    }

    @Test
    public void loadIPLBallerData_ShouldReturnCorrectRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            int count = iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH);
            Assert.assertEquals(99,count);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void loadIPLData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,WRONG_IPL_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void loadIPLData_WithWrongFileType_ShouldThrowException_() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,WRONG_IPL_FILE_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void loadIPLFileData_WithWrongDelimiterPosition_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_FILE_FOR_WRONG_DELIMITER_OR_HEADER_POSITION);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.HEADER_OR_DELIMITER_PROBLEM, e.type);
        }
    }

    @Test
    public void loadIPLFileData_WithWrongHeader_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_FILE_FOR_WRONG_DELIMITER_OR_HEADER_POSITION);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.HEADER_OR_DELIMITER_PROBLEM, e.type);
        }
    }

    @Test
    public void sortIPLFileData_OnBowlingAverage_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.BowlingAverage);
            IPLWktsDataCSV[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLWktsDataCSV[].class);
            Assert.assertEquals("Krishnappa Gowtham", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnStrikingRate_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.Striking_Rate);
            IPLWktsDataCSV[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLWktsDataCSV[].class);
            Assert.assertEquals("Krishnappa Gowtham", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnEconomyRate_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.EconomyRate);
            IPLWktsDataCSV[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLWktsDataCSV[].class);
            Assert.assertEquals("Shivam Dube", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnStrikingRateWith5WAnd4W_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.StrikingRateWith5WAnd4W);
            IPLWktsDataCSV[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLWktsDataCSV[].class);
            Assert.assertEquals("Kagiso Rabada", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnBowlingAverageWithStrikingRate_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.AverageWithStrikingRate);
            IPLWktsDataCSV[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLWktsDataCSV[].class);
            Assert.assertEquals("Krishnappa Gowtham", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnMaximumWicketWithBowlingAverage_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.MaximumWicketWithAverage);
            IPLWktsDataCSV[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLWktsDataCSV[].class);
            Assert.assertEquals("Imran Tahir", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnBattingAverageAndBowlingAverage_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH,IPL_MOST_RUNS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.BestBallingAndBattingAverage);
            IPLCensusDAO[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLCensusDAO[].class);
            Assert.assertEquals("Andre Russell", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void sortIPLFileData_OnRunsAndWickets_ShouldReturnSortedRecords() {
        try {
            iplAnalyser.setIplAdapter(IPLAdapterFactory.getCsvType(IPLAnalyser.IplDataType.WICKET));
            iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH,IPL_MOST_RUNS_FILE_PATH);
            String iplCensusCSVS = iplAnalyser.sortIPLDataBasedOnFields(FieldNameForSorting.MostRunsWithWickets);
            IPLCensusDAO[] CensusCSV = new Gson().fromJson(iplCensusCSVS, IPLCensusDAO[].class);
            Assert.assertEquals("Andre Russell", CensusCSV[0].player);
        } catch (CSVBuilderException e) {
        }
    }
}
