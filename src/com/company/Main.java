/*
 *
 * Classname: Main
 *
 * @version 23.06.2020
 * @author Serhii Tronevych
 *
 *
 * Final course task.
 *
 * Topic: GLOSSARY - 10 points
 *
 * 1.1. Download a text about Harry Potter.
 * 1.2. For each distinct word in the text calculate the number of occurrence.
 * 1.3. Use RegEx.
 * 1.4. Sort in the DESC mode by the number of occurrence.
 * 5. Find  the first 20 pairs.
 * 1.6  Find all the proper names
 * 1.7.  Count them and arrange in alphabetic order.
 * 1.8.   First 20 pairs and names write into to a file test.txt
 * 1.9.  Create a fine header for the file
 * 1.10  Use Java  Collections to demonstrate your experience (Map, List )
 *
 */
package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main (String[] args) throws IOException {
        /*Download a text about Harry Potter and get from file*/
         String txtHarry = new String(Files.readAllBytes(Paths
                .get("C:\\Users\\Administrator\\Desktop\\harry.txt")));

       /* Use RegEx and Map initialization*/
         Map<String, Integer> distinct = new HashMap<>();
         String txtClean= txtHarry.replaceAll("[^a-zA-Z0-9' ]", "");
         String[] txtWords = txtClean.split(" ");

         /* For each distinct word in the text calculate the number of occurrence.*/
         for (int i = 0; i < txtWords.length; i++) {
            if(!distinct.containsKey(txtWords[i])){
                distinct.put(txtWords[i],1);
            }else{
                Integer newValue = distinct.get(txtWords[i]);
                distinct.put(txtWords[i], newValue+1);
            }
        }
         distinct.forEach((el,a)->{
            System.out.println(el + " - " + a);
         });

        /*Sort in the DESC mode by the number of occurrence.*/
         Map<String , Integer> sortedList = distinct.entrySet()
                .stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

         /*print sorted list of all words*/
         sortedList.forEach((key,value)->{
            System.out.println(key + " - " + value);
        });

         /*Find  the first 20 pairs.*/
         List<String> keys = new ArrayList<>();
         Map<String, Integer> first20 = new LinkedHashMap<>();
         sortedList.keySet().stream().forEach(key-> keys.add(key));
         for (int i = 0; i < 20; i++) {
             first20.put(keys.get(i), sortedList.get(keys.get(i)));
        }

         /*added Iterator elmnts to be able to move on to the following elements*/
        Iterator<Map.Entry<String, Integer>> elmnts = sortedList.entrySet().iterator();

        /*where pairs will be written*/
        Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\test.txt");

        /* write first 20 pairs to the file test.txt*/
        for (int i = 0; i < 20; i++)
        {
            Map.Entry<String, Integer> pair = elmnts.next();
            /*next element*/
            System.out.format("Word: %s, occurences: %d%n",
                    pair.getKey(), pair.getValue());
            /*pairs to enter the file*/
            Files.write(path, (pair.getKey() + "\n").getBytes(),
                    StandardOpenOption.APPEND);
        }

        //Find all the proper names
        List<String> names = new ArrayList<>();

        Pattern a1 = Pattern.compile("\\b[A-Z][a-z]{3,}\\b");
        Matcher a2 = a1.matcher(txtHarry);
        while (a2.find())
        {
            String word = a2.group();
            names.add(word);
        }
     }
 }