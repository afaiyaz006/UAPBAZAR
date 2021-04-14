package uapbazar;
import uapbazar.product.*;
import java.io.*;
import java.util.Locale.Category;
public class StoreDataLoader {
	public static Store store=new Store("UAP BAZAR");
	public static String path="src\\uapbazar\\Store.txt";
    public static void writeObject(){
    	//loadSample();
        try{
            FileOutputStream fileOut = new FileOutputStream(new File(path));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(store);
            objectOut.close();
            fileOut.close();
            System.out.println("OK");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void  readObject(){
        try{
            FileInputStream fileIn = new FileInputStream(new File(path));
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            store=(Store)objectIn.readObject();
            fileIn.close();
            objectIn.close();
        }
        catch (Exception e){
            e.printStackTrace();
            
        }
    }
    public static void loadSample() {
    	store.addProduct("ABCD","ABCD", 0, "ABCD", null, null, 0);
    	store.addProduct("ABCD","ABCD", 0, "ABCD", null, null, 0);
    	store.addProduct("ABCD","ABCD", 0, "ABCD", null, null, 0);
    }
	
}
