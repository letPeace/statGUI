package sub;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.LinkedHashMap;

public class Calc{

    private Double geometricMean;
    private Double mean;
    private Double standardDeviation;
    private Double sampleRange;
    private Double covariance;
    private Double lenght;
    private Double koefVariance;
    private Double intervalLow;
    private Double intervalHigh;
    private Double variance;
    private Double min;
    private Double max;

    public Calc(double arr1[], double arr2[]){
        this.geometricMean = StatUtils.geometricMean(arr1);
        this.mean = StatUtils.mean(arr1);
        this.standardDeviation = Math.sqrt(StatUtils.variance(arr1));
        this.sampleRange = (StatUtils.max(arr1) - StatUtils.min(arr1));
        this.covariance = new Covariance().covariance(arr1,arr2);
        this.lenght = Double.valueOf(arr1.length);
        this.koefVariance = (Math.sqrt(StatUtils.variance(arr1))/Math.abs(StatUtils.mean(arr1)));
        this.intervalLow = (StatUtils.mean(arr1)-(new TDistribution(arr1.length-1).inverseCumulativeProbability(0.95)*Math.sqrt(StatUtils.variance(arr1))))/Math.sqrt(arr1.length);
        this.intervalHigh = (StatUtils.mean(arr1)+(new TDistribution(arr1.length-1).inverseCumulativeProbability(0.95)*Math.sqrt(StatUtils.variance(arr1))))/Math.sqrt(arr1.length);
        this.variance = StatUtils.variance(arr1);
        this.min = StatUtils.min(arr1);
        this.max = StatUtils.max(arr1);
    }

    private void fillMap(LinkedHashMap<String, Double> hashMap){

        hashMap.put("Среднее геометрическое", this.geometricMean);
        hashMap.put("Среднее арифметическое", this.mean);
        hashMap.put("Стандартное отклонение", this.standardDeviation);
        hashMap.put("Размах выборки", this.sampleRange);
        hashMap.put("Коэффициент ковариации", this.covariance);
        hashMap.put("Количество элементов", this.lenght);
        hashMap.put("Коэффициент вариации", this.koefVariance);
        hashMap.put("Доверительный интервал (нижний)", this.intervalLow);
        hashMap.put("Доверительный интервал (верхний)", this.intervalHigh);
        hashMap.put("Дисперсия", this.variance);
        hashMap.put("Минимум", this.min);
        hashMap.put("Максимум", this.max);

    }

    public LinkedHashMap<String, Double> getHashMap(){
        LinkedHashMap<String, Double> hashMap = new LinkedHashMap<>();
        fillMap(hashMap);
        return hashMap;
    }

}
