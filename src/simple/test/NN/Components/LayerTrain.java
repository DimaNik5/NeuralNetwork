package simple.test.NN.Components;


public class LayerTrain extends Layer {
    private final NeuronTrain[] neuronTrains;
    // Нейрон смещения
    private NeuronTrain b;

    public LayerTrain(int length, int nexLen){
        super(length, nexLen);
        neuronTrains = new NeuronTrain[length];
        if(nexLen > 0){
            for(int i = 0; i < length; i++){
                neuronTrains[i] = new NeuronTrain(nexLen);
            }
            b = new NeuronTrain((nexLen));
        }
        else {
            for(int i = 0; i < length; i++){
                neuronTrains[i] = new NeuronTrain();
            }
            b = new NeuronTrain();
        }
    }

    public void print(int i, boolean last){
        if(i < 0 || i > neuronTrains.length) return;
        if(i == neuronTrains.length){
            if(last) return;
            if(b == null) return;
            System.out.print("(");
            for(int j = 0; j < b.getLenWeight(); j++){
                System.out.print(String.format(" %.2f",b.getWeight(j)) + " ");
            }
            System.out.print(")");
            return;
        }
        System.out.print(String.format(" %.2f", neuronTrains[i].getNormResult()) + "(");
        if(last) return;
        for(int j = 0; j < neuronTrains[i].getLenWeight(); j++){
            System.out.print(String.format(" %.2f", neuronTrains[i].getWeight(j)) + " ");
        }
        System.out.print(")");
    }

    // Нормализация(активация)
    public void normalize(){
        for (NeuronTrain neuronTrain : neuronTrains) {
            neuronTrain.normalizeRes();
        }
    }

    // Установка входных значений
    public void setInput(double[] in){
        for (int i = 0; i < in.length; i++) {
            neuronTrains[i].setResult(in[i]);
            neuronTrains[i].setNormResult(in[i]);
        }
    }

    // Получение нормального значения i-того нейрона
    public double getNormResult(int i){
        if(i < 0 || i >= neuronTrains.length) return 0;
        return neuronTrains[i].getNormResult();
    }

    // Получение списка нормальных значений нейронов
    public double[] getNormResult(){
        double[] res = new double[neuronTrains.length];
        for(int i = 0; i < neuronTrains.length; i++){
            res[i] = neuronTrains[i].getNormResult();
        }
        return res;
    }

    // Получение веса от i к j нейрону
    public double getWeight(int i, int j){
        if(i < 0 || i >= neuronTrains.length || j < 0 || j >= neuronTrains[i].getLenWeight()) return 0;
        return neuronTrains[i].getWeight(j);
    }

    // Получение веса нейрона смещения
    public double getWeightB(int i){
        return b.getWeight(i);
    }

    // добавление к результату i-того нейрона
    public void addResult(int i, double add){
        if(i < 0 || i >= neuronTrains.length) return;
        neuronTrains[i].addRes(add);
    }

    public int getLength(){
        return neuronTrains.length;
    }

    // Расчет и получение списка дельт
    public double[] setDelta(double[] ideal, boolean last){
        double[] res = new double[neuronTrains.length];
        for(int i = 0; i < neuronTrains.length; i++){
            // Выходной слой
            if(last){
                // Разница между идеалом
                res[i] = neuronTrains[i].setDelta(ideal[i] - neuronTrains[i].getNormResult());
            }
            else{
                // Сумма произведений весов на соот. дельты следующего слоя
                double sum = 0;
                for(int j = 0; j < ideal.length; j++){
                    sum += neuronTrains[i].getWeight(j) * ideal[j];
                }
                res[i] = neuronTrains[i].setDelta(sum);
            }
        }
        return res;
    }

    // Установка новых весов
    public double setDeltaWeight(double[] delta, double speed, double alpha){
        double max = 0, t;
        for(NeuronTrain neuronTrain : neuronTrains){
            t = neuronTrain.setDeltaWeight(delta, speed, alpha);
            if(t > max) max = t;
        }
        t = b.setDeltaWeight(delta, speed, alpha);
        if(t > max) max = t;
        return max; // максимальный новый вес
    }

    // Получение списка дельт
    public double[] getDeltas(){
        double[] res = new double[neuronTrains.length];
        for(int i = 0; i < neuronTrains.length; i++){
            res[i] = neuronTrains[i].getDelta();
        }
        return res;
    }

    // Установка результатов нейронов в 0
    public void setZero(){
        for (NeuronTrain neuronTrain : neuronTrains){
            neuronTrain.setResult(0);
        }
    }

    public boolean setWeight(String[] weights, int i){
        if(i < 0 || i >= neuronTrains.length) return false;
        try{
            for(int j = 0; j < weights.length; j++){
                neuronTrains[i].setWeight(Double.parseDouble(weights[j]), j);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean setWeightB(String[] weights){
        try{
            for(int j = 0; j < weights.length; j++){
                b.setWeight(Double.parseDouble(weights[j]), j);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Пропорциональной деление весов нейронов
    public void divWeight(double div){
        for(NeuronTrain neuronTrain : neuronTrains){
            neuronTrain.divWeight(div);
        }
    }
}
