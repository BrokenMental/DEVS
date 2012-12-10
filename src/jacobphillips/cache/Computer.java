package jacobphillips.cache;

import jacobphillips.cache.Cache.InvalidCacheException;
import java.awt.Point;
import simView.ViewableComponent;
import simView.ViewableDigraph;

public class Computer extends ViewableDigraph {
    static final int            MAX_ADDRESS             = 100;
    static final int            ADDRESSES_TO_PROCESS    = 200;    
    
    // Size must be a multiple of associativity
    private static final int    CACHE1_SIZE             = 32;
    private static final int    CACHE1_ASSOCIATIVITY    = 1;
    private static final String CACHE1_NAME             = "Cache1";
            static final float  CACHE1_HIT_TIME         = 1;
            static final int    CACHE1_MISS_PENELTY     = 20;
    
    private static final int    CACHE2_SIZE             = 32;    
    private static final int    CACHE2_ASSOCIATIVITY    = 2;
    private static final String CACHE2_NAME             = "Cache2";
            static final float  CACHE2_HIT_TIME         = 1.35f;
            static final int    CACHE2_MISS_PENELTY     = 20;

    
    public Computer() {
        super("Computer");
        
        Processor processor = new Processor();
        Cache cache1 = null;
        Cache cache2 = null;
        try {
            cache1 = new Cache(CACHE1_NAME, CACHE1_SIZE, CACHE1_ASSOCIATIVITY); 
            cache2 = new Cache(CACHE2_NAME, CACHE2_SIZE, CACHE2_ASSOCIATIVITY);
            
            cache1.setHitTime(CACHE1_HIT_TIME);
            cache1.setMissPenalty(CACHE1_MISS_PENELTY);
            
            cache2.setHitTime(CACHE2_HIT_TIME);
            cache2.setMissPenalty(CACHE2_MISS_PENELTY);
        }
        catch (InvalidCacheException e) {
            System.out.println(e.getMessage());
        }
        
        add(processor);
        add(cache1);
        add(cache2);
        
        addCoupling(processor, Processor.OUTPUT_ADDRESS, cache1, Cache.INPUT_ADDRESS);
        addCoupling(processor, Processor.OUTPUT_ADDRESS, cache2, Cache.INPUT_ADDRESS);
        
        initialize();        
    }

    @Override
    public void layoutForSimView() {
        if (withName(Processor.DEFAULT_NAME) != null) {
             ((ViewableComponent) withName(Processor.DEFAULT_NAME)).setPreferredLocation(new Point(100, 100));
        }
        
        if (withName(CACHE1_NAME) != null) {
             ((ViewableComponent) withName(CACHE1_NAME)).setPreferredLocation(new Point(300, 50));
        }
        
        if (withName(CACHE2_NAME) != null) {
             ((ViewableComponent) withName(CACHE2_NAME)).setPreferredLocation(new Point(300, 150));
        }
    }    
}
