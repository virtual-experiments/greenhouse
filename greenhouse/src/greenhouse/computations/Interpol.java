package greenhouse.computations;
// 
// Decompiled by Procyon v0.5.36
// 

public class Interpol
{
    public static final double[] PGRED;
    public static final double[] TMPG;
    public static final double[] GENTEM;
    public static final double[] XTEM;
    public static final double[] GENRAT;
    public static final double[] XGEN;
    public static final double[] RDVLVT;
    public static final double[] XLV;
    public static final double[] RDVFRT;
    public static final double[] XFR;
    public static final double[] PROOT;
    public static final double[] XROOT;
    public static final double[] BOX;
    public static final double[] POL;
    public static final double[] POF;
    
    public static double interpolatePGRED(final double temp) {
        boolean small = true;
        int counter = 0;
        double result;
        if (temp < 50.0) {
            while (small) {
                if (temp >= Interpol.TMPG[counter]) {
                    ++counter;
                }
                else {
                    small = false;
                }
            }
            final double width = Interpol.TMPG[counter] - Interpol.TMPG[counter - 1];
            final double hight = Interpol.PGRED[counter] - Interpol.PGRED[counter - 1];
            final double rico = hight / width;
            result = Interpol.PGRED[counter - 1] + rico * (temp - Interpol.TMPG[counter - 1]);
        }
        else {
            result = 0.0;
        }
        return result;
    }
    
    public static double interpolateRDVLVT(final double temp) {
        boolean small = true;
        int counter = 0;
        double result;
        if (temp < 80.0) {
            while (small) {
                if (temp >= Interpol.XLV[counter]) {
                    ++counter;
                }
                else {
                    small = false;
                }
            }
            final double width = Interpol.XLV[counter] - Interpol.XLV[counter - 1];
            final double hight = Interpol.RDVLVT[counter] - Interpol.RDVLVT[counter - 1];
            final double rico = hight / width;
            result = Interpol.RDVLVT[counter - 1] + rico * (temp - Interpol.XLV[counter - 1]);
        }
        else {
            result = 0.0;
        }
        return result;
    }
    
    public static double interpolatePROOT(final double plstnMethod) {
        boolean small = true;
        int counter = 0;
        double result;
        if (plstnMethod < 90.0) {
            while (small) {
                if (plstnMethod >= Interpol.XROOT[counter]) {
                    ++counter;
                }
                else {
                    small = false;
                }
            }
            final double width = Interpol.XROOT[counter] - Interpol.XROOT[counter - 1];
            final double hight = Interpol.PROOT[counter] - Interpol.PROOT[counter - 1];
            final double rico = hight / width;
            result = Interpol.PROOT[counter - 1] + rico * (plstnMethod - Interpol.XROOT[counter - 1]);
        }
        else {
            result = 0.02;
        }
        return result;
    }
    
    public static double interpolatePOF(final double xboxMethod) {
        boolean small = true;
        int counter = 0;
        double result;
        if (xboxMethod < 100.0) {
            while (small) {
                if (xboxMethod >= Interpol.BOX[counter]) {
                    ++counter;
                }
                else {
                    small = false;
                }
            }
            final double width = Interpol.BOX[counter] - Interpol.BOX[counter - 1];
            final double hight = Interpol.POF[counter] - Interpol.POF[counter - 1];
            final double rico = hight / width;
            result = Interpol.POF[counter - 1] + rico * (xboxMethod - Interpol.BOX[counter - 1]);
        }
        else {
            result = 0.0;
        }
        return result;
    }
    
    public static double interpolateGENTEM(final double temp) {
        boolean small = true;
        int counter = 0;
        double result;
        if (temp < 50.0) {
            while (small) {
                if (temp >= Interpol.XTEM[counter]) {
                    ++counter;
                }
                else {
                    small = false;
                }
            }
            final double width = Interpol.XTEM[counter] - Interpol.XTEM[counter - 1];
            final double hight = Interpol.GENTEM[counter] - Interpol.GENTEM[counter - 1];
            final double rico = hight / width;
            result = Interpol.GENTEM[counter - 1] + rico * (temp - Interpol.XTEM[counter - 1]);
        }
        else {
            result = 0.0;
        }
        return result;
    }
    
    static {
        PGRED = new double[] { 0.0, 0.67, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0 };
        TMPG = new double[] { 0.0, 9.0, 12.0, 15.0, 21.0, 28.0, 35.0, 50.0 };
        GENTEM = new double[] { 0.0, 0.0, 0.0, 0.55, 1.0, 0.0 };
        XTEM = new double[] { 0.0, 5.0, 9.0, 12.0, 28.0, 50.0 };
        GENRAT = new double[] { 0.0, 0.55, 0.55, 0.55, 0.55, 0.55, 0.55, 0.55 };
        XGEN = new double[] { 0.0, 5.0, 20.0, 43.0, 48.0, 60.0, 90.0, 200.0 };
        RDVLVT = new double[] { 0.0, 0.0, 0.0096, 0.0126, 0.019, 0.026, 0.026, 0.0, 0.0 };
        XLV = new double[] { 0.0, 9.0, 12.0, 15.0, 21.0, 28.0, 35.0, 50.0, 80.0 };
        RDVFRT = new double[] { 0.0, 0.0, 0.007, 0.016, 0.021, 0.024, 0.022, 0.02, 0.0 };
        XFR = new double[] { 0.0, 9.0, 15.0, 18.0, 21.0, 24.0, 28.0, 35.0, 50.0 };
        PROOT = new double[] { 0.18, 0.15, 0.12, 0.08, 0.02, 0.02 };
        XROOT = new double[] { 1.0, 10.0, 20.0, 30.0, 50.0, 90.0 };
        BOX = new double[] { 0.0, 5.0, 15.0, 25.0, 35.0, 45.0, 55.0, 65.0, 75.0, 85.0, 95.0, 100.0 };
        POL = new double[] { 0.0, 3.5E-4, 0.00165, 0.00255, 0.0023, 0.00155, 9.0E-4, 5.0E-4, 2.5E-4, 1.5E-4, 5.0E-5, 0.0 };
        POF = new double[] { 0.0, 0.09, 0.25, 0.45, 0.57, 0.58, 0.51, 0.41, 0.31, 0.22, 0.11, 0.0 };
    }
    
    public static double interpolateGENRAT(final double plstnMethod) {
        boolean small = true;
        int counter = 0;
        double result;
        if (plstnMethod < 200.0) {
            while (small) {
                if (plstnMethod >= Interpol.XGEN[counter]) {
                    ++counter;
                }
                else {
                    small = false;
                }
            }
            final double width = Interpol.XGEN[counter] - Interpol.XGEN[counter - 1];
            final double hight = Interpol.GENRAT[counter] - Interpol.GENRAT[counter - 1];
            final double rico = hight / width;
            result = Interpol.GENRAT[counter - 1] + rico * (plstnMethod - Interpol.XGEN[counter - 1]);
        }
        else {
            result = 0.55;
        }
        return result;
    }
    
    public static double interpolateRDVFRT(final double temp) {
        boolean small = true;
        int counter = 0;
        double result;
        if (temp < 50.0) {
            while (small) {
                if (temp >= Interpol.XFR[counter]) {
                    ++counter;
                }
                else {
                    small = false;
                }
            }
            final double width = Interpol.XFR[counter] - Interpol.XFR[counter - 1];
            final double hight = Interpol.RDVFRT[counter] - Interpol.RDVFRT[counter - 1];
            final double rico = hight / width;
            result = Interpol.RDVFRT[counter - 1] + rico * (temp - Interpol.XFR[counter - 1]);
        }
        else {
            result = 0.0;
        }
        return result;
    }
    
    public static double interpolatePOL(final double xboxMethod) {
        boolean small = true;
        int counter = 0;
        double result;
        if (xboxMethod < 100.0) {
            while (small) {
                if (xboxMethod >= Interpol.BOX[counter]) {
                    ++counter;
                }
                else {
                    small = false;
                }
            }
            final double width = Interpol.BOX[counter] - Interpol.BOX[counter - 1];
            final double hight = Interpol.POL[counter] - Interpol.POL[counter - 1];
            final double rico = hight / width;
            result = Interpol.POL[counter - 1] + rico * (xboxMethod - Interpol.BOX[counter - 1]);
        }
        else {
            result = 0.0;
        }
        return result;
    }
}
