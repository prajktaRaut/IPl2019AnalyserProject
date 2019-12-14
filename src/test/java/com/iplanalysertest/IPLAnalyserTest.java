package com.iplanalysertest;

import com.bridgelabzs.CSVBuilderException;
import com.iplanalyser.IPLAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class IPLAnalyserTest {

    private final String IPL_FILE_PATH="/home/admin1/Documents/IPL2019Analyser/src/test/resources/IPL2019FactsheetMostRuns.csv";

    IPLAnalyser iplAnalyser=new IPLAnalyser();

    @Test
    public void method_ToCheck_IPLFileExist_ShouldReturnHappy() {

        String result=iplAnalyser.checkIPLFilePresensce(IPL_FILE_PATH);
        Assert.assertEquals("EXIST",result);
    }
}
