package bit.ankem1.WeatherWorks;

import java.util.ArrayList;

/**
 * Created by matt on 3/06/15.
 */
// This class exists to perform operations on our data
public class Worker
{
    public static double calculateVariance(ArrayList<Double> numbers)
    {
        // first calculate the mean.
        double mean = 0;
        for (int i = 0; i < numbers.size(); i++)
        {
            mean += numbers.get(i);
        }

        mean = mean / numbers.size();

        // Then for each number:
        //      Subtract the Mean and square the result (the squared difference).
        ArrayList<Double> squaredDifferences = new ArrayList<>();

        for (int i = 0; i < numbers.size(); i++)
        {
            double squaredDifference = numbers.get(i) - mean;               // subtract the mean
            squaredDifference = squaredDifference * squaredDifference;      // square the result
            squaredDifferences.add(squaredDifference);
        }

        // Now work out the average of our squared differences.
        double variance = 0;

        for (int i = 0; i < squaredDifferences.size(); i++)
        {
            variance += squaredDifferences.get(i);
        }

        variance = variance / squaredDifferences.size();

        // Square root our variance and return it.
        return Math.sqrt(variance);

    }
}
