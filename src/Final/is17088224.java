package Final;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.lang.Math;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


class EvenOddRenderer implements TableCellRenderer {

	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Color foreground, background;

		double val = Double.parseDouble(value.toString());
		val = 255 - 255 * ((double) val / (double) is17088224.max);
		Color col = new Color((int) val, 0, 0);
		foreground = Color.WHITE;
		background = col;

		renderer.setForeground(foreground);
		renderer.setBackground(background);
		return renderer;
	}
}

public class is17088224 
{
	public static int max = 0;
	public int array[][];
	public List<int[]> list;
	public int N,P,Gen,H,Cr,Mu;
	public List<int[]> OrderingList;
	public int simSize;
	
	
	public static void main(String args[]) 
	{
		
		is17088224 g = new is17088224();
		g.checkRowAndCol();
		g.checkDiagonal();

		int v = g.list.size(); // The size of the data
		g.simSize = v;

		int[] ordering = new int [v];
		for(int i=0;i<v;i++)
		{
			ordering[i] = i+1;
		}
		max = 9;  // Find the maximum value in the similarity matrix. It is needed in order to encode the colors
 		 // send your data for heat map visualization.
		
		g.iniApplication();
		g.sortOrderings(g.OrderingList);
		g.replaceWorstOrderings(g.OrderingList);;
		TableCreation(g.list, ordering.length, ordering);
		g.createGenerations(g.OrderingList);
	}

	public static void TableCreation(List<int[]> sim, int v, int[] ord) {

		final Object rowData[][] = new Object[v][v];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				rowData[i][j] = sim.get(i)[j];
			}
		}
		
		final String columnNames[] = new String[v];
		for (int i = 0; i < v; i++) {
			columnNames[i] = ord[i] + "";
		}
		final JTable table = new JTable(rowData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultRenderer(Object.class, new EvenOddRenderer());
		JFrame frame = new JFrame("Heat Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(19*v, 18*v);
		frame.setVisible(true);
	}


	public is17088224()
	{
		this.read();
	}

	
	public void read()		
	{
		
		List<int[]> list = new ArrayList();
		int  arrTmp[] ;
		FileReader fr = null;
		BufferedReader br = null;

		try
		{
			File file = new File("src/input.txt");
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String temp="";
			
			while(null!=(temp=br.readLine()))		//read by line
			{
				
			    String newArr[] = temp.split("  ");
			    
			    
			    arrTmp = new int[newArr.length];
			    for(int i=0; i<newArr.length;i++)
			    {
			    	arrTmp[i] = Integer.parseInt(newArr[i]);
			    }
			    list.add(arrTmp);
			}
//			for(int i=0;i<list.size();i++)
//			{
//				for(int j=0;j<list.get(i).length;j++)
//				{
//					System.out.print(list.get(i)[j]);
//				}
//				System.out.println();
//			}
			this.list = list;
		}
		catch(Exception e)
		{
		  System.out.print(e.toString());
		}
		finally
		{
			
				try 
				{
					br.close();
					fr.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}		
	    
	}
	
	public void checkRowAndCol()	//check the input.txt file and validate it.
	{
		String rowAndCol = "The number of columns is not equal to the number of rows";
		int rowNum = this.list.size();
		for(int i=0; i<rowNum;i++)
		{
			if(this.list.get(i).length!=rowNum)
			{
				rowAndCol += " in row "+i;
				JOptionPane.showMessageDialog(null, rowAndCol, null, JOptionPane.ERROR_MESSAGE);
				rowAndCol = "The number of columns is not equal to the number of rows";
			
			}
		}
		this.N = rowNum;
		
	}
	public void checkDiagonal()
	{	
		int rowNum = this.list.size();
		int sum=0, count=0;
		String diagonal = "The number on line ";
		
		for(int i=0; i<rowNum; i++)
		{
			if(this.list.get(i)[i]!=0)
			{
				diagonal += i + " column "+ i +" is not zero"; 
				JOptionPane.showMessageDialog(null, diagonal, null, JOptionPane.ERROR_MESSAGE);
				diagonal = "The number on line ";
			}	
			else
			{
				for(int j=0;j<this.list.get(i).length;j++)
				{
					if(this.list.get(i)[j]!=0)
					{
						count++;
						sum+=this.list.get(i)[j];
					}
				}
				this.list.get(i)[i] = sum/count;
				sum=0;
				count=0;
			}
			
		}
		
		
	}
	
	public void inputP()
	{
	
		double nFactorial = factorial(this.N);

		String getP = "Input the size of population:P, P<=N!("+nFactorial+")";
        do
        {
        	String input = JOptionPane.showInputDialog( getP );
        	this.P = Integer.parseInt(input);
        }while(this.P>nFactorial||this.P<0);
	}
	
	public void inputGen()
	{
		

		String getGen = "Input the number of generations: An integer and positive vale!";
        do
        {
        	String input = JOptionPane.showInputDialog( getGen );
        	this.Gen = Integer.parseInt(input);
        }while(this.Gen<=0);
	}
	
	public void inputH()
	{
		String getH = "Input the number H(H<Gen)";
        do
        {
        	String input = JOptionPane.showInputDialog( getH );
        	this.H = Integer.parseInt(input);
        }while(H>=Gen||H<=0);
	}
	
	public void inputCrAndMu()
	{

		String getCr = "Input probability for cross-over[0-100]";
		String getMu = "Input probability for mutation[0-100]";
		String sum = "The sum of the probability of cross-over and mutation should less than 100";
		
		do
		{
			do
			{
				String input1 = JOptionPane.showInputDialog(getCr);
				Cr = Integer.parseInt(input1);
			}while(Cr>100||Cr<0);
			
			do
			{
				String input2 = JOptionPane.showInputDialog(getMu);
				Mu = Integer.parseInt(input2);
			}while(Mu>100||Mu<0);
			
			if(Cr+Mu>100)
			{
				
				JOptionPane.showMessageDialog(null, sum);
			}
		}while(Cr+Mu>100);
	}
	
	public void iniApplication()	//enter some parameters and validate them
	{
		inputP();
		inputGen();
		inputCrAndMu();
		inputH();
		iniOrderings();
	}
	
	public  double factorial(int num)
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
	
	
	
	public void iniOrderings()
	{
		int []ordering = new int[37];
		this.OrderingList = new ArrayList();
		Random random = new Random();
		
		for(int i=0;i<this.P;i++)
		{
			ordering = new int[this.list.size()+1];
			for(int j=0;j<ordering.length-1;j++)
			{
				ordering[j] = j+1;
			}
	        for(int j = 0; j < ordering.length-1; j++)
	        {
	            int p = random.nextInt(ordering.length-1);
	            int tmp = ordering[j];
	            ordering[j] = ordering[p];
	            ordering[p] = tmp;
	        }
	     //   System.out.println(ordering);
			OrderingList.add(ordering);
		}
		
//		for(int i=0;i<this.P;i++)
//		{
//				
//	        for(int j = 0; j < ordering.length-1; j++)
//	        {
//	        	System.out.print(this.OrderingList.get(i)[j]+" ");
//	        }
//	        System.out.println("");
//		}
		
		addfitness2Ordering(OrderingList);
		
		OrderingList = this.addfitness2Ordering(OrderingList);
		
		writeToFile(OrderingList);
	}
	
	public void writeToFile(List<int []> list) 
	{
		File file = new File("src/AI17.txt");
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		String tmpStr = "";
		
		
		try
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
			for(int i=0;i<list.size();i++)
			{
				tmpStr="";
		        for(int j = 0; j < list.get(i).length-1; j++)
		        {
		        	tmpStr += list.get(i)[j]+" ";
		        	
		        }
//		        System.out.println(tmpStr);
		        bw.write(tmpStr+"\r\n");
			}
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
			try
			{
				bw.close();
				fw.close();
			} catch (Exception e2)
			{
				// TODO: handle exception
			}
		}
	}
	
	/*-----------------------------final submisssion part------------------------------------------*/
	public List<int []> changeSimMatric(List<int []> oldList, int [] ordering )	//move elements according to an ordering
	{
		List<int []> newList = new ArrayList();
		for(int i=0;i<oldList.size();i++)
		{
			int [] newArr = new int[oldList.get(i).length];
			for(int j=0;j<newArr.length;j++)
			{
				newArr[j] = oldList.get(ordering[i]-1)[ordering[j]-1];
			}
			newList.add(newArr);
		}
		return newList;
	}
	
	public int fitnessCostCal(int []ordering)
	{
		int fitnessCost = 0;
		for(int i=1; i<this.simSize;i++)
		{
			for(int j =1; j<this.simSize;j++)
			{
				fitnessCost += list.get(ordering[i]-1)[ordering[j]-1]*Math.abs(i-j);
			}
		}
//		System.out.println(fitnessCost);
		return fitnessCost;
	}
	
	public List<int []> addfitness2Ordering(List<int []> orderings)		//calculae each fitness and add to the end of each ordering
	{
		for(int i=0;i<orderings.size();i++)
		{
			orderings.get(i)[orderings.get(i).length-1] = fitnessCostCal(orderings.get(i));
		}
		
		return orderings;
	}
	
	public List<int[]> sortOrderings(List<int []> orderings)	//sort orderings according to the fitness, from best to worst
	{
		Collections.sort(orderings, new Comparator<int[]>()
		{
		    public int compare(int arr1 [],int arr2[])
		    {
		    	
                int d1=arr1[36];
                int d2=arr2[36];
                if(d2>d1)
                {                   
                    return -1;
                }
                else if (d1<d2)
                {
                    return 1;
                }
                return 0;
		     }
		});
	
//		for(int i=0;i<this.P;i++)
//		{
//			for(int j=0;j<orderings.get(i).length;j++)
//			{
//				System.out.print(orderings.get(i)[j]+" ");
//			}
//			System.out.println();
//		}
		
		return orderings;
	}
	
	public List<int []> replaceWorstOrderings(List<int []> orderings)	//replace the worst 1/3 orderings by using 1/3 best orderings
	{
		int count = this.P/3;
		for(int i=0;i<count;i++)
		{
			for(int j=0;j<orderings.get(i).length;j++)
			{
				orderings.get(this.P - 1 - i)[j] = orderings.get(i)[j];
			}
		}
		
		
		
//		System.out.println();
//		for(int i=0;i<this.P;i++)
//		{
//			for(int j=0;j<orderings.get(i).length;j++)
//			{
//				System.out.print(orderings.get(i)[j]+" ");
//			}
//			System.out.println();
//		}
		
		return orderings;
	}

	
	public int getRandomValue(int begin, int end)		//get a random value between [1-100]
	{
		Random rand = new Random();
		return rand.nextInt(end)+begin;
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
	
	public String decideCrMuGe( int restPopulation)		//judge new ordering comes from Mutation, Cross-over or Generate
	{
		
		if(restPopulation>1)
		{
			int value = getRandomValue(1,100);
			if(value>=1 && value<=this.Cr)
			{
				return "C";
			}
			else if(value>this.Cr && value<= this.Cr+this.Mu)
			{
				return "M";
			}
			else if(value> this.Cr+this.Mu && value<=100)
			{
				return "G";
			}
		}
		
		else if(restPopulation == 1)
		{
			int value = getRandomValue(this.Cr+1,100);
			if(value>this.Cr && value<= this.Cr+this.Mu)
			{
				return "M";
			}
			else if(value> this.Cr+this.Mu && value<=100)
			{
				return "G";
			}
		}
		
		return "Again";
	}
	
	public List<int []> createNewGeneration(List<int []> orderings)		//create new Generation of orderings
	{
		int restPopulation = orderings.size();
		List<int []> newOrderings = new ArrayList();
		int tmpInd1, tmpInd2;
		
		while(!orderings.isEmpty())		//judge origin ordering is not empty
		{
			if(decideCrMuGe(restPopulation) == "C")
			{
				do
				{
					tmpInd1 = getRandomValue(0, restPopulation);
					tmpInd2 = getRandomValue(0, restPopulation);
				}while(tmpInd2 == tmpInd1);
				
				newOrderings = getCrossover(orderings.get(tmpInd1), orderings.get(tmpInd2), newOrderings);
				
				if(tmpInd1>tmpInd2)
				{
					orderings.remove(tmpInd1);
					orderings.remove(tmpInd2);
				}
				else
				{
					orderings.remove(tmpInd2);
					orderings.remove(tmpInd1);
				}
				
				restPopulation = orderings.size();
			}
			else if(decideCrMuGe(restPopulation) == "M")
			{										
				int randomIndex = getRandomValue(0, restPopulation);
				newOrderings = getMutation(orderings.get(randomIndex), newOrderings);
				orderings.remove(randomIndex);
				restPopulation = orderings.size();
			}
			else if(decideCrMuGe(restPopulation) == "G")
			{
				int randomIndex = getRandomValue(0, restPopulation);
				newOrderings.add(orderings.get(randomIndex));
				orderings.remove(randomIndex);
				restPopulation -= 1;
			}
		}
		newOrderings = addfitness2Ordering(newOrderings);
		newOrderings = sortOrderings(newOrderings);
		newOrderings = replaceWorstOrderings(newOrderings);
		return newOrderings;
	}

/*----------------------create two new orderings from crossover-----------------------------------------------*/
	public List<int[]> getCrossover(int [] ordering1, int [] ordering2, List<int[]> orderings)
	{
		int cp, size = ordering1.length-1;
		Random rand = new Random();
		cp = rand.nextInt(size-3)+2;		//get Mutation point
		ArrayList al1;
		ArrayList al2;
//		System.out.println(cp);
		int [] crOrdering1 =  new int[size+1];
		int [] crOrdering2 = new int[size+1];
		int [] tmp = new int[size+1];
		
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
				}

			}
		}
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
		
/*-----------Create each generation of orderings-----------------------*/
	public List<int[]> createGenerations(List<int[]> orderings)
	{
		
		for(int k=0;k<this.Gen;k++)
		{
			
			orderings = this.createNewGeneration(orderings);
			if(k==this.H)
			{
				List<int []> newlist = this.changeSimMatric(this.list, orderings.get(0));
				TableCreation(newlist, this.simSize, orderings.get(0));
			}
		}
			
		List<int []> newlist = this.changeSimMatric(this.list, orderings.get(0));
		TableCreation(newlist, this.simSize, orderings.get(0));
		
		return orderings;
	}

}
