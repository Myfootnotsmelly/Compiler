import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Parser p = new Parser();
        Scanner in = new Scanner(System.in);
        StringBuffer str =  new StringBuffer(in.nextLine()).append("#");
        p.parse(str.toString());
    }

    //Test Case
    //if var1 >= 20 then var1 :=20 else var1 := 10
}
