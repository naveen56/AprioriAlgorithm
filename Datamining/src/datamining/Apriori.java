/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeObject.keys;

/**
 *
 * @author naveen
 */
public class Apriori {

    static private TreeMap<String, Integer> cumhash = new TreeMap();
    static private TreeMap<String, Integer> lhash = new TreeMap();
    static private int threshold = 2;

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader("//home//naveen//Desktop//itemset"))) {
            String line = br.readLine();
            while (line != null) {
                creatingSingle(line);
                line = br.readLine();
            }
            prune();
            int i=2;
            while (lhash.isEmpty() != true) {
                cumhash.clear();
                //creating next length frequent patterns
                createnextLengthCum(lhash);

                ItemSet items = new ItemSet(i, cumhash);
                i++;
                cumhash = items.calfrq();
                lhash.clear();
                prune();
            }

        } catch (IOException ex) {
            System.out.println("file doesnot exist");
        }

    }
    //creating single length frequent sets
    //@param :v-> line of the file
    //@return:updating the single length hash table

    private static void creatingSingle(String v) {
        String[] line = v.split(":");

        if (line.length == 2) {
            String[] items = line[1].split(",");
            for (String item : items) {
                if (cumhash.containsKey(item)) {
                    int count = cumhash.get(item);
                    cumhash.replace(item, count + 1);

                } else {
                    cumhash.put(item, 1);

                }
            }

        }
    }

    private static void createnextLengthCum(TreeMap<String, Integer> lhash) {
        Set<String> keys = lhash.keySet();
        Object[] keychain = keys.toArray();

        for (int i = 0; i < keychain.length; i++) {
            String firststring=keychain[i].toString();
            for (int j = i + 1; j < keychain.length; j++) {
                String secondstring=keychain[j].toString();
                /*if (keychain[i].toString().length() == 1) {

                    cumhash.put(mergesingle(keychain[i].toString(), keychain[j].toString()), 0);
                } else*/ if (firststring.substring(0, firststring.length()-1).equals(secondstring.substring(0,secondstring.length()-1))) {
                    cumhash.put(merge(keychain[i].toString(), keychain[j].toString()), 0);
                }
            }
        }

    }

    private static String merge(String string1, String string2) {
        if(string2==null)
            return null;
        StringBuilder resultstring = new StringBuilder(string1);
        //for(int i=1;i<string2.length();i++){
        
        resultstring.append(","+string2.charAt(string2.length()-1));
        ;
        System.out.println(resultstring);
        return resultstring.toString();

    }

    private static String mergesingle(String String1, String String2) {
         String resultstring=String1.concat(",").concat(String2);
       
        return resultstring;
    }

    private static void prune() {
        System.out.println("*******************************************************************");
        for (String keyvalue : cumhash.keySet()) {
            int count = cumhash.get(keyvalue);
            if (count < 2) {

            } else {
                System.out.println(keyvalue + " : " + cumhash.get(keyvalue));
                lhash.put(keyvalue, cumhash.get(keyvalue));
            }
        }
    }

}
