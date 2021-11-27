package greenhouse;
// 
// Decompiled by Procyon v0.5.36
// 

public class CarbonSupply
{
    public static final double XK = 0.58;
    public static final double XM = 0.1;
    public static final double D = 2.593;
    public static final double Q10 = 1.4;
    public static final double RMRL = 0.015;
    public static final double RMRF = 0.01;
    
    public static double photosynthesis(final int co2, final double tIn, final double ppfd, final Init ini) {
        final double lfMAX = getLFMAX(co2, tIn);
        final double lai = ini.PLAR2;
        double gpf = 0.0;
        final double cd = 50.0 * Math.exp(0.0295 * (tIn - 23.0));
        final double qE = 0.084 * ((co2 - cd) / co2);
        if (ppfd >= 0.001) {
            final double top = 0.9 * lfMAX + qE * 0.58 * ppfd;
            final double bot = 0.9 * lfMAX + qE * 0.58 * ppfd * Math.exp(-0.58 * lai * 2.2);
            gpf = lfMAX / 0.58 * Math.log(top / bot);
            gpf *= 0.682;
            gpf = gpf * 3.8016 / 2.2;
        }
        return gpf;
    }
    
    public static double respiration(final double tIn, final double wl, final double ws, final double wf) {
        final double mresp = Math.pow(1.4, 0.1 * tIn - 2.0) * (0.015 * (wl + ws) + 0.01 * wf);
        return mresp;
    }
    
    public static double getLFMAX(final double co2, final double tHouse) {
        final double tau1 = 0.1;
        final double tau2 = 0.03;
        double pMAX;
        if (co2 <= 1500.0) {
            pMAX = tau1 * co2;
        }
        else {
            pMAX = tau1 * 1500.0 + tau2 * (co2 - 1500.0);
        }
        pMAX *= Interpol.interpolatePGRED(tHouse);
        return pMAX;
    }
}
