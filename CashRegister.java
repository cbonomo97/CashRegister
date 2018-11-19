/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashregister;

/**
 *
 * @author cbonomo97
 */
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class CashRegister extends JFrame {

    /**
     * @param args the command line arguments
     */  

    //Register monetary elements 
    private final Stack<BigDecimal> registerQueue = new Stack<>();
    private BigDecimal total = new BigDecimal(0.00);
    private BigDecimal subtotal = new BigDecimal(0.00);
    private BigDecimal dailyTotal = new BigDecimal(0.00);
    private final BigDecimal taxPercent = new BigDecimal(1.0875);
    
    //item/button containers
    private final int maxNum = 110;
    private Item[] items = new Item[maxNum];
    private JButton[] itemBtns = new JButton[maxNum];
    private JButton[] otherBtns = new JButton[16];
   
    //display layouts
    private final GridLayout itemButtonLayout = new GridLayout(10,11);
    private final GridLayout otherButtonLayout = new GridLayout(4,4);
    private final GridLayout displayLayout = new GridLayout(1,4);
    
    
    //Class constructor extends JFrame
    public CashRegister(String name){
        super(name);
        setSize(1024,768);
        setResizable(false);
    }
    
    public Item[] makeItems() {
        ItemArray bonomosItems = new ItemArray(1);
       Item[] itms = bonomosItems.getItemArray();
        return itms;
    }

    public JButton[] makeButtons(Item[] items) {
        for(int i=0; i<items.length; i++){
            itemBtns[i]=new JButton(items[i].getName());
            itemBtns[i].setBackground(items[i].getButtonColor());
            itemBtns[i].setPreferredSize(new Dimension(90,70));
            /*if(itemBtns[i].getName()==""){
                itemBtns[i].setEnabled(false);
            }*/   
        }    
        return itemBtns;
    }
    
    public JButton[] makeOtherButtons(){
        JButton[] otherButtons = new JButton[16];
        
        //otherButtons[0]=new JButton
        otherButtons[0]=new JButton("7");
        otherButtons[1]=new JButton("8");
        otherButtons[2]=new JButton("9");
        otherButtons[3]=new JButton("CLEAR");
        otherButtons[4]=new JButton("4");
        otherButtons[5]=new JButton("5");
        otherButtons[6]=new JButton("6");
        otherButtons[7]=new JButton("CANCEL");
        otherButtons[8]=new JButton("1");
        otherButtons[9]=new JButton("2");
        otherButtons[10]=new JButton("3");
        otherButtons[11]=new JButton("SBTL");
        otherButtons[12]=new JButton("00");
        otherButtons[13]=new JButton("0");
        otherButtons[14]=new JButton("");
        otherButtons[15]=new JButton("CASH");
                              
        return otherButtons;
    }
    
    public void makeRegisterLayout(JPanel content){
        JPanel itemButtons = new JPanel();
        itemButtons.setLayout(itemButtonLayout);
        
        JPanel otherButtons = new JPanel();
        otherButtons.setLayout(otherButtonLayout);
        
        JPanel displayLabels = new JPanel();
        displayLabels.setLayout(displayLayout);
        
        items=makeItems();
        itemBtns=makeButtons(items);
        otherBtns=makeOtherButtons();
        
        //adding item buttons to gridlayout
        for(int i=0; i<items.length; i++){
            itemButtons.add(itemBtns[i]);
        }
        
        //adding other buttons to gridlayout
        for(int i=0; i<otherBtns.length; i++){
            otherButtons.add(otherBtns[i]);
        }
 
        //Create labels
        
        Label current =new Label("Current Item: ");
        Label sub =new Label("Subtotal: ");
        Label tot =new Label("Total: ");
        displayLabels.add(current);
        displayLabels.add(sub);
        displayLabels.add(tot);
        
        
        //handles button events
        ActionListener buttonListener = (ActionEvent e) -> {
            //ITEM BUTTONS
            if(e.getSource() == itemBtns[0]){
                registerQueue.push(items[0].getPrice());
                //System.out.print("sm added ");
                //current.setText("Current Item: " + items[0].getName());            
            }
            if(e.getSource() == itemBtns[1]){
                registerQueue.push(items[1].getPrice());
                //System.out.print("md added ");
                current.setText("Current Item: " + items[1].getName());
            }
            if(e.getSource() == itemBtns[2]){
                registerQueue.push(items[2].getPrice());
                //System.out.print("lg added ");
                current.setText("Current Item: " + items[2].getName());
            }
            
            //OTHER BUTTONS
            if(e.getSource() == otherBtns[7]){ //CANCEL BTN
                registerQueue.clear();
                total = BigDecimal.ZERO;
                subtotal = BigDecimal.ZERO;
                
                System.out.print(registerQueue.toString());
            }
            if(e.getSource() == otherBtns[11]){ //SBTL BTN
                for(int i=0; i<registerQueue.size(); i++){
                    //needs to continue to add/display correct amounts multiple times if checking sbtl multiple times in one order
                    subtotal = subtotal.add(registerQueue.elementAt(i));
                }
                
                subtotal = subtotal.multiply(taxPercent);
                subtotal = subtotal.setScale(2, BigDecimal.ROUND_HALF_UP);
                System.out.print(subtotal.toString());
            }
            if(e.getSource() == otherBtns[15]){ //CASH BTN
                
                for(int i=0; i<registerQueue.size(); i++){
                    total = total.add(registerQueue.elementAt(i));
                }
                
                total = total.multiply(taxPercent);
                total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
                System.out.print(total.toString());
                tot.setName(total.toString());
                
                registerQueue.clear();
                subtotal = BigDecimal.ZERO;
                total = BigDecimal.ZERO;
            }
        };
        
        for(int i=0; i<itemBtns.length; i++){
            itemBtns[i].addActionListener(buttonListener);
        }
        
        for(int i=0; i<otherBtns.length; i++){
            otherBtns[i].addActionListener(buttonListener);
        }
        
        content.add(itemButtons, BorderLayout.WEST);
        content.add(otherButtons, BorderLayout.EAST);
        content.add(displayLabels, BorderLayout.NORTH);      
    }

    public static void makeRegisterFrame() {
        CashRegister r;
        r = new CashRegister("Bonomo's Dari Creme");
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        r.makeRegisterLayout((JPanel)r.getContentPane());

        r.pack();
        r.setVisible(true);
    }

    public static void main(String[] args) {
        // TODO code application logic here
        makeRegisterFrame();

    }

}
