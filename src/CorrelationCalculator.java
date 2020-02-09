import java.util.ArrayList;

public class CorrelationCalculator {

    public double count(ArrayList<Integer> vec1, ArrayList<Integer> vec2) {
        if (vec1.size()!= vec2.size())
        {
            return -1.0;
        }

        double sumx = 0.0;
        double sumy = 0.0;
        double sumxx = 0.0;
        double sumyy = 0.0;
        double sumxy = 0.0;
        double x,y;

        int n = vec1.size();

        for (int i = 0; i<n ; ++i)
        {
            x = vec1.get(i);
            y = vec2.get(i);

            sumx += x;
            sumy += y;
            sumxx += x * x;
            sumyy += y * y;
            sumxy += x * y;
        }

        double correlation = (n*sumxy-sumx*sumy)/(Math.sqrt((n*sumxx-sumx*sumx)*(n*sumyy-sumy*sumy)));

        return correlation;
    }
}