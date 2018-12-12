/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashregister;

import java.awt.Color;
import java.math.BigDecimal;

/**
 *
 * @author cbonomo97
 */
public class ItemArray {
    
    Item[] items;
    final int maxItems = 110;
    private final BigDecimal smPrice = new BigDecimal(2.48);
    private final BigDecimal mdPrice = new BigDecimal(2.99);
    private final BigDecimal lgPrice = new BigDecimal(3.45);
    
    public ItemArray(int i){
        this.items = new Item[maxItems];
        
        Item blankItem = new Item("");
        
        if(i==1){
            items[0]=new Item("Sm Ice Cream", smPrice, Color.CYAN, true);
            items[1]=new Item("Sm Yogurt", new BigDecimal(2.89), Color.GREEN, true);
            items[2]=new Item("Reg Sundae", new BigDecimal(3.91), Color.CYAN, true);
            items[3]=new Item("Reg Shake", new BigDecimal(3.31), Color.CYAN, true);
            items[4]=new Item("Sm FlavorBurst", new BigDecimal(2.97), Color.PINK, true);
            items[5]=new Item("Reg Soda", new BigDecimal(1.99), Color.YELLOW, true);
            items[6]=new Item("Ice Cream Sandwich", new BigDecimal(1.60), Color.CYAN, true);
            items[7]=new Item("Hotdog", new BigDecimal(2.85), Color.ORANGE, true);
            items[8]=new Item("Hamburg", new BigDecimal(3.39), Color.ORANGE, true);
            items[9]=new Item("Sm Fry", new BigDecimal(2.49), Color.ORANGE, true);
            items[10]=new Item("Jumbo Hot Ham", new BigDecimal(4.59), Color.ORANGE, true);
            items[11]=new Item("Md Ice Cream", mdPrice, Color.CYAN, true);
            items[12]=new Item("Md Yogurt", new BigDecimal(3.33), Color.GREEN, true);
            items[13]=new Item("Lg Sundae", new BigDecimal(3.91), Color.CYAN, true);
            items[14]=new Item("Thick Shake", new BigDecimal(3.77), Color.CYAN, true);
            items[15]=new Item("Md FlavorBurst", new BigDecimal(3.50), Color.PINK, true);
            items[16]=new Item("Lg Soda", new BigDecimal(2.49), Color.YELLOW, true);
            items[17]=new Item("Dozen Sandwiches", new BigDecimal(15.00), Color.CYAN, false);
            items[18]=new Item("One Item Dog", new BigDecimal(3.35), Color.ORANGE, true);
            items[19]=new Item("Superburg", new BigDecimal(4.55), Color.ORANGE, true);
            items[20]=new Item("Lg Fry", new BigDecimal(2.89), Color.ORANGE, true);
            items[21]=new Item(".40", new BigDecimal(.40), Color.ORANGE, true);
            items[22]=new Item("Lg Ice Cream", lgPrice, Color.CYAN, true);
            items[23]=new Item("Lg Yogurt", new BigDecimal(3.89), Color.GREEN, true);
            items[24]=new Item("Nuts", new BigDecimal(.55), Color.CYAN, true);
            items[25]=new Item("SuperGiant Shake", new BigDecimal(6.15), Color.CYAN, true);
            items[26]=new Item("Lg FlavorBurst", new BigDecimal(3.99), Color.PINK, true);
            items[27]=new Item("Seltzer", new BigDecimal(1.99), Color.YELLOW, true);
            items[28]=new Item("Pint", new BigDecimal(4.50), Color.CYAN, false);
            items[29]=new Item("Two Item Dog", new BigDecimal(3.64), Color.ORANGE, true);
            items[30]=new Item("Cheeseburg", new BigDecimal(3.68), Color.ORANGE, true);
            items[31]=new Item("Sm Cheese Fry", new BigDecimal(3.39), Color.ORANGE, true);
            items[32]=new Item(".90", new BigDecimal(.90), Color.ORANGE, true);
            items[33]=new Item("Waffle Cone", new BigDecimal(3.89), Color.CYAN, true);
            items[34]=new Item("Sm Yog Coated", new BigDecimal(3.38), Color.GREEN, true);
            items[35]=new Item("Extra Topping", new BigDecimal(.59), Color.CYAN, true);
            items[36]=new Item("Reg Malt", new BigDecimal(3.59), Color.CYAN, true);
            items[37]=new Item("FlavorBurst Extra", new BigDecimal(.50), Color.PINK, true);
            items[38]=new Item("Milk", new BigDecimal(1.84), Color.YELLOW, true);
            items[39]=new Item("Quart", new BigDecimal(7.00), Color.CYAN, false);
            items[40]=new Item("Sweet Potato Fry", new BigDecimal(3.59), Color.ORANGE, true);
            items[41]=new Item("Supercheese", new BigDecimal(4.85), Color.ORANGE, true);
            items[42]=new Item("Lg Cheese Fry", new BigDecimal(3.79), Color.ORANGE, true);
            items[43]=new Item(".99", new BigDecimal(.99), Color.ORANGE, true);
            items[44]=new Item("Enrober", new BigDecimal(4.49), Color.CYAN, true);
            items[45]=new Item("Md Yog Coated", new BigDecimal(3.82), Color.GREEN, true);
            items[46]=new Item("Waffle Sundae", new BigDecimal(4.39), Color.CYAN, true);
            items[47]=new Item("Thick Malt", new BigDecimal(4.09), Color.CYAN, true);
            items[48]=new Item("Sm Burst Coated", new BigDecimal(3.46), Color.PINK, true);
            items[49]=new Item("Sm Slush", new BigDecimal(2.05), Color.YELLOW, true);
            items[50]=new Item("Ice Cream Pie", new BigDecimal(13.00), Color.CYAN, false);
            items[51]=new Item("Holy Mole", new BigDecimal(6.09), Color.ORANGE, true);
            items[52]=new Item("Black Bean Burg", new BigDecimal(5.49), Color.ORANGE, true);
            items[53]=new Item("Sm Garlic Fry", new BigDecimal(3.39), Color.ORANGE, true);
            items[54]=new Item("1.59", new BigDecimal(1.59), Color.ORANGE, true);
            items[55]=new Item("Sm Coated", new BigDecimal(2.97), Color.CYAN, true);
            items[56]=new Item("Puppy Pop", new BigDecimal(1.84), Color.CYAN, true);
            items[57]=new Item("Banana Split", new BigDecimal(5.10), Color.CYAN, true);
            items[58]=new Item("SuperGiant Malt", new BigDecimal(2.49), Color.CYAN, true);
            items[59]=new Item("Md Burst Coated", new BigDecimal(3.99), Color.PINK, true);
            items[60]=new Item("Lg Slush", new BigDecimal(2.49), Color.YELLOW, true);
            items[61] = blankItem;
            items[62]=new Item("Mozz Sticks", new BigDecimal(4.99), Color.ORANGE, true);
            items[63]=new Item("BBB", new BigDecimal(6.39), Color.ORANGE, true);
            items[64]=new Item("Lg Garlic", new BigDecimal(3.79), Color.ORANGE, true);
            items[65]=new Item("Bacon", new BigDecimal(1.19), Color.ORANGE, true);
            items[66]=new Item("Md Coated", new BigDecimal(3.48), Color.CYAN, true);
            items[67]=new Item("Frozen Banana", new BigDecimal(1.84), Color.CYAN, true);
            items[68]=new Item("Parfait", new BigDecimal(4.50), Color.CYAN, true);
            items[69]=new Item("Cooler", new BigDecimal(3.31), Color.CYAN, true);
            items[70]=new Item("Sm Float", new BigDecimal(3.22), Color.CYAN, true);
            items[71]=new Item("Bottled Water", new BigDecimal(1.84), Color.YELLOW, true);
            items[72] = blankItem;
            items[73]=new Item("Turkey BBQ", new BigDecimal(3.89), Color.ORANGE, true);
            items[74]=new Item("Grilled Chick", new BigDecimal(5.29), Color.ORANGE, true);
            items[75]=new Item("Onion Rings", new BigDecimal(3.68), Color.ORANGE, true);
            items[76]=new Item("Cole Slaw", new BigDecimal(1.84), Color.ORANGE, true);
            items[77]=new Item("Baby Cone", new BigDecimal(1.38), Color.CYAN, true);
            items[78]=new Item("Sm Flurry", new BigDecimal(3.89), Color.CYAN, true);
            items[79]=new Item("Hotfudge Cake", new BigDecimal(4.50), Color.CYAN, true);
            items[80]=new Item("Smoothie", new BigDecimal(3.69), Color.CYAN, true);
            items[81]=new Item("Lg Float", new BigDecimal(3.68), Color.CYAN, true);
            items[82] = blankItem;
            items[83] = blankItem;
            items[84]=new Item("Pork BBQ", new BigDecimal(3.68), Color.ORANGE, true);
            items[85]=new Item("Grilled Cheese", new BigDecimal(3.39), Color.ORANGE, true);
            items[86]=new Item("Waffle Fry", new BigDecimal(3.29), Color.ORANGE, true);
            items[87]=new Item("Waffle Cheese", new BigDecimal(4.19), Color.ORANGE, true);
            items[88]=new Item("Baby Coated", new BigDecimal(1.47), Color.CYAN, true);
            items[89]=new Item("Lg Flurry", new BigDecimal(5.10), Color.CYAN, true);
            items[90]=new Item("Shortcake", new BigDecimal(4.50), Color.CYAN, true);
            items[91]=new Item("Sm Glacier", new BigDecimal(2.59), Color.CYAN, true);
            items[92]=new Item("Reg Ice Cream Soda", new BigDecimal(3.31), Color.CYAN, true);
            items[93] = blankItem;
            items[94] = blankItem;
            items[95]=new Item("Ham BBQ", new BigDecimal(3.68), Color.ORANGE, true);
            items[96]=new Item("Buff Chick", new BigDecimal(6.19), Color.ORANGE, true);
            items[97]=new Item("Tender Fry", new BigDecimal(8.19), Color.ORANGE, true);
            items[98]=new Item("Mac Salad", new BigDecimal(3.49), Color.ORANGE, true);
            items[99]=new Item("Extra Sprinkle/Dip", new BigDecimal(.49), Color.CYAN, true);
            items[100]=new Item(".59", new BigDecimal(.59), Color.RED, true);
            items[101]=new Item("Oreo Pop", new BigDecimal(2.76), Color.CYAN, true);
            items[102]=new Item("Lg Glacier", new BigDecimal(2.99), Color.CYAN, true);
            items[103]=new Item("Lg Ice Cream Soda", new BigDecimal(3.77), Color.CYAN, true);
            items[104] = blankItem;
            items[105] = blankItem;
            items[106]=new Item("Lettuce & Tomato", new BigDecimal(.79), Color.ORANGE, true);
            items[107]=new Item("Fish Sandwich", new BigDecimal(6.99), Color.ORANGE, true);
            items[108]=new Item("Tender Fry Drink", new BigDecimal(8.99), Color.ORANGE, true);
            items[109]=new Item("Boom Boom", new BigDecimal(6.09), Color.ORANGE, true);      
        }          
    }
    
    public Item[] getItemArray(){
        return items;
    }   
    
}
