import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;


public class sequential {

    public static void main(final String args[]){

        hello helloObject = new hello();
        int[] m = new int[2];
        m = helloObject.arraySize("large_in.txt");
        int rows = m[0];
        int cols = m[1];
        float[][] matrix = new float[rows][cols];
        matrix = helloObject.readData("large_in.txt");
        int result = sequentialComparison(matrix,rows,cols,"large_out.txt");
        System.out.println(result);
    }
    public static int sequentialComparison(float [][] m, int rows, int cols, String outName){
        float[][] matrix;
        matrix = m;

        int basinNumber = 0;

        //Arraylist to hold columns that represent basins found
        ArrayList<Integer> columns = new ArrayList<Integer>();
        ArrayList<Integer> row = new ArrayList<Integer>();

        //Array to hold elements that will be looped through in the matrix
        float [] thread = new float[cols];

        for(int j=1;j<rows-1;j++){
            for(int i=1;i<cols-1;i++){
                thread[i] = matrix[j][i]; 
                if( (matrix[j][i-1] - thread[i] >0.01) &&
                (matrix[j][i+1] - thread[i] >0.01) &&
                (matrix[j-1][i] - thread[i] >0.01) &&
                (matrix[j+1][i] - thread[i] >0.01) &&
                (matrix[j-1][i-1] - thread[i] >0.01) &&
                (matrix[j+1][i-1] - thread[i] >0.01) &&
                (matrix[j-1][i+1] - thread[i] >0.01) &&
                (matrix[j+1][i+1] - thread[i] >0.01) 
                ){
                    columns.add(j);
                    row.add(i);
                    basinNumber = basinNumber +1;}   
            }
            thread = new float[cols];
        }

            // Variables which hold float when it is converted to string so filewriter can write to outfile
            String tempWriter = "";
            
            try{
                FileWriter writer = new FileWriter(outName);
                tempWriter = String.valueOf(basinNumber) + "\n";
                writer.write(tempWriter);
                for(int k = 0;k<row.size();k++){
                    tempWriter = String.valueOf(columns.get(k)) + " "+ String.valueOf( row.get(k)) + "\n";
                    writer.write(tempWriter);
                    //System.out.println(row.get(k)+" "+columns.get(k));
                }
                writer.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }

        
        return basinNumber;
    }
}