// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.util.Vector;

public class Container
{
    private final Experiment experiment;
    private final Vector<Plant> plants;
    
    public int getGroupFactorCount() {
        return this.experiment.getGroupFactorCount();
    }
    
    public int getPlantCount() {
        return this.plants.size();
    }
    
    public Container(final Experiment experiment) {
        this.plants = new Vector<>();
        this.experiment = experiment;
    }
    
    public Plant getPlant(final int index) {
        return this.plants.elementAt(index);
    }
    
    public void removePlant(final Plant plant) {
        this.plants.removeElement(plant);
    }
    
    public Plant addPlant(final double initialWeight, final Treatment treat, final double x, final double y) {
        final Plant plant = new Plant(this, initialWeight, treat, x, y);
        this.plants.addElement(plant);
        return plant;
    }
    
    public void addPlant(final Plant plant) {
        this.plants.addElement(plant);
    }
}
