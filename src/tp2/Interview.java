package tp2;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public final class Interview {

    /** TODO
     * This function returns if the two texts are similar based on if they have a similar entropy of the HashMap
     * @return boolean based on if the entropy is similar
     */
    public static Double compareEntropies(String filename1, String filename2) throws IOException {
        String file1Content = readFile(filename1);
        String file2Content = readFile(filename2);
        HashMap<Character, Integer> map1 = getFrequencyHashTable(file1Content);
        HashMap<Character, Integer> map2 = getFrequencyHashTable(file2Content);
        return calculateEntropy(map2)-calculateEntropy(map1);
    }

    /** TODO
     * This function returns the difference in frequencies of two HashMaps which corresponds
     * to the sum of the differences of frequencies for each letter.
     * @return the difference in frequencies of two HashMaps
     */
    public static Integer compareFrequencies(String filename1, String filename2) throws IOException{
        String file1Content = readFile(filename1);
        String file2Content = readFile(filename2);
        HashMap<Character, Integer> map1 = getFrequencyHashTable(file1Content);
        HashMap<Character, Integer> map2 = getFrequencyHashTable(file2Content);
        char alphabet[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        Integer sum=0;
        for( Character character: alphabet)
        {
            Integer nOccurences1 = map1.containsKey(character)? map1.get(character):0;
            Integer nOccurences2 = map2.containsKey(character)? map2.get(character):0;
            sum += Math.abs(nOccurences1-nOccurences2);
        }
        return sum;
    }

    /** TODO
     * @return This function returns the entropy of the HashMap
     */
    public static Double calculateEntropy(HashMap<Character, Integer> map){
        double total = map.values().stream().reduce(0,((acc,elem)->acc+elem));
        double enthropy =0;

        for (Integer nOccurences :map.values()) {
            double p = nOccurences/total;
            enthropy += p* Math.log(1/p)/Math.log(2);
        }
        return enthropy;
    }

    /**
     * This function reads a text file {filenamme} and returns the appended string of all lines
     * in the text file
     */
    public static String readFile(String filename) throws IOException {
        Scanner file = new Scanner(new File(filename));
        String response= "";
        while (file.hasNextLine()){
            response+= file.nextLine();
        }
        return response;
    }

    /** TODO
     * This function takes a string as a parameter and creates and returns a HashTable
     * of character frequencies
     */
    public static HashMap<Character, Integer> getFrequencyHashTable(String text) {

        HashMap<Character, Integer> map=new HashMap<Character, Integer>();

    for(Character character : text.toCharArray())
    {
        if (isAlphabetic(character))
            if(map.containsKey(character))
                map.put(character,map.get(character)+1);
            else
                map.put(character,1);
    }

        return map;
    }

    /** TODO
     * This function takes a character as a parameter and returns if it is a letter in the alphabet
     */
    public static Boolean isAlphabetic(Character c){
        return Character.isAlphabetic(c);
    }
}
