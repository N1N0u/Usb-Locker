import java.io.File;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;


public class AutoDetect {
	static DefaultTableModel model=(DefaultTableModel) Main.table.getModel();
	
    public static void detect() {
    	//set table row to 0
    	model.setRowCount(0);
    	
    	//get All Drives [C, ....] 
        
        
       // System.out.println("Current Root Directories: " + Arrays.toString(roots));
        Thread thread = new Thread(() -> {
        	File[] roots = File.listRoots();
        while (true) {
        	//Get new Drives
            File[] newRoots = File.listRoots();
           //Check if there is a new Drive connected
            if(roots.length < newRoots.length)
            {
            	//Loop files if the new Drive list to see if there is a new one
            	for (File root : newRoots) {
                 	if (!contains(roots,root)) {
                 		model.addRow(new Object[] {root.getAbsolutePath(),"Protection is On"});
                 		String xString=root.getAbsolutePath().substring(0, root.getAbsolutePath().length() - 2);
                 		LockUnlock.lockUsb(xString);
                 		try {
            				Runtime.
            				   getRuntime().
            				   exec("powershell.exe Start-Process  lock.bat -verb RunAs");
            	        } catch (IOException e) {
            	            e.printStackTrace();
            	        }
                      //  System.out.println(xString); 
                 		System.out.println("Drive connected and now readonly: " + root.getAbsolutePath());
                     }
            	 }
            	//Modify the main list of drives to new one with new drives
            	 roots = newRoots;
            	 }
            //Check if there is a removed drive and do same thing as adding a new one (remove it from the list)
            else if(roots.length > newRoots.length)
            {
            	 for (File root : roots) {
                 	if (!contains(newRoots,root)) 
                 	{
                 		for(int i=0;i<model.getRowCount();i++)
                 		{
                 			if(((String)model.getValueAt(i, 0)).equals(root.getAbsolutePath()))
                 			{
                 				model.removeRow(i);
                 			}
                 		}
                         System.out.println("Drive removed: " + root.getAbsolutePath());
                     }
            	 }
             	//Modify the main list of drives to new one with new drives

            	 roots = newRoots;
            }
            try {
                Thread.sleep(1000); // Check every second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        });

        thread.start();
        }
    //Function to check if a file exist in a list
    private static boolean contains(File[] roots, File root) {
        for (File r : roots) {
            if (r.equals(root)) {
                return true;
            }
        }
        return false;
    }
}
