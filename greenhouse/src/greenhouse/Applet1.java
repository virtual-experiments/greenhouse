// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.applet.Applet;

public class Applet1
{
    private Experiment experiment;
    
    public String getAppletInfo() {
        return """
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
    }
    
    public void init() {
        this.experiment = new Experiment(this);
        final Toolkit mykit = this.experiment.View.getToolkit();
        final Dimension d = mykit.getScreenSize();
        this.experiment.View.setSize(d.width, d.height - 20);
    }
}
