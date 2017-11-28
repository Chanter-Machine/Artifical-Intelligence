package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test2
{

	public static void main(String[] args)
	{
		Test2 t = new Test2();
		int [] ord3 = {27 ,26, 32, 23, 34, 29, 7, 11, 28, 5, 35, 24, 14, 12, 19, 16, 33, 4, 30, 3, 21, 31, 2, 1, 10, 15, 6, 13, 25, 8, 17, 22, 20,18,9,36,-1};
		List<int []> newOrderings = new ArrayList();
		for(int i=0;i<ord3.length;i++)
		{
			System.out.print(ord3[i]+" ");
		}
		System.out.println();
		newOrderings = t.getMutation(ord3, newOrderings);
		for(int i=0;i<ord3.length;i++)
		{
			System.out.print(newOrderings.get(0)[i]+" ");
		}
	}

	public List<int[]> getMutation(int [] ordering, List<int[]> newOrderings)
	{
		int index1 = get2RandowValue(0, ordering.length-1)[0];
		int index2 = get2RandowValue(0, ordering.length-1)[1];
		int tmp = 0;
		System.out.println(index1);
		System.out.println(index2);
		tmp = ordering[index1];
		ordering[index1] = ordering[index2];
		ordering[index2] = tmp;
		
		newOrderings.add(ordering);
		return newOrderings;
	}
	
	public int[] get2RandowValue(int begin, int end)
	{
		int []tmp = new int[2];
		Random rand1 = new Random();
		Random rand2 = new Random();
		do
		{
			tmp[0] = rand1.nextInt(end)+begin;
			tmp[1] = rand2.nextInt(end)+begin;
		}while(tmp[0]==tmp[1]);
		return tmp;
	}
}
