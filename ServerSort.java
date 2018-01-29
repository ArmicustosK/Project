/*Kai Kang
 *CEG7370 section 02
 *project 2
 **/
	
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

	
public class ServerSort extends UnicastRemoteObject implements Sort {
	
    public ServerSort() throws RemoteException {}

    public int[] callsort(int[] arr) {

    	
    	int i,j,k;
    	System.out.print("input from client: ");
    	for(k=0;k<arr.length;k++) System.out.print(arr[k]+" ");
    	System.out.println();
    	for(i=0;i<arr.length;i++)//sort
    	{
    		for(j=0;j<arr.length-1;j++)
    		{
    			int temp;
    			if(arr[j]>arr[j+1])
    			{
    				temp=arr[j];
    				arr[j]=arr[j+1];
    				arr[j+1]=temp;
    			}
    		}
    	}
    	System.out.println("Sorting process successed in Client");
    	return arr;
    	
    }
	
    public static void main(String args[]) {
	
	try {
	    //ServerSort obj = new ServerSort();
	    //Sort stub = (Sort) UnicastRemoteObject.exportObject(obj, 0);

	    // Bind the remote object's stub in the registry
	    //Registry registry = LocateRegistry.getRegistry();
	    //registry.bind("Sort", stub);
	    Naming.rebind("ServerSort", new ServerSort());
	    System.err.println("Server ready");
	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}

