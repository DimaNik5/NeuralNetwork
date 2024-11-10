package simple.test.NN;

import simple.test.NN.Components.Layer;


public class TrainingNeuralNetwork {
    private Layer[] layers;
    private double speed;
    private double alpha;
    private double maxWeight;

    public TrainingNeuralNetwork(int length, int[] lenLayer, double speed, double alpha, double maxWeight){
        if(length < 2) return;
        this.speed = speed;
        this.maxWeight = maxWeight;
        this.alpha = alpha;
        layers = new Layer[length];
        for(int i = 0; i < length; i++){
            if(lenLayer[i] < 0) return;
            layers[i] = new Layer(lenLayer[i], (i < length - 1 ? lenLayer[i + 1] : 0));
        }
    }

    public void train(DataSet dataSet, int epochs){
        double[][][] tests = dataSet.getTests();
        for (int k = 0; k < epochs; k++){
            //if(k == 0 || k == epochs - 1)
            System.out.println("\tEpoch " + k);
            for (double[][] test : tests) {
                //if (k == 0 || k == epochs - 1)
                System.out.println("Result: " + String.format(" %.2f", counting(test[0])[0]) + " Ideal : " + test[1][0]);
               // else counting(test[0]);
                correction(test[1]);
            }
        }
    }

    private double[] counting(double[] input){
        layers[0].setInput(input);
        for(int i = 0; i < layers.length - 1; i++){
            layers[i+1].setZero();
            if(i > 0)layers[i].normalize();
            for(int j = 0; j < layers[i].getLength(); j++){
                double curValue = layers[i].getNormResult(j);
                for(int k = 0; k < layers[i+1].getLength(); k++){
                    layers[i+1].addResult(k, curValue * layers[i].getWeight(j, k) + layers[i].getWeightB(k));
                }
            }
        }
        layers[layers.length - 1].normalize();
        return layers[layers.length - 1].getNormResult();
    }

    private void correction(double[] ideal){
        double[] curIdeal = ideal;
        for(int i = layers.length - 1; i > 0; i--){
            curIdeal = layers[i].setDelta(curIdeal, i == layers.length - 1);
        }
        boolean f = false;
        for(int i = 0; i < layers.length - 1; i++){
            f = f || (Math.abs(layers[i].setDeltaWeight(layers[i + 1].getDeltas(), speed, alpha)) > maxWeight);
        }
        if(f){
            for(int i = 0; i < layers.length - 1; i++){
                layers[i].divWeight(maxWeight / 2);
            }
        }

    }

}
