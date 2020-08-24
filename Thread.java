import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Thread extends RecursiveTask<Integer>{
    int basinNumber=0;
    int length;
    ArrayList<Integer> cols = new ArrayList<Integer>();
	static final int SEQUENTIAL_CUTOFF=500;


    //Each thread will be a specific column 
    int row;
    float[][] matrix;
    float[] thread;
    int hi;
    int lo;
    int col;

    // Constructor
    Thread(float [][] m, float [] t, int l, int h,int c,int r){
        matrix = m;
        thread = t;
        hi = h;
        lo = l;
        col = c;
        row = r;}
  

    protected Integer compute(){
        int length = thread.length;
        int tempRow = 0;
        int tempCol = 0;
        
        if((hi-lo)< SEQUENTIAL_CUTOFF){
            for(int i = lo; i<hi;i++){

               if(!(lo<col || (hi>length-col))){
                tempRow = i/row;
                tempCol = i - col*tempRow; 
                //System.out.println(tempCol);
               
              
                    if( !((i%col == 0) || (tempCol+1 ==col))){
                        if( (matrix[tempRow][tempCol-1] - thread[i] >0.01) &&
                            (matrix[tempRow][tempCol+1] - thread[i] >0.01) &&
                            (matrix[tempRow-1][tempCol] - thread[i] >0.01) &&
                            (matrix[tempRow+1][tempCol] - thread[i] >0.01) &&
                            (matrix[tempRow-1][tempCol-1] - thread[i] >0.01) &&
                            (matrix[tempRow+1][tempCol-1] - thread[i] >0.01) &&
                            (matrix[tempRow-1][tempCol+1] - thread[i] >0.01) &&
                            (matrix[tempRow+1][tempCol+1] - thread[i] >0.01) 
                            ){
                                basinNumber = basinNumber +1; }
                            }
                                    }
                else{
                    ;}
                         }// End of for loop
                         return basinNumber;}//End of first if statement
        else{
            Thread left = new Thread(matrix,thread,lo,(hi+lo)/2,col,row);
            Thread right = new Thread(matrix,thread,(hi+lo)/2,hi,col,row);

            left.fork();
            int rightAns = right.compute();
            int leftAns = left.join();
            return leftAns + rightAns;
        }
        
    }
}