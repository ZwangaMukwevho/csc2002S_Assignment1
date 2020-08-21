import java.util.ArrayList;
import java.util.List;
public class Thread extends java.lang.Thread{
    int basinNumber=0;
    int length;
    ArrayList<Integer> cols = new ArrayList<Integer>();

    //Each thread will be a specific column 
    int row;
    float[][] matrix;
    float[] thread;
    // Constructor
    Thread(int r,float [][] m, float [] t){
        row = r;
        matrix = m;
        thread = t;
      
    }
  

    public void run(){
        int length = thread.length;
        
        for(int i = 1; i<length-1;i++){

            
            //Comparisons
            if( (matrix[row][i-1] - thread[i] >0.01) &&
            (matrix[row][i+1] - thread[i] >0.01) &&
            (matrix[row-1][i] - thread[i] >0.01) &&
            (matrix[row+1][i] - thread[i] >0.01) &&
            (matrix[row-1][i-1] - thread[i] >0.01) &&
            (matrix[row+1][i-1] - thread[i] >0.01) &&
            (matrix[row-1][i+1] - thread[i] >0.01) &&
            (matrix[row+1][i+1] - thread[i] >0.01) 
            ){
                cols.add(i);
                basinNumber = basinNumber +1; 
                        
            }
            
        }
    }
}