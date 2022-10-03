import java.nio.file.Files;
import java.util.*;
import java.io.*;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[]{0};
        exponents = new int[]{0};
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException {
        List<String> fileContents = Files.readAllLines(file.toPath());
        String poly = (String) fileContents.get(0);
        System.out.println(poly);

        poly = poly.replaceAll("-", "+-");

        String[] terms = poly.split("\\+", 0);

        int length = terms.length;
        if (terms[0].equals("")) length--;
        coefficients = new double[length];
        exponents = new int[length];
        int c = 0;

        for (String term : terms) {
            if (term.equals("")) continue;
            if (!term.contains("x")) {
                coefficients[c] = Double.parseDouble(term);
                exponents[c] = 0;
            } else {
                coefficients[c] = Double.parseDouble(term.substring(0, term.indexOf("x")));
                exponents[c] = Integer.parseInt(term.substring(term.indexOf("x") + 1));
            }
            c++;
        }
    }

    public Polynomial add(Polynomial p2) {
        List<Double> newCoefficients = new ArrayList<>();
        List<Integer> newExponents = new ArrayList<>();
        for (int i = 0; i < exponents.length; i++) {
            newCoefficients.add(coefficients[i]);
            newExponents.add(exponents[i]);
        }

        for (int i = 0; i < p2.exponents.length; i++) {
            if (newExponents.contains(p2.exponents[i])) {
                int index = newExponents.indexOf(p2.exponents[i]);
                newCoefficients.set(index, newCoefficients.get(index) + p2.coefficients[i]);
            } else {
                newExponents.add(p2.exponents[i]);
                newCoefficients.add(p2.coefficients[i]);
            }
        }

        //stupid stuff
        double[] newCoeffs = new double[newCoefficients.size()];
        int[] newExpos = new int[newCoefficients.size()];
        for (int i = 0; i < newExpos.length; i++) {
            newCoeffs[i] = newCoefficients.get(i);
            newExpos[i] = newExponents.get(i);
        }

        return new Polynomial(newCoeffs, newExpos);

    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < exponents.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial p2) {
        Polynomial np = new Polynomial();
        for (int i = 0; i < exponents.length; i++) {
            double[] newCoefficients = new double[p2.exponents.length];
            int[] newExponents = new int[p2.exponents.length];
            for (int j = 0; j < p2.exponents.length; j++) {
                newCoefficients[j] = coefficients[i] * p2.coefficients[j];
                newExponents[j] = exponents[i] + p2.exponents[i];
            }
            np = np.add(new Polynomial(newCoefficients, newExponents));
        }
        return np;
    }

    public void saveToFile(String filename) throws FileNotFoundException {
        String carry = "";
        for (int i = 0; i < exponents.length; i++) {
            if (coefficients[i] < 0) carry = carry.concat(String.valueOf(coefficients[i]));
            else carry = carry.concat("+").concat(String.valueOf(coefficients[i]));
            if (exponents[i] != 0) carry = carry.concat("x").concat(String.valueOf(exponents[i]));
        }
        if (carry.charAt(0) == '+') carry = carry.substring(1);
        System.out.println(carry);

        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(carry);
        }
    }
}
