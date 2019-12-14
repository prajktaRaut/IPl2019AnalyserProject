package com.iplanalyser;

import java.io.File;

public class IPLAnalyser {

    public String checkIPLFilePresensce(String ipl_file_path) {
        File file = new File(ipl_file_path);
        if (file.exists())
            return "EXIST";
        return "DOES NOT EXIST";
    }
}
