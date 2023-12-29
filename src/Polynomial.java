import java.util.Arrays;

public class Polynomial {
    private double coefficients[];
    private int degree;

    Polynomial() {
        this.coefficients = null;
        this.degree = -1;
    }

    Polynomial(double coeff[]) {
        this.coefficients = coeff;
        this.degree = coeff.length - 1;
        normalize();
    }

    Polynomial(Polynomial polynomial) {
        this.degree = polynomial.degree;
        this.coefficients = new double[this.degree + 1];
        for (int i = 0; i <= this.degree; i++) {
            this.coefficients[i] = polynomial.coefficients[i];
        }
    }


    void normalize() {
        if (this.degree == -1) return;
        int position = this.degree;
        while(this.coefficients[position] == 0) {
            position--;
        }

        if (position == degree) return;
        double newCoeff[] = new double[position + 1];
        System.arraycopy(this.coefficients, 0, newCoeff, 0, position + 1);
        this.coefficients = newCoeff;
        this.degree = position;
    }

    double evaluateAt(double x) {
        if (this.degree == -1) return 0;
        double argExp = x;
        double value = this.coefficients[0];
        for(int i = 1; i <= this.degree; i++) {
            value += this.coefficients[i] * argExp;
            argExp *= x;
        }
        return value;
    }

    Polynomial negate() {
        if(this.degree == -1) return new Polynomial();
        double[] resultCoefficients = new double[this.degree + 1];
        for(int i = 0; i <= this.degree; i++) {
            resultCoefficients[i] = -this.coefficients[i];
        }
        return new Polynomial(resultCoefficients);
    }

    Polynomial add(Polynomial other) {
        if(this.degree == -1 || other.degree == -1) return new Polynomial();
        int resultDegree = Math.max(this.degree, other.degree);
        double[] resultCoefficients = new double[resultDegree + 1];
        for(int i = 0; i <= this.degree; i++) {
            resultCoefficients[i] += this.coefficients[i];
        }
        for(int i = 0; i <= other.degree; i++) {
            resultCoefficients[i] += other.coefficients[i];
        }
        return new Polynomial(resultCoefficients);
    }

    Polynomial subtract(Polynomial other) {
        if(this.degree == -1 || other.degree == -1) return new Polynomial();
        int resultDegree = Math.max(this.degree, other.degree);
        double[] resultCoefficients = new double[resultDegree + 1];
        for(int i = 0; i <= this.degree; i++) {
            resultCoefficients[i] += this.coefficients[i];
        }
        for(int i = 0; i <= other.degree; i++) {
            resultCoefficients[i] -= other.coefficients[i];
        }
        return new Polynomial(resultCoefficients);
    }

    Polynomial multiply(Polynomial other) {
        if(this.degree == -1 || other.degree == -1) return new Polynomial();
        int resultDegree = this.degree + other.degree;
        double[] resultCoefficients = new double[resultDegree + 1];
        double[] thisCoefficients = new double[resultDegree + 1];
        double[] otherCoefficients = new double[resultDegree + 1];
        if (this.degree + 1 >= 0) System.arraycopy(this.coefficients, 0, thisCoefficients, 0, this.degree + 1);
        if (other.degree + 1 >= 0) System.arraycopy(other.coefficients, 0, otherCoefficients, 0, other.degree + 1);
        for(int i = 0; i <= resultDegree; i++) {
            for(int j = 0; j <= i; j++) {
                resultCoefficients[i] += thisCoefficients[j] * otherCoefficients[i - j];
            }
        }
        return new Polynomial(resultCoefficients);
    }

    Polynomial multiply(double value) {
        if(this.degree == -1) return new Polynomial();
        double[] resultCoefficients = new double[this.degree + 1];
        for(int i = 0; i <= this.degree; i++) {
            resultCoefficients[i] = this.coefficients[i] * value;
        }
        return new Polynomial(resultCoefficients);
    }

    Polynomial derive() {
        if(this.degree == -1) return new Polynomial();
        int resultDegree = this.degree - 1;
        double[] resultCoefficients = new double[resultDegree + 1];
        for(int i = 0; i < this.degree; i++) {
            resultCoefficients[i] = this.coefficients[i + 1] * (i + 1);
        }
        return new Polynomial(resultCoefficients);
    }

    Polynomial integrate() {
        if(this.degree == -1) return new Polynomial();
        int resultDegree = this.degree + 1;
        double[] resultCoefficients = new double[resultDegree + 1];
        for(int i = 1; i <= resultDegree; i++) {
            resultCoefficients[i] = this.coefficients[i - 1] / i;
        }
        return new Polynomial(resultCoefficients);
    }

    double integrate(double from, double to) {
        if(this.degree == -1) return 0;
        Polynomial integral = this.integrate();
        return integral.evaluateAt(to) - integral.evaluateAt(from);
    }

    double scalarMultiply(Polynomial other) {
        return multiply(other).integrate(0., 1.);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Polynomial)) {
            return false;
        }
        Polynomial other = (Polynomial) obj;
        return Arrays.equals(this.coefficients, other.coefficients);
    }

    @Override
    public String toString() {
        String string = new String();
        for(int i = 0; i <= this.degree; i++) {
            if (i == 0) {
                if (this.coefficients[0] == 0) continue;
                string += String.valueOf(this.coefficients[0]);
            }
            else if (i == 1) {
                if (this.coefficients[1] == 0) continue;
                if (!string.isEmpty()) string += " + ";
                if (this.coefficients[i] != 1) string += this.coefficients[i] + "*";
                string += "x";
            }
            else {
                if (this.coefficients[i] == 0) continue;
                if (!string.isEmpty()) string += " + ";
                if (this.coefficients[i] != 1) string += this.coefficients[i] + "*";
                string += "x^" + i;
            }
        }
        return string;
    }

}
