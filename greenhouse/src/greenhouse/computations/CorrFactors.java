package greenhouse.computations;
// 
// Decompiled by Procyon v0.5.36
// 

public class CorrFactors
{
    public static final double SCO2 = 3.0E-4;
    
    public static double sCO2(final int co2) {
        final double fC = 1.0 + 3.0E-4 * (co2 - 350);
        return fC;
    }
}
