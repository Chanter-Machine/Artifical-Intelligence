package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test
{

	public static void main(String[] args)
	{
		Test t = new Test();
//		int cp;
//		for(int i=0;i<900;i++)
//		{
//			Random rand = new Random();
//			cp = rand.nextInt(37-4)+2;
//			System.out.println(cp);
//		}
		
		int[] ord1 = {0,1,2,3,4,5,6,7,8};
		int[] ord2 = {8,7,6,5,4,3,2,1,0};
		int [] ord3 = {27 ,26, 32, 23, 34, 29, 7, 11, 28, 5, 35, 24, 14, 12, 19, 16, 33, 4, 30, 3, 21, 31, 2, 1, 10, 15, 6, 13, 25, 8, 17, 22, 20,18,9,36,-1};
		int [] ord4 = {7,4, 6, 31, 18, 5, 36, 10, 13, 11, 12, 22, 30, 9, 29, 1, 35, 34, 27, 16, 8, 28, 3, 26, 20, 2, 33, 23, 24, 17, 14, 21, 15, 19, 32, 25,-1};
		//		t.getMutation(ord1, ord2);
		List<int[]> orderings = new ArrayList();
		for(int i=0;i<ord3.length;i++)
		{
			System.out.print(ord3[i]+" ");
		}
		System.out.println();
		for(int i=0;i<ord3.length;i++)
		{
			System.out.print(ord4[i]+" ");
		}
		System.out.println();
		orderings = t.getCrossover(ord3, ord4, orderings);
		
		System.out.println();
		for(int i=0;i<ord3.length;i++)
		{
			System.out.print(orderings.get(0)[i]+" ");
		}
		System.out.println();
		
		for(int i=0;i<ord3.length;i++)
		{
			System.out.print(orderings.get(1)[i]+" ");
		}
	}
	
	public List<int[]> getCrossover(int [] ordering1, int [] ordering2, List<int[]> orderings)
	{
		int cp, size = ordering1.length-1;
		Random rand = new Random();
		cp = rand.nextInt(size-3)+2;		//get Mutation point
		ArrayList al1;
		ArrayList al2;
		System.out.println(cp);
		int [] crOrdering1 =  new int[size+1];
		int [] crOrdering2 = new int[size+1];
		int [] tmp = new int[size+1];
		
//		for(int i=0; i<=cp;i++)
//		{
//			crOrdering1[i] = ordering1[i];
//		}
		tmp = ordering1;
		crOrdering1 = copyArr(cp, ordering1, ordering2, crOrdering1);
		crOrdering2 = copyArr(cp, ordering2, ordering1, crOrdering2);
		
		al1 = findRepeatElement(crOrdering1);
		al2 = findRepeatElement(crOrdering2);
		if(!al1.isEmpty())
		{
			for(int i=0;i<al1.size();i++)
			{
				int tmpInt = crOrdering1[(int) al1.get(i)];
				crOrdering1[(int) al1.get(i)] = crOrdering2[(int) al2.get(al1.size()-1-i)];
				crOrdering2[(int) al2.get(al1.size()-1-i)] = tmpInt;
			}
		}
		
		orderings.add(crOrdering1);
		orderings.add(crOrdering2);
		return orderings;
	}
	
	
	public int[] copyArr(int cp, int [] ordering1, int[] ordering2, int[] newOrdering)		//the process of mutation, copy a part of orderings to another ordering
	{
		for(int i=0; i<cp; i++)
		{
			newOrdering[i] = ordering1[i];
		}
		for(int i=cp;i<ordering1.length-1;i++)
		{
			newOrdering[i] = ordering2[i];
		}
		return newOrdering;
	}
	
	public ArrayList findRepeatElement(int[]ordering)	//create a list to record index of which value has repeated 
	{
		ArrayList al = new ArrayList();
		for(int i=0;i<ordering.length-1;i++)
		{
			for(int j=i+1;j<ordering.length;j++)
			{
				if(ordering[i]==ordering[j])
				{			
					al.add(i);
					System.out.print(i+" ");
				}

			}
		}
		System.out.println();
		return al;
	}
	
/*----------------------Create an new Ordering from Mutation--------------------------------------------*/
	
	public List<int[]> getMutation(int [] ordering, List<int[]> newOrderings)
	{
		int index1 = get2RandowValue(0, ordering.length-1)[0];
		int index2 = get2RandowValue(0, ordering.length-1)[1];
		int tmp = 0;
		
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
