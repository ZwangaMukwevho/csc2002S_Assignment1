
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class hello{  
    public static void main(final String args[]) {

        // Defining local variables
        int[] m = new int[2];
        m = arraySize("med_in.txt");
        int rows = m[0];
        int cols = m[1];
        float[][] matrix = new float[rows][cols];
        matrix = readData("med_in.txt");
        
        try{
        int basinNumber = comparisons(m[0]-2,matrix,rows,cols,"med_out.txt");
        System.out.println(basinNumber);}
        catch (InterruptedException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}     
        
    }


    public static float[][] readData(final String fileName ) {
      
        int loopLength;
        String Name = fileName;
        // Getting rows and columns of the grid
        int[] m = new int[2];
        m = arraySize(Name);

        final float[][] matrix = new float[m[0]][m[1]];

        // Reading in the data from the text files
        
        final File file = new File(Name);
        try {
            final Scanner sc = new Scanner(file);

            final StringTokenizer st1 = new StringTokenizer(sc.nextLine());
            final StringTokenizer st2 = new StringTokenizer(sc.nextLine());

            loopLength = Integer.parseInt(st1.nextToken());
            loopLength = st2.countTokens() / loopLength;
           
            // Adding lines into an array
            for (int j = 0; j < m[1]; j++) {
                for (int i = 0;i<m[0]; i++){
                matrix[j][i] = Float.parseFloat(st2.nextToken());}
            }
            sc.close();

        } catch (final IOException e) {
            e.printStackTrace();
        }

        return matrix;

    }

    public static int[] arraySize(final String fileName) {

        //Initialising values that will hold values for the columns and the rows
        int rows = 0;
        int cols = 0;
        int[] m = new int[2];


        // Reading in the data from the text files
        String Name = fileName;
        final File file = new File(Name);
        try {
            final Scanner sc = new Scanner(file);

            final StringTokenizer st1 = new StringTokenizer(sc.nextLine());
            
            rows = Integer.parseInt(st1.nextToken());
            cols = Integer.parseInt(st1.nextToken());
            m[0] = rows;
            m[1] = cols;
            sc.close();

        } catch (final IOException e) {
                e.printStackTrace();
            }
   
            return m ;
           
    }

    public static int comparisons(int numTs, float [][] m, int rows, int cols,String outName) throws InterruptedException{
        //Variable to hold the number of basins
        int basinNumber = 0;

        //Creating the number of thread object
        Thread[] ts = new Thread[numTs];

        // Assigning values to the grid
        float[][] matrix;
        matrix = m;

        // Changing variables in the loop
        int counter = 0;
        float [] threadElements = new float[cols];

        // Adding lines into individaul Threads arrays
        for (int j = 1; j < rows-1; j++) {
            
            for (int i = 0;i<cols; i++){
                threadElements[i] = matrix[j][i];   
            }
            ts[counter] = new Thread(j,matrix,threadElements);

            ts[counter].start();
            threadElements = new float[cols];
           
            counter = counter + 1;
        }

        //Arraylist to hold columns that represent basins found
        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> row = new ArrayList<String>();
        
        //Wrting to output file
        String tempWriter = "";
       
           
        for(int p = 0;p < numTs;p++){
            ts[p].join();
            basinNumber = basinNumber + ts[p].basinNumber; 

            for(int r = 0; r<ts[p].cols.size() ; r ++) {
                row.add(String.valueOf(ts[p].row));
                columns.add(String.valueOf(ts[p].cols.get(r)));}
                                                    }
           
        try{
            FileWriter writer = new FileWriter(outName);  
            tempWriter = String.valueOf(basinNumber) + "\n";
            writer.write(tempWriter);

            for(int s = 0; s<row.size();s++){
                tempWriter = row.get(s) + " " + columns.get(s) + "\n";
                writer.write(tempWriter);
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
           
        }


        return basinNumber;
    }
    

    
}