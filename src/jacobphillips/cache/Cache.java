package jacobphillips.cache;

import GenCol.entity;
import genDevs.modeling.message;
import simView.ViewableAtomic;

public class Cache extends ViewableAtomic { 
    public static class InvalidCacheException extends Exception {
        public InvalidCacheException(String msg) {
            super(msg);
        }
    }
    public static final String DEFAULT_NAME = Cache.class.getSimpleName();    
    public static final int DEFAULT_ASSOCIATIVITY = 1;

    public static final String INPUT_ADDRESS = "Address";
    public static final String OUTPUT_RESULT = "Result";
    
    
    public static final String STRATEGY_LRU = "LRU";
    public static final String DEFAULT_STRATEGY = STRATEGY_LRU;
    
    private static final int EMPTY      = -1;
    private static final int NOT_FOUND  = -1;
    private static final int ERROR      = -1;
    
    private final int[][] mCache;
    private final String mStrategy;
    private final int mSetAssociativity;
    private final int mSets;
    private final String mName;
    
    private int mAttempts;
    private int mMisses;
    private float mHitTime;
    private int mMissPenalty;
    
    public Cache(int totalAddresses) throws InvalidCacheException {
        this(DEFAULT_NAME, totalAddresses);        
    }
    
    public Cache(String name, int totalAddresses) throws InvalidCacheException {
        this(name, totalAddresses, DEFAULT_ASSOCIATIVITY);
    }
    
    public Cache(String name, int totalAddresses, int setAssociativity) throws InvalidCacheException {
        this(name, totalAddresses, setAssociativity, DEFAULT_STRATEGY);
    }
    
    public Cache(String name, int totalAddresses, int setAssociativity, String strategy) throws InvalidCacheException {
        super(name);
        
        if (totalAddresses < 1 || setAssociativity < 1) {
            throw new InvalidCacheException("Addresses and associativity must be positive.");
        }
        if (totalAddresses % setAssociativity > 0) {
            throw new InvalidCacheException("Addresses must be a multiple of associativity.");
        }
        
        mName = name;
        mStrategy = strategy;
        mSetAssociativity = setAssociativity;        
        mSets = totalAddresses / mSetAssociativity;
        
        mCache = new int[mSets][mSetAssociativity];

        addInport(INPUT_ADDRESS);
//        addTestInput(INPUT_ADDRESS, new entity(INPUT_ADDRESS));
        
        addOutport(OUTPUT_RESULT);
    }

    private void processAddress(int address) {
        ++mAttempts;        
        int location = locationOf(address);        
        if (location == NOT_FOUND) {
            ++mMisses;                        
            log("Cache miss for address " + address 
                + ". Miss rate: " + getMissRate()
                + ", avg. access time: " + getAverageAccessTime()
            );
        }
        else {
            log("Cache hit in set " + address % mSets
                + " location " + location + " for address " + address
                + ". Miss rate: " + getMissRate()
                + ", avg. access time: " + getAverageAccessTime()
            );
        }                
        int newLocation = storeAddress(address, location);        
        log("Address " + address + " stored in set " + address % mSets
                    + " location " + newLocation);
    }
    
    private int locationOf(int address) {  
        int set = address % mSets;
        for (int i = 0; i < mSetAssociativity; ++i) {
            if (mCache[set][i] == address) {
                return i;
            }
        }
        return NOT_FOUND;
    }       
    
    private int storeAddress(int address, int previousLocation) {
        int set = address % mSets;
        
        if (mStrategy.equals(STRATEGY_LRU)) {            
                // Optimization
                if (mCache[set][0] == address) { 
                    return 0;
                }
                
                // Where to start shifting the array
                int start = mSetAssociativity - 1;                
                if (previousLocation != NOT_FOUND) {
                    start = previousLocation;
                }
                
                // Shift array to the right
                for (int i = start; i > 0; --i) {
                    mCache[set][i] = mCache[set][i - 1];
                }
                
                // Insert address "on top"
                mCache[set][0] = address;                
                return 0;
        }
        else {            
            log("Unknown strategy");                        
            return ERROR;
        }
        
    }
    
    public float getMissRate() {
        return (float) mMisses / (float) mAttempts;
    }
    
    public String getReplacementStrategy() {
        return mStrategy;
    }
    
    public float getAverageAccessTime() {
        return mHitTime + getMissRate() * mMissPenalty;
    }
    
    public void setHitTime(float time) {
        mHitTime = time;
    }
    
    public void setMissPenalty(int penalty) {
        mMissPenalty = penalty;
    }
    
    private void log(String msg) {
        System.out.println(String.format("%15s", mName)
                + " attempt " + mAttempts + ": " + msg);
//        System.out.println(String.format("%60s", mName
//                + " (" + mSets * mSetAssociativity + " elements "
//                + mSetAssociativity + "-way " 
//                + mStrategy
//                + ", htime " + mHitTime
//                + ", mpenlty " + mMissPenalty)
//                + "): " + msg);
    }
    
    
    @Override
    public void initialize() {
        mAttempts = 0;
        mMisses = 0;
        for (int i = 0; i < mSets; ++i) {
            for (int j = 0; j < mSetAssociativity; ++j) {
                mCache[i][j] = EMPTY;
            }
        }        
        passivateIn("passive");        
    }

    @Override
    public void deltint() {
        System.out.println(getName() + ": deltint()");
        if (phaseIs("InjectDet")) {
            System.out.println(getName() + ": deltint() -> InjectDet");
            passivateIn("passive");
        }
    }

    @Override
    public void deltext(double e, message x) {
        System.out.println(getName() + ": deltext()");
        for (int i = 0; i < x.getLength(); ++i) {
            if(messageOnPort(x, INPUT_ADDRESS, i)) {
                System.out.println(getName() + ": deltext() -> messageOnPort");
                String address = x.getValOnPort(INPUT_ADDRESS, i).getName();
                processAddress(Integer.valueOf(address));
                holdIn("active", 1);                                   
            }
        }
    }
    
    @Override
    public message out() {
        System.out.println(getName() + ": out()");
        message m = new message();
        if (phaseIs("Active")) {
            String result = mAttempts + " attempts, " + mMisses + " misses";
            entity e = new entity(result);
            m.add(makeContent(OUTPUT_RESULT, e));
        }
        return m;
    }    
}
