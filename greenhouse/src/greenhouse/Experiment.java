// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import java.awt.Choice;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;

import greenhouse.computations.MetroLogicalData;

public final class Experiment extends Observable
{
    public static final int maxGroupFactorCountCTE = 4;
    public static final int CO2 = 350;
    private int startDate;
    private int endDate;
    private int startMonth;
    private int endMonth;
    private int treatmentIndex;
    private final Vector<Container> containers;
    private final Vector<Treatment> treatments;
    private final GroupFactor[] groupFactors;
    
    public Container getContainer(final int index) {
        return this.containers.elementAt(index);
    }
    
    public Treatment getTreatment(final int index) {
        return this.treatments.elementAt(index);
    }
    
    public int getGroupFactorCount() {
        int i;
        for (i = 0; i < 4 && this.groupFactors[i] != null; ++i) {}
        return i;
    }
    
    public Experiment() {
        this.treatmentIndex = 1;
        this.containers = new Vector<>();
        this.treatments = new Vector<>();
        this.groupFactors = new GroupFactor[4];
        MetroLogicalData.readMetroLogicalData("refin.txt");
        final Treatment defaultTreat = this.addTreatment();
        this.addContainer();
        final Random random = new Random();
        for (int i = 0; i < 12; ++i) {
            final Container tray = this.addContainer();
            double y = 0.35;
            for (int row = 0; row < 3; ++row) {
                double x = 0.35;
                for (int column = 0; column < 4; ++column) {
                    final double meanInitialWeight = 10.0;
                    final double initialWeightStandardDeviation = 2.0;
                    final double initialWeight = meanInitialWeight + initialWeightStandardDeviation * random.nextGaussian();
                    tray.addPlant(initialWeight, defaultTreat, x, y);
                    x += 0.67;
                }
                y += 0.67;
            }
        }
    }
    
    public GroupFactor addGroupFactor() {
        GroupFactor groupFactor = null;
        if (this.getGroupFactorCount() < 4) {
            groupFactor = new GroupFactor(this.getGroupFactorCount());
            this.groupFactors[this.getGroupFactorCount()] = groupFactor;
            final Group defaulftGroup = groupFactor.getGroup(0);
            for (int k = 0; k < this.getContainerCount(); ++k) {
                for (int i = 0; i < this.getContainer(k).getPlantCount(); ++i) {
                    final Plant plant = this.getContainer(k).getPlant(i);
                    plant.joinGroup(defaulftGroup);
                }
            }
        }
        return groupFactor;
    }
    
    public Treatment addTreatment() {
        final Treatment treatment = new Treatment(this.treatmentIndex++);
        this.treatments.addElement(treatment);
        return treatment;
    }
    
    public int getEndDate() {
        return this.endDate;
    }
    
    public void treatmentChanged(final int index) {
        this.setChanged();
        this.notifyObservers("Treatment Changed");
    }
    
    public int getEndMonth() {
        return this.endMonth;
    }
    
    public Container addContainer() {
        final Container cont = new Container(this);
        this.containers.addElement(cont);
        return cont;
    }
    
    public boolean getDates(final Choice endDateChooser, final Choice beginDateChooser, final Choice endMonthChooser, final Choice beginMonthChooser) {
        try {
            final int startDag = Integer.parseInt(beginDateChooser.getSelectedItem());
            final int endDag = Integer.parseInt(endDateChooser.getSelectedItem());
            final int startMaand = Integer.parseInt(beginMonthChooser.getSelectedItem());
            final int endMaand = Integer.parseInt(endMonthChooser.getSelectedItem());
            if ((startMaand == 2 && startDag > 28) || ((startMaand == 4 || startMaand == 6 || startMaand == 9 || startMaand == 11) && startDag > 30)) {
                return false;
            }
            if ((endMaand == 2 && endDag > 28) || ((endMaand == 4 || endMaand == 6 || endMaand == 9 || endMaand == 11) && endDag > 30)) {
                return false;
            }
            this.startDate = startDag;
            this.endDate = endDag;
            this.startMonth = startMaand;
            this.endMonth = endMaand;
            return true;
        }
        catch (NumberFormatException NFE) {
            return false;
        }
    }
    
    public int getContainerCount() {
        return this.containers.size();
    }
    
    public int getTreatmentCount() {
        return this.treatments.size();
    }
    
    public GroupFactor getGroupFactorAt(final int i) {
        return this.groupFactors[i];
    }
    
    public int getStartDate() {
        return this.startDate;
    }
    
    public int getStartMonth() {
        return this.startMonth;
    }
}
