package simple.test.NN.Components;


public class Layer {
    private final Neuron[] neurons;
    // Нейрон смещения
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

    public void print(int i, boolean last){
        if(i < 0 || i > neurons.length) return;
        if(i == neurons.length){
            if(last) return;
            if(b == null) return;
            System.out.print("(");
            for(int j = 0; j < b.getLenWeight(); j++){
                System.out.print(String.format(" %.2f",b.getWeight(j)) + " ");
            }
            System.out.print(")");
            return;
        }
        System.out.print(String.format(" %.2f",neurons[i].getNormResult()) + "(");
        if(last) return;
        for(int j = 0; j < neurons[i].getLenWeight(); j++){
            System.out.print(String.format(" %.2f",neurons[i].getWeight(j)) + " ");
        }
        System.out.print(")");
    }

    // Нормализация(активация)
    public void normalize(){
        for (Neuron neuron : neurons) {
            neuron.normalizeRes();
        }
    }

    // Установка входных значений
    public void setInput(double[] in){
        for (int i = 0; i < in.length; i++) {
            neurons[i].setResult(in[i]);
            neurons[i].setNormResult(in[i]);
        }
    }

    // Получение нормального значения i-того нейрона
    public double getNormResult(int i){
        if(i < 0 || i >= neurons.length) return 0;
        return neurons[i].getNormResult();
    }

    // Получение списка нормальных значений нейронов
    public double[] getNormResult(){
        double[] res = new double[neurons.length];
        for(int i = 0; i < neurons.length; i++){
            res[i] = neurons[i].getNormResult();
        }
        return res;
    }

    // Получение веса от i к j нейрону
    public double getWeight(int i, int j){
        if(i < 0 || i >= neurons.length || j < 0 || j >= neurons[i].getLenWeight()) return 0;
        return neurons[i].getWeight(j);
    }

    // Получение веса нейрона смещения
    public double getWeightB(int i){
        return b.getWeight(i);
    }

    // добавление к результату i-того нейрона
    public void addResult(int i, double add){
        if(i < 0 || i >= neurons.length) return;
        neurons[i].addRes(add);
    }

    public int getLength(){
        return neurons.length;
    }

    // Расчет и получение списка дельт
    public double[] setDelta(double[] ideal, boolean last){
        double[] res = new double[neurons.length];
        for(int i = 0; i < neurons.length; i++){
            // Выходной слой
            if(last){
                // Разница между идеалом
                res[i] = neurons[i].setDelta(ideal[i] - neurons[i].getNormResult());
            }
            else{
                // Сумма произведений весов на соот. дельты следующего слоя
                double sum = 0;
                for(int j = 0; j < ideal.length; j++){
                    sum += neurons[i].getWeight(j) * ideal[j];
                }
                res[i] = neurons[i].setDelta(sum);
            }
        }
        return res;
    }

    // Установка новых весов
    public double setDeltaWeight(double[] delta, double speed, double alpha){
        double max = 0, t;
        for(Neuron neuron : neurons){
            t = neuron.setDeltaWeight(delta, speed, alpha);
            if(t > max) max = t;
        }
        t = b.setDeltaWeight(delta, speed, alpha);
        if(t > max) max = t;
        return max; // максимальный новый вес
    }

    // Получение списка дельт
    public double[] getDeltas(){
        double[] res = new double[neurons.length];
        for(int i = 0; i < neurons.length; i++){
            res[i] = neurons[i].getDelta();
        }
        return res;
    }

    // Установка результатов нейронов в 0
    public void setZero(){
        for (Neuron neuron : neurons){
            neuron.setResult(0);
        }
    }

    // Пропорциональной деление весов нейронов
    public void divWeight(double div){
        for(Neuron neuron : neurons){
            neuron.divWeight(div);
        }
    }
}
