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
    public static void writeKMDataToFile(String[][] data, String fileName)
    {
        try
        {
            FileWriter writer = new FileWriter(fileName + ".arff");

            // Write header
            writer.append("@RELATION " + fileName + "\n");
            writer.append("@ATTRIBUTE year NUMERIC" + "\n");
            writer.append("@ATTRIBUTE country STRING" + "\n");
            writer.append("@ATTRIBUTE unemploymentRate NUMERIC" + "\n");
            writer.append("@ATTRIBUTE balanceOfPayments NUMERIC" + "\n");
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
    
    public static void writeAPDataToFile(String[][] data, String fileName)
    {
        try
        {
            FileWriter writer = new FileWriter(fileName + ".arff");

            String classes = 
            		"{" +
            		"0.0-0.5,"+
            		"0.5-1.0,"+
            		"1.0-1.5,"+
            		"1.5-2.0,"+
            		"2.0-2.5,"+
            		"2.5-3.0,"+
            		"3.0-3.5,"+
            		"3.5-4.0,"+
            		"4.0-4.5,"+ 
            		"4.5-5.0,"+
            		"5.0-5.5,"+
            		"5.5-6.0,"+
            		"6.0-6.5,"+
            		"6.5-7.0,"+
            		"7.0-7.5,"+
            		"7.5-8.0,"+
            		"8.0-8.5,"+
            		"8.5-9.0,"+
            		"9.0-9.5,"+
            		"9.5-10.0,"+
            		"10.0,"+
        			"}";
            
            // Write header
            writer.append("@RELATION " + fileName + "\n");
            writer.append("@ATTRIBUTE year NUMERIC " + "\n");
            writer.append("@ATTRIBUTE country STRING" + "\n");
            writer.append("@ATTRIBUTE unemploymentRate " + classes + "\n");
            writer.append("@ATTRIBUTE balanceOfPayments " + classes + "\n");
            writer.append("@ATTRIBUTE gdpPerInhabitant " + classes + "\n");
            writer.append("@ATTRIBUTE population " + classes + "\n");

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