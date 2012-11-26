package Comprehensive;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import java.awt.*;
import simView.*;
import genDevs.*;


public class CTG extends ViewableDigraph{

    public CTG(String name){
        super(name);


         Deterministic det=new Deterministic("Deterministic "+name);
                Router router=new Router("Router "+name);
        Decisionmaker dm=new Decisionmaker();

        add(det);
       
        add(dm);
        add(router);
    

        addOutport("Route1");
        addOutport("Route2");
        addOutport("Route3");
        addOutport("Route4");
        addInport("InputPort");

        addCoupling(dm,"InformationPort",det,"InfoPort");
        addCoupling(dm,"Stop",det,"Stop");

       

        addCoupling(det,"Feedback",dm,"ComingFeedback");
       


       addCoupling(det,"PacketsPort",router,"In");
       

       addCoupling(this,"InputPort",dm,"Input");
       addCoupling(this, "InputPort",router, "In");

       addCoupling(router,"Out1",this,"Route1");
       addCoupling(router,"Out2",this,"Route2");
       addCoupling(router,"Out3",this,"Route3");
       addCoupling(router,"Out4",this,"Route4");

        initialize();

    }


    public void layoutForSimView()
    {
        ComputeLayout = false;
        // dimension (700,400)
        preferredSize = new Dimension(190,100);

        if (withName("Decisionmaker")!=null)
             ((ViewableComponent)withName("Decisionmaker")).setPreferredLocation(new Point(5, 150));

        if (withName("Deterministic "+name)!=null)
             ((ViewableComponent)withName("Deterministic "+name)).setPreferredLocation(new Point(330,50));

       
        if (withName("Router "+name)!=null)
             ((ViewableComponent)withName("Router "+name)).setPreferredLocation(new Point(20, 100));


    }
}