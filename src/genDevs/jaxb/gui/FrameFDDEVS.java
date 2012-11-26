/*
 * FrameFDDEVS.java
 *
 * Created on July 12, 2007, 11:15 AM
 */

package genDevs.jaxb.gui;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import com.acims.fddevs.*;
import com.acims.coupled.*;

import genDevs.jaxb.fddevs.*;
import genDevs.jaxb.coupled.*;

import java.awt.FlowLayout;
import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import GenCol.*;
/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 *
 * @author  Saurabh
 */
public class FrameFDDEVS extends javax.swing.JFrame {
    
    public static /*final*/ String packageString =
            //
            //   "src/testSynthesis/SpreadExample/Models";
            "src/Models";
    
    public static /*final*/  String packagePath = packageString+File.separator;
    public static /*final*/  String packageJavaPath = packagePath+"java"+File.separator;
    public static /*final*/  String packageXmlPath = packagePath+"xml"+File.separator;
    public static final String VERSION = "0.5.6";
    String javaFileStr = "";
    String xmlFileStr = "";
    String modelName = "";
    public static String coupledModelName = "";
    
    public static Hashtable<String,Double> stateAndTA;
    public static Hashtable<Integer,StructureDeltint> deltint;
    public static Hashtable<Integer, StructureDeltext> deltext;
    
    public static Vector<ModelType> coupledModelElementNames;
    public static Vector<CouplingType> couplingRelations;
    
    
    public static PanelTextArea xmlPanel;
    public static PanelTextArea javaPanel;
    public static PanelTextArea coupledXmlPanel;
    public static PanelTextArea coupledJavaPanel;
    
    public static Vector<String> atomicModelNames;
    public static Vector<String> atomicComponentNames;
    
    AtomicType atomic = null;
    
    /** Creates new form FrameFDDEVS */
    public FrameFDDEVS() {
        initComponents();
        stateAndTA = new Hashtable<String,Double>();
        deltint = new Hashtable<Integer,StructureDeltint>();
        deltext = new Hashtable<Integer,StructureDeltext>();
        
        atomicModelNames = new Vector<String>();
        atomicComponentNames = new Vector<String>();
        addFDDevsTabs();
        loadPackage();
        
    }
  
    public static boolean[] getRootModelTypeFromFile(String xmlFileName){
        
        boolean[] modelTypes = new boolean[2];
        boolean isDigraph = false;
        boolean isAtomic = false;
        
        DigraphType digraph = null;
        AtomicType atomic = null;
        
        try{
            JAXBContext jc = JAXBContext.newInstance("com.acims.coupled");
            Unmarshaller unm = jc.createUnmarshaller();
            
            //			JAXBElement element = (JAXBElement)
            //				//unm.unmarshal(new File("xmlModels/fddevsXmlInstance.xml"));
            //				unm.unmarshal(new File(xmlFileName));
            //
            digraph = (DigraphType)unm.unmarshal(new File(xmlFileName));
        } catch(Exception ex){
            System.out.println("Model not digraph");
        }
        
        if(digraph!=null){
            isDigraph = true;
            System.out.println("Model is Digraph: "+digraph.getName());
        }
        
        try{
            JAXBContext jc = JAXBContext.newInstance("com.acims.fddevs");
            Unmarshaller unm = jc.createUnmarshaller();
            
            //			JAXBElement element = (JAXBElement)
            //				//unm.unmarshal(new File("xmlModels/fddevsXmlInstance.xml"));
            //				unm.unmarshal(new File(xmlFileName));
            //
            atomic = (AtomicType)unm.unmarshal(new File(xmlFileName));
        } catch(Exception ex){
            System.out.println("Model not atomic");
        }
        
        if(atomic !=null){
            isAtomic = true ;
            System.out.println("Model is atomic: "+atomic.getModelName());
        }
        
        modelTypes[0] = isDigraph;
        modelTypes[1] = isAtomic;
        
        
        
        return modelTypes;
    }
    
    public void loadPackage(){
        this.jListModels.removeAll();
        
        try{
            File packageDir = new File(packageXmlPath);
            if(packageDir.exists()){
                Object[] files = (Object[])packageDir.list();
                jListModels.setListData(files);
                this.atomicModelNames.clear();
                for(int i=0; i<files.length; i++){
                    String filename = (String)files[i];
                    String model = filename.substring(0,filename.length()-4);
                    System.out.println("Loading model : "+model);
                    atomicModelNames.add(model);
                }
                System.out.println("total atomic models in package: "+atomicModelNames.size());
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        validate();
    }
    public void addFDDevsTabs(){
        xmlPanel = new PanelTextArea();
        jTabbedPane.add("W3C XML FD-DEVS", xmlPanel);
        
        javaPanel = new PanelTextArea();
        jTabbedPane.add("DEVSJAVA FD-DEVS", javaPanel);
        
        validate();
    }
    
    public void removeAllTabs(){
        xmlPanel = null;
        javaPanel = null;
        jTabbedPane.removeAll();
        validate();
    }
    
    public void addCoupledTabs(){
        xmlPanel = new PanelTextArea();
        jTabbedPane.add("XML Coupled-DEVS", xmlPanel);
        
        javaPanel = new PanelTextArea();
        jTabbedPane.add("DEVSJAVA Coupled-DEVS", javaPanel);
        
        validate();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbuttonStateTA = new javax.swing.JButton();
        jButtonDeltint = new javax.swing.JButton();
        jButtonDeltext = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtfModelName = new javax.swing.JTextField();
        jButtonCreateAtomic = new javax.swing.JButton();
        jButtonGenerateAtomic = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListModels = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        jButtonShowModel = new javax.swing.JButton();
        jButtonDeleteModel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButtonMakeIntoCoupled = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListComponentFDDEVS = new javax.swing.JList();
        jButtonCreateCoupled = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jtfCoupledModelName = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jButtonLoadModel = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButtonSimulate = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButtonResetCoupled = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jbuttonStateTA.setText("Finite State - Time Advance Functions");
        jbuttonStateTA.setEnabled(false);
        jbuttonStateTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonStateTAActionPerformed(evt);
            }
        });

        jButtonDeltint.setText("Default Internal State Machine Specifications");
        jButtonDeltint.setEnabled(false);
        jButtonDeltint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeltintActionPerformed(evt);
            }
        });

        jButtonDeltext.setText("External Input State Machine Specifications");
        jButtonDeltext.setEnabled(false);
        jButtonDeltext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeltextActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("Develop FD-DEVS Atomic Model");

        jLabel3.setText("Model Name:");

        jButtonCreateAtomic.setText("Create");
        jButtonCreateAtomic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateAtomicActionPerformed(evt);
            }
        });

        jButtonGenerateAtomic.setText("Generate FD-DEVS");
        jButtonGenerateAtomic.setEnabled(false);
        jButtonGenerateAtomic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerateAtomicActionPerformed(evt);
            }
        });

        jListModels.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListModels.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jListModels);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel4.setText("Package containing all FD-DEVS models");

        jButtonShowModel.setText("Show Model");
        jButtonShowModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowModelActionPerformed(evt);
            }
        });

        jButtonDeleteModel.setText("Delete Model");
        jButtonDeleteModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteModelActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel5.setText("Develop Coupled Model from FD-DEVS Atomic Models");

        jButtonMakeIntoCoupled.setText("Make as Component in Coupled");
        jButtonMakeIntoCoupled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMakeIntoCoupledActionPerformed(evt);
            }
        });

        jListComponentFDDEVS.setModel(new javax.swing.AbstractListModel() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListComponentFDDEVS);
        jListComponentFDDEVS.setPreferredSize(new java.awt.Dimension(185, 146));

        jButtonCreateCoupled.setText("Generate Coupled Model");
        jButtonCreateCoupled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateCoupledActionPerformed(evt);
            }
        });

        jLabel6.setText("Coupled Model Name:");

        jButtonLoadModel.setText("Load Model");
        jButtonLoadModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadModelActionPerformed(evt);
            }
        });

        jLabel7.setText("Component FD-DEVS models");

        jButtonSimulate.setText("Simulate Coupled Model");

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane3.setViewportView(jTextArea1);
        jTextArea1.setPreferredSize(new java.awt.Dimension(183, 64));

        jButtonResetCoupled.setText("Reset");
        jButtonResetCoupled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetCoupledActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
        	.addContainerGap()
        	.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	    .addComponent(jtfModelName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	    .addComponent(jLabel3, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	    .addComponent(jButtonCreateAtomic, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
        	.addGap(18)
        	.addComponent(jbuttonStateTA, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jButtonDeltint, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jButtonDeltext, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jButtonGenerateAtomic, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addGroup(jPanel1Layout.createParallelGroup()
        	    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
        	        .addComponent(jButtonShowModel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	        .addComponent(jButtonLoadModel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	        .addComponent(jButtonDeleteModel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	        .addGap(19)
        	        .addComponent(jButtonMakeIntoCoupled, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
        	    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
        	.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	    .addComponent(jtfCoupledModelName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	    .addComponent(jLabel6, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	    .addComponent(jButtonResetCoupled, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addGroup(jPanel1Layout.createParallelGroup()
        	    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
        	        .addComponent(jButtonCreateCoupled, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	        .addComponent(jScrollPane3, 0, 86, Short.MAX_VALUE)
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, GroupLayout.PREFERRED_SIZE)
        	        .addComponent(jButtonSimulate, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
        	    .addComponent(jScrollPane2, GroupLayout.Alignment.LEADING, 0, 161, Short.MAX_VALUE))
        	.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	.addComponent(getJLabel8(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE));
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
        	.addContainerGap()
        	.addGroup(jPanel1Layout.createParallelGroup()
        	    .addGroup(jPanel1Layout.createSequentialGroup()
        	        .addGroup(jPanel1Layout.createParallelGroup()
        	            .addComponent(jScrollPane2, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
        	            .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	        .addGroup(jPanel1Layout.createParallelGroup()
        	            .addComponent(jButtonCreateCoupled, GroupLayout.Alignment.LEADING, 0, 185, Short.MAX_VALUE)
        	            .addComponent(jScrollPane3, GroupLayout.Alignment.LEADING, 0, 185, Short.MAX_VALUE)
        	            .addComponent(jButtonSimulate, GroupLayout.Alignment.LEADING, 0, 185, Short.MAX_VALUE)
        	            .addComponent(jButtonShowModel, GroupLayout.Alignment.LEADING, 0, 185, Short.MAX_VALUE)
        	            .addComponent(jButtonLoadModel, GroupLayout.Alignment.LEADING, 0, 185, Short.MAX_VALUE)
        	            .addComponent(jButtonDeleteModel, GroupLayout.Alignment.LEADING, 0, 185, Short.MAX_VALUE)
        	            .addComponent(jButtonMakeIntoCoupled, GroupLayout.Alignment.LEADING, 0, 185, Short.MAX_VALUE)))
        	    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
        	        .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	        .addComponent(jtfCoupledModelName, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	        .addComponent(jButtonResetCoupled, 0, 100, Short.MAX_VALUE))
        	    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
        	        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	        .addComponent(jtfModelName, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
        	        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	        .addComponent(jButtonCreateAtomic, 0, 110, Short.MAX_VALUE))
        	    .addComponent(jLabel2, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
        	    .addComponent(jbuttonStateTA, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
        	    .addComponent(jButtonDeltint, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
        	    .addComponent(jButtonDeltext, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
        	    .addComponent(jSeparator1, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
        	    .addComponent(jLabel4, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
        	    .addComponent(jSeparator2, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
        	    .addComponent(jLabel5, GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
        	    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
        	        .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	        .addComponent(jButtonGenerateAtomic, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
        	        .addGap(6))
        	    .addComponent(getJLabel8(), GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE))
        	.addContainerGap());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Template based DEVS Model Generation ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
        	.addContainerGap(10, 10)
        	.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        	.addContainerGap(15, 15));
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
        	.addContainerGap(81, 81)
        	.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 621, GroupLayout.PREFERRED_SIZE)
        	.addContainerGap(85, 85));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        this.setTitle("Finite Deterministic (FD) DEVS Workbench ("+VERSION+")");
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
private void jButtonCreateCoupledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateCoupledActionPerformed
    coupledModelName = this.jtfCoupledModelName.getText();
    if(coupledModelElementNames!=null)
        coupledModelElementNames = null;
    coupledModelElementNames = new Vector<ModelType>();
    //  createCoupledModel(coupledModelName);
    
    if(couplingRelations!=null)
        couplingRelations = null;
    couplingRelations = new Vector<CouplingType>();
    //moved get name
    if(coupledModelName.length()==0){
        JOptionPane.showMessageDialog(this, "Coupled Model Name can not be null.\nEnter Coupled model name.");
        return;
    }
    Hashtable<String, Vector> modelAndInports = new Hashtable<String, Vector>();
    Hashtable<String, Vector> modelAndOutports = new Hashtable<String, Vector>();
    Vector<StructureCoupling> couplings = new Vector<StructureCoupling>();
    
    Hashtable<String,Vector> inportAndModels = new Hashtable<String, Vector>();
    Hashtable<String,Vector> outportAndModels = new Hashtable<String, Vector>();
    
    com.acims.coupled.ObjectFactory coupledFactory = new com.acims.coupled.ObjectFactory();
    
    
    Iterator it = atomicComponentNames.iterator();
    
    while(it.hasNext()){
        String modelFile = (String)it.next();
        System.out.println("Evaluating model: "+modelFile);
        
        AtomicType atomicFDD = getAtomicFDDContext(modelFile);
        if(atomicFDD==null)
            continue;
        Vector inports = getInportsFromModelXmlFile(atomicFDD);
        Vector outports = getOutportsFromModelXmlFile(atomicFDD);
        
        if(inports==null)inports = new Vector<String>();
        if(outports==null)outports = new Vector<String>();
        
        if(!modelAndInports.containsKey(modelFile) &&
                !modelAndOutports.containsKey(modelFile)){
            modelAndInports.put(modelFile, inports);
            modelAndOutports.put(modelFile,outports);
        }
    }
    
    Enumeration e = modelAndOutports.keys();
    while(e.hasMoreElements()){
        String modelFile = (String)e.nextElement();
        Vector outports = (Vector)modelAndOutports.get(modelFile);
        Iterator ito = outports.iterator();
        while(ito.hasNext()){
            String outport = (String)ito.next();
            addModelToHashtable(outportAndModels, outport,modelFile);
        }
    }
    
    e = modelAndInports.keys();
    ////////bpz
    if(coupledModelElementNames!=null)
        coupledModelElementNames = null;
    coupledModelElementNames = new Vector<ModelType>();
    
    while(e.hasMoreElements()){
        String modelFile = (String)e.nextElement();
        Vector inports = (Vector)modelAndInports.get(modelFile);
        Iterator iti = inports.iterator();
        while(iti.hasNext()){
            String inport = (String)iti.next();
            addModelToHashtable(inportAndModels, inport,modelFile);
        }
    }
    
    System.out.println("\n-------outport and Models------->");
    printHashtable(outportAndModels);
    System.out.println("\n-------Inport and Models------->");
    printHashtable(inportAndModels);
    
    Enumeration srcEnum = outportAndModels.keys();
    while(srcEnum.hasMoreElements()){
        String outport = (String)srcEnum.nextElement();
        String msg = outport.substring(3,outport.length());
        
        String inport = "in"+msg;
        System.out.println("finding pair: "+outport+"\t<"+msg+">"+"\t"+inport);
        
        if(!inportAndModels.containsKey(inport))
            continue;
        
        Vector modelDestinations = (Vector)inportAndModels.get(inport);
        Vector modelSources = (Vector)outportAndModels.get(outport);
        
        
        //  if(coupledModelElementNames!=null)
        //      coupledModelElementNames = null;
        //  coupledModelElementNames = new Vector<ModelType>();
        
        Iterator destIt = modelDestinations.iterator();
        while(destIt.hasNext()){
            String dest = (String)destIt.next();
            Iterator srcIt = modelSources.iterator();
            while(srcIt.hasNext()){
                String src = (String)srcIt.next();
                String srcString = src.substring(0,src.length()-4);
                String destString = dest.substring(0,dest.length()-4);
                String coupling = srcString+":"+outport+
                        "\t"+destString+":"+inport;
                
                populateJAXBCoupledCollections(coupledFactory, srcString, outport, destString, inport);
                System.out.println("\nCouple: "+coupling);
            }
            
        }
        
    }
    
    System.out.println("Coupled Model Name: "+coupledModelName);
    writeJAXBCoupledModel(coupledFactory);
    
    
    
    
}//GEN-LAST:event_jButtonCreateCoupledActionPerformed
/*
public void createCoupledModel(String coupledModelName){
    if(couplingRelations!=null)
        couplingRelations = null;
    couplingRelations = new Vector<CouplingType>();
 
    if(coupledModelName.length()==0){
        JOptionPane.showMessageDialog(this, "Coupled Model Name can not be null.\nEnter Coupled model name.");
        return;
    }
    Hashtable<String, Vector> modelAndInports = new Hashtable<String, Vector>();
    Hashtable<String, Vector> modelAndOutports = new Hashtable<String, Vector>();
    Vector<StructureCoupling> couplings = new Vector<StructureCoupling>();
 
    Hashtable<String,Vector> inportAndModels = new Hashtable<String, Vector>();
    Hashtable<String,Vector> outportAndModels = new Hashtable<String, Vector>();
 
    com.acims.coupled.ObjectFactory coupledFactory = new com.acims.coupled.ObjectFactory();
 
    Iterator it = atomicComponentNames.iterator();
 
    while(it.hasNext()){
        String modelFile = (String)it.next();
        System.out.println("Evaluating model: "+modelFile);
 
        AtomicType atomicFDD = getAtomicFDDContext(modelFile);
        if(atomicFDD==null)
            continue;
        Vector inports = getInportsFromModelXmlFile(atomicFDD);
        Vector outports = getOutportsFromModelXmlFile(atomicFDD);
 
        if(inports==null)inports = new Vector<String>();
        if(outports==null)outports = new Vector<String>();
 
        if(!modelAndInports.containsKey(modelFile) &&
                !modelAndOutports.containsKey(modelFile)){
            modelAndInports.put(modelFile, inports);
            modelAndOutports.put(modelFile,outports);
        }
    }
 
    Enumeration e = modelAndOutports.keys();
    while(e.hasMoreElements()){
        String modelFile = (String)e.nextElement();
        Vector outports = (Vector)modelAndOutports.get(modelFile);
        Iterator ito = outports.iterator();
        while(ito.hasNext()){
            String outport = (String)ito.next();
            addModelToHashtable(outportAndModels, outport,modelFile);
        }
    }
 
    e = modelAndInports.keys();
    ////////bpz
 
  //  if(coupledModelElementNames!=null)
   //     coupledModelElementNames = null;
  //  coupledModelElementNames = new Vector<ModelType>();
 
 
    while(e.hasMoreElements()){
        String modelFile = (String)e.nextElement();
        Vector inports = (Vector)modelAndInports.get(modelFile);
        Iterator iti = inports.iterator();
        while(iti.hasNext()){
            String inport = (String)iti.next();
            addModelToHashtable(inportAndModels, inport,modelFile);
        }
    }
 
    System.out.println("\n-------outport and Models------->");
    printHashtable(outportAndModels);
    System.out.println("\n-------Inport and Models------->");
    printHashtable(inportAndModels);
 
    Enumeration srcEnum = outportAndModels.keys();
    while(srcEnum.hasMoreElements()){
        String outport = (String)srcEnum.nextElement();
        String msg = outport.substring(3,outport.length());
 
        String inport = "in"+msg;
        System.out.println("finding pair: "+outport+"\t<"+msg+">"+"\t"+inport);
 
        if(!inportAndModels.containsKey(inport))
            continue;
 
        Vector modelDestinations = (Vector)inportAndModels.get(inport);
        Vector modelSources = (Vector)outportAndModels.get(outport);
 
 
      //  if(coupledModelElementNames!=null)
       //     coupledModelElementNames = null;
       // coupledModelElementNames = new Vector<ModelType>();
 
        Iterator destIt = modelDestinations.iterator();
        while(destIt.hasNext()){
            String dest = (String)destIt.next();
            Iterator srcIt = modelSources.iterator();
            while(srcIt.hasNext()){
                String src = (String)srcIt.next();
                String srcString = src.substring(0,src.length()-4);
                String destString = dest.substring(0,dest.length()-4);
                String coupling = srcString+":"+outport+
                        "\t"+destString+":"+inport;
 
                populateJAXBCoupledCollections(coupledFactory, srcString, outport, destString, inport);
                System.out.println("\nCouple: "+coupling);
            }
 
        }
 
    }
 
    System.out.println("Coupled Model Name: "+coupledModelName);
    writeJAXBCoupledModel(coupledFactory);
   }
 */



private void writeJAXBCoupledModel(com.acims.coupled.ObjectFactory coupledFactory){
    
    System.out.println("\nWriting JAXB collections:..");
    
    ModelsType models = coupledFactory.createModelsType();
    if (models == null)return;
    ArrayList modelsList = (ArrayList)models.getModel();
    if (coupledModelElementNames == null) return;
    for(int i=0; i<coupledModelElementNames.size(); i++){
        ModelType model = coupledModelElementNames.get(i);
        modelsList.add(model);
        System.out.println("Added model: "+model.getDevs());
    }
    
    CouplingsType couplings = coupledFactory.createCouplingsType();
    ArrayList couplingList = (ArrayList)couplings.getCoupling();
    for(int i=0; i<couplingRelations.size(); i++){
        CouplingType coupling = couplingRelations.get(i);
        couplingList.add(coupling);
        System.out.println("Added coupling: "+coupling.toString());
    }
    
    DigraphType digraph = coupledFactory.createDigraphType();
    digraph.setCouplings(couplings);
    digraph.setModels(models);
    
    digraph.setName(coupledModelName);
    digraph.setHost("localhost");
    
    try{
        JAXBContext jc = JAXBContext.newInstance("com.acims.coupled");
        Marshaller nm = jc.createMarshaller();
        nm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        String filename = packageXmlPath+coupledModelName+".xml";
        FileWriter writer = new FileWriter(new File(filename));
        nm.marshal(digraph, writer);
        writer.close();
        
        java.io.StringWriter sw = new StringWriter();
        nm.marshal(digraph, sw);
        
        CoupledJAXB coupledJAXB = new CoupledJAXB();
        coupledJAXB.initializeModel(filename);
        
        //AtomicJAXB atJaxb = new AtomicJAXB();
        //atJaxb.initializeModel(filename);
        //xmlFileStr = atJaxb.returnXmlModelString();
        removeAllTabs();
        addCoupledTabs();
        
        xmlPanel.setText(sw.toString());
        
        System.out.println(sw.toString());
        
        javaFileStr = coupledJAXB.returnJavaModelString();
        javaPanel.setText(javaFileStr);
        
        String javaPath = writeJavaFile(coupledModelName);
        
        JOptionPane.showMessageDialog(this, "SAVED: \nFD-DEVS XML saved at: "+filename+
                "\nFD-DEVS Java saved at: "+javaPath);
        loadPackage();
        
        
    } catch(Exception ex){
        ex.printStackTrace();
    }
}



private void populateJAXBCoupledCollections(com.acims.coupled.ObjectFactory coupledFactory,
        String src, String outport, String dest, String inport){
    
    System.out.println("Populating JAXB collections...");
    
    ModelType srcModelType = coupledFactory.createModelType();
    srcModelType.setDevs(src);
    
    if(!isModelInCoupledModelElementNames(src)){
        coupledModelElementNames.add(srcModelType);
    }
    
    ModelType destModelType = coupledFactory.createModelType();
    destModelType.setDevs(dest);
    if(!isModelInCoupledModelElementNames(dest)){
        coupledModelElementNames.add(destModelType);
    }
    
    System.out.println("CoupledModelElementNames size: "+coupledModelElementNames.size());
    
    //  CouplingType coupling = coupledFactory.createCouplingType();
    CouplingType coupling = new CouplingType();
    coupling.setSrcModel(src);
    coupling.setDestModel(dest);
    coupling.setOutport(outport);
    coupling.setInport(inport);
    
    if(!couplingRelations.contains(coupling))
        couplingRelations.add(coupling);
    
    
    System.out.println("CouplingRelations size: "+couplingRelations.size());
    
}

private boolean isModelInCoupledModelElementNames(String name){
    Iterator<ModelType> it = coupledModelElementNames.iterator();
    while(it.hasNext()){
        ModelType modelType = it.next();
        String devs = modelType.getDevs();
        if(devs.equalsIgnoreCase(name))
            return true;
    }
    
    return false;
}

private void printHashtable(Hashtable<String, Vector> ht){
    String str = "";
    Enumeration e = ht.keys();
    while(e.hasMoreElements()){
        String key = (String)e.nextElement();
        str += "\nkey="+key+"\t";
        Vector values = (Vector)ht.get(key);
        str += "values=[";
        Iterator vi = values.iterator();
        while(vi.hasNext()){
            String value = (String)vi.next();
            str += value+";";
        }
        str += "]";
    }
    System.out.println(str);
}
private void addModelToHashtable(Hashtable<String,Vector> portAndModels,String port, String modelFile){
    Vector elems = (Vector)portAndModels.get(port);
    if(elems==null)
        elems = new Vector();
    
    elems.add(modelFile);
    portAndModels.put(port, elems);
}

private AtomicType getAtomicFDDContext(String xmlFile){
    AtomicType atomic = null;
    try{
        JAXBContext jc = JAXBContext.newInstance("com.acims.fddevs");
        Unmarshaller unm = jc.createUnmarshaller();
        atomic = (AtomicType)unm.unmarshal(new File(packageXmlPath+xmlFile));
        return atomic;
    } catch(Exception ex){
        ex.printStackTrace();
    }
    
    return null;
}
private Vector getOutportsFromModelXmlFile(AtomicType atomicModel){
    Vector outports = null;
    
    ArrayList list = (ArrayList)atomicModel.getInportsAndOutportsAndStates();
    for(int i=0; i<list.size(); i++) {
        Object obj = (Object)list.get(i);
        if(obj instanceof OutportType) {
            outports = processOutportType((OutportType)obj);
        } else{
            //System.out.println("Outports: Ignoring: process type: "+obj.toString());
        }
    }
    return outports;
}

private Vector processOutportType(OutportType type) {
    Vector<String> ports = new Vector<String>();
    ArrayList outports = (ArrayList)type.getOutport();
    if(outports.size()==0)
        return null;
    for(int i=0; i<outports.size(); i++) {
        System.out.println("\tadding ouport: "+(String)outports.get(i));
        ports.add((String)outports.get(i));
    }
    return ports;
}

private Vector processInportType(InportType type){
    Vector<String> ports = new Vector<String>();
    ArrayList inports = (ArrayList)type.getInport();
    if(inports.size()==0)
        return null;
    for(int i=0; i<inports.size(); i++) {
        System.out.println("\tadding inport: "+(String)inports.get(i));
        ports.add((String)inports.get(i));
    }
    return ports;
}
private Vector getInportsFromModelXmlFile(AtomicType atomicModel){
    Vector inports = null;
    ArrayList list = (ArrayList)atomicModel.getInportsAndOutportsAndStates();
    for(int i=0; i<list.size(); i++) {
        Object obj = (Object)list.get(i);
        if(obj instanceof InportType) {
            inports = processInportType((InportType)obj);
        } else{
            //System.out.println("Inports: Ignoring: process type: "+obj.toString());
        }
    }
    
    
    return inports;
}


private void jButtonMakeIntoCoupledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMakeIntoCoupledActionPerformed
    if(jListModels.getSelectedValue()==null)return;
    
    String file = (String)jListModels.getSelectedValue();
    
    atomicComponentNames.add(file);
    loadComponentModels();
}//GEN-LAST:event_jButtonMakeIntoCoupledActionPerformed
private void loadComponentModels(){
    Object[] listData = new Object[atomicComponentNames.size()];
    atomicComponentNames.copyInto(listData);
    
    this.jListComponentFDDEVS.setListData(listData);
}

private void jButtonResetCoupledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetCoupledActionPerformed
    atomicComponentNames.clear();
    loadComponentModels();
}//GEN-LAST:event_jButtonResetCoupledActionPerformed

private void jButtonLoadModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadModelActionPerformed
    //bpz Sept 2007
    
    //   if(!jListModels.isSelectionEmpty())
    //   JOptionPane.showMessageDialog(this, "COMING SOON.....!");
    if(jListModels.getSelectedValue()==null)return;
    String file = (String)jListModels.getSelectedValue();
    String modelName = file.substring(0,file.length()-4);
    
    String filename = packageXmlPath+file;
    
    boolean modelTypes[] = getRootModelTypeFromFile(filename);
    boolean isDigraph = modelTypes[0];
    boolean isAtomic = modelTypes[1];
    
    this.removeAllTabs();
    if(isAtomic){
        this.addFDDevsTabs();
    }
    if(isDigraph){
        this.addCoupledTabs();
  /*
        //bpz 2007 delete digraph model and reconstruct
   
        File xmlFile = new File(packageXmlPath+file);
        if(xmlFile.exists()){
            xmlFile.delete();
            atomicComponentNames.add("proc.xml");
            atomicComponentNames.add("genr.xml");
   
            coupledModelElementNames = new Vector<ModelType>();
            String src = "proc";
            ModelType srcModelType = new ModelType();
            srcModelType.setDevs(src);
   
            if(!isModelInCoupledModelElementNames(src)){
                coupledModelElementNames.add(srcModelType);
            }
             src = "genr";
             srcModelType = new ModelType();
            srcModelType.setDevs(src);
   
            if(!isModelInCoupledModelElementNames(src)){
                coupledModelElementNames.add(srcModelType);
            }
            loadComponentModels();
            createCoupledModel(modelName);
   */
    } else
        try{
            String xml = getFileContents(filename);
///////////////////////
            AtomicJAXB atJaxb = new AtomicJAXB();
            atJaxb.initializeModel(filename);
            javaFileStr = atJaxb.returnJavaModelString();
            javaPanel.setText(javaFileStr);
            AtomicFDD atomicFDD = atJaxb.atomicFDD;
            stateAndTA = new Hashtable<String,Double>();
            TableModel_StateTA tableTA = new TableModel_StateTA();
            Hashtable<String,TaType>   tatab  =    atomicFDD.getTimeAdvance();
            Enumeration e = tatab.keys();
            while(e.hasMoreElements()){
                String key = (String)e.nextElement();
                TaType ta = (TaType)tatab.get(key);
                stateAndTA.put(ta.getState(),ta.getTimeout());
                Object[] newRow = {ta.getState(),ta.getTimeout()};
                tableTA.addRow(newRow);
            }
            
            deltint = new Hashtable<Integer,StructureDeltint>();
            deltext = new Hashtable<Integer,StructureDeltext>();
            
            TableModel_Deltint deltintTab = new TableModel_Deltint();
            Hashtable<String, Transition> deltints =  atomicFDD.getDeltint();
            Hashtable<String, LambdaType> outs =  atomicFDD.getLamda();
            
            Enumeration en = deltints.keys();
            while(en.hasMoreElements()){
                String key = (String)en.nextElement();
                Transition tr = (Transition)deltints.get(key);
                String phase = tr.getStartState().trim();
                String outpt = getOutputMsg(outs,phase);
                String StartState = tr.getStartState();
                String NextState = tr.getNextState();
                Double ta = stateAndTA.get(NextState);
                if (ta == null || NextState.equals("passive")) ta = Double.POSITIVE_INFINITY;
                //(int id, String start, String  next, double ta, String outMsg)
                Object[] newRow = {key,StartState,NextState, (double)ta,outpt};
                deltintTab.addRow(newRow);
                StructureDeltint sdi =
                        //  new StructureDeltint(Integer.parseInt(key),(String)tr.getStartState(),(String)tr.getNextState(),
                        // (double)stateAndTA.get(tr.getNextState()),outpt);
                        new StructureDeltint(Integer.parseInt(key),StartState,NextState,ta,outpt);
                deltint.put(Integer.parseInt(key),sdi);
            }
            
            TableModel_Deltext deltextTab = new TableModel_Deltext();
            Hashtable<String, ExtTransition> deltexts =  atomicFDD.getDeltext();
            if (deltexts == null)deltexts =new Hashtable<String, ExtTransition>();
            en = deltexts.keys();
            while(en.hasMoreElements()){
                String key = (String)en.nextElement();
                ExtTransition tr = (ExtTransition)deltexts.get(key);
                String inMsg = tr.getIncomingMsg();
                boolean indicator = tr.isScheduleIndicator();
                Transition trs = tr.getTransition();
                String phase = trs.getStartState().trim();
                String nextphase = trs.getNextState().trim();
                String outpt = getOutputMsg(outs,nextphase);
                //"S.No", "Incoming Msg", "Start State", "Next State",
                //"Rechedule", "Timeout", "Outgoing Msg"
                Object[] newRow = {
                    Integer.parseInt(key),inMsg,phase,nextphase,indicator,
                    (double)stateAndTA.get(nextphase),outpt};
                deltextTab.addRow(newRow);
                StructureDeltext sde =
                        new StructureDeltext(
                        Integer.parseInt(key),inMsg,phase,nextphase,indicator,
                        (double)stateAndTA.get(nextphase),outpt
                        );
                deltext.put(Integer.parseInt(key),sde);
            }
            
            
            com.acims.fddevs.ObjectFactory factory = new
                    com.acims.fddevs.ObjectFactory();
            
            
            //*Create States tag
            StatesType states = factory.createStatesType();
            List<String> list = (List<String>) states.getState();
            
            TimeAdvanceType taTypes = factory.createTimeAdvanceType();
            List<TaType> taList = taTypes.getTa();
            
            e = stateAndTA.keys();
            while(e.hasMoreElements()){
                String state = (String)e.nextElement();
                list.add(state);
                
                TaType taType = factory.createTaType();
                taType.setState(state);
                taType.setTimeout(stateAndTA.get(state));
                taList.add(taType);
            }
            
            System.out.println("states: "+states.getState().size());
            System.out.println("ta: "+taTypes.getTa().size());
            
            //Get lamdaset
            LamdaAllType lambdaset = factory.createLamdaAllType();
            List<LambdaType> lamdaList = lambdaset.getLamda();
            
            //get inports
            InportType inports = factory.createInportType();
            List<String> inportList = (List<String>)inports.getInport();
            
            //get outports
            OutportType outports = factory.createOutportType();
            List<String> outportList = (List<String>)outports.getOutport();
            
            //get deltint
            DeltintType deltintFactory = factory.createDeltintType();
            List<IntTransitionType> deltintList = deltintFactory.getInternalTransition();
            
            //get deltext
            DeltextType deltextFactory = factory.createDeltextType();
            List<ExtTransitionType> deltextList = deltextFactory.getExternalTransition();
            
            
            Enumeration eDintKey = deltint.keys();
            while(eDintKey.hasMoreElements()){
                Integer key = (Integer)eDintKey.nextElement();
                StructureDeltint dint = (StructureDeltint)deltint.get(key);
                
                //add Internal Transition
                IntTransitionType intTransition = factory.createIntTransitionType();
                intTransition.setIntTransitionID(key.intValue());
                List<TransitionType> transitionList = intTransition.getTransition();
                
                TransitionType transition = factory.createTransitionType();
                transition.setNextState(dint.nextState);
                transition.setStartState(dint.startState);
                transitionList.add(transition);
                
                deltintList.add(intTransition);
                
                //work for outgoingMsg related tags
                if(dint.outgoingMsg==null)continue;
                if(dint.outgoingMsg.length()==0)continue;
                
                //add outport
                outportList.add("out"+dint.outgoingMsg);
                
                //augment LamdaList
                LambdaType lamda = factory.createLambdaType();
                lamda.setState(dint.startState);
                lamda.setOutport("out"+dint.outgoingMsg);
                if(!isLamdaAdded(lamda, lamdaList))
                    lamdaList.add(lamda);
            }
            
            Enumeration eDextKey = deltext.keys();
            while(eDextKey.hasMoreElements()){
                Integer key = (Integer)eDextKey.nextElement();
                StructureDeltext dext = (StructureDeltext)deltext.get(key);
                
                //add deltext
                ExtTransitionType extTransition = factory.createExtTransitionType();
                extTransition.setExtTransitionID(key.intValue());
                extTransition.setIncomingMessage(dext.incomingMsg);
                extTransition.setScheduleIndicator(dext.reschedule);
                
                TransitionType transition = factory.createTransitionType();
                transition.setStartState(dext.startState);
                transition.setNextState(dext.nextState);
                extTransition.setTransition(transition);
                
                deltextList.add(extTransition);
                
                //add inport
                if(!inportList.contains("in"+dext.incomingMsg))
                    inportList.add("in"+dext.incomingMsg);
                
                //work for outoingmsg related tags
                if(dext.outgoingMsg==null)continue;
                if(dext.outgoingMsg.length()==0)continue;
                
                //add outport
                if(!outportList.contains("out"+dext.outgoingMsg)){
                    outportList.add("out"+dext.outgoingMsg);
                }
                
                //augment LamdaList
                LambdaType lamda = factory.createLambdaType();
                lamda.setState(dext.nextState);
                lamda.setOutport("out"+dext.outgoingMsg);
                if(!isLamdaAdded(lamda, lamdaList))
                    lamdaList.add(lamda);
            }
            
            
            atomic = factory.createAtomicType();
            atomic.setModelName(jtfModelName.getText());
            atomic.setHost("localhost");
            List<Object> atomicElements = atomic.getInportsAndOutportsAndStates();
            
            atomicElements.add(states);
            atomicElements.add(taTypes);
            atomicElements.add(lambdaset);
            atomicElements.add(inports);
            atomicElements.add(outports);
            atomicElements.add(deltintFactory);
            atomicElements.add(deltextFactory);
            
            this.jbuttonStateTA.setEnabled(true);
            this.jButtonDeltext.setEnabled(true);
            this.jButtonDeltint.setEnabled(true);
            this.jButtonGenerateAtomic.setEnabled(true);
            jtfModelName.setText(atomicFDD.getModelName());
            xmlPanel.setText(xml);
        } catch(Exception ex){
            ex.printStackTrace();
        }
}//GEN-LAST:event_jButtonLoadModelActionPerformed

private void jButtonDeleteModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteModelActionPerformed
    if(jListModels.getSelectedValue()==null)return;
    String file = (String)jListModels.getSelectedValue();
    String modelName = file.substring(0,file.length()-4);
    
    try{
        File xmlFile = new File(packageXmlPath+file);
        if(xmlFile.exists()){
            xmlFile.delete();
        }
        
        File javaFile = new File(packageJavaPath+modelName+".java");
        if(javaFile.exists()){
            javaFile.delete();
        }
        JOptionPane.showMessageDialog(this, "Model deleted!");
        this.atomicModelNames.remove(modelName);
        loadPackage();
    } catch(Exception ex){
        ex.printStackTrace();
    }
}//GEN-LAST:event_jButtonDeleteModelActionPerformed

private void jButtonShowModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowModelActionPerformed
    if(jListModels.getSelectedValue()==null)return;
    String file = (String)jListModels.getSelectedValue();
    String modelName = file.substring(0,file.length()-4);
    
    String filename = packageXmlPath+file;
    
    boolean modelTypes[] = getRootModelTypeFromFile(filename);
    boolean isDigraph = modelTypes[0];
    boolean isAtomic = modelTypes[1];
    
    this.removeAllTabs();
    if(isAtomic){
        this.addFDDevsTabs();
    }
    if(isDigraph){
        this.addCoupledTabs();
    }
    
    try{
        String xml = getFileContents(filename);
        filename = packageJavaPath+modelName+".java";
        String java = getFileContents(filename);
        if (java.equals(""))return;//b[z
        xmlPanel.setText(xml);
        javaPanel.setText(java);
        
    } catch(Exception ex){
        ex.printStackTrace();
    }
}//GEN-LAST:event_jButtonShowModelActionPerformed

public String getOutputMsg(  Hashtable<String, LambdaType> lambda,String phase){
    if (lambda != null) {
        Enumeration e = lambda.elements();
        while (e.hasMoreElements()) {
            LambdaType lamda = (LambdaType) e.nextElement();
            
            String keyphase = lamda.getState().trim();
            String out = lamda.getOutport().trim();
            if (keyphase.equals(phase))return out.substring(3,out.length());
        }
    }
    return "";
}



private String getFileContents(String filename){
    java.lang.String str = "";
    
    java.io.BufferedReader in = null;
    try {
        java.io.File file = new java.io.File(filename);
        if (!file.exists()) {
            return str;
        }
        in = new java.io.BufferedReader(new java.io.FileReader(file));
        java.lang.String line = in.readLine();
        while (line != null) {
            str += line;
            str += "\n";
            line = in.readLine();
        }
        in.close();
        return str;
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return str;
}

private void jButtonGenerateAtomicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerateAtomicActionPerformed
    String modelName = this.jtfModelName.getText();
    if(modelName==null || modelName.length()==0){
        JOptionPane.showMessageDialog(this, "Enter atomic model name to generate model.");
        return;
    }
    generateFDDEVSModel();
    //this.jButtonGenerateAtomic.setEnabled(false);
    //this.jtfModelName.setText("");
}//GEN-LAST:event_jButtonGenerateAtomicActionPerformed

private void generateFDDEVSModel(){
    com.acims.fddevs.ObjectFactory factory = new
            com.acims.fddevs.ObjectFactory();
    
    
    //*Create States tag
    StatesType states = factory.createStatesType();
    List<String> list = (List<String>) states.getState();
    
    TimeAdvanceType taTypes = factory.createTimeAdvanceType();
    List<TaType> taList = taTypes.getTa();
    
    Enumeration e = stateAndTA.keys();
    while(e.hasMoreElements()){
        String state = (String)e.nextElement();
        list.add(state);
        
        TaType taType = factory.createTaType();
        taType.setState(state);
        taType.setTimeout(stateAndTA.get(state));
        taList.add(taType);
    }
    
    System.out.println("states: "+states.getState().size());
    System.out.println("ta: "+taTypes.getTa().size());
    
    //Get lamdaset
    LamdaAllType lambdaset = factory.createLamdaAllType();
    List<LambdaType> lamdaList = lambdaset.getLamda();
    
    //get inports
    InportType inports = factory.createInportType();
    List<String> inportList = (List<String>)inports.getInport();
    
    //get outports
    OutportType outports = factory.createOutportType();
    List<String> outportList = (List<String>)outports.getOutport();
    
    //get deltint
    DeltintType deltintFactory = factory.createDeltintType();
    List<IntTransitionType> deltintList = deltintFactory.getInternalTransition();
    
    //get deltext
    DeltextType deltextFactory = factory.createDeltextType();
    List<ExtTransitionType> deltextList = deltextFactory.getExternalTransition();
    
    
    Enumeration eDintKey = deltint.keys();
    while(eDintKey.hasMoreElements()){
        Integer key = (Integer)eDintKey.nextElement();
        StructureDeltint dint = (StructureDeltint)deltint.get(key);
        
        //add Internal Transition
        IntTransitionType intTransition = factory.createIntTransitionType();
        intTransition.setIntTransitionID(key.intValue());
        List<TransitionType> transitionList = intTransition.getTransition();
        
        TransitionType transition = factory.createTransitionType();
        transition.setNextState(dint.nextState);
        transition.setStartState(dint.startState);
        transitionList.add(transition);
        
        deltintList.add(intTransition);
        
        //work for outgoingMsg related tags
        if(dint.outgoingMsg==null)continue;
        if(dint.outgoingMsg.length()==0)continue;
        
        //add outport
        outportList.add("out"+dint.outgoingMsg);
        
        //augment LamdaList
        LambdaType lamda = factory.createLambdaType();
        lamda.setState(dint.startState);
        lamda.setOutport("out"+dint.outgoingMsg);
        if(!isLamdaAdded(lamda, lamdaList)){
            lamdaList.add(lamda);
        }
    }
    
    Enumeration eDextKey = deltext.keys();
    while(eDextKey.hasMoreElements()){
        Integer key = (Integer)eDextKey.nextElement();
        StructureDeltext dext = (StructureDeltext)deltext.get(key);
        
        //add deltext
        ExtTransitionType extTransition = factory.createExtTransitionType();
        extTransition.setExtTransitionID(key.intValue());
        extTransition.setIncomingMessage(dext.incomingMsg);
        extTransition.setScheduleIndicator(dext.reschedule);
        
        TransitionType transition = factory.createTransitionType();
        transition.setStartState(dext.startState);
        transition.setNextState(dext.nextState);
        extTransition.setTransition(transition);
        
        deltextList.add(extTransition);
        
        //add inport
        if(!inportList.contains("in"+dext.incomingMsg))
            inportList.add("in"+dext.incomingMsg);
        
        //work for outoingmsg related tags
        if(dext.outgoingMsg==null)continue;
        if(dext.outgoingMsg.length()==0)continue;
        
        //add outport
        if(!outportList.contains("out"+dext.outgoingMsg))
            outportList.add("out"+dext.outgoingMsg);
        
        //augment LamdaList
        LambdaType lamda = factory.createLambdaType();
        lamda.setState(dext.nextState);
        lamda.setOutport("out"+dext.outgoingMsg);
        if(!isLamdaAdded(lamda, lamdaList)){
            lamdaList.add(lamda);
        }
    }
    
    
    atomic = factory.createAtomicType();
    atomic.setModelName(jtfModelName.getText());
    atomic.setHost("localhost");
    List<Object> atomicElements = atomic.getInportsAndOutportsAndStates();
    
    atomicElements.add(states);
    atomicElements.add(taTypes);
    atomicElements.add(lambdaset);
    atomicElements.add(inports);
    atomicElements.add(outports);
    atomicElements.add(deltintFactory);
    atomicElements.add(deltextFactory);
    
    try{
        JAXBContext jc = JAXBContext.newInstance("com.acims.fddevs");
        Marshaller nm = jc.createMarshaller();
        nm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        modelName = atomic.getModelName();
        this.atomicModelNames.add(modelName);
        String filename = packageXmlPath+modelName+".xml";
        FileWriter writer = new FileWriter(new File(filename));
        nm.marshal(atomic, writer);
        writer.close();
        
        java.io.StringWriter sw = new StringWriter();
        nm.marshal(atomic, sw);
        
        AtomicJAXB atJaxb = new AtomicJAXB();
        atJaxb.initializeModel(filename);
        xmlFileStr = atJaxb.returnXmlModelString();
        
        xmlPanel.setText(sw.toString());
        javaFileStr = atJaxb.returnJavaModelString();
        javaPanel.setText(javaFileStr);
        
        String javaPath = writeJavaFile(modelName);
        
        JOptionPane.showMessageDialog(this, "SAVED: \nFD-DEVS XML saved at: "+filename+
                "\nFD-DEVS Java saved at: "+javaPath);
        loadPackage();
    } catch(Exception ex){
        ex.printStackTrace();
    }
}

private boolean isLamdaAdded(LambdaType lamda,List<LambdaType> lamdaList ){
    for(int i=0; i<lamdaList.size(); i++){
        LambdaType lamdaInst = (LambdaType)lamdaList.get(i);
        
        String outport = lamdaInst.getOutport().trim();
        String state = lamdaInst.getState().trim();
        if((outport.equalsIgnoreCase(lamda.getOutport().trim()))&&
                (state.equalsIgnoreCase(lamda.getState()))){
            return true;
        }
        
    }
    
    return false;
}
private String writeXmlFile(String modelName){
    return writeFile(modelName, packageXmlPath,xmlFileStr,"xml" );
}

private String writeJavaFile(String modelName){
    return writeFile(modelName, packageJavaPath, javaFileStr, "java");
}

private String writeFile(String modelName,String path, String contents, String type){
    
    String filename = path+modelName+"."+type;
    File file = new File(filename);
    if(file.exists())
        file.delete();
    try{
        FileWriter writer = new FileWriter(new File(filename));
        writer.write(contents);
        writer.close();
        
    } catch(Exception ex){
        ex.printStackTrace();
    }
    
    return filename;
}


private void jButtonCreateAtomicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateAtomicActionPerformed
    
    if(!atomicModelNames.contains(this.jtfModelName.getText())){
        
        stateAndTA = new Hashtable<String,Double>();
        deltint = new Hashtable<Integer,StructureDeltint>();
        deltext = new Hashtable<Integer,StructureDeltext>();
        
        this.jbuttonStateTA.setEnabled(true);
        this.jButtonDeltext.setEnabled(true);
        this.jButtonDeltint.setEnabled(true);
        this.jButtonGenerateAtomic.setEnabled(true);
    } else{
        if(this.jtfModelName.getText().length()>0)
            JOptionPane.showMessageDialog(this, "Model with same name Exists!");
        return;
    }
    
}//GEN-LAST:event_jButtonCreateAtomicActionPerformed

private Vector getStatesFromHashtableStateAndTA(){
    Vector<String> v = new Vector<String>();
    Enumeration e = stateAndTA.keys();
    while(e.hasMoreElements()){
        String state = (String)e.nextElement();
        System.out.println("Adding state: "+state);
        v.add(state);
    }
    return v;
}

private void jButtonDeltextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeltextActionPerformed
    DialogDeltext dlgDeltext = new DialogDeltext(this, true);
    dlgDeltext.setTitle("Model "+this.jtfModelName.getText()+":   External Induced Behavior (deltext) Specifications");
    dlgDeltext.resetTables();
    Vector states = getStatesFromHashtableStateAndTA();
    if(states.size()>0)
        dlgDeltext.loadStateComboBoxes(states);
    
    dlgDeltext.setVisible(true);
}//GEN-LAST:event_jButtonDeltextActionPerformed

private void jButtonDeltintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeltintActionPerformed
    DialogDeltint dlgDeltint = new DialogDeltint(this, true);
    dlgDeltint.setTitle("Model "+this.jtfModelName.getText()+":   Internal Behavior (deltint) Specifications");
    dlgDeltint.resetTables();
    Vector states = getStatesFromHashtableStateAndTA();
    if(states.size()>0)
        dlgDeltint.loadStateComboBoxes(states);
    dlgDeltint.setVisible(true);
}//GEN-LAST:event_jButtonDeltintActionPerformed

private void jbuttonStateTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonStateTAActionPerformed
    DialogStateTA dlg = new DialogStateTA(this,true);
    dlg.setTitle("Model "+this.jtfModelName.getText()+":   State-Timeout relations");
    dlg.addStateTAPanel();
    dlg.setVisible(true);
    
}//GEN-LAST:event_jbuttonStateTAActionPerformed

/**
 * @param args the command line arguments
 */

public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

    
    try {
        //UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessLookAndFeel");
        UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel");
        //UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel");
        //UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceCremeLookAndFeel");
    } catch (UnsupportedLookAndFeelException ulafe) {
        ulafe.printStackTrace();
    }

    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new FrameFDDEVS().setVisible(true);
        }
    }
    );
}

  public static void runFDDEVSGUI(String packageNm) {
     //   String packageNm = "testSynthesis/SpreadExample/";
        String packageStringModel = packageNm+"Models";
        FrameFDDEVS.packageString ="src/"+packageStringModel;
        FrameFDDEVS.packagePath = FrameFDDEVS.packageString+File.separator;
        FrameFDDEVS.packageJavaPath = FrameFDDEVS.packagePath+"java"+File.separator;
        FrameFDDEVS.packageXmlPath = FrameFDDEVS.packagePath+"xml"+File.separator;
        try {
            
            FrameFDDEVS.main(new String[]{});
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
  }
        
private JPanel getJPanel4() {
    if (jPanel4 == null) {
        jPanel4 = new JPanel();
        FlowLayout jPanel4Layout = new FlowLayout();
        jPanel4.setLayout(jPanel4Layout);
        jPanel4.setPreferredSize(new java.awt.Dimension(10, 10));
    }
    return jPanel4;
}

private JLabel getJLabel8() {
    if (jLabel8 == null) {
        jLabel8 = new JLabel();
        jLabel8.setText("(c) 2007 Saurabh Mittal");
        jLabel8.setFont(new java.awt.Font("Tahoma",0,9));
        jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
    }
    return jLabel8;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSimulate;
    private javax.swing.JButton jButtonCreateAtomic;
    private javax.swing.JButton jButtonCreateCoupled;
    private javax.swing.JButton jButtonDeleteModel;
    private javax.swing.JButton jButtonDeltext;
    private javax.swing.JButton jButtonDeltint;
    private JLabel jLabel8;
    private JPanel jPanel4;
    private javax.swing.JButton jButtonGenerateAtomic;
    private javax.swing.JButton jButtonLoadModel;
    private javax.swing.JButton jButtonMakeIntoCoupled;
    private javax.swing.JButton jButtonResetCoupled;
    private javax.swing.JButton jButtonShowModel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jListComponentFDDEVS;
    private javax.swing.JList jListModels;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jbuttonStateTA;
    private javax.swing.JTextField jtfModelName;
    private javax.swing.JTextField jtfCoupledModelName;
    // End of variables declaration//GEN-END:variables
    
}
