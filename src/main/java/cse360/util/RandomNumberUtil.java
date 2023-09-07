package cse360.util;

import java.util.UUID;

//Generate random number
public class RandomNumberUtil {
    static int counter = 0;

    //Generate ID
    public static int generateId(){
        counter++;
        return counter;
    }
    
    //Generate UUID
    public static String  getRandomNumber(){
        return UUID.randomUUID().toString();


    }
}
