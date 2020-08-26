
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class hello{  
    static long startTime = 0;
    static Map<Integer,Integer> map = new HashMap<>();

    public static void main(final String args[]) {

        // Defining local variables
        int[] m = new int[2];
        m = arraySize("4600by4600_in.txt");
        int rows = m[0];
        int cols = m[1];
        float[][] matrix = new float[rows][cols];
        float [] threads = new float[rows*cols];
        
        matrix =  readData("4600by4600_in.txt");
        threads = singeDimensionArrayData("4600by4600_in.txt");

        double start = System.nanoTime();
        int basins = comparisons(matrix, threads, cols, rows);
        double end = System.nanoTime();
        System.out.println((end-start)/1000000000);
      
        writefile("small_out_thread.txt",basins);
        
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

    public static float[] singeDimensionArrayData(final String fileName){
        int loopLength;
        String Name = fileName;
        // Getting rows and columns of the grid
        final float[] array;
        final float[] temparr = new float[1];

        // Reading in the data from the text files
        
        final File file = new File(Name);
        try {
            final Scanner sc = new Scanner(file);

            final StringTokenizer st1 = new StringTokenizer(sc.nextLine());
            final StringTokenizer st2 = new StringTokenizer(sc.nextLine());
 
            loopLength = st2.countTokens();
            

            array = new float[loopLength ];
            // Adding lines into an array
            for (int j = 0; j < loopLength; j++) {
                array[j] = Float.parseFloat(st2.nextToken());
            }
            
            sc.close();
                return array;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return temparr;
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

    public static int comparisons(float [][] m, float[] t, int cols, int rows) {

        ForkJoinPool fjpool = new ForkJoinPool();
        Thread result  = new Thread(m,t,0,t.length,cols,rows);
        int basins = fjpool.invoke(result);
        map = result.rowList();

        return(basins); }

    public static void writefile(String outName,int basins){
        String tempWriter = "";
        //sortbykey();

        int basinNumber = basins;
        try{
            FileWriter writer = new FileWriter(outName);  
            tempWriter = String.valueOf(basinNumber) + "\n";
            writer.write(tempWriter);

            // Sorting the columns and rows 
            ArrayList<Integer> sortedKeys = new ArrayList<Integer>(map.keySet()); 
            Collections.sort(sortedKeys); 

            for (Integer x : sortedKeys) {
                tempWriter =  String.valueOf(x) + " " + String.valueOf(map.get(x)) + "\n";
                writer.write(tempWriter);
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();  } 
    }
    
   
}