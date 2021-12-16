package greenhouse;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.Label;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.Image;
import java.awt.Frame;
import java.awt.Dialog;

// 
// Decompiled by Procyon v0.5.36
// 

public class GrowingDialog extends Dialog implements Runnable
{
    private ImagePanel panel;
    private Frame parentFrame;
    
    public void stop() {
        this.dispose();
    }
    
    public GrowingDialog(final Frame parentFrame, final String title, final Image img) {
        super(parentFrame, title, false);
        this.parentFrame = parentFrame;
        this.add("Center", this.panel = new ImagePanel(img));
        final Panel p = new Panel();
        p.setLayout(new FlowLayout(1, 2, 2));
        this.add("South", p);
        this.setBounds(300, 300, 250, 200);
        this.pack();
        this.addWindowListener(new WindowAdapter() {
            {
                GrowingDialog.this.getClass();
            }
            
            public void windowClosing(final WindowEvent e) {
                GrowingDialog.this.dispose();
            }
        });
    }
    
    public void voegtoe(final int index, final int totaal) {
        if (totaal == 0 && index == 0) {
            this.panel.progress.setText("all");
            this.panel.percentage = 0.0;
        }
        else {
            this.panel.percentage = (index + 1.0) / totaal;
            this.panel.progress.setText("" + (totaal - index - 1));
        }
        this.panel.repaint();
    }
    
    public void run() {
        this.setBounds(300, 300, 250, 200);
        this.show();
    }
    
    private class ImagePanel extends Panel
    {
        Image image;
        Panel firstPanel;
        Panel secondPanel;
        double percentage;
        Label progress;
        
        public ImagePanel(final Image image) {
            GrowingDialog.this.getClass();
            this.firstPanel = new Panel();
            this.secondPanel = new Panel();
            this.percentage = 0.0;
            this.progress = new Label("all");
            this.image = image;
            this.setCursor(new Cursor(3));
            this.firstPanel.setLayout(new GridLayout(3, 0));
            this.firstPanel.add(new Label("  The plants are growing."));
            this.firstPanel.add(new Label("   Please be patient."));
            this.secondPanel.add(new Label(" Still "));
            this.progress.setBackground(Color.gray);
            this.progress.setForeground(Color.red);
            this.progress.setAlignment(1);
            this.progress.setFont(new Font("Arial", 1, 12));
            this.secondPanel.add(this.progress);
            this.secondPanel.add(new Label(" plants to go !"));
            this.firstPanel.add(this.secondPanel);
            this.add(this.firstPanel);
        }
        
        public void paint(final Graphics g) {
            g.drawImage(this.image, 8, 100, 50, 50, this);
            g.drawRect(80, 120, 100, 20);
            g.setColor(Color.blue);
            g.fillRect(81, 121, (int)(100.0 * this.percentage), 19);
        }
    }
}
