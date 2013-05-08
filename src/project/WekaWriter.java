package project;

import java.io.FileWriter;
import java.io.IOException;

public class WekaWriter
{
    /**
     * Private constructor for static class.
     */
    private WekaWriter() {}
    
    /**
     * Write data to .arff file.
     *
     * @param data The data to write to the file.
     * @param fileName The file to write data to (leave out extension).
     */
    public static void writeDataToFile(String[][] data, String fileName)
    {
        try
        {
            FileWriter writer = new FileWriter(fileName + ".arff");

            // Write header
            writer.append("@RELATION " + fileName + "\n");
            writer.append("@ATTRIBUTE year DATE \"y\" " + "\n");
            writer.append("@ATTRIBUTE country STRING" + "\n");
            writer.append("@ATTRIBUTE balanceOfPayments NUMERIC" + "\n");
            writer.append("@ATTRIBUTE unemploymentRate NUMERIC" + "\n");
            writer.append("@ATTRIBUTE gdpPerInhabitant NUMERIC" + "\n");
            writer.append("@ATTRIBUTE population NUMERIC" + "\n");

            // Write data
            writer.append("@DATA" + "\n");
            for (String[] dataLine : data)
            {
                for (int i = 0; i < dataLine.length; i++)
                {
                	if (i != 1) // Column 1 (country) is the only non-numeric / string value
                	{
                	    dataLine[i] = dataLine[i].replaceAll("\"", "");
                	}
                	
                    writer.append(dataLine[i]);
                    
                    if (i < dataLine.length-1)
                    {
                        writer.append(',');
                    }
                }
                
                writer.append('\n');
                writer.flush();
            }
            
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}