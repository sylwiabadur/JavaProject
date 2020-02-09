import java.util.ArrayList;

public class Results
{
    private ArrayList<Result> resultsList;
    boolean isHistogramMethod;

    public Results()
    {
        resultsList = new ArrayList<Result>();
    }

    public void addToArr(String fileName, Double resultNum)
    {
        resultsList.add(new Result(fileName,resultNum));
    }

    public String getBestMatch(boolean isHistogram)
    {
        isHistogramMethod = isHistogram;
        Double reference = null;
        String fileName = null;
        for (Result r : resultsList)
        {
            if (reference == null)
            {
                reference = r.getResultNumber();
                fileName = r.getFileName();
            }

            if (isHistogramMethod == true)
            {
                if (r.getResultNumber() > reference)
                {
                    reference = r.getResultNumber();
                    fileName = r.getFileName();
                }
            }
            else
                {
                if (r.getResultNumber() < reference) {
                    reference = r.getResultNumber();
                    fileName = r.getFileName();
                }
            }
        }
        return fileName;
    }

    public ArrayList<Result> getResultsList()
    {
        return resultsList;
    }
}


