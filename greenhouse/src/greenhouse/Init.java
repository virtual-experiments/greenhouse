package greenhouse;
// 
// Decompiled by Procyon v0.5.36
// 

public class Init
{
    public double xcoo;
    public double ycoo;
    double TABF;
    double TNSF;
    double TNF;
    double[] DWL;
    double[] DWS;
    double[] DWTR;
    double PLAR2;
    double[] XLA;
    double[] ABOR;
    double[] XNFT;
    double[] AGLS;
    double[] PLE;
    double[] PGS;
    double[] PGL;
    double[][] PGF;
    double[][] DWF;
    double[][] AGF;
    double[] ABNF;
    int[] NSF;
    double[] RCNF;
    double PLAR;
    double PLSTN;
    int NBRU;
    int NBRUP;
    int NBLV;
    double TRCNF;
    double CPOOL;
    public double TDML;
    public double TDML2;
    public double TDMS;
    public double TDMF;
    double TNMF;
    double DMGF;
    double DMGL;
    double DMMF;
    double stemsDemand;
    double leavesDemand;
    double fruitsDemand;
    double DEMAND;
    double RCDRW;
    double SOSIR;
    
    public void general(final int sDAY, final int sMONTH, final int eDAY, final int eMONTH, final int cOtwo, final double nitro) {
        final int day1 = Date.dateToInt(sDAY, sMONTH);
        final int day2 = Date.dateToInt(eDAY, eMONTH);
        final int NSTART = day1;
        int NDAYS;
        if (day2 >= day1) {
            NDAYS = day2 - day1;
        }
        else {
            NDAYS = 365 - day1 + day2;
        }
        for (int counter = NSTART; counter < NSTART + NDAYS; ++counter) {
            int jday = counter % 365;
            if (jday == 0) {
                jday = 365;
            }
            double GP = 0.0;
            double TEMFAC = 0.0;
            double RMAINT = 0.0;
            double RDVLV = 0.0;
            double RDVFR = 0.0;
            for (int jhour = 1; jhour <= 24; ++jhour) {
                final double temperature = Regulation.getInsideTemp(jday, jhour, this.xcoo);
                final double pPFD = Regulation.getInsideLight(jday, jhour, this.xcoo, this.ycoo);
                final double hourTemfac = Interpol.interpolateGENTEM(temperature);
                TEMFAC += hourTemfac * 0.041666666666666664;
                final double hourRMAINT = CarbonSupply.respiration(temperature, this.TDML2, this.TDMS, this.DMGF);
                RMAINT += hourRMAINT * 0.041666666666666664;
                final double hourRdvlv = Interpol.interpolateRDVLVT(temperature);
                RDVLV += hourRdvlv * 0.041666666666666664;
                final double hourRdvfr = Interpol.interpolateRDVFRT(temperature);
                RDVFR += hourRdvfr * 0.041666666666666664;
                final double hourGP = CarbonSupply.photosynthesis(cOtwo, temperature, pPFD, this);
                GP += hourGP * 0.041666666666666664;
            }
            final double reductionCoor = 0.7952105 + 0.1892599 * Math.log(0.1 + nitro / 2.0);
            GP *= reductionCoor;
            this.DEMAND = Devrate6.devrate6(jday, cOtwo, this, RDVLV, TEMFAC, RDVFR);
            this.RCDRW = 0.75 * (GP + this.CPOOL - RMAINT);
            if (this.RCDRW < 0.0) {
                this.RCDRW = 0.0;
                RMAINT = GP + this.CPOOL;
            }
            this.RCDRW *= 1.0 - Interpol.interpolatePROOT(this.PLSTN);
            this.SOSIR = Math.min(1.0, this.RCDRW / (this.DEMAND + 1.0E-11));
            final double GRESP = (GP + this.CPOOL - RMAINT) * 0.15625;
            Losrate.losrate(this);
            GP = Dmrate.dmrate(this, GP);
        }
    }
    
    public Init(final double nodenumber, final double dryweightleaves, final double leafarea, final double dryweightstem, final double x, final double y) {
        this.TABF = 0.0;
        this.TNSF = 0.0;
        this.TNF = 0.0;
        this.DWL = new double[100];
        this.DWS = new double[100];
        this.DWTR = new double[100];
        this.PLAR2 = 0.0;
        this.XLA = new double[100];
        this.ABOR = new double[100];
        this.XNFT = new double[100];
        this.AGLS = new double[100];
        this.PLE = new double[100];
        this.PGS = new double[100];
        this.PGL = new double[100];
        this.PGF = new double[100][7];
        this.ABNF = new double[100];
        this.NSF = new int[100];
        this.RCNF = new double[100];
        this.NBRU = 0;
        this.NBRUP = 0;
        this.NBLV = 0;
        this.TRCNF = 0.0;
        this.TDMF = 0.0;
        this.TNMF = 0.0;
        this.DMGF = 0.0;
        this.DMMF = 0.0;
        this.xcoo = x;
        this.ycoo = y;
        this.DWL[0] = dryweightleaves;
        this.DWS[0] = dryweightstem;
        this.DWTR[0] = 0.0;
        this.PLAR = leafarea;
        this.XLA[0] = this.PLAR;
        this.ABOR[0] = 0.0;
        this.XNFT[0] = 0.0;
        this.AGLS[0] = 0.15;
        this.RCNF[0] = 0.0;
        this.PLE[0] = 0.0;
        this.PGS[0] = 0.0;
        this.PGL[0] = 0.0;
        this.ABNF[0] = 0.0;
        this.NSF[0] = 0;
        for (int i = 1; i < 100; ++i) {
            this.DWL[i] = 0.0;
            this.DWS[i] = 0.0;
            this.AGLS[i] = 0.0;
            this.ABOR[i] = 0.0;
            this.XNFT[i] = 0.0;
            this.DWTR[i] = 0.0;
            this.RCNF[i] = 0.0;
            this.PLE[i] = 0.0;
            this.PGS[i] = 0.0;
            this.PGL[i] = 0.0;
            this.ABNF[i] = 0.0;
            this.NSF[i] = 0;
        }
        this.DWF = new double[100][7];
        this.AGF = new double[100][7];
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 7; ++j) {
                this.DWF[i][j] = 0.0;
                this.AGF[i][j] = 0.0;
                this.PGF[i][j] = 0.0;
            }
        }
        this.PLSTN = nodenumber;
        this.DMGL = dryweightleaves;
        this.CPOOL = 0.1 * dryweightleaves;
        this.TDML = dryweightleaves;
        this.TDML2 = 0.0;
        this.TDMS = dryweightstem;
    }
    
    public double getWeight() {
        return this.TDMF + this.TDML + this.TDMS + this.CPOOL;
    }
}
