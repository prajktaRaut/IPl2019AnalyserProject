package com.iplanalysertest;

import com.bridgelabzs.CSVBuilderException;
import com.iplanalyser.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoTest {

    private final String IPL_MOST_WKTS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";
    private final String IPL_MOST_RUNS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    Map<String, IPLCensusDAO> userList;

    @Before
    public void setUp(){
        userList=new HashMap<>();
        userList.put("1",new IPLCensusDAO("MS Dhoni",145,33.54,67,23,8));
        userList.put("2",new IPLCensusDAO("Virat Kohali",125,12.54,7,31,10));
    }

    @Test
    public void loadIPLRunsData_ShouldReturn_CorrectRecords()
    {
            IPLAdapter iplAdapter=mock(IPLBattingAdapter.class);
        try {
            when(iplAdapter.loadIPLData(IPLAnalyser.IplDataType.RUNS,IPL_MOST_RUNS_FILE_PATH)).thenReturn(this.userList);
            IPLAnalyser iplAnalyser=new IPLAnalyser();
            iplAnalyser.setIplAdapter(iplAdapter);
            int count = iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.RUNS, IPL_MOST_RUNS_FILE_PATH);
            Assert.assertEquals(2,count);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadIPLWicketsData_ShouldReturn_CorrectRecords()
    {
        IPLAdapter iplAdapter=mock(IPLBattingAdapter.class);
        try {
            when(iplAdapter.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH)).thenReturn(this.userList);
            IPLAnalyser iplAnalyser=new IPLAnalyser();
            iplAnalyser.setIplAdapter(iplAdapter);
            int count = iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET, IPL_MOST_WKTS_FILE_PATH);
            Assert.assertEquals(2,count);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }

    }
}
