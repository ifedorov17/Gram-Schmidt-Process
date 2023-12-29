import java.util.ArrayList;

public class Orto {
    private ArrayList<Polynomial> orthogonalized;
    private int n1;
    private int n2;
    double maxScalar;

    Orto() {
        this.orthogonalized = new ArrayList<>();
        this.maxScalar = 0;
    }

    public void orthogonolize() {
        this.orthogonalized.add(new Polynomial (new double[] {1}));
        int iteration = 1;
        boolean n1Set = false;
        boolean n2Set = false;
        while (iteration < 150) {
            System.out.println("Iteration : " + iteration);

            double[] coefficients = new double[iteration + 1];
            coefficients[iteration] = 1;
            Polynomial fi = new Polynomial (coefficients);
            Polynomial gi = new Polynomial(fi);

            for(int j = 0; j <= iteration - 1; j++) {
                Polynomial gj = this.orthogonalized.get(j);
                gi = gi.add(this.orthogonalized.get(j).multiply(-fi.scalarMultiply(gj) / gj.scalarMultiply(gj)));
            }
            this.orthogonalized.add(gi);
            System.out.println("F_i: " + fi);
            System.out.println("G_i: " + gi);
            boolean cond1 = condition1();
            calculateMaxScalarMultiplication(iteration);
            System.out.println("Max scalar multiplication : " + this.maxScalar);
            System.out.println("\n");
            boolean cond2 = condition2();

            if (cond1 && !n1Set) {
                this.n1 = iteration;
                n1Set = true;
            }
            if (cond2 && !n2Set) {
                this.n2 = iteration;
                n2Set = true;
            }

            if (cond1 && cond2) break;

            iteration++;
        }
        System.out.println("n1 : " + this.n1);
        System.out.println("n2 : " + this.n2);
    }

    public boolean condition1() {
        if (this.orthogonalized.size() <= 2) return false;
        System.out.println("Norm difference : " + (calculateNorm(this.orthogonalized.get(this.orthogonalized.size() - 1))
                - calculateNorm(this.orthogonalized.get(this.orthogonalized.size() - 2))));
        return calculateNorm(this.orthogonalized.get(this.orthogonalized.size() - 1))
                > calculateNorm(this.orthogonalized.get(this.orthogonalized.size() - 2));
    }

    public boolean condition2() {
        return this.maxScalar > 1;
    }

    public double calculateNorm(Polynomial poly) {
        Polynomial integrand = new Polynomial(poly);
        integrand = integrand.multiply(poly);

        return Math.sqrt(integrand.integrate(0., 1.));
    }

    private void calculateMaxScalarMultiplication(int iteration) {
        if(iteration == 0) this.maxScalar = 0;
        for(int i = 0; i < this.orthogonalized.size() - 1; i++) {
            double scalar = this.orthogonalized.get(i).scalarMultiply(this.orthogonalized.get(iteration));
            if(this.maxScalar < scalar) this.maxScalar = scalar;
        }
    }
}
