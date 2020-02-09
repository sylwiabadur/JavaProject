public class Result
{
    private String fileName;
    private Double resultNumber;

    public Result(String f, Double r)
    {
        fileName = f;
        resultNumber = r;
    }

    public String getFileName()
    {
        return fileName;
    }

    public Double getResultNumber()
    {
        return resultNumber;
    }
}
