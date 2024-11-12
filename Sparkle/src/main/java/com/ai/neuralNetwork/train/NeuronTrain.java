package com.ai.neuralNetwork.train;

import com.ai.neuralNetwork.Neuron;

import java.util.Random;

public class NeuronTrain extends Neuron {
    private double result;
    // Нормальное значение (0-1)
    private double normResult;
    private double[] weight;
    // Прошлые изменения весов (дельта весов)
    private double[] lastModWeight;
    private double delta;

    // Функция нормализации(активации)
    private double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }

    // Производная от функции ^
    private double derSigmoid(double res){
        return (1 - res) * res;
    }

    public NeuronTrain(int countWeight){
        weight = new double[countWeight];
        lastModWeight = new double[countWeight];
        for(int i = 0; i < countWeight; i++){
            Random random = new Random();
            weight[i] = random.nextDouble(2) - 1;
        }
    }

    public NeuronTrain(){}

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

    public void setWeight(double w, int i){
        weight[i] = w;
    }

    public double getLenWeight(){
        return weight.length;
    }

    // Вычисленное значение (разница от идеала/суииа произведений) домнажается на производную от функции
    public double setDelta(double delta){
        return (this.delta = delta * derSigmoid(normResult));
    }

    // Деление весов
    public void divWeight(double div){
        for(int i = 0; i < weight.length; i++)
            weight[i] /= div;
    }

    public double getDelta(){
        return delta;
    }

    // Установка весов
    public double setDeltaWeight(double[] delta, double speed, double alpha){
        double max = 0;
        // Проход п дельтам следующего слоя (по весам)
        for(int i = 0; i < delta.length; i ++){
            // result * delta[i] - GRADIENT
            // Изменение весов
            double wd = delta[i] * speed * result + alpha * lastModWeight[i];
            weight[i] += wd;
            if(weight[i] > max) max = weight[i];
            if(Math.abs(weight[i]) < 0.00001) weight[i] = 0;
            lastModWeight[i] = wd;
        }
        return max;
    }
}
