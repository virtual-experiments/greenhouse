package greenhouse.computations;
// 
// Decompiled by Procyon v0.5.36
// 

public class Date
{
    public static int dateToInt(final int day, final int month) {
        int numberOfDays = 0;
        for (int mo = 1; mo < month; ++mo) {
            switch (mo) {
                case 1: {
                    numberOfDays += 31;
                    break;
                }
                case 2: {
                    numberOfDays += 28;
                    break;
                }
                case 3: {
                    numberOfDays += 31;
                    break;
                }
                case 4: {
                    numberOfDays += 30;
                    break;
                }
                case 5: {
                    numberOfDays += 31;
                    break;
                }
                case 6: {
                    numberOfDays += 30;
                    break;
                }
                case 7: {
                    numberOfDays += 31;
                    break;
                }
                case 8: {
                    numberOfDays += 31;
                    break;
                }
                case 9: {
                    numberOfDays += 30;
                    break;
                }
                case 10: {
                    numberOfDays += 31;
                    break;
                }
                case 11: {
                    numberOfDays += 30;
                    break;
                }
            }
        }
        numberOfDays += day;
        return numberOfDays;
    }
}
