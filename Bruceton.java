import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author malicious
 */
public class Bruceton {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("Input");
BufferedReader br = new BufferedReader(fr);
String s;

s = br.readLine();
Scanner scan = new Scanner(new File("Input"));
        int [] sr = new int[100];
        int [] curr = new int[100];
        int [] status = new int[100];
        int [] curr_val = new int[20];
        int i = 0, sample_no, j, flag = 0;
        while(scan.hasNextInt()){
                sr[i] = scan.nextInt();
                curr[i] = scan.nextInt();
                if(scan.next().equalsIgnoreCase("F"))
                    status[i] = 1;
                else
                    status[i] = 0;
                
                i++;
        }
        
        sample_no = i;
        int k = 0;
        for(i = 0; i < sample_no; i++){
            //hash_res = curr[i] % 20;
            for(j = 0; curr_val[j] != 0; j++){
                if(curr[i] == curr_val[j]){
                    flag = 1;
                    break;
                }
                else
                    flag = 0;
            }
            
            if(flag == 0){
                curr_val[k] = curr[i];
                k++;
            }
                
            
        }
        
        int dist_curr = k;
        int difference = Math.abs(curr_val[4] - curr_val[3]);
        int [][] table = new int[dist_curr][sample_no + 1];
        
        for(i = 0, j = dist_curr - 1; i < dist_curr && j >= 0; i++)
            table[i][0] = curr_val[j--];
        
        for(j = 0; j < sample_no; j++)
        {
            //i = 0;
            if(status[j] == 0)
            {
                i = 0;
                while(!(table[i][0] == curr[j])){
                    table[i][j+1] = -1;
                    i++;
                }
                    
                
                for(; i < dist_curr; i++)
                    table[i][j+1] = 0;
            }
            else
            {   
                i = 0;
               if(status[j] == 1){
                   
                while(!(table[i][0] == curr[j])){
                    table[i][j+1] = 1;
                    i++;
                }
                    table[i][j+1] = 1;
                    i++;
                
                for(; i < dist_curr; i++)
                    table[i][j+1] = -1;
               }
            }
            
        }
        
        //System.out.println(status[5]);
        System.out.println("\n");
                  
        for(i = 0; i < dist_curr; i++){
            if(table[i][0] < 1000)
                System.out.print(" ");
            for(j = 0; j <= sample_no; j++){
                
                
                switch(table[i][j]){
                    case -1: System.out.print("- ");
                            break;
                    case 0: System.out.print("N ");
                            break;
                    case 1: System.out.print("F ");
                            break;
                    default: System.out.print(table[i][j] + " ");
                            break;
                }
            }
            System.out.print("\n\n");
        }
        
        int [][] final_table = new int[dist_curr][5];
        
        int p = dist_curr;
        
        for(i = 0; i < dist_curr; i++){
            final_table[i][0] = table[i][0];
            final_table[i][1] = p--;
            k = 0;
            for(j = 0; j <= sample_no; j++){
                if(table[i][j] == 1)
                    k++;                
            }
            final_table[i][2] = k;
            final_table[i][3] = final_table[i][1] * final_table[i][2];
            final_table[i][4] = final_table[i][3] * final_table[i][1];
                
        }
        
        double sum_n = 0, sum_ni = 0, sum_nii = 0;
        
        for(i = 0; i < dist_curr; i++){
            sum_n = sum_n + final_table[i][2]; 
            sum_ni = sum_ni + final_table[i][3];
            sum_nii = sum_nii + final_table[i][4];
            
        }
        
        double Xmean = sum_ni / sum_n;
            Xmean = Xmean - 0.5;
            Xmean = Xmean * difference;
            Xmean = Xmean + (final_table[dist_curr - 1][0] - 20);
        
            
            
        double s_deviation = sum_n * sum_nii;
        s_deviation = s_deviation - Math.pow(sum_ni, 2);
        s_deviation = s_deviation / Math.pow(sum_n, 2);
        s_deviation = difference * (s_deviation + 0.029);
        s_deviation = s_deviation * 1.620;
        
        
        
        
        
        for(i = 0; i < dist_curr; i++){
            for(j = 0; j < 5; j++){
                System.out.print(final_table[i][j] + " ");
            }
            System.out.println("\n");
        }
        
        System.out.println("\n");
        System.out.println("Average Current Level: " + Xmean);
        System.out.println("Standard Deviation is: " + s_deviation);
        System.out.println("\nThe NFC for the tested sample is: " + (Xmean - (3.09 * s_deviation)) + " mA");
        System.out.println("The AFC for the tested sample is: " + (Xmean + (3.09 * s_deviation)) + " mA");          
    }
}
