/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sin11368;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import prog24178.labs.objects.CookieInventoryItem;

/**
 *
 * @author Gurveer
 * @version 1.0
 */
public class CookieInventoryFile extends ArrayList<CookieInventoryItem>{
    
    public CookieInventoryFile(){}
    
    public CookieInventoryFile(File file){}
    
    File file = new File("cookies.txt");
    
    /**
     * Find function to the cookie from the array list using its index
     * @param id as int
     * @return CookieInventoryItem
     */
    public CookieInventoryItem find(int id) throws FileNotFoundException{
        for(int i=0; i<super.size(); i++){
            if(super.get(i).cookie.getId()==id){
                return super.get(i);
            }
        }
        return null;
    }
        
    /**
     * loadFromFile function to get the cookie id and quantity from the external file
     * @param file as a File
     */
    public void loadFromFile(File file){
        if(file.exists()){
            try {
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()){
                    String s = scanner.nextLine();
                    String[] fields = s.split("\\|");
                    super.add(new CookieInventoryItem(Integer.parseInt(fields[0]),Integer.parseInt(fields[1])));
                    scanner.close();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CookieInventoryFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
    }
    
    /**
     * writeFromFile to write content to the external file
     * @param file as a File
     */
    public void writeFromFile(File file){
            try{
                PrintWriter write = new PrintWriter(file);
                for(int i=0; i<super.size(); i++){
                    write.println(super.get(i).toFileString());
                }
                write.close();
                
            }catch(FileNotFoundException ex){
                Logger.getLogger(CookieInventoryFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
}
    
//}
