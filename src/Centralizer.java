import java.awt.image.BufferedImage;

public class Centralizer {
    private int height, width;
    private BufferedImage image;
    private int offsetX, offsetY;

    public BufferedImage getCentralized(BufferedImage bImage){
        image = new BufferedImage(bImage.getWidth(), bImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        image.getGraphics().drawImage(bImage, 0, 0, null);
        height = image.getHeight();
        width = image.getWidth();

        centralizeHorizontally();
        centralizeVertically();

        return image;
    }

    private void centralizeHorizontally()
    {
        int minx = -1;
        int maxx = -1;

        for (int y = 0; y < height; ++y)
        {
            for (int x = 0; x < width; ++x)
            {
                int pixelValue = image.getRaster().getSample(x,y,0);
                if (pixelValue < 200)
                {
                    if (minx == -1 || x < minx)
                    {
                        minx = x;
                    }
                    if (maxx == -1 || x > maxx)
                    {
                        maxx = x;
                    }
                }

            }
        }

        int freeFromLeft = minx;
        int freeFromRight = 99-maxx;
        offsetX = (freeFromRight - freeFromLeft)/2;

        if ((freeFromRight - freeFromLeft) > 0)
        {
            moveRight(offsetX);
        }
        else
        {
            moveLeft(-offsetX);
        }
    }

    private void moveRight(int offset)
    {
        for (int y = 0; y < height; ++y)
        {
            for (int x = (width-1-offset) ; x > 0; --x)
            {
                image.getRaster().setSample(x+offset,y,0, image.getRaster().getSample(x,y,0));
            }
        }
        for (int y = 0; y < height; ++y)
        {
            for (int x = offset ; x > 0; --x)
            {
                image.getRaster().setSample(x,y,0, 255);
            }
        }
    }

    private void moveLeft(int offset)
    {
        for (int y = 0; y < height; ++y)
        {
            for (int x = offset ; x < width; ++x)
            {
                image.getRaster().setSample(x-offset,y,0,image.getRaster().getSample(x,y,0));
            }
        }
        for (int y = 0; y < height; ++y)
        {
            for (int x = width-1-offset ; x <width; ++x)
            {
                image.getRaster().setSample(x,y,0, 255);
            }
        }
    }

    private void centralizeVertically()
    {
        int miny = -1;
        int maxy = -1;

        for (int x = 0; x < width; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                int pixelValue = image.getRaster().getSample(x,y,0);
                if (pixelValue < 200)
                {
                    if (miny == -1 || y < miny)
                    {
                        miny = y;
                    }
                    if (maxy == -1 || y > maxy)
                    {
                        maxy = y;
                    }
                }

            }
        }

        int freeFromTop = miny;
        int freeFromBottom = 99-maxy;
        offsetY = (freeFromBottom - freeFromTop)/2;

        if ((freeFromBottom - freeFromTop) > 0)
        {
            moveDown(offsetY);
        }
        else
        {
            moveUp(-offsetY);
        }
    }

    private void moveDown(int offset)
    {
        for (int x = 0; x < width; ++x)
        {
            for (int y = (height-1-offset) ; y > 0; --y)
            {
                image.getRaster().setSample(x,y+offset,0, image.getRaster().getSample(x,y,0));
            }
        }
        for (int x = 0; x < width; ++x)
        {
            for (int y = offset ; y > 0; --y)
            {
                image.getRaster().setSample(x,y,0, 255);
            }
        }
    }

    private void moveUp(int offset)
    {
        for (int x = 0; x < width; ++x)
        {
            for (int y = offset ; y < height; ++y)
            {
                image.getRaster().setSample(x,y-offset,0,image.getRaster().getSample(x,y,0));
            }
        }
        for (int x = 0; x < width; ++x)
        {
            for (int y = height-1-offset ; y <width; ++y)
            {
                image.getRaster().setSample(x,y,0, 255);
            }
        }
    }
}
