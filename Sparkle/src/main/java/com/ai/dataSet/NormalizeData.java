package com.ai.dataSet;

public class NormalizeData {
    //TODO заменить на массив функций, а то это пиздец

    public static double normalize(String line, int i){
        switch(i)
        {
            case 1:
                try {
                    return (Double.parseDouble("" + line.charAt(5) + line.charAt(6)) - 1) / (12 - 1);
                } catch (Exception e) {
                    return -1;
                }
            case 2:
                if ("Male".equals(line)) return 1;
                if ("Female".equals(line)) return 0;
                return -1;
            case 3:
                if ("Service".equals(line)) return 1;
                if ("Product".equals(line)) return 0;
                return -1;
            case 4:
                if ("Yes".equals(line)) return 1;
                if ("No".equals(line)) return 0;
                return -1;    
            case 5:
                try {
                    double temp = Double.parseDouble(line) / 5;
                    return (temp < 0 || temp > 1) ? -1 : temp;
                } catch (Exception e) {
                    return -1;
                }
            case 6:
                try {
                    double temp = (Double.parseDouble(line) - 1) / (10 - 1);
                    return (temp < 0 || temp > 1) ? -1 : temp;
                } catch (Exception e) {
                    return -1;
                }
            case 7:
                try {
                    double temp = Double.parseDouble(line) / 10;
                    return (temp < 0 || temp > 1) ? -1 : temp;
                } catch (Exception e) {
                    return -1;
                }
            case 8:
                try {
                    double temp = Double.parseDouble(line);
                    return (temp < 0 || temp > 1) ? -1 : temp;
                } catch (Exception e) {
                    return -1;
                }
            default:
                return -1;
        }
    }
}
