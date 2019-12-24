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
    public void getlist()
    {
        try {
            IPLAdapter iplAdapter=mock(IPLBattingAdapter.class);
            when(iplAdapter.loadIPLData(IPLAnalyser.IplDataType.WICKET,IPL_MOST_WKTS_FILE_PATH)).thenReturn(this.userList);
            IPLAnalyser iplAnalyser=new IPLAnalyser();
            iplAnalyser.setIplAdapter(iplAdapter);
            int i = iplAnalyser.loadIPLData(IPLAnalyser.IplDataType.WICKET, IPL_MOST_WKTS_FILE_PATH);
            Assert.assertEquals(2,i);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }

    }
}
