package com.ai.dataSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader {
    public CSVReader() {}
    
    public ArrayList<Data> readCsv(String fileName){
        ArrayList<Data> resultList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String dataLine = br.readLine();
            double[] doubleData = new double[8];
            while ((dataLine = br.readLine()) != null){
                boolean status = false;
                String[] splitLine = dataLine.split(",");
                int len = splitLine.length;
                if(len != 9) continue;
                for (int i = 1; i < len; i++){
                    double normResult = NormalizeData.normalize(splitLine[i], i);
                    if (normResult == -1) {
                        status = true; break;
                    }
                    doubleData[i - 1] = normResult;
                }
                if (status) continue;
                resultList.add(new Data(doubleData));
            }
            return resultList;
        } catch (Exception e) {

            //TODO пофиксить возвращение пустого списка
            return resultList;
        }
    }
}
