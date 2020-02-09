import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Paint extends JComponent
{
    private BufferedImage image;

    private int x, y, oldX, oldY;
    private int imgSize = 100;
    private int scale = 5;

    private Graphics2D g2d;

    public Paint()
    {
        initBufferedImage();
        setDoubleBuffered(false);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                oldX = e.getX() / scale;
                oldY = e.getY() / scale;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                x = e.getX() / scale;
                y = e.getY() / scale;

                if (g2d != null)
                {
                    g2d.setStroke(new BasicStroke(4));
                    g2d.drawLine(oldX, oldY, x,y);
                    repaint();
                    oldX = x;
                    oldY = y;
                }
            }
        });
    }

    protected void paintComponent(Graphics g)
    {
        if (image != null)
        {
            g.drawImage(image, 0,0,imgSize*scale, imgSize*scale,null);
        }
    }

    private void initBufferedImage()
    {
        image = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_BYTE_GRAY);
        g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, imgSize,imgSize);
        g2d.setPaint(Color.black);
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public void clear()
    {
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, imgSize,imgSize);
        g2d.setPaint(Color.black);
        paintComponent(getGraphics());
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(500,500);
    }
}
