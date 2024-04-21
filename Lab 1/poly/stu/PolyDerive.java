package poly.stu;

/**
 * This class can compute the derivative of a polynomial.
 *
 * @author RIT CS
 * @author Nam Huynh
 */
public class PolyDerive {

    /**
     * Unused constructor, made private to avoid javadoc generation.
     */
    private PolyDerive() {
    }

    /**
     * Computes the derivative for a polynomial.  For example:
     * <pre>
     * poly=[1]: [0]
     * poly=[3, -1]: [-1]
     * poly=[0, 3]: [3]
     * poly=[2, -1, -2, 1]: [-1, -4, 3]
     * poly=[-5, 0, 0, 3, 3, 1]: [0, 0, 9, 12, 5]
     * </pre>
     *
     * @param poly A native array representing the polynomial, in reverse order.
     * @rit.pre poly is not an empty array.  Minimally it will contain
     *      a constant term.
     * @return A polynomial as a native array in reverse order.
     */
    public static int[] computeDerivative(int[] poly) {
        if (poly.length == 1){
            return new int[1];
        }
        int[] derivativeArray = new int[poly.length - 1];
        for (int i = 1; i < poly.length; i++){
            Integer val = poly[i] * i;
            derivativeArray[i-1] = val;
        }
        return derivativeArray;
    }
}
