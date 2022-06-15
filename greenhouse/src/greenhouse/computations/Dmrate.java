package greenhouse.computations;
// 
// Decompiled by Procyon v0.5.36
// 

public class Dmrate
{
    public static double dmrate(final Init ini, double iGP) {
        ini.TDML = 0.0;
        ini.TDML2 = 0.0;
        ini.DMGL = 0.0;
        ini.TDMS = 0.0;
        ini.TDMF = 0.0;
        ini.DMGF = 0.0;
        ini.TNMF = 0.0;
        ini.DMMF = 0.0;
        ini.PLAR = 0.0;
        ini.PLAR2 = 0.0;
        double ascspL = 0.0;
        double ascspS = 0.0;
        double ascspF = 0.0;
        double ascsp = 0.0;
        for (int dmCounter = 1; dmCounter <= ini.NBRU; ++dmCounter) {
            try {
                final double ascL = Math.min(ini.PGL[dmCounter - 1], ini.PGL[dmCounter - 1] * ini.SOSIR);
                ascspL += ascL;
                ini.DWL[dmCounter - 1] += ascL;
                if (ini.AGLS[dmCounter - 1] < 1.0) {
                    ini.DMGL += ini.DWL[dmCounter - 1];
                }
                ini.XLA[dmCounter - 1] += Math.min(ini.PLE[dmCounter - 1], ascL * 0.06 / 1.43);
                ini.TDML += ini.DWL[dmCounter - 1];
                ini.PLAR += ini.XLA[dmCounter - 1];
                if (ini.AGLS[dmCounter - 1] > 0.0) {
                    ini.PLAR2 += ini.XLA[dmCounter - 1];
                    ini.TDML2 += ini.DWL[dmCounter - 1];
                }
                final double ascS = Math.min(ini.PGS[dmCounter - 1], ini.PGS[dmCounter - 1] * ini.SOSIR);
                ascspS += ascS;
                ini.DWS[dmCounter - 1] += ascS;
                ini.TDMS += ini.DWS[dmCounter - 1];
                double ascF = 0.0;
                ini.DWTR[dmCounter - 1] = 0.0;
                for (int nft = (int)Math.floor(ini.XNFT[dmCounter - 1]), dmCounter2 = 1; dmCounter2 <= nft; ++dmCounter2) {
                    if (ini.AGF[dmCounter - 1][dmCounter2 - 1] < 1.0 && ini.DWF[dmCounter - 1][dmCounter2 - 1] >= 0.0) {
                        ascF = Math.min(ini.PGF[dmCounter - 1][dmCounter2 - 1], ini.PGF[dmCounter - 1][dmCounter2 - 1] * ini.SOSIR);
                        ascspF += ascF;
                        ini.DWF[dmCounter - 1][dmCounter2 - 1] += ascF;
                    }
                    if (ini.DWF[dmCounter - 1][dmCounter2 - 1] > 0.0) {
                        ini.DWTR[dmCounter - 1] += ini.DWF[dmCounter - 1][dmCounter2 - 1];
                    }
                    if (ini.AGF[dmCounter - 1][dmCounter2 - 1] == 1.0 && ini.DWF[dmCounter - 1][dmCounter2 - 1] > 0.0) {
                        ini.DMMF += ini.DWF[dmCounter - 1][dmCounter2 - 1];
                        ++ini.TNMF;
                    }
                    if (ini.AGF[dmCounter - 1][dmCounter2 - 1] < 1.0 && ini.DWF[dmCounter - 1][dmCounter2 - 1] > 0.0) {
                        ini.DMGF += ini.DWF[dmCounter - 1][dmCounter2 - 1];
                    }
                }
                if (ini.AGF[dmCounter - 1][(int)CTE.NBFPT[dmCounter - 1] - 1] >= 1.0) {
                    ini.AGLS[dmCounter - 1] = -1.0E-11;
                }
                ini.TDMF += ini.DWTR[dmCounter - 1];
            }
            catch (ArrayIndexOutOfBoundsException aiobe) {
                System.out.println("index excep");
            }
        }
        ascsp = ascspL + ascspS + ascspF;
        ini.CPOOL = Math.max(0.0, (ini.RCDRW - ascsp) / 0.75);
        final double cpoolmx = 0.06 * ini.TDML / 1.0725;
        if (ini.CPOOL > cpoolmx) {
            iGP -= ini.CPOOL - cpoolmx;
            ini.CPOOL = cpoolmx;
        }
        return iGP;
    }
}
