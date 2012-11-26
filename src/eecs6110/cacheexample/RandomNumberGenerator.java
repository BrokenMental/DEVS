package eecs6110.cacheexample;

import java.util.Random;

/**
 * Only generates integers.
 */
public class RandomNumberGenerator {
    static final int INCLUSIVE_INCLUSIVE = 1;
    static final int INCLUSIVE_EXCLUSIVE = 2;
    static final int EXCLUSIVE_INCLUSIVE = 3;
    static final int EXCLUSIVE_EXCLUSIVE = 4;

    private final Random mRandom;
    private final int mMin, mMax, mMode;        

    public RandomNumberGenerator(int max) {
        mRandom = new Random();
        mMin = 0;
        mMax = max;
        mMode = INCLUSIVE_INCLUSIVE;  
    }

    public RandomNumberGenerator(int min, int max, int mode) {
        mRandom = new Random();
        mMin = min;
        mMax = max;
        mMode = mode;            
    }
    
    public int getInt() {
        switch (mMode) {
            case INCLUSIVE_INCLUSIVE:
                return mMin +       mRandom.nextInt(mMax - mMin + 1);            
            case EXCLUSIVE_INCLUSIVE:
                return mMin + 1 +   mRandom.nextInt(mMax - mMin + 1);
            case EXCLUSIVE_EXCLUSIVE:
                return mMin + 1 +   mRandom.nextInt(mMax - mMin);
            default:
                // INCLUSIVE_EXCLUSIVE
                return mMin +       mRandom.nextInt(mMax - mMin);
        }  
    }
}
