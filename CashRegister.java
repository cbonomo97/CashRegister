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
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public class CashRegister extends JFrame {

    /**
     * @param args the command line arguments
     */  

    //Register monetary elements 
    private final Stack<BigDecimal> registerQueue = new Stack<>();
    private final Stack<Item> itemQueue = new Stack<>();
    private static final Stack<BigDecimal> CASH = new Stack<>();
    private static BigDecimal total = new BigDecimal(0.00);
    private static BigDecimal subtotal = new BigDecimal(0.00);
    private static BigDecimal dailyTotal = new BigDecimal(0.00);
    private static BigDecimal creditTotal = new BigDecimal(0.00);
    private static BigDecimal cashTotal = new BigDecimal(0.00);
    private static BigDecimal gcTotal = new BigDecimal(0.00);
    private final BigDecimal taxPercent = new BigDecimal(1.0875);
    
    //item/button containers
    private final int maxNum = 110;
    private final int otherMaxNum = 9;
    private Item[] items = new Item[maxNum];
    private JButton[] itemBtns = new JButton[maxNum];
    private JButton[] otherBtns = new JButton[otherMaxNum];
   
    //display layouts
    private final GridLayout itemButtonLayout = new GridLayout(10,11);
    private final GridLayout otherButtonLayout = new GridLayout(3,3);
    private final GridLayout displayLayout = new GridLayout(1,4);
    
    //file handing and variables
    private static PrintWriter dailyCashOut;
    private static Date date;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int smIC = 0;
    private int mdIC = 0;
    private int lgIC = 0;
    
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
            itemBtns[i].setOpaque(true);
            //itemBtns[i].setBorderPainted(false);
        }    
        return itemBtns;
    }
    
    public JButton[] makeOtherButtons(){
        JButton[] otherButtons = new JButton[9];
        
        otherButtons[0]=new JButton("CASH OUT");
        otherButtons[0].setBackground(Color.RED);
        otherButtons[1]=new JButton("ERR COR");
        otherButtons[1].setBackground(Color.GREEN);
        otherButtons[2]=new JButton("CANCEL");
        otherButtons[2].setBackground(Color.RED);
        otherButtons[3]=new JButton("VOID");
        otherButtons[3].setBackground(Color.PINK);        
        otherButtons[4]=new JButton("CLEAR");
        otherButtons[4].setBackground(Color.BLUE);
        otherButtons[5]=new JButton("SBTL");
        otherButtons[5].setBackground(Color.YELLOW);
        otherButtons[6]=new JButton("GIFT CARD");
        otherButtons[6].setBackground(Color.YELLOW);
        otherButtons[7]=new JButton("CHARGE");
        otherButtons[7].setBackground(Color.CYAN);
        otherButtons[8]=new JButton("CASH"); 
        otherButtons[8].setBackground(Color.RED);
                    
        for(int i=0; i<otherButtons.length; i++){
            otherButtons[i].setPreferredSize(new Dimension(90,70));
            otherButtons[i].setOpaque(true);
        }
        return otherButtons;
    }
    
    public void createOutputFile(){//BigDecimal cashTotal, BigDecimal creditTotal){
        try {
            date = new Date();
            dailyCashOut = new PrintWriter(dateFormat.format(date) + ".txt");                 
            
            dailyCashOut.print("Bonomo's Dari Creme\n" + dateFormat.format(date)+"\n\n");
            dailyCashOut.print("# of Items Sold: \n");
            dailyCashOut.print("Sm Ice Cream:\t\t"+smIC+"\n");           
            dailyCashOut.print("Md Ice Cream:\t\t"+mdIC+"\n");    
            dailyCashOut.print("LG Ice Cream:\t\t"+lgIC+"\n\n");    
            
            dailyCashOut.print("Daily Totals:\n");
            dailyCashOut.print("Gift Card total:\t\t$"+gcTotal.toString()+"\n");
            dailyCashOut.print("Credit/Debit Total:\t\t$"+creditTotal.toString()+"\n");
            dailyCashOut.print("Cash Total:\t\t\t$"+cashTotal.toString()+"\n");
            dailyCashOut.print("\bDaily Total:\t\t\t$"+dailyTotal.toString()+"\n\b");       
            dailyCashOut.close();
        
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
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
        current.setFont(new Font("San Serif", Font.BOLD, 20));
        displayLabels.add(current);
        
        Label subLabel =new Label("Subtotal: ");
        subLabel.setFont(new Font("San Serif", Font.BOLD, 20));
        displayLabels.add(subLabel);
        
        Label totalLabel =new Label("Total: ");
        totalLabel.setFont(new Font("San Serif", Font.BOLD, 20));        
        displayLabels.add(totalLabel);
        
        
        //handles button events
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*//ITEM BUTTONS
                if(e.getSource() == itemBtns[0]){
                    registerQueue.push(items[0].getPrice());
                    current.setText("Current Item: " + items[0].getName());
                    itemQueue.push(items[0]);
                }
                if(e.getSource() == itemBtns[1]){
                    registerQueue.push(items[1].getPrice());
                    current.setText("Current Item: " + items[1].getName());
                    //System.out.print("md added ");
                    //current.setText("Current Item: " + items[1].getName());
                }
                if(e.getSource() == itemBtns[2]){
                    registerQueue.push(items[2].getPrice());
                    //System.out.print("lg added ");
                    //current.setText("Current Item: " + items[2].getName());
                }
                */
                //ITEM BUTTONS
                if(e.getSource() == itemBtns[0]){
                    itemQueue.push(items[0]);
                    current.setText("Current Item: " + items[0].getName());
                    smIC++;
                }
                if(e.getSource() == itemBtns[1]){
                    itemQueue.push(items[1]);
                    current.setText("Current Item: " + items[1].getName());
                    mdIC++;
                }
                if(e.getSource() == itemBtns[2]){
                    itemQueue.push(items[2]);
                    current.setText("Current Item: " + items[2].getName());
                    lgIC++;
                }
                if(e.getSource() == itemBtns[3]){
                    itemQueue.push(items[3]);
                    current.setText("Current Item: " + items[3].getName());
                }
                if(e.getSource() == itemBtns[4]){
                    itemQueue.push(items[4]);
                    current.setText("Current Item: " + items[4].getName());
                }
                if(e.getSource() == itemBtns[5]){
                    itemQueue.push(items[5]);
                    current.setText("Current Item: " + items[5].getName());
                }
                if(e.getSource() == itemBtns[6]){
                    itemQueue.push(items[6]);
                    current.setText("Current Item: " + items[6].getName());
                }
                if(e.getSource() == itemBtns[7]){
                    itemQueue.push(items[7]);
                    current.setText("Current Item: " + items[7].getName());
                }
                if(e.getSource() == itemBtns[8]){
                    itemQueue.push(items[8]);
                    current.setText("Current Item: " + items[8].getName());
                }
                if(e.getSource() == itemBtns[9]){
                    itemQueue.push(items[9]);
                    current.setText("Current Item: " + items[9].getName());
                }
                if(e.getSource() == itemBtns[10]){
                    itemQueue.push(items[10]);
                    current.setText("Current Item: " + items[10].getName());
                }
                if(e.getSource() == itemBtns[11]){
                    itemQueue.push(items[11]);
                    current.setText("Current Item: " + items[11].getName());
                }
                if(e.getSource() == itemBtns[12]){
                    itemQueue.push(items[12]);
                    current.setText("Current Item: " + items[12].getName());
                }
                if(e.getSource() == itemBtns[13]){
                    itemQueue.push(items[13]);
                    current.setText("Current Item: " + items[13].getName());
                }
                if(e.getSource() == itemBtns[14]){
                    itemQueue.push(items[14]);
                    current.setText("Current Item: " + items[14].getName());
                }
                if(e.getSource() == itemBtns[15]){
                    itemQueue.push(items[15]);
                    current.setText("Current Item: " + items[15].getName());
                }
                if(e.getSource() == itemBtns[16]){
                    itemQueue.push(items[16]);
                    current.setText("Current Item: " + items[16].getName());
                }
                if(e.getSource() == itemBtns[17]){
                    itemQueue.push(items[17]);
                    current.setText("Current Item: " + items[17].getName());
                }
                if(e.getSource() == itemBtns[18]){
                    itemQueue.push(items[18]);
                    current.setText("Current Item: " + items[18].getName());
                }
                if(e.getSource() == itemBtns[19]){
                    itemQueue.push(items[19]);
                    current.setText("Current Item: " + items[19].getName());
                }
                if(e.getSource() == itemBtns[20]){
                    itemQueue.push(items[20]);
                    current.setText("Current Item: " + items[20].getName());
                }
                if(e.getSource() == itemBtns[21]){
                    itemQueue.push(items[21]);
                    current.setText("Current Item: " + items[21].getName());
                }
                if(e.getSource() == itemBtns[22]){
                    itemQueue.push(items[22]);
                    current.setText("Current Item: " + items[22].getName());
                }
                if(e.getSource() == itemBtns[23]){
                    itemQueue.push(items[23]);
                    current.setText("Current Item: " + items[23].getName());
                }
                if(e.getSource() == itemBtns[24]){
                    itemQueue.push(items[24]);
                    current.setText("Current Item: " + items[24].getName());
                }
                if(e.getSource() == itemBtns[25]){
                    itemQueue.push(items[25]);
                    current.setText("Current Item: " + items[25].getName());
                }
                if(e.getSource() == itemBtns[26]){
                    itemQueue.push(items[26]);
                    current.setText("Current Item: " + items[26].getName());
                }
                if(e.getSource() == itemBtns[27]){
                    itemQueue.push(items[27]);
                    current.setText("Current Item: " + items[27].getName());
                }
                if(e.getSource() == itemBtns[28]){
                    itemQueue.push(items[28]);
                    current.setText("Current Item: " + items[28].getName());
                }
                if(e.getSource() == itemBtns[29]){
                    itemQueue.push(items[29]);
                    current.setText("Current Item: " + items[29].getName());
                }
                if(e.getSource() == itemBtns[30]){
                    itemQueue.push(items[30]);
                    current.setText("Current Item: " + items[30].getName());
                }
                if(e.getSource() == itemBtns[31]){
                    itemQueue.push(items[31]);
                    current.setText("Current Item: " + items[31].getName());
                }
                if(e.getSource() == itemBtns[32]){
                    itemQueue.push(items[32]);
                    current.setText("Current Item: " + items[32].getName());
                }
                if(e.getSource() == itemBtns[33]){
                    itemQueue.push(items[33]);
                    current.setText("Current Item: " + items[33].getName());
                }
                if(e.getSource() == itemBtns[34]){
                    itemQueue.push(items[34]);
                    current.setText("Current Item: " + items[34].getName());
                }
                if(e.getSource() == itemBtns[35]){
                    itemQueue.push(items[35]);
                    current.setText("Current Item: " + items[35].getName());
                }
                if(e.getSource() == itemBtns[36]){
                    itemQueue.push(items[36]);
                    current.setText("Current Item: " + items[36].getName());
                }
                if(e.getSource() == itemBtns[37]){
                    itemQueue.push(items[37]);
                    current.setText("Current Item: " + items[37].getName());
                }
                if(e.getSource() == itemBtns[38]){
                    itemQueue.push(items[38]);
                    current.setText("Current Item: " + items[38].getName());
                }
                if(e.getSource() == itemBtns[39]){
                    itemQueue.push(items[39]);
                    current.setText("Current Item: " + items[39].getName());
                }
                if(e.getSource() == itemBtns[40]){
                    itemQueue.push(items[40]);
                    current.setText("Current Item: " + items[40].getName());
                }
                if(e.getSource() == itemBtns[41]){
                    itemQueue.push(items[41]);
                    current.setText("Current Item: " + items[41].getName());
                }
                if(e.getSource() == itemBtns[42]){
                    itemQueue.push(items[42]);
                    current.setText("Current Item: " + items[42].getName());
                }
                if(e.getSource() == itemBtns[43]){
                    itemQueue.push(items[43]);
                    current.setText("Current Item: " + items[43].getName());
                }
                if(e.getSource() == itemBtns[44]){
                    itemQueue.push(items[44]);
                    current.setText("Current Item: " + items[44].getName());
                }
                if(e.getSource() == itemBtns[45]){
                    itemQueue.push(items[45]);
                    current.setText("Current Item: " + items[45].getName());
                }
                if(e.getSource() == itemBtns[46]){
                    itemQueue.push(items[46]);
                    current.setText("Current Item: " + items[46].getName());
                }
                if(e.getSource() == itemBtns[47]){
                    itemQueue.push(items[47]);
                    current.setText("Current Item: " + items[47].getName());
                }
                if(e.getSource() == itemBtns[48]){
                    itemQueue.push(items[48]);
                    current.setText("Current Item: " + items[48].getName());
                }
                if(e.getSource() == itemBtns[49]){
                    itemQueue.push(items[49]);
                    current.setText("Current Item: " + items[49].getName());
                }
                if(e.getSource() == itemBtns[50]){
                    itemQueue.push(items[50]);
                    current.setText("Current Item: " + items[50].getName());
                }
                if(e.getSource() == itemBtns[51]){
                    itemQueue.push(items[51]);
                    current.setText("Current Item: " + items[51].getName());
                }
                if(e.getSource() == itemBtns[52]){
                    itemQueue.push(items[52]);
                    current.setText("Current Item: " + items[52].getName());
                }
                if(e.getSource() == itemBtns[53]){
                    itemQueue.push(items[53]);
                    current.setText("Current Item: " + items[53].getName());
                }
                if(e.getSource() == itemBtns[54]){
                    itemQueue.push(items[54]);
                    current.setText("Current Item: " + items[54].getName());
                }
                if(e.getSource() == itemBtns[55]){
                    itemQueue.push(items[55]);
                    current.setText("Current Item: " + items[55].getName());
                }
                if(e.getSource() == itemBtns[56]){
                    itemQueue.push(items[56]);
                    current.setText("Current Item: " + items[56].getName());
                }
                if(e.getSource() == itemBtns[57]){
                    itemQueue.push(items[57]);
                    current.setText("Current Item: " + items[57].getName());
                }
                if(e.getSource() == itemBtns[58]){
                    itemQueue.push(items[58]);
                    current.setText("Current Item: " + items[58].getName());
                }
                if(e.getSource() == itemBtns[59]){
                    itemQueue.push(items[59]);
                    current.setText("Current Item: " + items[59].getName());
                }
                if(e.getSource() == itemBtns[60]){
                    itemQueue.push(items[60]);
                    current.setText("Current Item: " + items[60].getName());
                }
                if(e.getSource() == itemBtns[61]){
                    itemQueue.push(items[61]);
                    current.setText("Current Item: " + items[61].getName());
                }
                if(e.getSource() == itemBtns[62]){
                    itemQueue.push(items[62]);
                    current.setText("Current Item: " + items[62].getName());
                }
                if(e.getSource() == itemBtns[63]){
                    itemQueue.push(items[63]);
                    current.setText("Current Item: " + items[63].getName());
                }
                if(e.getSource() == itemBtns[64]){
                    itemQueue.push(items[64]);
                    current.setText("Current Item: " + items[64].getName());
                }
                if(e.getSource() == itemBtns[65]){
                    itemQueue.push(items[65]);
                    current.setText("Current Item: " + items[65].getName());
                }
                if(e.getSource() == itemBtns[66]){
                    itemQueue.push(items[66]);
                    current.setText("Current Item: " + items[66].getName());
                }
                if(e.getSource() == itemBtns[67]){
                    itemQueue.push(items[67]);
                    current.setText("Current Item: " + items[67].getName());
                }
                if(e.getSource() == itemBtns[68]){
                    itemQueue.push(items[68]);
                    current.setText("Current Item: " + items[68].getName());
                }
                if(e.getSource() == itemBtns[69]){
                    itemQueue.push(items[69]);
                    current.setText("Current Item: " + items[69].getName());
                }
                if(e.getSource() == itemBtns[70]){
                    itemQueue.push(items[70]);
                    current.setText("Current Item: " + items[70].getName());
                }
                if(e.getSource() == itemBtns[71]){
                    itemQueue.push(items[71]);
                    current.setText("Current Item: " + items[71].getName());
                }
                if(e.getSource() == itemBtns[72]){
                    itemQueue.push(items[72]);
                    current.setText("Current Item: " + items[72].getName());
                }
                if(e.getSource() == itemBtns[73]){
                    itemQueue.push(items[73]);
                    current.setText("Current Item: " + items[73].getName());
                }
                if(e.getSource() == itemBtns[74]){
                    itemQueue.push(items[74]);
                    current.setText("Current Item: " + items[74].getName());
                }
                if(e.getSource() == itemBtns[75]){
                    itemQueue.push(items[75]);
                    current.setText("Current Item: " + items[75].getName());
                }
                if(e.getSource() == itemBtns[76]){
                    itemQueue.push(items[76]);
                    current.setText("Current Item: " + items[76].getName());
                }
                if(e.getSource() == itemBtns[77]){
                    itemQueue.push(items[77]);
                    current.setText("Current Item: " + items[77].getName());
                }
                if(e.getSource() == itemBtns[78]){
                    itemQueue.push(items[78]);
                    current.setText("Current Item: " + items[78].getName());
                }
                if(e.getSource() == itemBtns[79]){
                    itemQueue.push(items[79]);
                    current.setText("Current Item: " + items[79].getName());
                }
                if(e.getSource() == itemBtns[80]){
                    itemQueue.push(items[80]);
                    current.setText("Current Item: " + items[80].getName());
                }
                if(e.getSource() == itemBtns[81]){
                    itemQueue.push(items[81]);
                    current.setText("Current Item: " + items[81].getName());
                }
                if(e.getSource() == itemBtns[82]){
                    itemQueue.push(items[82]);
                    current.setText("Current Item: " + items[82].getName());
                }
                if(e.getSource() == itemBtns[83]){
                    itemQueue.push(items[83]);
                    current.setText("Current Item: " + items[83].getName());
                }
                if(e.getSource() == itemBtns[84]){
                    itemQueue.push(items[84]);
                    current.setText("Current Item: " + items[84].getName());
                }
                if(e.getSource() == itemBtns[85]){
                    itemQueue.push(items[85]);
                    current.setText("Current Item: " + items[85].getName());
                }
                if(e.getSource() == itemBtns[86]){
                    itemQueue.push(items[86]);
                    current.setText("Current Item: " + items[86].getName());
                }
                if(e.getSource() == itemBtns[87]){
                    itemQueue.push(items[87]);
                    current.setText("Current Item: " + items[87].getName());
                }
                if(e.getSource() == itemBtns[88]){
                    itemQueue.push(items[88]);
                    current.setText("Current Item: " + items[88].getName());
                }
                if(e.getSource() == itemBtns[89]){
                    itemQueue.push(items[89]);
                    current.setText("Current Item: " + items[89].getName());
                }
                if(e.getSource() == itemBtns[90]){
                    itemQueue.push(items[90]);
                    current.setText("Current Item: " + items[90].getName());
                }
                if(e.getSource() == itemBtns[91]){
                    itemQueue.push(items[91]);
                    current.setText("Current Item: " + items[91].getName());
                }
                if(e.getSource() == itemBtns[92]){
                    itemQueue.push(items[92]);
                    current.setText("Current Item: " + items[92].getName());
                }
                if(e.getSource() == itemBtns[93]){
                    itemQueue.push(items[93]);
                    current.setText("Current Item: " + items[93].getName());
                }
                if(e.getSource() == itemBtns[94]){
                    itemQueue.push(items[94]);
                    current.setText("Current Item: " + items[94].getName());
                }
                if(e.getSource() == itemBtns[95]){
                    itemQueue.push(items[95]);
                    current.setText("Current Item: " + items[95].getName());
                }
                if(e.getSource() == itemBtns[96]){
                    itemQueue.push(items[96]);
                    current.setText("Current Item: " + items[96].getName());
                }
                if(e.getSource() == itemBtns[97]){
                    itemQueue.push(items[97]);
                    current.setText("Current Item: " + items[97].getName());
                }
                if(e.getSource() == itemBtns[98]){
                    itemQueue.push(items[98]);
                    current.setText("Current Item: " + items[98].getName());
                }
                if(e.getSource() == itemBtns[99]){
                    itemQueue.push(items[99]);
                    current.setText("Current Item: " + items[99].getName());
                }
                if(e.getSource() == itemBtns[100]){
                    itemQueue.push(items[100]);
                    current.setText("Current Item: " + items[100].getName());
                }
                if(e.getSource() == itemBtns[101]){
                    itemQueue.push(items[101]);
                    current.setText("Current Item: " + items[101].getName());
                }
                if(e.getSource() == itemBtns[102]){
                    itemQueue.push(items[102]);
                    current.setText("Current Item: " + items[102].getName());
                }
                if(e.getSource() == itemBtns[103]){
                    itemQueue.push(items[103]);
                    current.setText("Current Item: " + items[103].getName());
                }
                if(e.getSource() == itemBtns[104]){
                    itemQueue.push(items[104]);
                    current.setText("Current Item: " + items[104].getName());
                }
                if(e.getSource() == itemBtns[105]){
                    itemQueue.push(items[105]);
                    current.setText("Current Item: " + items[105].getName());
                }
                if(e.getSource() == itemBtns[106]){
                    itemQueue.push(items[106]);
                    current.setText("Current Item: " + items[106].getName());
                }
                if(e.getSource() == itemBtns[107]){
                    itemQueue.push(items[107]);
                    current.setText("Current Item: " + items[107].getName());
                }
                if(e.getSource() == itemBtns[108]){
                    itemQueue.push(items[108]);
                    current.setText("Current Item: " + items[108].getName());
                }              
                if(e.getSource() == itemBtns[109]){
                    itemQueue.push(items[109]);
                    current.setText("Current Item: " + items[109].getName());
                }
                
                //OTHER BUTTONS
                
                if(e.getSource() == otherBtns[0]){//CASH OUT
                    createOutputFile();
                    System.exit(0);
                }
                if(e.getSource() == otherBtns[1]){ //ERROR CORRECT
                    itemQueue.pop();
                }
                if(e.getSource() == otherBtns[2]){ //CANCEL BTN
                    itemQueue.clear();
                    total = BigDecimal.ZERO;
                    subtotal = BigDecimal.ZERO;
                    
                    System.out.print(registerQueue.toString());
                }
                if(e.getSource() == otherBtns[3]){ //VOID
                    for(int i=0; i<itemQueue.size(); i++){
                        if(itemQueue.elementAt(i).getTax()==false){
                            registerQueue.push(itemQueue.elementAt(i).getPrice());
                        }
                        else{
                            BigDecimal taxed = new BigDecimal(0.00);
                            taxed = itemQueue.elementAt(i).getPrice().multiply(taxPercent);
                            registerQueue.push(taxed);
                        }
                    }
                    
                    for(int i=0; i<registerQueue.size(); i++){
                        total = total.add(registerQueue.elementAt(i));
                    }
                    
                    total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
                    System.out.print("-"+total.toString()+"\n");
                    //dailyCashOut.print("Total: \t"+total.toString()+"\n");
                    
                    current.setText("Current Item: ");
                    subLabel.setText("Subtotal: ");
                    totalLabel.setText("Total: -" + total.toString());
                    content.validate();
                    content.repaint();
                    
                    dailyTotal = dailyTotal.subtract(total);

                    registerQueue.clear();
                    itemQueue.clear();
                    subtotal = BigDecimal.ZERO;
                    total = BigDecimal.ZERO;
                }                   
                if(e.getSource() == otherBtns[4]){ //CLEAR DISPLAY
                    current.setText("Current Item: ");
                    subLabel.setText("Subtotal: ");
                    totalLabel.setText("Total: " );
                }
                if(e.getSource() == otherBtns[5]){ //SBTL BTN
                    for(int i=0; i<itemQueue.size(); i++){
                        if(itemQueue.elementAt(i).getTax()==false){
                            registerQueue.push(itemQueue.elementAt(i).getPrice());
                        }
                        else{
                            BigDecimal taxed = new BigDecimal(0.00);
                            taxed = itemQueue.elementAt(i).getPrice().multiply(taxPercent);
                            registerQueue.push(taxed);
                        }
                    }
                    
                    for(int i=0; i<registerQueue.size(); i++){
                        subtotal = subtotal.add(registerQueue.elementAt(i));
                    }
                    
                    subtotal = subtotal.setScale(2, BigDecimal.ROUND_HALF_UP);
                    
                    registerQueue.clear();
                    subLabel.setText("Subtotal: " + subtotal.toString());
                    content.validate();
                    content.repaint();
                    
                    subtotal = BigDecimal.ZERO;
                }
                if(e.getSource() == otherBtns[6]){ //GIFT CARD BUTTON
                    for(int i=0; i<itemQueue.size(); i++){
                        if(itemQueue.elementAt(i).getTax()==false){
                            registerQueue.push(itemQueue.elementAt(i).getPrice());
                        }
                        else{
                            BigDecimal taxed = new BigDecimal(0.00);
                            taxed = itemQueue.elementAt(i).getPrice().multiply(taxPercent);
                            registerQueue.push(taxed);
                        }
                    }
                    
                    for(int i=0; i<registerQueue.size(); i++){
                        total = total.add(registerQueue.elementAt(i));
                    }
                    
                    total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
                    System.out.print(total.toString()+"\n");
                    //dailyCashOut.print("Total: \t"+total.toString()+"\n");
                    
                    current.setText("Current Item: ");
                    subLabel.setText("Subtotal: ");
                    totalLabel.setText("Total: " + total.toString());
                    content.validate();
                    content.repaint();
                    
                    gcTotal = gcTotal.add(total);
                    dailyTotal = dailyTotal.add(total);

                    registerQueue.clear();
                    itemQueue.clear();
                    subtotal = BigDecimal.ZERO;
                    total = BigDecimal.ZERO;
                }
                if(e.getSource() == otherBtns[7]){ //CREDIT DEBIT BTN   
                    for(int i=0; i<itemQueue.size(); i++){
                        if(itemQueue.elementAt(i).getTax()==false){
                            registerQueue.push(itemQueue.elementAt(i).getPrice());
                        }
                        else{
                            BigDecimal taxed = new BigDecimal(0.00);
                            taxed = itemQueue.elementAt(i).getPrice().multiply(taxPercent);
                            registerQueue.push(taxed);
                        }
                    }
                    
                    for(int i=0; i<registerQueue.size(); i++){
                        total = total.add(registerQueue.elementAt(i));
                    }
                    
                    total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
                    System.out.print(total.toString()+"\n");
                    //dailyCashOut.print("Total: \t"+total.toString()+"\n");
                    
                    current.setText("Current Item: ");
                    subLabel.setText("Subtotal: ");
                    totalLabel.setText("Total: " + total.toString());
                    content.validate();
                    content.repaint();
                    
                    creditTotal = creditTotal.add(total);
                    dailyTotal = dailyTotal.add(total);

                    registerQueue.clear();
                    itemQueue.clear();
                    subtotal = BigDecimal.ZERO;
                    total = BigDecimal.ZERO;
                }  
                if(e.getSource() == otherBtns[8]){ //CASH BTN   
                    for(int i=0; i<itemQueue.size(); i++){
                        if(itemQueue.elementAt(i).getTax()==false){
                            registerQueue.push(itemQueue.elementAt(i).getPrice());
                        }
                        else{
                            BigDecimal taxed = new BigDecimal(0.00);
                            taxed = itemQueue.elementAt(i).getPrice().multiply(taxPercent);
                            registerQueue.push(taxed);
                        }
                    }
                    
                    for(int i=0; i<registerQueue.size(); i++){
                        total = total.add(registerQueue.elementAt(i));
                    }
                    
                    total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
                    System.out.print(total.toString()+"\n");                  
                    
                    current.setText("Current Item: ");
                    subLabel.setText("Subtotal: ");
                    totalLabel.setText("Total: " + total.toString());
                    content.validate();
                    content.repaint();
                    
                    cashTotal = cashTotal.add(total);
                    dailyTotal = dailyTotal.add(total);
                    
                    registerQueue.clear();
                    itemQueue.clear();
                    subtotal = BigDecimal.ZERO;
                    total = BigDecimal.ZERO;      
                }
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
        
        //System.out.print(dailyTotal);
    }

    public static void main(String[] args) {

        CashRegister r;
        r = new CashRegister("Bonomo's Dari Creme");
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        r.makeRegisterLayout((JPanel)r.getContentPane());
        r.pack();
        r.setVisible(true);

    }

}
