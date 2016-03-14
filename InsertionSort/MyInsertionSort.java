import java.util.Scanner;
public class MyInsertionSort 
{
    public static void main(String[] args) 
    { 
        Scanner in = new Scanner(System.in);
        
        System.out.print("Enter data type to sort : ");
        String type = in.nextLine(); 
        
        System.out.print("Enter number of elements : ");
        String insertionSort = in.nextLine(); 
        int num=Integer.parseInt(insertionSort);
        String array[] = new String[num];
        for (int i = 0; i < array.length; i++)
        {
            System.out.print("Input the Number at array index "+i+": ");
            array[i] = in.nextLine();
        }
        MyInsertionSort.insertionSortByType(array,type);
        in.close();
    }
    

    public static void insertionSortByType(String array[], String type)
    {
    	switch (type) {
    		case "double":
    			Double[] ConvertedArrayDouble = new Double[array.length];
    			for (int i = 0; i<array.length; i++) ConvertedArrayDouble[i] = Double.parseDouble(array[i]);
    			MyInsertionSort.insertionSort(ConvertedArrayDouble);
    			break;
    		case "int":
    			Integer[] ConvertedArrayInt = new Integer[array.length];
    			for (int i = 0; i<array.length; i++) ConvertedArrayInt[i] = Integer.parseInt(array[i]);
    			MyInsertionSort.insertionSort(ConvertedArrayInt);
    		    break;
    		default:
    			MyInsertionSort.insertionSort(array);	
    	}
    }
    
    public static <E extends Comparable<? super E>> void insertionSort(E array[]) 
    { 
        int n = array.length; 
        for (int j = 1; j < n; j++) 
        { 
            E key = array[j]; 
            int i = j-1; 
            while ( (i > -1) && ( array[i].compareTo(key) > 0 ) ) 
            { 
                array [i+1] = array [i]; i--; 
            } 
            array[i+1] = key; 
        }
        printNumbers(array); 
    }
    
    public static <E> void printNumbers(E array[]) {
    	for (E i : array) {
	   System.out.println(i);
	}
    }
} 
