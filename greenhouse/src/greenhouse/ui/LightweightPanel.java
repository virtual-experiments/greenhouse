// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse.ui;

import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.awt.Container;

public class LightweightPanel extends Container
{
    public LightweightPanel() {
        this(new FlowLayout());
    }
    
    public LightweightPanel(final LayoutManager layout) {
        this.setLayout(layout);
    }
}
