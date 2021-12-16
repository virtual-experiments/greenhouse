// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.Image;
import java.awt.List;
import java.awt.Button;
import java.awt.Label;
import draganddrop.DragAndDropManager;
import java.awt.Container;

public class GroupFactorsView extends Container
{
	final ExperimentView experimentView;
    final Experiment experiment;
    final DragAndDropManager dragAndDropManager;
    final LightweightPanel groupFactorsPanel;
    private final LightweightPanel groupFactorsHeaderPanel;
    private final Label groupFactorsLabel;
    private final Button addGroupFactorButton;
    final List groupFactorsList;
    final LightweightPanel helpPanel;
    Label[] helpLabels;
    private static final Image helpImage;
    private final LightweightPanel groupsPanel;
    private final LightweightPanel groupsHeaderPanel;
    private final Label groupsLabel;
    final TextField groupsTitel;
    private final Button addGroupButton;
    private final LightweightPanel groupLabelsPanel;
    Label[] groupLabels;
    TextField[] groupTitels;
    
    void updateAddGroupFactorButton() {
        this.addGroupFactorButton.setEnabled(this.experiment.getGroupFactorCount() < 4);
    }
    
    void updateAddGroupButton() {
        if (this.groupFactorsList.getSelectedIndex() >= 0) {
            this.addGroupButton.setEnabled(this.getCurrentGroupFactor().getGroupCount() < 11);
        }
    }
    
    void updateGroupsPanel() {
        this.remove(this.groupsPanel);
        final int index = this.groupFactorsList.getSelectedIndex();
        if (index >= 0) {
            this.add(this.groupsPanel, "South");
            this.groupsLabel.setText("Groups for: ");
            this.groupsTitel.setText(this.experimentView.getCurrentGroupFactor().getName());
            this.groupsTitel.setVisible(true);
            this.recreateGroupLabelsPanel();
        }
    }
    
    public GroupFactorsView(final ExperimentView experimentView, final DragAndDropManager dragAndDropManager) {
    	this.experimentView = experimentView;
        this.experiment = experimentView.experiment;
        this.dragAndDropManager = dragAndDropManager;
        final BorderLayout BLayout = new BorderLayout(2, 2);
        this.setLayout(BLayout);
        this.helpPanel = new LightweightPanel(new BorderLayout());
        final LightweightPanel helpLabelsPanel = new LightweightPanel(new GridLayout(6, 1));
        helpLabelsPanel.add(new Label("The initial weight"));
        helpLabelsPanel.add(new Label("The treatment-color"));
        this.helpLabels = new Label[4];
        for (int i = 0; i < 4; ++i) {
            final Label label1 = new Label();
            helpLabelsPanel.add(this.helpLabels[i] = label1);
        }
        this.groupFactorsLabel = new Label("Group Factors");
        (this.addGroupFactorButton = new Button("Add")).addActionListener(new AddGroupFactorButtonActionListener());
        (this.groupFactorsHeaderPanel = new LightweightPanel(new BorderLayout(2, 2))).add(this.groupFactorsLabel, "Center");
        this.groupFactorsHeaderPanel.add(this.addGroupFactorButton, "East");
        (this.groupFactorsList = new List()).addItemListener(new GroupFactorsListItemListener());
        this.updateGroupFactorsList();
        (this.groupFactorsPanel = new LightweightPanel(new BorderLayout(2, 2))).add(this.groupFactorsHeaderPanel, "Center");
        this.groupFactorsPanel.add(this.groupFactorsList, "South");
        final ImagePanel imagePanel = new ImagePanel(GroupFactorsView.helpImage);
        this.helpPanel.add(imagePanel, "Center");
        this.helpPanel.add(helpLabelsPanel, "East");
        this.groupsLabel = new Label();
        (this.groupsTitel = new TextField()).setVisible(false);
        this.groupsTitel.addTextListener(new TextListener() {
            {
                GroupFactorsView.this.getClass();
            }
            
            public void textValueChanged(final TextEvent e) {
                final int inde = GroupFactorsView.this.groupFactorsList.getSelectedIndex();
                GroupFactorsView.this.getCurrentGroupFactor().setName(GroupFactorsView.this.groupsTitel.getText());
                GroupFactorsView.this.groupFactorsList.replaceItem(GroupFactorsView.this.getCurrentGroupFactor().getName(), inde);
                GroupFactorsView.this.groupFactorsList.select(inde);
                GroupFactorsView.this.helpLabels[GroupFactorsView.this.getCurrentGroupFactor().getIndex()].setText(GroupFactorsView.this.getCurrentGroupFactor().getName());
            }
        });
        this.addGroupButton = new Button("Add");
        this.updateAddGroupButton();
        this.addGroupButton.addActionListener(new AddGroupButtonActionListener());
        (this.groupsHeaderPanel = new LightweightPanel(new BorderLayout(2, 2))).add(this.groupsLabel, "West");
        this.groupsHeaderPanel.add(this.groupsTitel, "Center");
        this.groupsHeaderPanel.add(this.addGroupButton, "East");
        this.groupLabelsPanel = new LightweightPanel(new BorderLayout());
        (this.groupsPanel = new LightweightPanel(new BorderLayout(2, 2))).add(this.groupsHeaderPanel, "North");
        this.groupsPanel.add(this.groupLabelsPanel, "Center");
        this.add(this.groupFactorsPanel, "North");
        this.updateGroupsPanel();
    }
    
    public GroupFactor getCurrentGroupFactor() {
        if (this.groupFactorsList.getSelectedIndex() == -1) {
            return this.experiment.getGroupFactorAt(0);
        }
        return this.experiment.getGroupFactorAt(this.groupFactorsList.getSelectedIndex());
    }
    
    void recreateGroupLabelsPanel() {
        this.groupLabelsPanel.removeAll();
        final LightweightPanel groupLabelsP = new LightweightPanel(new GridLayout(11, 0));
        final LightweightPanel groupTitelsP = new LightweightPanel(new GridLayout(11, 0));
        final GroupFactor groupFactor = this.getCurrentGroupFactor();
        this.groupLabels = new Label[11];
        this.groupTitels = new TextField[11];
        for (int i = 0; i < this.groupLabels.length; ++i) {
            final Label label1 = new Label("        ");
            groupLabelsP.add(this.groupLabels[i] = label1);
            final TextField text1 = new TextField();
            (this.groupTitels[i] = text1).setVisible(false);
            groupTitelsP.add(text1);
        }
        this.groupLabelsPanel.add(groupLabelsP, "West");
        this.groupLabelsPanel.add(groupTitelsP, "Center");
        for (int j = 0; j < groupFactor.getGroupCount(); ++j) {
            final int index = j;
            final Group group = groupFactor.getGroup(index);
            final Label label2 = this.groupLabels[j];
            if (j > 0) {
                label2.setBackground(group.getColor());
            }
            label2.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(final MouseEvent e) {
                    if (!GroupFactorsView.this.dragAndDropManager.isDragging()) {
                        final Image image = ContainerView.createTransparentImage(GroupFactorsView.this, ContainerView.createGroupMarkImage(GroupFactorsView.this, group, GroupFactorsView.this.getCurrentGroupFactor()));
                        GroupFactorsView.this.dragAndDropManager.startDragging(GroupFactorsView.this.groupLabels[index], GroupFactorsView.this.getCurrentGroupFactor().getGroup(index), image, 0, 0);
                    }
                    GroupFactorsView.this.dragAndDropManager.mouseDragged(e.getX(), e.getY());
                }
                
                {
                    GroupFactorsView.this.getClass();
                }
            });
            label2.addMouseListener(new MouseAdapter() {
                {
                    GroupFactorsView.this.getClass();
                }
                
                public void mouseReleased(final MouseEvent e) {
                    if (GroupFactorsView.this.dragAndDropManager.isDragging()) {
                        GroupFactorsView.this.dragAndDropManager.mouseReleased(e.getX(), e.getY());
                    }
                }
            });
            this.groupTitels[j].setText(group.getName());
            this.groupTitels[j].setVisible(true);
            this.groupTitels[j].addTextListener(new TextListener() {
                {
                    GroupFactorsView.this.getClass();
                }
                
                public void textValueChanged(final TextEvent e) {
                    GroupFactorsView.this.getCurrentGroupFactor().getGroup(index).setName(GroupFactorsView.this.groupTitels[index].getText());
                }
            });
        }
    }
    
    static {
        helpImage = Toolkit.getDefaultToolkit().getImage(Experiment.class.getResource("help.gif"));
        final MediaTracker mediaTracker = new MediaTracker(new Label());
        mediaTracker.addImage(GroupFactorsView.helpImage, 1);
        try {
            mediaTracker.waitForAll();
        }
        catch (InterruptedException ex) {}
    }
    
    private void updateGroupFactorsList() {
        this.groupFactorsList.removeAll();
        for (int i = 0; i < this.experiment.getGroupFactorCount(); ++i) {
            this.groupFactorsList.add(this.experiment.getGroupFactorAt(i).getName());
        }
    }
    
    private class AddGroupFactorButtonActionListener implements ActionListener
    {
        AddGroupFactorButtonActionListener() {
            GroupFactorsView.this.getClass();
        }
        
        public void actionPerformed(final ActionEvent e) {
            if (GroupFactorsView.this.experiment.getGroupFactorCount() < 4) {
                final GroupFactor newGroupFactor = GroupFactorsView.this.experiment.addGroupFactor();
                GroupFactorsView.this.groupFactorsList.add(newGroupFactor.getName());
                GroupFactorsView.this.helpLabels[GroupFactorsView.this.experiment.getGroupFactorCount() - 1].setText(newGroupFactor.getName());
            }
            GroupFactorsView.this.updateAddGroupFactorButton();
            GroupFactorsView.this.updateGroupsPanel();
            GroupFactorsView.this.invalidate();
            TreatmentsView.getValidateRoot(GroupFactorsView.this).validate();
        }
    }
    
    private class AddGroupButtonActionListener implements ActionListener
    {
        AddGroupButtonActionListener() {
            GroupFactorsView.this.getClass();
        }
        
        public void actionPerformed(final ActionEvent e) {
            final GroupFactor groupFactor = GroupFactorsView.this.getCurrentGroupFactor();
            groupFactor.addGroup();
            GroupFactorsView.this.updateAddGroupButton();
            GroupFactorsView.this.recreateGroupLabelsPanel();
            GroupFactorsView.this.invalidate();
            TreatmentsView.getValidateRoot(GroupFactorsView.this).validate();
        }
    }
    
    private class GroupFactorsListItemListener implements ItemListener
    {
        GroupFactorsListItemListener() {
            GroupFactorsView.this.getClass();
        }
        
        public void itemStateChanged(final ItemEvent e) {
            GroupFactorsView.this.experimentView.setCurrentGroupFactorIndex(Math.max(0, GroupFactorsView.this.groupFactorsList.getSelectedIndex()));
            GroupFactorsView.this.groupFactorsPanel.add(GroupFactorsView.this.helpPanel, "North");
            GroupFactorsView.this.updateGroupsPanel();
            GroupFactorsView.this.updateAddGroupButton();
            GroupFactorsView.this.invalidate();
            TreatmentsView.getValidateRoot(GroupFactorsView.this).validate();
        }
    }
    
    private class ImagePanel extends Panel
    {
        Image image;
        
        public ImagePanel(final Image image) {
            GroupFactorsView.this.getClass();
            this.image = image;
        }
        
        public void paint(final Graphics g) {
            g.drawImage(this.image, 0, 0, 100, 130, this);
        }
    }
}
