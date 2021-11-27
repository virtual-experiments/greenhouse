// 
// Decompiled by Procyon v0.5.36
// 

package draganddrop;

public interface DragAndDropListener
{
    boolean canDrop(final Object p0, final int p1, final int p2);
    
    void dropped(final Object p0, final int p1, final int p2);
}
