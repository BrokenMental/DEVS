package jacobphillips;

import java.util.Random;

/**
 * Generates random integers and doubles.
 */
public class RandomNumberGenerator {
    static final int INCLUSIVE_INCLUSIVE = 1;
    static final int INCLUSIVE_EXCLUSIVE = 2;
    static final int EXCLUSIVE_INCLUSIVE = 3;
    static final int EXCLUSIVE_EXCLUSIVE = 4;

    private final Random mRandom;      

    public RandomNumberGenerator() {
        mRandom = new Random();
    }

    public RandomNumberGenerator(long seed) {
        mRandom = new Random(seed);        
    }
    
    /**
     * Returns a number >= 0 and <= max.
     */
    public int getInt(int max) {
        return getInt(0, max, INCLUSIVE_INCLUSIVE);
    }
    
    /**
     * Returns a number in the given range, using the specified mode.
     */
    public int getInt(int min, int max, int mode) {
        switch (mode) {
            case EXCLUSIVE_INCLUSIVE:
                return min + 1 +    mRandom.nextInt(max - min + 1);
            case EXCLUSIVE_EXCLUSIVE:
                return min + 1 +    mRandom.nextInt(max - min);
            case INCLUSIVE_INCLUSIVE:
                return min +        mRandom.nextInt(max - min + 1);                        
            default:
                // INCLUSIVE_EXCLUSIVE
                return min +        mRandom.nextInt(max - min);
        }  
    }
    
    /** 
     * Returns a random number >= 0 and < 1.
     */
    public double getDouble() {
       return mRandom.nextDouble();       
    }
        
    /** 
     * Returns a random number in the range of [min, max).
     * (inclusive of the minimum, exclusive of the maximum)
     */ 
    public double getDouble(double min, double max) {
       return min + mRandom.nextDouble() * (max - min);       
    }
}
