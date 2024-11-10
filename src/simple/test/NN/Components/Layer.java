package simple.test.NN.Components;


public class Layer {
    private final Neuron[] neurons;
    private Neuron b;

    public Layer(int length, int nexLen){
        neurons = new Neuron[length];
        if(nexLen > 0){
            for(int i = 0; i < length; i++){
                neurons[i] = new Neuron(nexLen);
            }
            b = new Neuron((nexLen));
        }
        else {
            for(int i = 0; i < length; i++){
                neurons[i] = new Neuron();
            }
            b = new Neuron();
        }
    }

    public void normalize(){
        for (Neuron neuron : neurons) {
            neuron.normalizeRes();
        }
    }

    public void setInput(double[] in){
        for (int i = 0; i < in.length; i++) {
            neurons[i].setResult(in[i]);
            neurons[i].setNormResult(in[i]);
        }
    }

    public double getNormResult(int i){
        if(i < 0 || i >= neurons.length) return 0;
        return neurons[i].getNormResult();
    }

    public double[] getNormResult(){
        double[] res = new double[neurons.length];
        for(int i = 0; i < neurons.length; i++){
            res[i] = neurons[i].getNormResult();
        }
        return res;
    }

    public double getWeight(int i, int j){
        if(i < 0 || i >= neurons.length || j < 0 || j >= neurons[i].getLenWeight()) return 0;
        return neurons[i].getWeight(j);
    }

    public double getWeightB(int i){
        return b.getWeight(i);
    }

    public void addResult(int i, double add){
        if(i < 0 || i >= neurons.length) return;
        neurons[i].addRes(add);
    }

    public int getLength(){
        return neurons.length;
    }

    public double[] setDelta(double[] ideal, boolean last){
        double[] res = new double[neurons.length];
        for(int i = 0; i < neurons.length; i++){
            if(last){
                res[i] = neurons[i].setDelta(ideal[i] - neurons[i].getNormResult());
            }
            else{
                double sum = 0;
                for(int j = 0; j < ideal.length; j++){
                    sum += neurons[i].getWeight(j) * ideal[j];
                }
                res[i] = neurons[i].setDelta(sum);
            }
        }
        return res;
    }

    public double setDeltaWeight(double[] delta, double speed, double alpha){
        double max = 0, t;
        for(Neuron neuron : neurons){
            t = neuron.setDeltaWeight(delta, speed, alpha);
            if(t > max) max = t;
        }
        t = b.setDeltaWeight(delta, speed, alpha);
        if(t > max) max = t;
        return max;
    }

    public double[] getDeltas(){
        double[] res = new double[neurons.length];
        for(int i = 0; i < neurons.length; i++){
            res[i] = neurons[i].getDelta();
        }
        return res;
    }

    public void setZero(){
        for (Neuron neuron : neurons){
            neuron.setResult(0);
        }
    }

    public void divWeight(double div){
        for(Neuron neuron : neurons){
            neuron.divWeight(div);
        }
    }
}
