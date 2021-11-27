package greenhouse;
// 
// Decompiled by Procyon v0.5.36
// 

public class Regulation
{
    public static final double TRGH = 0.65;
    
    public static double getInsideLight(final int day, final int hour, final double xPosition, final double yPosition) {
        final double lightStrength = 400.0;
        boolean night = true;
        final double minDayLight = 400.0;
        double xxx;
        if (xPosition <= 4.0) {
            xxx = xPosition - 2.0;
        }
        else {
            xxx = xPosition - 6.0;
        }
        double yyy;
        if (yPosition <= 4.0) {
            yyy = yPosition - 2.0;
        }
        else {
            yyy = yPosition - 6.0;
        }
        final double lightFactor = Math.exp(-1.0 * xxx * xxx - yyy * yyy);
        final double extraLight = lightFactor * lightStrength * 0.3 / 3.11227;
        if (hour > 8 && hour <= 20) {
            night = false;
        }
        final double lightOut = MetroLogicalData.getLight(day, hour);
        final double lightIn = lightOut * 0.65;
        double lightReg;
        if (!night) {
            if (lightIn < minDayLight) {
                lightReg = lightIn + extraLight;
            }
            else {
                lightReg = lightIn;
            }
        }
        else {
            lightReg = lightIn;
        }
        lightReg = lightReg * 0.47 * 4.57;
        return lightReg;
    }
    
    public static double getInsideTemp(final int day, final int hour, final double xPosition) {
        final double minDayTemp = 18.0;
        final double minNightTemp = 15.0;
        final double tempDif = 3.0;
        boolean night = true;
        double tempReg = 0.0;
        final double positionFactor = xPosition * xPosition / 16.0 - xPosition / 2.0 + 1.0;
        final double tempOut = MetroLogicalData.getTemp(day, hour);
        if (hour > 8 && hour <= 20) {
            night = false;
        }
        if (night) {
            if (tempOut < minNightTemp) {
                tempReg = minNightTemp + positionFactor * tempDif;
            }
            else {
                tempReg = tempOut;
            }
        }
        if (!night) {
            if (tempOut < minDayTemp) {
                tempReg = minDayTemp + positionFactor * tempDif;
            }
            else {
                tempReg = tempOut;
            }
        }
        return tempReg;
    }
}
