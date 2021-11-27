package greenhouse;
// 
// Decompiled by Procyon v0.5.36
// 

public class Devrate6
{
    public static final double FPNPT = 0.8;
    public static final double FRLG = 9.0;
    public static final double FRST = 0.33;
    
    public static double devrate6(final int day, final int co2, final Init ini, final double iRDVLV, final double iTEMFAC, final double iRDVFR) {
        final double genr = iTEMFAC * Interpol.interpolateGENRAT(ini.PLSTN);
        ini.PLSTN += genr;
        ini.NBRU = (int)Math.floor((ini.PLSTN - 10.0 + 4.003003003003003) * 0.333 / 1.333);
        ini.NBRU = Math.max(0, ini.NBRU);
        ini.NBRUP = (int)Math.floor((ini.PLSTN - 10.0 - 9.0 + 4.003003003003003) * 0.333 / 1.333);
        ini.NBRUP = Math.max(0, ini.NBRUP);
        ini.NBLV = (int)Math.floor(ini.PLSTN) - ini.NBRU;
        ini.TRCNF = 0.0;
        for (int devCounter = 1; devCounter <= ini.NBRUP; ++devCounter) {
            try {
                final double xx = ini.XNFT[devCounter - 1];
                ini.XNFT[devCounter - 1] = Math.min(CTE.NBFPT[devCounter - 1], ini.XNFT[devCounter - 1] + genr * 0.8);
                ini.RCNF[devCounter - 1] = ini.XNFT[devCounter - 1] - xx;
                ini.TRCNF += ini.RCNF[devCounter - 1];
            }
            catch (ArrayIndexOutOfBoundsException iaobe) {
                System.out.println("IndexArrayOutOfBoundsException    1 ");
            }
        }
        ini.TNF += ini.TRCNF;
        double ptnlvs = 0.0;
        double ptnstm = 0.0;
        double ptnfrt = 0.0;
        for (int i = 1; i <= ini.NBRU; ++i) {
            try {
                if (ini.AGLS[i - 1] == -1.0E-11) {
                    ini.PLE[i - 1] = 0.0;
                    ini.PGL[i - 1] = 0.0;
                }
                else {
                    ini.AGLS[i - 1] = Math.min(1.0, ini.AGLS[i - 1] + iRDVLV);
                    final double xbox = 100.0 * ini.AGLS[i - 1];
                    if (i == 1) {
                        ini.PLE[i - 1] = iTEMFAC * Math.max(0.0, Interpol.interpolatePOL(xbox)) * 10.0;
                    }
                    else {
                        ini.PLE[i - 1] = iTEMFAC * Math.max(0.0, Interpol.interpolatePOL(xbox)) / 0.333;
                    }
                    if (co2 != 350) {
                        ini.PLE[i - 1] *= CorrFactors.sCO2(co2);
                    }
                }
                ini.PGL[i - 1] = ini.PLE[i - 1] * 1.43 / 0.022;
                ini.PGS[i - 1] = ini.PGL[i - 1] * 0.33;
                ptnlvs += ini.PGL[i - 1];
                ptnstm += ini.PGL[i - 1] * 0.33;
                for (int j = 1; j <= Math.floor(ini.XNFT[i - 1]); ++j) {
                    ini.AGF[i - 1][j - 1] = Math.min(1.0, ini.AGF[i - 1][j - 1] + iRDVFR);
                    final double ybox = 100.0 * ini.AGF[i - 1][j - 1];
                    if (ini.DWF[i - 1][j - 1] < 0.0) {
                        ini.PGF[i - 1][j - 1] = 0.0;
                    }
                    else {
                        ini.PGF[i - 1][j - 1] = iTEMFAC * Math.max(0.0, Interpol.interpolatePOF(ybox));
                    }
                    ptnfrt += ini.PGF[i - 1][j - 1];
                }
            }
            catch (ArrayIndexOutOfBoundsException aioobe) {
                System.out.println("ArrayIndexOutOfBoundsException hier");
            }
        }
        ini.leavesDemand = ptnlvs;
        ini.stemsDemand = ptnstm;
        return ptnlvs + ptnstm + (ini.fruitsDemand = ptnfrt);
    }
}
