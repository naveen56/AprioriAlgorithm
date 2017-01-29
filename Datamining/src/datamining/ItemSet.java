/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author naveen
 */
public class ItemSet {

    private int a[];
    private int r;
    private int n;
    private String[] string;
    private TreeMap<String,Integer> lhash=new TreeMap();
    private TreeMap<String,Integer> cumhash=new TreeMap();
    public ItemSet(int r,TreeMap<String,Integer> cumhash) {
        this.cumhash=cumhash;
        this.r = r+1;
        
        a=new int[this.r];
    }
    
    public TreeMap<String,Integer> calfrq(){
       try(BufferedReader br = new BufferedReader(new FileReader("//home//naveen//Desktop//itemset"))){
           String line=br.readLine();
           int count=1;
           while(line!=null){
               
               String [] data=line.split(":");
               if(data.length==2){
                   
                   string =data[1].split(",");
                   
                   n=string.length+1;
                   perOfData(1);
               }
               line=br.readLine();
               count++;
           }
           
           
       } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemSet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ItemSet.class.getName()).log(Level.SEVERE, null, ex);
        }
       return cumhash;
        
    }
    public  void perOfData(int location) {
        if (location ==r) {
            show();
        } else {

          
                
                for (int i = 1 ; i < n; i++) {
                    if(search(a,location-1,i)!=-1)
                    {
                    a[location] = i;
                    perOfData(location + 1);
                    }
                    
                }
          
        }

    }

    private int search(int[] a, int location, int i) {
        for(int index=1;index<=location;index++){
            if(a[index]>=i)
                return -1;
                
        }
        return 1;
        
    }

    private void show() {
        StringBuilder itemlist=new StringBuilder();
        int i;
        
        for( i=1;i<a.length-1;i++){
           
            itemlist.append(string[a[i]-1]);
            itemlist.append(",");
        }
        
     itemlist.append(string[a[i]-1]);
     
     if(cumhash.containsKey(itemlist.toString())){
        // System.out.println("present");
         int count=cumhash.get(itemlist.toString());
         cumhash.replace(itemlist.toString(),++count);
         
     } 
     
        
    }
    
}
