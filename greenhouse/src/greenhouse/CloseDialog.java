package greenhouse;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// 
// Decompiled by Procyon v0.5.36
// 

public class CloseDialog extends Dialog
{
    protected Panel panel;
    protected Button closeButton;
    
    public CloseDialog(final Frame parentFrame, final String title, final Panel panel, final boolean modal, final String buttonName, final Color color) {
        super(parentFrame, title, modal);
        this.panel = panel;
        this.setBackground(color);
        this.add("Center", panel);
        final Panel p = new Panel();
        p.setLayout(new FlowLayout(1, 2, 2));
        (this.closeButton = new Button(buttonName)).addActionListener(new closeButtonActionListener());
        p.add("Center", this.closeButton);
        this.add("South", p);
        this.addWindowListener(new WindowAdapter() {
            {
                CloseDialog.this.getClass();
            }
            
            public void windowClosing(final WindowEvent e) {
                CloseDialog.this.dispose();
            }
        });
        this.pack();
    }
    
    private class closeButtonActionListener implements ActionListener
    {
        closeButtonActionListener() {
            CloseDialog.this.getClass();
        }
        
        public void actionPerformed(final ActionEvent e) {
            CloseDialog.this.dispose();
        }
    }
}
