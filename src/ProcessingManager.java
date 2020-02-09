import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessingManager
{
    private ImageComparator comparator;
    private ArrayList<ArrayList<File>> files2DArray;
    private Results resultsArr;
    boolean isHistogramMethod;

    public ProcessingManager()
    {
        files2DArray = new ArrayList<ArrayList<File>>();
        setSizeOfArray();
        setFileArray();
    }

    private void setSizeOfArray()
    {
        for (int i = 0 ; i < 10 ; ++i)
        {
            files2DArray.add(new ArrayList<File>());
        }
    }

    public Results returnResults (boolean isHistogram, BufferedImage imgPattern) throws IOException
    {
        isHistogramMethod = isHistogram;
        resultsArr= new Results();
        comparator = new ImageComparator(isHistogramMethod, imgPattern);
        loopOverFiles();

        return resultsArr;
    }

    private void loopOverFiles() throws IOException
    {
        for (ArrayList<File> fileArray : files2DArray)
        {
            String fileName = null;
            Double referenceResult = null;
            for (File file: fileArray)
            {
                Double resultNumber = comparator.getComparisonResult(ImageIO.read(file));
                if (referenceResult == null)
                {
                    referenceResult = resultNumber;
                    fileName = file.getName();
                }

                if (isHistogramMethod == true)
                {
                    if(resultNumber > referenceResult)
                    {
                        referenceResult = resultNumber;
                        fileName = file.getName();
                    }
                }
                else
                {
                    if(resultNumber < referenceResult)
                    {
                        referenceResult = resultNumber;
                        fileName = file.getName();
                    }
                }
            }
            resultsArr.addToArr(fileName, referenceResult);
        }
    }

    private void setFileArray()
    {
        File dir = new File("/home/sylwiabadur/Desktop/Java/projekt/src/resources/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null)
        {
            for (File subdir : directoryListing)
            {
                if (subdir.isDirectory())
                {
                    String subdirName = subdir.getName();
                    int subdirNumber = Integer.parseInt(subdirName);
                    ArrayList<File> helperArr = files2DArray.get(subdirNumber);

                    File [] files = subdir.listFiles();
                    if (files != null)
                    {
                        for (File file : files)
                        {
                            helperArr.add(file);
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("IS NULL");
        }
    }
}