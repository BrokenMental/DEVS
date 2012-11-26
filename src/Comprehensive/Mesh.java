package Comprehensive;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import java.awt.*;
import simView.*;
import genDevs.*;
import genDevs.plots.CellGridPlot;


public class Mesh extends ViewableDigraph{

    int i=1;
    public Mesh(){
        super("Mesh");

        CTG cgt1=new CTG("Node1");
        CTG cgt2=new CTG("Node2");
        CTG cgt3=new CTG("Node3");
        CTG cgt4=new CTG("Node4");
        CTG cgt5=new CTG("Node5");
        CTG cgt6=new CTG("Node6");
        CTG cgt7=new CTG("Node7");
        CTG cgt8=new CTG("Node8");
        CTG cgt9=new CTG("Node9");

       add(cgt1);
       add(cgt2);
       add(cgt3);
       add(cgt4);
       add(cgt5);
       add(cgt6);
       add(cgt7);
       add(cgt8);
       add(cgt9);

       
       addCoupling(cgt1,"Route1",cgt2,"InputPort");
       addCoupling(cgt2,"Route2",cgt1,"InputPort");

       addCoupling(cgt1,"Route3",cgt4,"InputPort");
       addCoupling(cgt4,"Route4",cgt1,"InputPort");

       addCoupling(cgt2,"Route1",cgt3,"InputPort");
       addCoupling(cgt3,"Route2",cgt2,"InputPort");

       addCoupling(cgt3,"Route3",cgt6,"InputPort");
       addCoupling(cgt6,"Route4",cgt3,"InputPort");

       addCoupling(cgt2,"Route3",cgt5,"InputPort");
       addCoupling(cgt5,"Route4",cgt2,"InputPort");

       addCoupling(cgt4,"Route1",cgt5,"InputPort");
       addCoupling(cgt5,"Route2",cgt4,"InputPort");

       addCoupling(cgt5,"Route1",cgt6,"InputPort");
       addCoupling(cgt6,"Route2",cgt5,"InputPort");

       addCoupling(cgt4,"Route3",cgt7,"InputPort");
       addCoupling(cgt7,"Route4",cgt4,"InputPort");

       addCoupling(cgt5,"Route3",cgt8,"InputPort");
       addCoupling(cgt8,"Route4",cgt5,"InputPort");

       addCoupling(cgt6,"Route3",cgt9,"InputPort");
       addCoupling(cgt9,"Route4",cgt6,"InputPort");

       addCoupling(cgt7,"Route1",cgt8,"InputPort");
       addCoupling(cgt8,"Route2",cgt7,"InputPort");


       addCoupling(cgt8,"Route1",cgt9,"InputPort");
       addCoupling(cgt9,"Route2",cgt8,"InputPort");
      initialize();



    }



        public void layoutForSimView()
    {
        ComputeLayout = false;
        // dimension (700,400)
        preferredSize = new Dimension(900,500);

        if (withName("Node1")!=null)
             ((ViewableComponent)withName("Node1")).setPreferredLocation(new Point(50, 50));

        if (withName("Node2")!=null)
             ((ViewableComponent)withName("Node2")).setPreferredLocation(new Point(300,50));

        if (withName("Node3")!=null)
             ((ViewableComponent)withName("Node3")).setPreferredLocation(new Point(550, 50));




        if (withName("Node4")!=null)
             ((ViewableComponent)withName("Node4")).setPreferredLocation(new Point(50, 200));

        if (withName("Node5")!=null)
             ((ViewableComponent)withName("Node5")).setPreferredLocation(new Point(300, 200));

        if (withName("Node6")!=null)
             ((ViewableComponent)withName("Node6")).setPreferredLocation(new Point(550, 200));



        if (withName("Node7")!=null)
             ((ViewableComponent)withName("Node7")).setPreferredLocation(new Point(50, 350));

        if (withName("Node8")!=null)
             ((ViewableComponent)withName("Node8")).setPreferredLocation(new Point(300, 350));

        if (withName("Node9")!=null)
             ((ViewableComponent)withName("Node9")).setPreferredLocation(new Point(550, 350));


    }
}