package greenhouse.ui;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Frame;

// 
// Decompiled by Procyon v0.5.36
// 

public class AboutDialog extends CloseDialog
{
    public AboutDialog(final Frame parentFrame, final Panel aboutPanel) {
        super(parentFrame, "About this applet", aboutPanel, true, "Ok", Color.lightGray);
    }
}
