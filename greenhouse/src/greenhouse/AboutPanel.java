package greenhouse;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.util.StringTokenizer;

// 
// Decompiled by Procyon v0.5.36
// 

public class AboutPanel extends Panel
{
    Panel firstPanel;
    Panel secondPanel;
    GridBagLayout gridBag;
    GridBagConstraints gridBagCons;
    
    public AboutPanel(final String inputString) {
        this.firstPanel = new Panel();
        this.secondPanel = new Panel();
        this.gridBag = new GridBagLayout();
        this.gridBagCons = new GridBagConstraints();
        if (inputString == null || inputString.equals("")) {
            final Label defaultLabel = new Label("no contents");
            this.setBackground(Color.lightGray);
            this.setLayout(this.gridBag);
            this.gridBagCons.insets = new Insets(5, 5, 5, 5);
            this.gridBagCons.gridx = 0;
            this.gridBagCons.gridy = 0;
            this.gridBag.setConstraints(defaultLabel, this.gridBagCons);
            this.add(defaultLabel);
            return;
        }
        int maxTokensOnLine = 0;
        int linesInFirstPart = 0;
        StringTokenizer st = new StringTokenizer(inputString, "\n");
        final int linesInTotal = st.countTokens();
        while (st.hasMoreTokens()) {
            final String line = st.nextToken();
            if (line.equals("\t")) {
                break;
            }
            ++linesInFirstPart;
            final StringTokenizer st_line = new StringTokenizer(line, "\t");
            maxTokensOnLine = Math.max(st_line.countTokens(), maxTokensOnLine);
        }
        this.firstPanel.setLayout(new GridLayout(0, maxTokensOnLine));
        st = new StringTokenizer(inputString, "\n");
        for (int i = 0; i < linesInFirstPart; ++i) {
            int n = 0;
            final StringTokenizer st_line2 = new StringTokenizer(st.nextToken(), "\t");
            while (st_line2.hasMoreTokens()) {
                this.firstPanel.add(new Label(st_line2.nextToken()));
                ++n;
            }
            for (int j = n; j < maxTokensOnLine; ++j) {
                this.firstPanel.add(new Label(""));
            }
        }
        this.secondPanel.setLayout(new GridLayout(linesInTotal - linesInFirstPart - 1, 1, 0, 0));
        if (st.hasMoreTokens()) {
            st.nextToken();
        }
        while (st.hasMoreTokens()) {
            this.secondPanel.add(new Label(st.nextToken()));
        }
        this.setBackground(Color.lightGray);
        this.setLayout(this.gridBag);
        this.gridBagCons.insets = new Insets(5, 5, 5, 5);
        this.gridBagCons.gridx = 0;
        this.gridBagCons.gridy = 0;
        this.gridBag.setConstraints(this.firstPanel, this.gridBagCons);
        this.add(this.firstPanel);
        this.gridBagCons.gridy = 1;
        this.gridBagCons.insets = new Insets(5, 5, 5, 5);
        this.gridBag.setConstraints(this.secondPanel, this.gridBagCons);
        this.add(this.secondPanel);
    }
    
    public void paint(final Graphics g) {
        final Dimension d = this.getSize();
        final Color bg = this.getBackground();
        g.setColor(bg);
        g.draw3DRect(0, 0, d.width - 1, d.height - 1, true);
        g.draw3DRect(3, 3, d.width - 7, d.height - 7, false);
    }
}
