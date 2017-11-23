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
	
	
	public static void main(String args[]) {
		
		
		is17088224 g = new is17088224();
		g.checkRowAndCol();
		g.checkDiagonal();

		int v = g.list.size(); // The size of the data
		g.simSize = v;

		int[] ordering = new int [v];
		max = 9;  // Find the maximum value in the similarity matrix. It is needed in order to encode the colors
 //		TableCreation(g.list, ordering.length, ordering); // send your data for heat map visualization.
		
		g.iniApplication();
		g.sortingRandom(ordering);
		g.addfitness2Ordering();
		g.sortOrderings();
		g.reConstractOrderings();
		
	}

	public static void TableCreation(List<int[]> sim, int v, int[] ord) {

		final Object rowData[][] = new Object[v][v];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				rowData[i][j] = sim.get(i)[j];
			}
		}
		
		for(int i=0; i<ord.length;i++)
		{
			ord[i] = i+1;
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
		frame.setSize(50*v, 50*v);
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
//		inputGen();
//		inputCrAndMu();
//		inputH();
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
	
	
	
	public void sortingRandom( int[] ordering)
	{
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
//	        for(int j = 0; j < ordering.length; j++)
//	        {
//	        	System.out.print(orderingList.get(i)[j]+" ");
//	        }
//	        System.out.println("");
//		}
		
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
	
	public void addfitness2Ordering()
	{
		for(int i=0;i<this.OrderingList.size();i++)
		{
			OrderingList.get(i)[this.OrderingList.get(i).length-1] = fitnessCostCal(OrderingList.get(i));
		}
	}
	
	public void sortOrderings()	//sort orderings according to the fitness, from best to worst
	{
		Collections.sort(OrderingList, new Comparator<int[]>()
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
//			for(int j=0;j<OrderingList.get(i).length;j++)
//			{
//				System.out.print(OrderingList.get(i)[j]+" ");
//			}
//			System.out.println();
//		}
	}
	
	public void reConstractOrderings()	//replace the worst 1/3 orderings by using 1/3 best orderings
	{
		int count = this.P/3;
		for(int i=0;i<count;i++)
		{
			for(int j=0;j<this.OrderingList.get(i).length;j++)
			{
				OrderingList.get(this.P - 1 - i)[j] = OrderingList.get(i)[j];
			}
		}
	}
	
	
}
