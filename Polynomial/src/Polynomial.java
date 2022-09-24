public class Polynomial {
    double[] poly;

    public Polynomial(){
        poly = new double[]{0};
    }

    public Polynomial(double[] coefficients){
        poly = coefficients;
    }

    public Polynomial add(Polynomial poly2){
        double[] carry = new double[Math.max(poly.length, poly2.poly.length)];
        for(int i=0; i<carry.length; i++){
            if(i<poly.length && i<poly2.poly.length){
                carry[i] = poly[i] + poly2.poly[i];
            }else if(i<poly.length){
                carry[i] = poly[i];
            }else{
                carry[i] = poly2.poly[i];
            }
        }

        return new Polynomial(carry);
    }

    public double evaluate(double x){
        double result = 0;
        for(int i=0; i<poly.length; i++){
            result += poly[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }
}
