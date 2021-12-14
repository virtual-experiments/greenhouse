package greenhouse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import draganddrop.DragAndDropListener;
import draganddrop.DragAndDropManager;

public class ContainerView extends Component implements DragAndDropListener
{
    public static final double pixelsPerMeterCTE = 50.0;
    public static final double plantDiameterCTE = 0.64;
    public static final int plantDiameterPixelsCTE = 32;
    public static final int plantRadiusPixelsCTE = 16;
    public static final int treatmentBoxWidthCTE = 21;
    public static final int treatmentBoxHeightCTE = 14;
    public static final int treatmentBoxArcWidthCTE = 6;
    DragAndDropManager dragAndDropManager;
    private Dimension size;
    private Container container;
    Plant selectedPlant;
    int selectedX;
    int selectedY;
    private static Image lightsImage;
    private boolean lightson;
    private boolean heating;
    
    public Container getContainer() {
        return this.container;
    }
    
    public void setContainer(final Container value) {
        this.container = value;
        this.repaint();
    }
    
    private static void paintGroupMark(final Graphics g, final Group group, final int factorIndex, final int x, final int y) {
        g.setColor(group.getColor());
        g.fillArc(x, y, 32, 32, 360 - 45 * (factorIndex + 1), 45);
        g.setColor(Color.black);
        g.drawArc(x, y, 32, 32, 360 - 45 * (factorIndex + 1), 45);
        g.drawLine(x + 16, y + 16, (int)(Math.sin((90.0 - 45.0 * (factorIndex + 1)) / 180.0 * 3.141592653589793) * 32.0 / 2.0 + x + 16.0), (int)(Math.cos((90.0 - 45.0 * (factorIndex + 1)) / 180.0 * 3.141592653589793) * 32.0 / 2.0 + y + 16.0));
        g.drawLine(x + 16, y + 16, (int)(Math.sin((90.0 - 45.0 * factorIndex) / 180.0 * 3.141592653589793) * 32.0 / 2.0 + x + 16.0), (int)(Math.cos((90.0 - 45.0 * factorIndex) / 180.0 * 3.141592653589793) * 32.0 / 2.0 + y + 16.0));
    }
    
    static Image createTreatmentBoxImage(final Component c, final Treatment treatment) {
        final Image image = c.createImage(21, 14);
        paintTreatmentBox(image.getGraphics(), treatment, 0, 0);
        return image;
    }
    
    static Image createTransparentImage(final Component c, final Image image) {
        final int width = image.getWidth(null);
        final int height = image.getHeight(null);
        final int[] pixels = new int[width * height];
        final PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException ex) {}
        final int background = pixels[0];
        for (int i = 0; i < pixels.length; ++i) {
            if (pixels[i] == background) {
                pixels[i] = 0;
            }
            else {
                final int[] array = pixels;
                final int n = i;
                array[n] &= Integer.MAX_VALUE;
            }
        }
        final MemoryImageSource memoryImageSource = new MemoryImageSource(width, height, pixels, 0, width);
        return c.createImage(memoryImageSource);
    }
    
    public ContainerView(final DragAndDropManager dragAndDropManager, final Dimension size, final Container container) {
        this.lightson = false;
        this.heating = false;
        this.dragAndDropManager = dragAndDropManager;
        this.size = new Dimension(size);
        this.container = container;
        this.setBackground(Color.lightGray);
        this.addMouseListener(new MouseAdapter());
        this.addMouseMotionListener(new MouseMotionAdapter());
    }
    
    public void paint(final Graphics g) {
        super.paint(g);
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.size.width, this.size.height);
        if (this.lightson) {
            g.drawImage(ContainerView.lightsImage, 0, 0, this);
        }
        if (this.heating) {
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, 15, this.size.height);
            g.fillRect(this.size.width - 15, 0, 15, this.size.height);
        }
        for (int i = 0; i < this.container.getPlantCount(); ++i) {
            final Plant plant = this.container.getPlant(i);
            final int x = (int)(plant.getX() * 50.0) - 16;
            final int y = (int)(plant.getY() * 50.0) - 16;
            this.paintPlant(g, plant, x, y);
        }
    }
    
    public boolean canDrop(final Object data, final int xPixels, final int yPixels) {
        if (!(data instanceof Plant)) {
            return (data instanceof Treatment || data instanceof Group) && this.getPlantAtLocation(xPixels, yPixels) != null;
        }
        if (xPixels < 0 || this.size.width < xPixels + 32 || yPixels < 0 || this.size.height < yPixels + 32) {
            return false;
        }
        final double x = (xPixels + 16) / 50.0;
        final double y = (yPixels + 16) / 50.0;
        final double diameterSquare = 0.4096;
        for (int i = 0; i < this.container.getPlantCount(); ++i) {
            final Plant plant = this.container.getPlant(i);
            if (plant != data) {
                final double dx = plant.getX() - x;
                final double dy = plant.getY() - y;
                final double distanceSquare = dx * dx + dy * dy;
                if (distanceSquare < diameterSquare) {
                    return false;
                }
            }
        }
        return true;
    }
    
    Image createPlantImage(final Plant plant) {
        final Image image = this.createImage(32, 32);
        this.paintPlant(image.getGraphics(), plant, 0, 0);
        return image;
    }
    
    static Image createGroupMarkImage(final Component c, final Group group, final GroupFactor groupFactor) {
        final Image image = c.createImage(32, 32);
        if (group.getColor() != null) {
            paintGroupMark(image.getGraphics(), group, groupFactor.getIndex(), 0, 0);
        }
        return image;
    }
    
    public void light(final boolean on) {
        this.lightson = on;
        this.heating = true;
        this.repaint();
    }
    
    private void paintPlant(final Graphics g, final Plant plant, final int x, final int y) {
        final Treatment treatment = plant.getTreatment();
        g.setColor(TreatmentsView.getNitrateColor(treatment.getNitrateLevel()));
        g.fillOval(x, y, 32, 32);
        for (int i = 0; i < this.container.getGroupFactorCount(); ++i) {
            if (plant.getGroup(i).getColor() != null) {
                paintGroupMark(g, plant.getGroup(i), i, x, y);
            }
        }
        g.setColor(Color.black);
        g.setFont(g.getFont());
        final String label = String.valueOf((int)plant.getInitialWeight());
        final FontMetrics metrics = g.getFontMetrics();
        final int labelWidth = metrics.stringWidth(label);
        g.drawString(label, x + (32 - labelWidth) / 2, y - 7 + (32 - metrics.getHeight()) / 2 + metrics.getHeight());
    }
    
    static {
        ContainerView.lightsImage = Toolkit.getDefaultToolkit().getImage(Experiment.class.getResource("lights.gif"));
        final MediaTracker mediaTracker = new MediaTracker(new Label());
        mediaTracker.addImage(ContainerView.lightsImage, 1);
        try {
            mediaTracker.waitForAll();
        }
        catch (InterruptedException ex) {}
    }
    
    Plant getPlantAtLocation(final int pixelsX, final int pixelsY) {
        final double x = pixelsX / 50.0;
        final double y = pixelsY / 50.0;
        final double radius = 0.32;
        final double radiusSquare = radius * radius;
        for (int i = 0; i < this.container.getPlantCount(); ++i) {
            final Plant plant = this.container.getPlant(i);
            final double dx = plant.getX() - x;
            final double dy = plant.getY() - y;
            final double distanceSquare = dx * dx + dy * dy;
            if (distanceSquare < radiusSquare) {
                return plant;
            }
        }
        return null;
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(this.size);
    }
    
    public Dimension getMinimumSize() {
        return new Dimension(this.size);
    }
    
    public Dimension getMaximumSize() {
        return new Dimension(this.size);
    }
    
    public void dropped(final Object data, final int xPixels, final int yPixels) {
        if (data instanceof Plant) {
            final double x = (xPixels + 16) / 50.0;
            final double y = (yPixels + 16) / 50.0;
            ((Plant)data).moveTo(this.container, x, y);
        }
        else if (data instanceof Group) {
            this.getPlantAtLocation(xPixels, yPixels).joinGroup((Group)data);
        }
        else {
            this.getPlantAtLocation(xPixels, yPixels).setTreatment((Treatment)data);
        }
    }
    
    private static void paintTreatmentBox(final Graphics g, final Treatment treatment, final int x, final int y) {
        g.setColor(TreatmentsView.getNitrateColor(treatment.getNitrateLevel()));
        g.fillRoundRect(x, y, 21, 14, 6, 6);
    }
    
    private class MouseAdapter extends java.awt.event.MouseAdapter
    {
        public void mousePressed(final MouseEvent e) {
            ContainerView.this.selectedX = e.getX();
            ContainerView.this.selectedY = e.getY();
            ContainerView.this.selectedPlant = ContainerView.this.getPlantAtLocation(e.getX(), e.getY());
        }
        
        MouseAdapter() {
            ContainerView.this.getClass();
        }
        
        public void mouseReleased(final MouseEvent e) {
            if (ContainerView.this.dragAndDropManager.isDragging()) {
                ContainerView.this.dragAndDropManager.mouseReleased(e.getX(), e.getY());
            }
        }
    }
    
    private class MouseMotionAdapter extends java.awt.event.MouseMotionAdapter
    {
        public void mouseDragged(final MouseEvent e) {
            if (ContainerView.this.selectedPlant != null) {
                if (!ContainerView.this.dragAndDropManager.isDragging()) {
                    final Image image = ContainerView.createTransparentImage(ContainerView.this, ContainerView.this.createPlantImage(ContainerView.this.selectedPlant));
                    final int left = (int)(ContainerView.this.selectedPlant.getX() * 50.0) - 16;
                    final int top = (int)(ContainerView.this.selectedPlant.getY() * 50.0) - 16;
                    ContainerView.this.dragAndDropManager.startDragging(ContainerView.this, ContainerView.this.selectedPlant, image, left - ContainerView.this.selectedX, top - ContainerView.this.selectedY);
                }
                ContainerView.this.dragAndDropManager.mouseDragged(e.getX(), e.getY());
            }
        }
        
        MouseMotionAdapter() {
            ContainerView.this.getClass();
        }
    }
}
