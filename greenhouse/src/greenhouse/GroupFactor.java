// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.util.Vector;

public class GroupFactor
{
    private final int index;
    private String name;
    private final Vector<Group> groups;
    
    public int getGroupCount() {
        return this.groups.size();
    }
    
    public GroupFactor(final int index) {
        this.groups = new Vector<>();
        this.index = index;
        this.name = "Group Factor " + (index + 1);
        final Group defaultGroup = new Group(this, 0);
        this.groups.addElement(defaultGroup);
    }
    
    public Group addGroup() {
        final Group group = new Group(this, this.getGroupCount());
        this.groups.addElement(group);
        return group;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String naam) {
        this.name = naam;
    }
    
    public Group getGroup(final int index) {
        return this.groups.elementAt(index);
    }
}
