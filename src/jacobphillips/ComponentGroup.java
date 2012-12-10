package jacobphillips;

import simView.ViewableDigraph;

/** 
 * Class that handles creating connections between children (ViewableAtomic)
 * and displaying them on screen via x-y coordinates.
 */ 
public abstract class ComponentGroup extends ViewableDigraph {  
    private static final String DEFAULT_NAME = ComponentGroup.class.getSimpleName();
    
    public ComponentGroup() {
        this(DEFAULT_NAME);
    }
    
    public ComponentGroup(String name) {
        super(name);        
        Log.v(getName(), "Constructor");   
        createChildren();
        addChildren();
        makeConnections();
        initialize();
    }

    @Override
    public void initialize() {
        Log.v(getName(), "Initializing");
        super.initialize();
    }

    /** Create children to this component group. */
    protected abstract void createChildren();
    
    /** Add children to this component group. */
    protected abstract void addChildren();
    
    /** Add necessary coupling between this group's children. */
    protected abstract void makeConnections();
    
    /** Display this component group and its children. */
    @Override
    public abstract void layoutForSimView();
}

