package com.ai.dataSet;

import java.util.ArrayList;

public class DataSet {
    final private String fileName;
    private ArrayList<Data> dataSet;

    public DataSet(String fileName){
        this.fileName = fileName;
        dataSet = new ArrayList<>();
    }

    public ArrayList<Data> getDataSet(){
        return dataSet;
    }

    public double[][] getInList(){
        double[][] res = new double[dataSet.size()][];
        for(int i = 0; i < dataSet.size(); i++){
            res[i] = dataSet.get(i).getIn();
        }
        return res;
    }

    public double[][] getOutList(){
        double[][] res = new double[dataSet.size()][];
        for(int i = 0; i < dataSet.size(); i++){
            res[i] = dataSet.get(i).getOut();
        }
        return res;
    }

    public void loadFromCsv(){
        CSVReader reader = new CSVReader();
        dataSet = reader.readCsv(fileName);
    }

    public void setDataSet(ArrayList<Data> dataSet){
        this.dataSet = dataSet;
    }

    public void push_back(Data data){
        dataSet.add(data);
    }
}
