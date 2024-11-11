package simple.test.NN.Components;

import java.util.Random;

public class Neuron {
    private double result;
    // Нормальное значение (0-1)
    private double normResult;
    private double[] weight;

    // Функция нормализации(активации)
    private double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }

    public Neuron(int countWeight){
        weight = new double[countWeight];
        for(int i = 0; i < countWeight; i++){
            Random random = new Random();
            weight[i] = random.nextDouble(2) - 1;
        }
    }

    public Neuron(){}

    public void normalizeRes(){
        normResult = sigmoid(result);
    }

    public void addRes(double add){
        result += add;
    }

    // используется для установки входных данных
    public void setNormResult(double result){
        normResult = result;
    }

    public void setResult(double result){
        this.result = result;
    }

    public double getNormResult() {
        return normResult;
    }

    public double getWeight(int i){
        return weight[i];
    }

    public double getLenWeight(){
        return weight.length;
    }

    public void setWeight(double w, int i){
        weight[i] = w;
    }

}
