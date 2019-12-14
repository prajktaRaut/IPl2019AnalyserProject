package com.iplanalysertest;

import com.bridgelabzs.CSVBuilderException;
import com.iplanalyser.IPLAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IPLAnalyserTest {

    private static final String WRONG_IPL_FILE_TYPE = "/src/test/resources/IPL2019FactsheetMostRuns.txt";
    private final String IPL_FILE_PATH="/home/admin1/Documents/IPL2019Analyser/src/test/resources/IPL2019FactsheetMostRuns.csv";
    private final String WRONG_IPL_FILE_PATH="./src/main/resources/IPL2019FactsheetMostRuns.csv";
    private final String INCORRECT_IPL_FILE="/home/admin1/Documents/IPL2019Analyser/src/test/resources/IPL2019Data.csv";
    private final String EMPTY_IPL_FILE="/home/admin1/Documents/IPL2019Analyser/src/test/resources/EmptyIPLFile.csv";

    IPLAnalyser iplAnalyser=new IPLAnalyser();

    @Test
    public void method_ToCheck_IPLFileExist_ShouldReturnExist() {

        String result=iplAnalyser.checkIPLFilePresensce(IPL_FILE_PATH);
        Assert.assertEquals("EXIST",result);
    }

    @Test
    public void method_ToCheck_IPLFileExist_ShouldReturnDoesNotExist() {

        String result=iplAnalyser.checkIPLFilePresensce(INCORRECT_IPL_FILE);
        Assert.assertEquals("DOES NOT EXIST",result);
    }

    @Test
    public void method_ToCheck_EmptyIPLFile_ShouldReturnTrue() {

        boolean result=iplAnalyser.checkIPLFileEmptyOrNot(EMPTY_IPL_FILE);
        Assert.assertEquals(true,result);
    }

    @Test
    public void method_ToCheck_NOtEmptyIPLFile_ShouldReturnFalse() {

        boolean result=iplAnalyser.checkIPLFileEmptyOrNot(IPL_FILE_PATH);
        Assert.assertEquals(false,result);
    }

    @Test
    public void loadIPLData_ShouldReturnCorrectRecords() {
        int result= 0;
        try {
            result = iplAnalyser.loadIPLData(IPL_FILE_PATH);
            Assert.assertEquals(101,result);
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void loadIPLData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.loadIPLData(WRONG_IPL_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WithWrongFile_ShouldThrowException_() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            iplAnalyser.loadIPLData(WRONG_IPL_FILE_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }
}
