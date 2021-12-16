// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

import greenhouse.ui.OutputView;

public final class Plant
{
    private Container container;
    private double initialWeight;
    private double finalWeight;
    private double x;
    private double y;
    private Treatment treatment;
    private static final double RSF_N = 1.886;
    private static final double RSF_L = 0.606;
    private static final double RSF_A = 0.021;
    private static final double RSF_S = 0.394;
    private Group[] groups;
    
    public Container getContainer() {
        return this.container;
    }
    
    public double getInitialWeight() {
        return this.initialWeight;
    }
    
    public double getX() {
        return this.x;
    }
    
    public Treatment getTreatment() {
        return this.treatment;
    }
    
    public void setTreatment(final Treatment treatment) {
        this.treatment = treatment;
    }
    
    public Plant(final Container cont, final double init, final Treatment treat, final double xco, final double yco) {
        this.groups = new Group[4];
        this.container = cont;
        this.initialWeight = init;
        this.treatment = treat;
        this.x = xco;
        this.y = yco;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getFinalWeight() {
        return this.finalWeight;
    }
    
    public void moveTo(final Container container, final double x, final double y) {
        this.container.removePlant(this);
        (this.container = container).addPlant(this);
        this.x = x;
        this.y = y;
    }
    
    public Group getGroup(final GroupFactor groupFactor) {
        return this.groups[groupFactor.getIndex()];
    }
    
    public String print(final int groupFactorCount) {
        final String plantID = String.valueOf(OutputView.plantId++);
        final String intweight = String.valueOf(this.getInitialWeight()).concat("                      ").substring(0, 15);
        String finalweight = String.valueOf(this.getFinalWeight()).concat("                      ").substring(0, 15);
        if (this.getFinalWeight() == 0.0) {
            finalweight = "NOT YET GROWN !";
        }
        final String treat = this.getTreatment().getName().concat("                      ").substring(0, 15);
        final String Xco = String.valueOf(this.getX()).concat("                      ").substring(0, 10);
        final String Yco = String.valueOf(this.getY()).concat("                      ").substring(0, 10);
        String group = " ";
        for (int i = 0; i < groupFactorCount; ++i) {
            group = group + this.getGroup(i).getName().concat("                       ").substring(0, 15) + "\t\t";
        }
        return plantID + "\t\t" + intweight + "\t\t" + finalweight + "\t\t" + treat + "\t\t" + Xco + "\t\t" + Yco + "\t\t" + group + "\n";
    }
    
    public Group getGroup(final int groupFactorIndex) {
        return this.groups[groupFactorIndex];
    }
    
    private void setGroup(final GroupFactor groupFactor, final Group group) {
        this.groups[groupFactor.getIndex()] = group;
    }
    
    public void joinGroup(final Group group) {
        this.setGroup(group.getGroupFactor(), group);
    }
    
    public void grow(final int startday, final int startmonth, final int endday, final int endmonth, final int CO2) {
        final Init inie = new Init(this.initialWeight * RSF_N, this.initialWeight * RSF_L, this.initialWeight * RSF_A, this.initialWeight * RSF_S, this.x, this.y);
        inie.general(startday, startmonth, endday, endmonth, CO2, this.treatment.getNitrateLevel());
        this.finalWeight = inie.getWeight();
    }
}
