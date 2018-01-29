/*Kai Kang
 *CEG7370 section 02
 *project 2
 **/



import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sort extends Remote {
    int[] callsort(int[] arr) throws RemoteException;
}