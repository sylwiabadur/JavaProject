import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JComponent
{
    private Results results;
    boolean isHistogramMethod;

    int posx = 0;
    int posy = 20;
    int fontSize = 20;

    public ResultPanel()
    {
        results = new Results();
    }
    public void setMethod(boolean isHistogram)
    {
        isHistogramMethod = isHistogram;
    }
    public void setResults(Results res)
    {
        results = res;
    }

    public void clear()
    {
        results = new Results();
        posx = 0;
        posy = 20;
        repaint();
    }

    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, fontSize);
        g2d.setFont(font);

        g2d.drawString("My results:", posx, posy);

        for (Result r: results.getResultsList())
        {
            posy += 20;
            g2d.drawString(r.getFileName().charAt(0) + ": " + r.getResultNumber(), posx, posy);
        }
        try
        {
            g2d.drawString("BEST MATCH: " + results.getBestMatch(isHistogramMethod).charAt(0), posx, posy + 20);
            g2d.drawString("FILE: " + results.getBestMatch(isHistogramMethod), posx, posy + 40);
        }
        catch (Exception e)
        {
            g2d.drawString("Not recognized yet", posx, posy + 20);
        }
    }


    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(200,280);
    }
}
