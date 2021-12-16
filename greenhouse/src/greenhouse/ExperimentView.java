// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;

import draganddrop.DragAndDropManager;

public class ExperimentView extends Frame implements ActionListener
{
    public static final int trayCountCTE = 12;
    public static final int plantsPerTrayRowCTE = 4;
    public static final int plantsPerTrayColumnCTE = 3;
    private static Image loadingImage;
    static GrowingDialog gd;
    final Experiment experiment;
    final ContainerView greenhouse;
    final ContainerView tray;
    private final TreatmentsView treatmentsView;
    private final GroupFactorsView groupFactorsView;
    private int currentGroupFactorIndex;
    private final DragAndDropManager dragAndDropManager;
    private Image buffer;
    final Button outputButton;
    private final Button growButton;
    final Label trayLabel;
    final Scrollbar trayScrollbar;
    private final LightweightPanel trayHeaderPanel;
    private final LightweightPanel trayPanel;
    private final LightweightPanel centerPanel;
    private final LightweightPanel rightPanel;
    private final LightweightPanel leftPanel;
    private final LightweightPanel lowerPanel;
    private final LightweightPanel datePanel;
    final Choice endDateChooser;
    final Choice beginDateChooser;
    final Choice endMonthChooser;
    final Choice beginMonthChooser;
    final Label errorMsgLabel;
    private String site1;
    private MenuBar mbalk;
    private MenuItem Mexit;
    private MenuItem Mabout;
    private MenuItem MappletHelp;
    public CheckboxMenuItem Mlights;
    
    public ExperimentView() {
        super("Greenhouse");
        this.site1 = "http://www.kuleuven.ac.be/ucs/";
        this.mbalk = new MenuBar();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(ExperimentView.class.getResource("icon.gif")));
        this.setVisible(true);
        this.setLayout(new BorderLayout(2, 2));
        this.experiment = new Experiment();
        ExperimentView.gd = new GrowingDialog(this, "Growing", ExperimentView.loadingImage);
        experiment.addObserver(new Observer() {
            {
                ExperimentView.this.getClass();
            }
            
            public void update(final Observable o, final Object identifier) {
                ExperimentView.this.repaint();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            {
                ExperimentView.this.getClass();
            }
            
            public void windowClosing(final WindowEvent e) {
                ExperimentView.this.dispose();
            }
        });
        this.dragAndDropManager = new DragAndDropManager(this);
        final Dimension greenhouseSize = new Dimension(400, 400);
        (this.greenhouse = new ContainerView(this.dragAndDropManager, greenhouseSize, experiment.getContainer(0))).light(true);
        this.trayLabel = new Label("Tray 1");
        (this.trayScrollbar = new Scrollbar(0, 1, 2, 1, 14)).addAdjustmentListener(new TrayScrollbarAdjustmentListener());
        (this.trayHeaderPanel = new LightweightPanel(new BorderLayout(5, 5))).add(this.trayLabel, "West");
        this.trayHeaderPanel.add(this.trayScrollbar, "Center");
        final Dimension traySize = new Dimension(136, 104);
        this.tray = new ContainerView(this.dragAndDropManager, traySize, experiment.getContainer(1));
        (this.trayPanel = new LightweightPanel(new BorderLayout(5, 5))).add(this.trayHeaderPanel, "North");
        this.trayPanel.add(this.tray, "Center");
        this.treatmentsView = new TreatmentsView(this.dragAndDropManager, experiment);
        this.groupFactorsView = new GroupFactorsView(this, this.dragAndDropManager);
        (this.centerPanel = new LightweightPanel()).add(this.greenhouse);
        final GridBagLayout rightPanelLayout = new GridBagLayout();
        this.rightPanel = new LightweightPanel(rightPanelLayout);
        final GridBagConstraints trayPanelConstraints = new GridBagConstraints();
        trayPanelConstraints.insets = new Insets(5, 5, 5, 5);
        this.rightPanel.add(this.trayPanel);
        final GridBagConstraints treatmentsViewConstraints = new GridBagConstraints();
        treatmentsViewConstraints.insets = new Insets(5, 5, 5, 5);
        treatmentsViewConstraints.gridy = 1;
        treatmentsViewConstraints.fill = 2;
        this.rightPanel.add(this.treatmentsView);
        rightPanelLayout.setConstraints(this.treatmentsView, treatmentsViewConstraints);
        final GridBagLayout leftPanelLayout = new GridBagLayout();
        this.leftPanel = new LightweightPanel(leftPanelLayout);
        final GridBagConstraints groupFactorsViewConstraints = new GridBagConstraints();
        groupFactorsViewConstraints.insets = new Insets(5, 5, 5, 5);
        groupFactorsViewConstraints.gridy = 2;
        groupFactorsViewConstraints.fill = 2;
        this.leftPanel.add(this.groupFactorsView);
        leftPanelLayout.setConstraints(this.groupFactorsView, groupFactorsViewConstraints);
        this.add(this.leftPanel, "West");
        this.lowerPanel = new LightweightPanel(new FlowLayout());
        this.datePanel = new LightweightPanel(new FlowLayout());
        final Label beginDateLabel = new Label("beginDate");
        this.datePanel.add(beginDateLabel);
        (this.beginDateChooser = new Choice()).add("1");
        this.beginDateChooser.add("2");
        this.beginDateChooser.add("3");
        this.beginDateChooser.add("4");
        this.beginDateChooser.add("5");
        this.beginDateChooser.add("6");
        this.beginDateChooser.add("7");
        this.beginDateChooser.add("8");
        this.beginDateChooser.add("9");
        this.beginDateChooser.add("10");
        this.beginDateChooser.add("11");
        this.beginDateChooser.add("12");
        this.beginDateChooser.add("13");
        this.beginDateChooser.add("14");
        this.beginDateChooser.add("15");
        this.beginDateChooser.add("16");
        this.beginDateChooser.add("17");
        this.beginDateChooser.add("18");
        this.beginDateChooser.add("19");
        this.beginDateChooser.add("20");
        this.beginDateChooser.add("21");
        this.beginDateChooser.add("22");
        this.beginDateChooser.add("23");
        this.beginDateChooser.add("24");
        this.beginDateChooser.add("25");
        this.beginDateChooser.add("26");
        this.beginDateChooser.add("27");
        this.beginDateChooser.add("28");
        this.beginDateChooser.add("29");
        this.beginDateChooser.add("30");
        this.beginDateChooser.add("31");
        this.datePanel.add(this.beginDateChooser);
        final Label beginMonthLabel = new Label("beginMonth");
        this.datePanel.add(beginMonthLabel);
        (this.beginMonthChooser = new Choice()).add("1");
        this.beginMonthChooser.add("2");
        this.beginMonthChooser.add("3");
        this.beginMonthChooser.add("4");
        this.beginMonthChooser.add("5");
        this.beginMonthChooser.add("6");
        this.beginMonthChooser.add("7");
        this.beginMonthChooser.add("8");
        this.beginMonthChooser.add("9");
        this.beginMonthChooser.add("10");
        this.beginMonthChooser.add("11");
        this.beginMonthChooser.add("12");
        this.datePanel.add(this.beginMonthChooser);
        final Label endDateLabel = new Label("endDate");
        this.datePanel.add(endDateLabel);
        (this.endDateChooser = new Choice()).add("1");
        this.endDateChooser.add("2");
        this.endDateChooser.add("3");
        this.endDateChooser.add("4");
        this.endDateChooser.add("5");
        this.endDateChooser.add("6");
        this.endDateChooser.add("7");
        this.endDateChooser.add("8");
        this.endDateChooser.add("9");
        this.endDateChooser.add("10");
        this.endDateChooser.add("11");
        this.endDateChooser.add("12");
        this.endDateChooser.add("13");
        this.endDateChooser.add("14");
        this.endDateChooser.add("15");
        this.endDateChooser.add("16");
        this.endDateChooser.add("17");
        this.endDateChooser.add("18");
        this.endDateChooser.add("19");
        this.endDateChooser.add("20");
        this.endDateChooser.add("21");
        this.endDateChooser.add("22");
        this.endDateChooser.add("23");
        this.endDateChooser.add("24");
        this.endDateChooser.add("25");
        this.endDateChooser.add("26");
        this.endDateChooser.add("27");
        this.endDateChooser.add("28");
        this.endDateChooser.add("29");
        this.endDateChooser.add("30");
        this.endDateChooser.add("31");
        this.datePanel.add(this.endDateChooser);
        final Label endMonthLabel = new Label("endMonth");
        this.datePanel.add(endMonthLabel);
        (this.endMonthChooser = new Choice()).add("1");
        this.endMonthChooser.add("2");
        this.endMonthChooser.add("3");
        this.endMonthChooser.add("4");
        this.endMonthChooser.add("5");
        this.endMonthChooser.add("6");
        this.endMonthChooser.add("7");
        this.endMonthChooser.add("8");
        this.endMonthChooser.add("9");
        this.endMonthChooser.add("10");
        this.endMonthChooser.add("11");
        this.endMonthChooser.add("12");
        this.datePanel.add(this.endMonthChooser);
        this.lowerPanel.add(this.datePanel);
        this.errorMsgLabel = new Label("                 ");
        this.lowerPanel.add(this.errorMsgLabel);
        (this.growButton = new Button("Grow")).addActionListener(new growButtonActionListener());
        this.lowerPanel.add(this.growButton);
        (this.outputButton = new Button("View Output")).setEnabled(false);
        this.outputButton.addActionListener(new outputButtonActionListener());
        this.lowerPanel.add(this.outputButton);
        this.add(this.lowerPanel, "South");
        this.add(this.centerPanel, "Center");
        this.add(this.rightPanel, "East");
        this.setBackground(new Color(0, 128, 192));
        this.setMenuBar(this.mbalk);
        final Menu mF = new Menu("File");
        this.mbalk.add(mF);
        final Menu mH = new Menu("Help");
        this.mbalk.add(mH);
        final Menu mO = new Menu("Options");
        this.mbalk.add(mO);
        mF.add(this.Mexit = new MenuItem("Exit"));
        mH.add(this.Mabout = new MenuItem("About"));
        mH.add(this.MappletHelp = new MenuItem("Applet Help"));
        mO.add(this.Mlights = new CheckboxMenuItem("Visualisation Lights ", true));
        this.Mexit.addActionListener(this);
        this.MappletHelp.addActionListener(this);
        this.Mabout.addActionListener(this);
        this.Mlights.addItemListener(new checkboxListener());
    }
    
    public void paint(final Graphics g) {
        final Dimension size = this.getSize();
        if (this.buffer == null || this.buffer.getWidth(this) != size.width || this.buffer.getHeight(this) != size.height) {
            this.buffer = this.createImage(size.width, size.height);
        }
        final Graphics bufferGraphics = this.buffer.getGraphics();
        bufferGraphics.clearRect(0, 0, size.width, size.height);
        super.paint(bufferGraphics);
        if (this.dragAndDropManager != null) {
            this.dragAndDropManager.paint(bufferGraphics);
        }
        g.drawImage(this.buffer, 0, 0, this);
    }
    
    public Experiment getExperiment() {
        return this.experiment;
    }
    
    public void actionPerformed(final ActionEvent evt) {
        final MenuItem keuze = (MenuItem)evt.getSource();
        if (keuze == this.Mabout) {
            this.about();
        }
        if (keuze == this.MappletHelp) {
            this.appletHelp();
        }
        if (keuze == this.Mexit) {
            this.dispose();
        }
    }
    
    public GroupFactor getCurrentGroupFactor() {
        return this.experiment.getGroupFactorAt(this.currentGroupFactorIndex);
    }
    
    public void update(final Graphics g) {
        this.paint(g);
    }
    
    static {
        ExperimentView.loadingImage = Toolkit.getDefaultToolkit().getImage(ExperimentView.class.getResource("plant.gif"));
        final MediaTracker mediaTracker = new MediaTracker(new Label());
        mediaTracker.addImage(ExperimentView.loadingImage, 1);
        try {
            mediaTracker.waitForAll();
        }
        catch (InterruptedException ex) {}
    }
    
    public int getCurrentGroupFactorIndex() {
        return this.currentGroupFactorIndex;
    }
    
    public void setCurrentGroupFactorIndex(final int value) {
        if (value <= 4) {
            this.currentGroupFactorIndex = value;
        }
    }
    
    private static final String appInfo = """
    		Project Name:\tVIRTEX
    		Applet Name:\tGreenhouse
    		Applet Version:\tVersion 1.6
    		Project Promotor:\tProf. Paul Darius
    		Author(s):\tLiesbeth Lievens
    		 \tSteve Dufresne
    		 \tEddie Schrevens
    		 \tBart Jacobs
    		Last Modification:\t17/4/2002
    		\t
    		Copyright (c) 2001-2002
    		Katholieke Universiteit Leuven
    		Universitair Centrum voor Statistiek
    		""";
    
    private void about() {
        final AboutPanel helpinhoud = new AboutPanel(appInfo);
        final AboutDialog helpframe = new AboutDialog(this, helpinhoud);
        helpframe.pack();
        final Rectangle r = this.drawableSurface();
        final Dimension d = helpframe.getMinimumSize();
        d.width = Math.max(r.width / 3, d.width);
        d.height = Math.max(r.height / 2, d.height);
        helpframe.setBounds(r.x + (r.width - d.width) / 2, r.y + (r.height - d.height) / 2, d.width, d.height);
        helpframe.show();
    }
    
    public void appletHelp() {
        try {
            final URI location = new URI(this.site1);
            java.awt.Desktop.getDesktop().browse(location);
        }
        catch (URISyntaxException | IOException e) {
            System.out.println("Error" + e);
        }
    }
    
    Rectangle drawableSurface() {
        final Insets i = this.getInsets();
        final Point p = this.location();
        final Dimension size;
        final Dimension d = size = this.getSize();
        size.width -= i.left + i.right;
        final Dimension dimension = d;
        dimension.height -= i.top + i.bottom;
        final Point point = p;
        point.x += i.left;
        final Point point2 = p;
        point2.y += i.top;
        return new Rectangle(p, d);
    }
    
    private class LetThemGrow extends Thread
    {
        public LetThemGrow() {
            ExperimentView.this.getClass();
        }
        
        public void run() {
            for (int plantSize = ExperimentView.this.experiment.getContainer(0).getPlantCount(), p = 0; p < plantSize; ++p) {
                ExperimentView.this.experiment.getContainer(0).getPlant(p).grow(ExperimentView.this.experiment.getStartDate(), ExperimentView.this.experiment.getStartMonth(), ExperimentView.this.experiment.getEndDate(), ExperimentView.this.experiment.getEndMonth(), 350);
                ExperimentView.gd.voegtoe(p, plantSize);
                ExperimentView.gd.repaint();
            }
            ExperimentView.gd.dispose();
            ExperimentView.gd.voegtoe(0, 0);
        }
    }
    
    private class TrayScrollbarAdjustmentListener implements AdjustmentListener
    {
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            final int trayIndex = ExperimentView.this.trayScrollbar.getValue();
            ExperimentView.this.trayLabel.setText("Tray " + trayIndex);
            ExperimentView.this.tray.setContainer(ExperimentView.this.experiment.getContainer(trayIndex));
        }
        
        TrayScrollbarAdjustmentListener() {
            ExperimentView.this.getClass();
        }
    }
    
    private class checkboxListener implements ItemListener
    {
        checkboxListener() {
            ExperimentView.this.getClass();
        }
        
        public void itemStateChanged(final ItemEvent e) {
            ExperimentView.this.greenhouse.light(ExperimentView.this.Mlights.getState());
        }
    }
    
    private class outputButtonActionListener implements ActionListener
    {
        outputButtonActionListener() {
            ExperimentView.this.getClass();
        }
        
        public void actionPerformed(final ActionEvent e) {
            final OutputView outputView = new OutputView(ExperimentView.this.experiment);
            final Rectangle drawableSurface;
            final Rectangle r = drawableSurface = ExperimentView.this.drawableSurface();
            drawableSurface.width -= 50;
            final Rectangle rectangle = r;
            rectangle.height -= 50;
            outputView.setBounds(r.x, r.y, r.width, r.height);
            outputView.show();
        }
    }
    
    private class growButtonActionListener implements ActionListener
    {
        growButtonActionListener() {
            ExperimentView.this.getClass();
        }
        
        public void actionPerformed(final ActionEvent e) {
            if (ExperimentView.this.experiment.getDates(ExperimentView.this.endDateChooser, ExperimentView.this.beginDateChooser, ExperimentView.this.endMonthChooser, ExperimentView.this.beginMonthChooser)) {
                ExperimentView.this.outputButton.setEnabled(true);
                ExperimentView.this.errorMsgLabel.setText("                 ");
                new Thread(ExperimentView.gd).start();
                final LetThemGrow growthread = new LetThemGrow();
                growthread.start();
            }
            else {
                ExperimentView.this.errorMsgLabel.setForeground(Color.red);
                ExperimentView.this.errorMsgLabel.setText("Invalid Date !");
            }
        }
    }
    
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
	        ExperimentView experimentView = new ExperimentView();
	        final Toolkit mykit = experimentView.getToolkit();
	        final Dimension d = mykit.getScreenSize();
	        experimentView.setSize(d.width, d.height - 20);
		});
	}

}
