package com.ai.dataSet;

public class Data {
    private final static byte joinDay = 0;
    private final static byte gender = 1;
    private final static byte companyType = 2;
    private final static byte distanceWork = 3;
    private final static byte workLoad = 4;
    private final static byte workingTime = 5;
    private final static byte mentalFatigueScore = 6;
    private final static byte burnRate = 7;

    private final static byte countIn = 7;
    private final static byte countOut = 1;

    private double[] in;
    private double[] out;

    public Data(double[] doubleData){
        in = new double[countIn];
        out = new double[countOut];

        in[joinDay] = doubleData[joinDay];
        in[gender] = doubleData[gender];
        in[companyType] = doubleData[companyType];
        in[distanceWork] = doubleData[distanceWork];
        in[workLoad] = doubleData[workLoad];
        in[workingTime] = doubleData[workingTime];
        in[mentalFatigueScore] = doubleData[mentalFatigueScore];

        out[0] = doubleData[burnRate];
    }

    public Data(double  joinDay, double gender, double companyType, double distanceWork, double workLoad, double workingTime, double mentalFatigueScore, double burnRate){
        in = new double[countIn];
        out = new double[countOut];

        in[this.joinDay] = joinDay;
        in[this.gender] = gender;
        in[this.companyType] = companyType;
        in[this.distanceWork] = distanceWork;
        in[this.workLoad] = workLoad;
        in[this.workingTime] = workingTime;
        in[this.mentalFatigueScore] = mentalFatigueScore;

        out[0] = burnRate;
    }

    public double getJoinDay(){
        return in[joinDay];
    }

    public double getGender(){
        return in[gender];
    }

    public double getCompanyType(){
        return in[companyType];
    }

    public double getDistanceWork(){
        return in[distanceWork];
    }

    public double getWorkLoad(){
        return in[workLoad];
    }

    public double getWorkingTime(){
        return in[workingTime];
    }

    public double getMentalFatigueScore(){
        return in[mentalFatigueScore];
    }

    public double getBurnRate(){
        return out[0];
    }

    public void setJoinDay(double joinDay){
        in[this.joinDay] = joinDay;
    }

    public void setGender(double gender){
        in[this.gender] = gender;
    }

    public void setCompanyType(double companyType){
        in[this.companyType] = companyType;
    }

    public void setDistanceWork(double distanceWork){
        in[this.distanceWork] = distanceWork;
    }

    public void setWorkLoad(double workLoad){
        in[this.workLoad] = workLoad;
    }

    public void setWorkingTime(double workingTime){
        in[this.workingTime] = workingTime;
    }
    
    public void setMentalFatigueScore(double mentalFatigueScore){
        in[this.mentalFatigueScore] = mentalFatigueScore;
    }

    public void setBurnRate(double burnRate){
        out[0] = burnRate;
    }

    public double[] getIn() {
        return in;
    }

    public double[] getOut() {
        return out;
    }
}

