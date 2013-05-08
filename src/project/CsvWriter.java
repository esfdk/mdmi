package project;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter
{
    /**
     * Private constructor for static class.
     */
    private CsvWriter(){}
    
    /**
     * Write data to .csv file.
     *
     * @param data The data to write to the file.
     * @param fileName The file to write data to.
     */
    public static void writeDataToFile(String[][] data, String fileName)
    {
        try
        {
            FileWriter writer = new FileWriter(fileName);

            for (String[] dataLine : data)
            {
                for (int i = 0; i < dataLine.length; i++)
                {
                	if(!dataLine[i].endsWith("\""))
                	{
                		dataLine[i] = dataLine[i] + "\""; 	
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