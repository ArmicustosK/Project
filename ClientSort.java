/*Kai Kang
 *CEG7370 section 02
 *project 2
 **/
	


//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
import java.util.Scanner;
import java.rmi.*;

public class ClientSort {

    private ClientSort() {}

    public static void main(String[] args) {
    	int i,n;
    	System.out.println("please enter integers you want to sort");
    	Scanner read = new Scanner(System.in);
    	String a = read.nextLine();
    	String[] s = a.split("\\s+");
    	int size = s.length;
       	int[] arr = new int[size];

    	for(i=0;i<arr.length;i++)
    	{
    		arr[i] = Integer.parseInt(s[i]);
    	}
    	String host = (args.length < 1) ? null : args[0];
    	try {
	    
    		//Registry registry = LocateRegistry.getRegistry(host);
    		//Sort stub = (Sort) registry.lookup("Sort");
    		//String response = stub.callsort();
    		Sort sorter = (Sort) Naming.lookup("ServerSort");
    		arr = sorter.callsort(arr);
    		System.out.println("number of integers inputed:"+size);
    		System.out.println("response:sorted list");
    	} catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
    	}
    	for (int j=0;j<arr.length;j++) System.out.print(arr[j]+" ");
    	System.out.println();
    }
}

