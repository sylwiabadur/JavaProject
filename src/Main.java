import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Main
{
    private JFrame window;
    private JPanel btnPanel;
    private JButton processBtn, clearBtn;
    private JRadioButton histogramBtn, metricsBtn;

    private Paint paint;
    private ProcessingManager manager;
    private ResultPanel resultPanel;
    boolean isHistogramMethod;

    public Main() throws IOException
    {
        paint = new Paint();
        initializePanels();

        processButtonActions();
    }

    public static void main (String [] args) throws IOException
    {
        Main app = new Main();
        app.initGui();
        app.window.setSize(1000,600);
        app.window.setVisible(true);
    }

    protected void processButtonActions()
    {
        processBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Results res = manager.returnResults(isHistogramMethod, paint.getImage());
                    resultPanel.setMethod(isHistogramMethod);
                    resultPanel.setResults(res);
                    resultPanel.repaint();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        clearBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                paint.clear();
                resultPanel.clear();
            }
        });

        histogramBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                metricsBtn.setSelected(false);
                resultPanel.clear();
                isHistogramMethod = true;
            }
        });

        metricsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                histogramBtn.setSelected(false);
                resultPanel.clear();
                isHistogramMethod = false;
            }
        });
    }

    protected void initGui()
    {
        resultPanel = new ResultPanel();
        window = new JFrame("Swing Projects");

        Container content = window.getContentPane();
        content.setLayout(new FlowLayout());
        content.add(paint);
        content.add(btnPanel);
        content.add(resultPanel);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
    }

    protected void initializePanels()
    {
        Font myFont= new Font("TimesRoman", Font.PLAIN, 20);
        btnPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(180,300));
        manager = new ProcessingManager();

        histogramBtn = new JRadioButton("Histogram");
        histogramBtn.setMnemonic(KeyEvent.VK_B);
        histogramBtn.setSelected(false);

        metricsBtn = new JRadioButton("Metrics");
        metricsBtn.setMnemonic(KeyEvent.VK_C);
        metricsBtn.setSelected(true);

        processBtn = new JButton("Process");
        processBtn.setMinimumSize(new Dimension(150,60));
        processBtn.setPreferredSize(new Dimension(150,60));
        processBtn.setFont(myFont);

        clearBtn = new JButton ("Clear");
        clearBtn.setMinimumSize(new Dimension(150,60));
        clearBtn.setPreferredSize(new Dimension(150,60));
        clearBtn.setFont(myFont);

        btnPanel.setLayout(new FlowLayout());
        btnPanel.add(processBtn);
        btnPanel.add(clearBtn);
        btnPanel.add(histogramBtn);
        btnPanel.add(metricsBtn);
    }
}
