import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

class ImageComparator
{
    private BufferedImage readImgA, readImgB, imgA, imgB;
    private ArrayList<Integer> vectorxA = new ArrayList<Integer>(100);
    private ArrayList<Integer> vectoryA = new ArrayList<Integer>(100);
    private ArrayList<Integer> vectorxB = new ArrayList<Integer>(100);
    private ArrayList<Integer> vectoryB = new ArrayList<Integer>(100);
    private CorrelationCalculator correlation;
    private Centralizer centralizer;

    private double metricNumber;

    private int widthA, widthB, heightA, heightB;
    boolean isHistogramMethod;

    public ImageComparator(boolean isHistogram, BufferedImage imgPattern) throws IOException {
        isHistogramMethod = isHistogram;
        centralizer = new Centralizer();
        correlation = new CorrelationCalculator();
        readImgA = centralizer.getCentralized(imgPattern);
        readImgB = null;

        fillVectorsWithZeros();
        setUpPattern();
        createVectorsForPattern();
    }

    public double getComparisonResult(BufferedImage imageFromBase) throws IOException {
        readImgB = centralizer.getCentralized(imageFromBase);
        setUpBaseImage();

        if ((widthA != widthA) || (heightA != heightB))
        {
            System.out.println("Error: Images dimensions"+
                    " mismatch");
            return 0.0;
        }
            createVectorsForBaseImage();

            if(isHistogramMethod == true)
            {
                return countUsingHistogram();
            }
            else
            {
                return countUsingMetrics();
            }

    }

    private double countUsingHistogram()
    {
        for (int y = 0; y < heightA; ++y)
        {
            for (int x = 0; x < widthA; ++x)
            {
                double corrx, corry;
                corrx = correlation.count(vectorxA,vectorxB);
                corry = correlation.count(vectoryA, vectoryB);
                metricNumber = corrx + corry;
            }
        }
        return metricNumber;
    }

    private double countUsingMetrics()
    {
        double sum = 0.0;
        for (int y = 0; y < heightA; ++y)
        {
            for (int x = 0; x < widthA; ++x)
            {
                int rgbA = imgA.getRaster().getSample(x,y,0);
                int rgbB = imgB.getRaster().getSample(x,y,0);
                int mod = Math.abs(rgbA-rgbB);
                double pwr = Math.pow(mod,2);
                sum += pwr;
            }
        }
        metricNumber = Math.sqrt(sum);
        return metricNumber;
    }

    private void setUpBaseImage()
    {
        imgB = new BufferedImage(readImgB.getWidth(), readImgB.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        imgB.getGraphics().drawImage(readImgB, 0, 0, null);

        widthB = imgB.getWidth();
        heightB = imgB.getHeight();
    }

    private void fillVectorsWithZeros()
    {
        for (int i = 0; i<100;++i)
        {
            vectorxA.add(0);
            vectoryA.add(0);
            vectorxB.add(0);
            vectoryB.add(0);
        }
    }

    private void createVectorsForPattern()
    {
        for (int y = 0; y < heightA; ++y)
        {
            for (int x = 0; x < widthA; ++x)
            {
                if(imgA.getRaster().getSample(x,y,0)!=255)
                {
                    vectorxA.set(x,vectorxA.get(x)+1);
                    vectoryA.set(y,vectoryA.get(y)+1);
                }

            }
        }
    }

    private void createVectorsForBaseImage()
    {
        for (int y = 0; y < heightB; ++y)
        {
            for (int x = 0; x < widthB; ++x)
            {
                if(imgB.getRaster().getSample(x,y,0)!=255)
                {
                    vectorxB.set(x,vectorxB.get(x)+1);
                    vectoryB.set(y,vectoryB.get(y)+1);
                }

            }
        }
    }

    private void setUpPattern()
    {
        imgA = new BufferedImage(readImgA.getWidth(), readImgA.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        imgA.getGraphics().drawImage(readImgA, 0, 0, null);
        widthA = imgA.getWidth();
        heightA = imgA.getHeight();
    }
}
