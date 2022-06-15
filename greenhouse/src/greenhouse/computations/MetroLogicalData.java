package greenhouse.computations;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

// 
// Decompiled by Procyon v0.5.36
// 

public class MetroLogicalData
{
    private static int[][][] weatherMatrix;
    
    public static void readMetroLogicalData(final String filename) {
    	URL url = MetroLogicalData.class.getResource(filename);
    	if (url == null) {
    		System.out.println("Resource not found: " + filename);
    		return;
    	}
        try {
            final InputStream is = url.openStream();
            final BufferedReader in = new BufferedReader(new InputStreamReader(is));
            for (int i = 0; i < 365; ++i) {
                for (int j = 0; j < 24; ++j) {
                    final String lijn = in.readLine();
                    final String dagS = lijn.substring(0, 4);
                    final String uurS = lijn.substring(4, 8);
                    final String lichtS = lijn.substring(8, 14);
                    final String tempS = lijn.substring(14, 19);
                    final int dag = Integer.parseInt(dagS.trim());
                    final int uur = Integer.parseInt(uurS.trim());
                    final int licht = Integer.parseInt(lichtS.trim());
                    final int temp = Integer.parseInt(tempS.trim());
                    if (dag == i + 1 && uur == j + 1) {
                        MetroLogicalData.weatherMatrix[i][j][0] = licht;
                        MetroLogicalData.weatherMatrix[i][j][1] = temp;
                    }
                    else {
                        System.out.println("The given file does not have the specified format");
                    }
                }
            }
            in.close();
        }
        catch (IOException ioe) {
            System.out.println("IOException");
            System.out.println(ioe);
        }
    }
    
    public static double getLight(final int day, final int hour) {
        final int index1 = day - 1;
        final int index2 = hour - 1;
        return MetroLogicalData.weatherMatrix[index1][index2][0];
    }
    
    static {
        MetroLogicalData.weatherMatrix = new int[365][24][2];
    }
    
    public static int getTemp(final int day, final int hour) {
        final int index1 = day - 1;
        final int index2 = hour - 1;
        return MetroLogicalData.weatherMatrix[index1][index2][1];
    }
}
