package com.iplanalysertest;

import com.bridgelabzs.CSVBuilderException;
import com.iplanalyser.IPLAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class IPLAnalyserBallerTest {

    private static final String EMPTY_IPL_FILE = "./src/test/resources/EmptyIPLFile.csv";
    IPLAnalyser iplAnalyser=new IPLAnalyser();

    private static final String INCORRECT_IPL_FILE = "./src/main/resources/IPL2019FactsheetMostWkts.csv";
    private final String IPL_MOST_WKTS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";

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
            int count = iplAnalyser.loadIPLWicketsData(IPL_MOST_WKTS_FILE_PATH);
            System.out.printf("count is "+count);
            Assert.assertEquals(99,count);
        } catch (CSVBuilderException e) {
        }
    }
}
