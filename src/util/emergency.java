package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Jan on 28-10-2017.
 */
public class emergency {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("data.txt");
            BufferedReader r = new BufferedReader(reader);
            r.lines().forEach(s->transformAndPrint(s));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private static void transformAndPrint(String s) {
        StringBuilder b = new StringBuilder(s);
        int start = s.indexOf('{');
        int finish = s.indexOf('}');
        for (int i = start; i < finish; i++) {
            if (s.charAt(i) == ','){
                b.setCharAt(i, '.');
            }
        }
        System.out.println(b);
    }
}
