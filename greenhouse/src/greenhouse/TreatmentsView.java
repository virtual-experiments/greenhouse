// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.Image;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import draganddrop.DragAndDropManager;
import java.awt.TextField;
import java.awt.Scrollbar;
import java.awt.Button;
import java.awt.Label;
import java.awt.Color;
import java.awt.Container;

public class TreatmentsView extends Container
{
    static final int nitrateStepSize = 1;
    private static final int maxNitrateLevel = 11;
    private static final Color[] NitrateColors;
    private final Label treatmentsLabel;
    private final Button addTreatmentButton;
    private final LightweightPanel headerPanel;
    private final LightweightPanel gridPanel;
    Label[] labels;
    Scrollbar[] scrollbars;
    TextField[] titels;
    DragAndDropManager dragAndDropManager;
    Experiment experiment;
    
    void recreate() {
        this.gridPanel.removeAll();
        final int treatmentCount = this.experiment.getTreatmentCount();
        this.labels = new Label[treatmentCount];
        this.scrollbars = new Scrollbar[treatmentCount];
        this.titels = new TextField[treatmentCount];
        final LightweightPanel titelPanel = new LightweightPanel(new GridLayout(11, 0));
        final LightweightPanel labelPanel = new LightweightPanel(new GridLayout(11, 0));
        final LightweightPanel scrollbarPanel = new LightweightPanel(new GridLayout(11, 0));
        for (int i = 0; i < treatmentCount; ++i) {
            final int index = i;
            final Treatment treatment = this.experiment.getTreatment(i);
            (this.labels[i] = new Label()).addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(final MouseEvent e) {
                    if (!TreatmentsView.this.dragAndDropManager.isDragging()) {
                        final Image image = ContainerView.createTransparentImage(TreatmentsView.this, ContainerView.createTreatmentBoxImage(TreatmentsView.this, TreatmentsView.this.experiment.getTreatment(index)));
                        TreatmentsView.this.dragAndDropManager.startDragging(TreatmentsView.this.labels[index], TreatmentsView.this.experiment.getTreatment(index), image, -10, -7);
                    }
                    TreatmentsView.this.dragAndDropManager.mouseDragged(e.getX(), e.getY());
                }
                
                {
                    TreatmentsView.this.getClass();
                }
            });
            this.labels[i].addMouseListener(new MouseAdapter() {
                {
                    TreatmentsView.this.getClass();
                }
                
                public void mouseReleased(final MouseEvent e) {
                    if (TreatmentsView.this.dragAndDropManager.isDragging()) {
                        TreatmentsView.this.dragAndDropManager.mouseReleased(e.getX(), e.getY());
                    }
                }
            });
            (this.titels[i] = new TextField(treatment.getName())).addTextListener(new TextListener() {
                {
                    TreatmentsView.this.getClass();
                }
                
                public void textValueChanged(final TextEvent e) {
                    TreatmentsView.this.experiment.getTreatment(index).setName(TreatmentsView.this.titels[index].getText());
                }
            });
            (this.scrollbars[i] = new Scrollbar(0, treatment.getNitrateLevel() / 1, 1, 0, 11)).setBlockIncrement(3);
            this.scrollbars[i].addAdjustmentListener(new AdjustmentListener() {
                public void adjustmentValueChanged(final AdjustmentEvent e) {
                    TreatmentsView.this.experiment.getTreatment(index).setNitrateLevel(TreatmentsView.this.scrollbars[index].getValue() * 1);
                    TreatmentsView.this.experiment.treatmentChanged(index);
                    TreatmentsView.this.updateLabelColor(index);
                }
                
                {
                    TreatmentsView.this.getClass();
                }
            });
            this.updateLabelColor(index);
            titelPanel.add(this.titels[i]);
            labelPanel.add(this.labels[i]);
            scrollbarPanel.add(this.scrollbars[i]);
        }
        this.gridPanel.add(titelPanel, "West");
        this.gridPanel.add(labelPanel, "Center");
        this.gridPanel.add(scrollbarPanel, "East");
    }
    
    public TreatmentsView(final DragAndDropManager dragAndDropManager, final Experiment experiment) {
        this.dragAndDropManager = dragAndDropManager;
        this.experiment = experiment;
        this.setLayout(new BorderLayout(5, 5));
        this.gridPanel = new LightweightPanel(new BorderLayout());
        this.treatmentsLabel = new Label("Treatments");
        (this.addTreatmentButton = new Button("Add")).addActionListener(new AddTreatmentButtonActionListener());
        this.recreate();
        (this.headerPanel = new LightweightPanel(new BorderLayout(5, 5))).add(this.treatmentsLabel, "Center");
        this.headerPanel.add(this.addTreatmentButton, "East");
        this.add(this.headerPanel, "North");
        this.add(this.gridPanel, "Center");
    }
    
    void updateLabelColor(final int index) {
        this.labels[index].setBackground(getNitrateColor(this.experiment.getTreatment(index).getNitrateLevel()));
        this.labels[index].setText("Dose " + Integer.toString(this.experiment.getTreatment(index).getNitrateLevel()));
    }
    
    public static Color getNitrateColor(final int nitrateLevel) {
        switch (nitrateLevel) {
            case 0: {
                return new Color(29, 143, 16);
            }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10: {
                return TreatmentsView.NitrateColors[nitrateLevel - 1];
            }
            default: {
                return new Color(29, 143, 16);
            }
        }
    }
    
    static {
        NitrateColors = new Color[] { Color.green, Color.yellow, Color.orange, Color.pink, Color.red, Color.magenta, Color.blue, Color.cyan, Color.white, Color.gray };
    }
    
    static Component getValidateRoot(Component c) {
        while (c.getParent() != null) {
            c = c.getParent();
        }
        return c;
    }
    
    void updateAddTreatmentButton() {
        this.addTreatmentButton.setEnabled(this.experiment.getTreatmentCount() < 11);
    }
    
    private class AddTreatmentButtonActionListener implements ActionListener
    {
        AddTreatmentButtonActionListener() {
            TreatmentsView.this.getClass();
        }
        
        public void actionPerformed(final ActionEvent e) {
            TreatmentsView.this.experiment.addTreatment();
            TreatmentsView.this.recreate();
            TreatmentsView.this.invalidate();
            TreatmentsView.getValidateRoot(TreatmentsView.this).validate();
            TreatmentsView.this.updateAddTreatmentButton();
        }
    }
}
