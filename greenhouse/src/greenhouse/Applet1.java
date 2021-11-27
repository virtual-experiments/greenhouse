// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.applet.Applet;

public class Applet1 extends Applet
{
    private Experiment experiment;
    
    public String getAppletInfo() {
        return "Project Name:\tVIRTEX\nApplet Name:\tGreenhouse\nApplet Version:\tVersion 1.6\nProject Promotor:\tProf. Paul Darius\nAuthor(s):\tLiesbeth Lievens\n \tSteve Dufresne\n \tBart Jacobs\nLast Modification:\t17/4/2002\n\t\nCopyright (c) 2001-2002\nKatholieke Universiteit Leuven\nUniversitair Centrum voor Statistiek";
    }
    
    public void init() {
        this.experiment = new Experiment(this);
        final Toolkit mykit = this.experiment.View.getToolkit();
        final Dimension d = mykit.getScreenSize();
        this.experiment.View.setSize(d.width, d.height - 20);
    }
}
