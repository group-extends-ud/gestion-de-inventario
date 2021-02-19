package lib.sRAD.logic;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Extension {

    public static Boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }catch (NumberFormatException numberFormat){
            return false;
        }
    }

    public static Boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '^' || character == '/';
    }

    public static String toCOP(int integer) {
        String str = "$";
        String number = Integer.toString(integer);

        for (int i=0; i<number.length(); i++) {
            if(number.length() % 3 == 0 && str.length()!=1) {
                str += ","+number.charAt(i);
            }
            else {
                str += number.charAt(i);
            }
            number = number.substring(1,number.length());
        }

        return str;
    }

    public static Boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException numberFormat){
            return false;
        }
    }

    public static ArrayList<String> toArrayList(String str) {
        ArrayList<String> arrayList = new ArrayList();
        StringTokenizer tokens = new StringTokenizer(str);
        while(tokens.hasMoreTokens()) {
            arrayList.add(tokens.nextToken());
        }
        return arrayList;
    }

}
