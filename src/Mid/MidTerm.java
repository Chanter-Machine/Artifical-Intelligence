package Mid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class MidTerm
{

	public static void main(String[] args)
	{
//		String input = JOptionPane.showInputDialog( "enter an input" );
//        //String input = JOptionPane.showInputDialog( null, "enter an input", "Input Dialog Demo", JOptionPane.QUESTION_MESSAGE );
//        System.out.println( "input = " + input );
//        int xxx = Integer.parseInt( input );
//        System.out.println( input );
		
//		int P;
//		String getP = "Input a number P";
//        do
//        {
//        	String input = JOptionPane.showInputDialog( getP );
//        	P = Integer.parseInt(input);
//        }while(P>100);
		
	//	System.out.println(factorial(36));
		
//		for(int i=0;i<10;i++)
//		{
//			getSequence(36);
//			System.out.println("");
//		}
		
		int cp = 0;
		Random rand = new Random();
		for(int i=0;i<200;i++)
		{
			cp = rand.nextInt(8-3)+1;
			System.out.println(cp);
		}
		
	}
	
	public static double factorial(int num)
	{
		if (num == 1)
			return 1;
		else 
		{
			if(num >1)
			{
				return num * factorial(num-1);
			}
			return 1;
		}
	}	
	
	public static int[] getSequence(int no) {
        int[] sequence = new int[no];
        for(int i = 0; i < no; i++){
            sequence[i] = i;
        }
        Random random = new Random();
        for(int i = 0; i < no; i++){
            int p = random.nextInt(no);
            int tmp = sequence[i];
            sequence[i] = sequence[p];
            sequence[p] = tmp;
        }
        random = null;
        for(int i = 0; i < no; i++){
            System.out.print(sequence[i]+" ");
        }
        
        return sequence;
    }
	
	
	
	
}
