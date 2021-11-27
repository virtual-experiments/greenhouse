// 
// Decompiled by Procyon v0.5.36
// 

package draganddrop;

import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.MediaTracker;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.Component;
import java.awt.Image;

public final class DragAndDropManager
{
    static final Image noImage;
    Component arena;
    private DraggedObject draggedObject;
    private static /* synthetic */ Class class$draganddrop$DragAndDropManager;
    
    public void mouseDragged(final int x, final int y) {
        this.draggedObject.mouseDragged(x, y);
    }
    
    public boolean isDragging() {
        return this.draggedObject != null;
    }
    
    public DragAndDropManager(final Container arena) {
        this.arena = arena;
    }
    
    public void paint(final Graphics g) {
        if (this.draggedObject != null) {
            this.draggedObject.paint(g);
        }
    }
    
    public void mouseReleased(final int x, final int y) {
        this.mouseDragged(x, y);
        this.draggedObject.mouseReleased();
        this.draggedObject = null;
    }
    
    static {
        noImage = Toolkit.getDefaultToolkit().getImage(((DragAndDropManager.class$draganddrop$DragAndDropManager != null) ? DragAndDropManager.class$draganddrop$DragAndDropManager : (DragAndDropManager.class$draganddrop$DragAndDropManager = class$("draganddrop.DragAndDropManager"))).getResource("no.gif"));
        final MediaTracker mediaTracker = new MediaTracker(new Label());
        mediaTracker.addImage(DragAndDropManager.noImage, 1);
        try {
            mediaTracker.waitForAll();
        }
        catch (InterruptedException ex) {}
    }
    
    private static /* synthetic */ Class class$(final String s) {
        try {
            return Class.forName(s);
        }
        catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }
    
    public void startDragging(final Component component, final Object object, final Image image, final int imageXFromMouse, final int imageYFromMouse) {
        this.draggedObject = new DraggedObject(component, object, image, imageXFromMouse, imageYFromMouse);
        this.arena.repaint();
    }
    
    private class DraggedObject
    {
        public Component component;
        public Object data;
        public Image image;
        public int imageXFromMouse;
        public int imageYFromMouse;
        public int mouseX;
        public int mouseY;
        
        private Component getTarget(int x, int y) {
            Component component = DragAndDropManager.this.arena;
            Component target = null;
            while (true) {
                if (component instanceof DragAndDropListener) {
                    target = component;
                }
                if (!(component instanceof Container)) {
                    break;
                }
                final Component child = ((Container)component).getComponentAt(x, y);
                if (child == component) {
                    break;
                }
                final Point location = child.getLocation();
                x -= location.x;
                y -= location.y;
                component = child;
            }
            return target;
        }
        
        public void mouseDragged(final int x, final int y) {
            final Point location = this.getDescendantLocation(DragAndDropManager.this.arena, this.component);
            this.mouseX = location.x + x;
            this.mouseY = location.y + y;
            DragAndDropManager.this.arena.repaint();
        }
        
        public DraggedObject(final Component component, final Object data, final Image image, final int imageXFromMouse, final int imageYFromMouse) {
            DragAndDropManager.this.getClass();
            this.component = component;
            this.data = data;
            this.image = image;
            this.imageXFromMouse = imageXFromMouse;
            this.imageYFromMouse = imageYFromMouse;
        }
        
        public void paint(final Graphics g) {
            g.drawImage(this.image, this.mouseX + this.imageXFromMouse, this.mouseY + this.imageYFromMouse, DragAndDropManager.this.arena);
            if (!this.canDrop()) {
                final int width = DragAndDropManager.noImage.getWidth(DragAndDropManager.this.arena);
                final int height = DragAndDropManager.noImage.getHeight(DragAndDropManager.this.arena);
                if (width >= 0 && height >= 0) {
                    g.drawImage(DragAndDropManager.noImage, this.mouseX - width / 2, this.mouseY - height / 2, DragAndDropManager.this.arena);
                }
            }
        }
        
        public void mouseReleased() {
            final Component target = this.getTarget(this.mouseX, this.mouseY);
            if (target != null) {
                final DragAndDropListener l = (DragAndDropListener)target;
                final Point targetLocation = this.getDescendantLocation(DragAndDropManager.this.arena, target);
                final int targetX = this.mouseX - targetLocation.x;
                final int targetY = this.mouseY - targetLocation.y;
                final int objectX = targetX + this.imageXFromMouse;
                final int objectY = targetY + this.imageYFromMouse;
                if (l.canDrop(this.data, objectX, objectY)) {
                    l.dropped(this.data, objectX, objectY);
                }
            }
            DragAndDropManager.this.arena.repaint();
        }
        
        private boolean canDrop() {
            final Component c = this.getTarget(this.mouseX, this.mouseY);
            if (c != null) {
                final DragAndDropListener l = (DragAndDropListener)c;
                final Point targetLocation = this.getDescendantLocation(DragAndDropManager.this.arena, c);
                final int targetX = this.mouseX - targetLocation.x;
                final int targetY = this.mouseY - targetLocation.y;
                final int objectX = targetX + this.imageXFromMouse;
                final int objectY = targetY + this.imageYFromMouse;
                return l.canDrop(this.data, objectX, objectY);
            }
            return false;
        }
        
        private Point getDescendantLocation(final Component c, Component descendant) {
            final Point location = new Point();
            while (c != descendant) {
                final Point locationInParent = descendant.getLocation();
                final Point point = location;
                point.x += locationInParent.x;
                final Point point2 = location;
                point2.y += locationInParent.y;
                descendant = descendant.getParent();
            }
            return location;
        }
    }
}
