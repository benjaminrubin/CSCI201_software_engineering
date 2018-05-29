package assignment1;

public class Tester {
    public static String answer(String s) { 
        
        String s1 = "";
    
        // Your code goes here.
        for (int i = 0; i < s.length(); i++)
        {
            //if s[i] is between a and z,
            // then we need to cambia
            if(Character.getNumericValue(s.charAt(i)) >= 97 &&
            Character.getNumericValue(s.charAt(i)) < 123)
            {
                s1 += (char)(219 - (int)s.charAt(i));
            }
            else {
                s1 += s.charAt(i);
            }
        }
        return s1;
    } 
}
