package poly.stu;

/**
 * This class can compute the root of a polynomial (whose derivative is
 * non-zero), using Newton's method of successive approximation.
 *
 * @author RIT CS
 * @author Nam Huynh
 */
public class PolyRoot {
    /**
     * The degree of acceptable error for the root
     */
    public static final double EPSILON = 0.000001;

    /**
     * The initial guess for the root
     */
    public static final double INITIAL_GUESS = 0.1;

    /**
     * The maximum number of iterations to perform for root finding
     */
    public static final int MAX_ITERATIONS = 100;

    /**
     * Compute an estimate for a root of the polynomial, using  Newton's
     * method.  For example:
     * <pre>
     * poly=[3, -1]: 3.0
     * poly=[0, 3]: -1.3877787807814457E-17
     * poly=[2, -1, -2, 1]: 2.0000000358875707
     * poly=[-5, 0, 0, 3, 3, 1]: 0.9128983495621411
     * </pre>
     *
     * @param poly A native array representing the polynomial, in reverse order.
     * @rit.pre poly is not an empty array.  Minimally it will contain
     *      a constant term.
     * @rit.pre The derivative of poly is non-zero
     * @rit.pre The evaluation of the derivative of the polynomial at a guessed
     *      root is non-zero.
     * @return An estimated root for the polynomial.
     */
    public static double computeRoot(int[] poly) {
        return newtonsMethod(poly, INITIAL_GUESS, 1);
    }

    /**
     * Compute an estimate for a root of the polynomial, using Newton's
     * method.
     *
     * @param poly A native array representing the polynomial, in reverse order.
     * @param x0 The current guess for the root.
     * @param iter The current iteration being performed
     * @rit.pre poly is not an empty native array.  Minimally it will contain
     *      a constant term.
     * @rit.pre The derivative of poly is non-zero
     * @rit.pre The evaluation of the derivative of the polynomial at a guessed
     *      root is non-zero.
     * @return An estimated root for the polynomial.
     */
    private static double newtonsMethod(int[] poly, double x0, int iter) {
        double fx0 = PolyEval.evaluate(poly, x0);
        int[] derivative = PolyDerive.computeDerivative(poly);
        double fPrime = PolyEval.evaluate(derivative, x0);
        if (iter > MAX_ITERATIONS || Math.abs(x0) <= EPSILON) {
            return x0;
        }
        else {
            if (iter == 1) {
                x0 = INITIAL_GUESS - (fx0 / fPrime);
            }
            else {
                x0 = x0 - (fx0 / fPrime);
            }
            return newtonsMethod(poly, x0, iter + 1);
        }
    }
}
