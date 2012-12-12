package jacobphillips;

import simView.ViewableDigraph;

/** 
 * Class that handles creating connections between children (ViewableAtomic)
 * and displaying them on screen via x-y coordinates.
 */ 
public abstract class ComponentGroup extends ViewableDigraph {    
    public ComponentGroup(String name) {
        super(name);        
        Log.v(getName(), "Constructor");   
        onCreateChildComponents();
        onAddChildComponents();
        onAddCoupling();
        initialize();        
    }

    @Override
    public void initialize() {
        Log.v(getName(), "Initializing");
        super.initialize();
    }

    /** Create children to this component group. */
    protected abstract void onCreateChildComponents();
    
    /** Add children to this component group (call the add() function). */
    protected abstract void onAddChildComponents();
    
    /** Add necessary coupling between this group's children (call the addCoupling() function). */
    protected abstract void onAddCoupling();
    
    /** Display this component group and its children. */
    @Override
    public abstract void layoutForSimView();
}

