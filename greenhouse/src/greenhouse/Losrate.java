package greenhouse;
// 
// Decompiled by Procyon v0.5.36
// 

public class Losrate
{
    public static final double ABORMX = 0.73;
    
    public static void losrate(final Init ini) {
        double tabnf = 0.0;
        if (ini.TDMF >= 1.0E-11) {
            double fabor = Math.min(1.0, 0.67 - 0.73 * ini.SOSIR);
            fabor = Math.max(0.0, fabor);
            tabnf = fabor * ini.TRCNF;
        }
        ini.TABF += tabnf;
        ini.TNSF = ini.TNF - ini.TABF;
        double b = 0.0;
        try {
            for (int losCounter = 1; losCounter <= ini.NBRUP; ++losCounter) {
                if (ini.RCNF[losCounter - 1] != 0.0 && ini.XNFT[losCounter - 1] > 2.0 && b < tabnf) {
                    ini.ABNF[losCounter - 1] = Math.min(4.0, ini.RCNF[losCounter - 1]);
                    ini.ABNF[losCounter - 1] = Math.min(ini.ABNF[losCounter - 1], tabnf - b);
                    ini.ABNF[losCounter - 1] = Math.min(ini.ABNF[losCounter - 1], ini.XNFT[losCounter - 1] - 2.0 - ini.ABOR[losCounter - 1]);
                    ini.ABNF[losCounter - 1] = Math.max(0.0, ini.ABNF[losCounter - 1]);
                    b += ini.ABNF[losCounter - 1];
                    ini.ABOR[losCounter - 1] += ini.ABNF[losCounter - 1];
                    ini.NSF[losCounter - 1] = (int)Math.floor(ini.XNFT[losCounter - 1] - ini.ABOR[losCounter - 1]);
                    if (ini.ABOR[losCounter - 1] >= 1.0) {
                        for (int j = ini.NSF[losCounter - 1] + 1; j <= (int)Math.floor(ini.XNFT[losCounter - 1]); ++j) {
                            ini.DWF[losCounter - 1][j - 1] = -1.0E-11;
                        }
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException aiob) {
            System.out.println("array indeyout of ");
        }
    }
}
