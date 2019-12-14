package com.iplanalyser;

import com.bridgelabzs.CSVBuilderException;
import com.bridgelabzs.CSVBuilderFactory;
import com.bridgelabzs.ICSVBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class IPLAnalyser {

    public String checkIPLFilePresensce(String ipl_file_path) {
        File file = new File(ipl_file_path);
        if (file.exists())
            return "EXIST";
        return "DOES NOT EXIST";
    }

    public boolean checkIPLFileEmptyOrNot(String ipl_file_path)
    {
        File file = new File(ipl_file_path);
        if (file.length()==0)
            return true;
        return false;
    }

    public int loadIPLData(String csvPath) throws CSVBuilderException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvPath));
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createOpenCSVBuilder();
            Iterator<IPLCensusCSV> iterator = icsvBuilder.getCSVFileIterator(reader, IPLCensusCSV.class);
            Iterable<IPLCensusCSV> iterable = () -> iterator;
            int noOfEntries = (int) StreamSupport.stream(iterable.spliterator(), false).count();
            return noOfEntries;
        } catch (CSVBuilderException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.FILE_PROBLEM);
        }
    }


}
