// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.awt.Color;

public class Group
{
    public static final int maxGroupCountCTE = 11;
    private static final Color[] groupColors;
    private final GroupFactor groupFactor;
    private String name;
    private final Color color;
    
    public Group(final GroupFactor groupFactor, final int index) {
        this.groupFactor = groupFactor;
        this.name = "Group " + index;
        if (index == 0) {
            this.name = "Default Group ";
            this.color = null;
        }
        else {
            this.color = Group.groupColors[(index + 3 * groupFactor.getIndex()) % 11];
        }
    }
    
    public Color getColor() {
        return this.color;
    }
    
    static {
        groupColors = new Color[] { Color.blue, Color.cyan, Color.magenta, Color.red, Color.yellow, Color.green, Color.black, Color.gray, Color.white, Color.pink, Color.orange };
    }
    
    public GroupFactor getGroupFactor() {
        return this.groupFactor;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String naam) {
        this.name = naam;
    }
}
