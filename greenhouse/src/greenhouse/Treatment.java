// 
// Decompiled by Procyon v0.5.36
// 

package greenhouse;

public final class Treatment
{
    private int nitrateLevel;
    private String name;
    
    public Treatment(final int treatmentIndex) {
        this.nitrateLevel = 0;
        this.name = "Treatment " + treatmentIndex;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String naam) {
        this.name = naam;
    }
    
    public int getNitrateLevel() {
        return this.nitrateLevel;
    }
    
    public void setNitrateLevel(final int value) {
        this.nitrateLevel = value;
    }
}
