// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.TextArea;
import java.awt.Button;
import java.awt.Frame;

public class OutputView extends Frame
{
    private Experiment experiment;
    private Button closeButton;
    private TextArea text;
    private final LightweightPanel lowerPanel;
    public static int plantId;
    private static /* synthetic */ Class class$greenhouse$Applet1;
    
    public OutputView(final Experiment experiment) {
        super("Results");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(((OutputView.class$greenhouse$Applet1 != null) ? OutputView.class$greenhouse$Applet1 : (OutputView.class$greenhouse$Applet1 = class$("greenhouse.Applet1"))).getResource("icon.gif")));
        this.setVisible(true);
        this.setLayout(new BorderLayout(5, 5));
        OutputView.plantId = 1;
        this.experiment = experiment;
        this.addWindowListener(new WindowAdapter() {
            {
                OutputView.this.getClass();
            }
            
            public void windowClosing(final WindowEvent e) {
                OutputView.this.dispose();
            }
        });
        this.lowerPanel = new LightweightPanel(new FlowLayout());
        (this.closeButton = new Button("Close")).addActionListener(new closeButtonActionListener());
        this.lowerPanel.add(this.closeButton);
        (this.text = new TextArea("")).setColumns(7);
        this.text.append("PlantID\t\t");
        this.text.append("Initialweight\t\t\t");
        this.text.append("End weight\t\t\t");
        this.text.append("Treatment\t\t");
        this.text.append("X-coordinate\t");
        this.text.append("Y-coordinate\t");
        for (int i = 0; i < experiment.getGroupFactorCount(); ++i) {
            this.text.append((experiment.getGroupFactorAt(i).getName() + "               ").substring(0, 15) + "\t\t");
        }
        this.text.append("\n");
        final greenhouse.Container greenhouse = experiment.getContainer(0);
        for (int j = 0; j < greenhouse.getPlantCount(); ++j) {
            this.text.append(greenhouse.getPlant(j).print(experiment.getGroupFactorCount()));
        }
        this.add(this.text, "Center");
        this.add(this.lowerPanel, "South");
        this.setBackground(new Color(0, 128, 192));
    }
    
    private static /* synthetic */ Class class$(final String s) {
        try {
            return Class.forName(s);
        }
        catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }
    
    private class closeButtonActionListener implements ActionListener
    {
        closeButtonActionListener() {
            OutputView.this.getClass();
        }
        
        public void actionPerformed(final ActionEvent e) {
            OutputView.this.dispose();
        }
    }
}
