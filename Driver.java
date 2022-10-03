import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Driver {

    public static void main(String [] args) throws IOException {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,0,0,5};
        int[] e1 = {0,3,4,5};
        Polynomial p1 = new Polynomial(c1, e1);
        System.out.println("p1(0)=" + p1.evaluate(2));
        double [] c2 = {0,-2,0,0,-9};
        int[] e2 = {1,2,6,7,8};
        Polynomial p2 = new Polynomial(c2, e2);
        System.out.println("p2(0)=" + p2.evaluate(2));
        Polynomial s = p1.add(p2);
        System.out.println("s(0) = " + s.evaluate(2));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        Polynomial m = p1.multiply(p2);
        System.out.println("m(0) = " + m.evaluate(1));
        File polynomialFile = new File("./polynomial.txt");
        Polynomial testImported = new Polynomial(polynomialFile);
        System.out.println(testImported.evaluate(2));

        testImported.saveToFile("testexport.txt");
    }



}
