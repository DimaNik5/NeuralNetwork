package simple.test.NN.Components;

import java.util.Random;

public class Neuron {
    private double result;
    private double normResult;
    private double[] weight;
    private double[] lastModWeight;
    private double delta;

    private double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }

    private double derSigmoid(double res){
        return (1 - res) * res;
    }

    public Neuron(int countWeight){
        weight = new double[countWeight];
        lastModWeight = new double[countWeight];
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

    public double setDelta(double delta){
        return (this.delta = delta * derSigmoid(normResult));
    }

    public void divWeight(double div){
        for(int i = 0; i < weight.length; i++)
            weight[i] /= div;
    }

    public double getDelta(){
        return delta;
    }

    public double setDeltaWeight(double[] delta, double speed, double alpha){
        double max = 0;
        for(int i = 0; i < delta.length; i ++){
            double wd = delta[i] * speed * result + alpha * lastModWeight[i];
            weight[i] += wd;
            if(weight[i] > max) max = weight[i];
            if(weight[i] < 0.00001) weight[i] = 0;
            lastModWeight[i] = wd;
        }
        return max;
    }
}