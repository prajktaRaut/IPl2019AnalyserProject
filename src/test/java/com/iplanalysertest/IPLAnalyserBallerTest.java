package com.iplanalysertest;

import com.bridgelabzs.CSVBuilderException;
import com.iplanalyser.IPLAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class IPLAnalyserBallerTest {

    IPLAnalyser iplAnalyser=new IPLAnalyser();
    private final String IPL_MOST_WKTS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";

    @Test
    public void method_ToCheck_IPLFileExist_ShouldReturnExist() {
        String result = iplAnalyser.checkILLFilePresence(IPL_MOST_WKTS_FILE_PATH);
        Assert.assertEquals("EXIST", result);
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
