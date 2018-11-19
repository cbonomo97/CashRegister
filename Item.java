/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashregister;


import java.math.BigDecimal;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author cbonomo97
 */
public class Item {

    private String name = "";
    private BigDecimal price;
    private Color buttonColor;
    private boolean tax=true;

    
    private final int maxItems=110;
    private int i=0;
    
    private Item[] itemArray = new Item[maxItems];
    
    public Item() {}
    
    public Item(String name){
        this.name = name;
    }
    
    public Item(String name, BigDecimal price, Color buttonColor, boolean tax){
        this.name = name;
        this.price = price;
        this.buttonColor = buttonColor;
        this.tax = tax;
    }
    
    public String getName(){
        return name;
    }
    
    public BigDecimal getPrice(){
        return price;
    }
    
    public Color getButtonColor(){
        return buttonColor;
    }
    
    public boolean getTax(){
        return tax;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPrice(BigDecimal price){
        this.price = price;
    }
    
    public void setColor(Color itemColor){
        this.buttonColor = buttonColor;
    }
    
    public void setTax(boolean tax){
        this.tax = tax;
    }
    
    public void setItemArray(Item item, int i){
        this.itemArray[i]=item;
        i++;
    }
    
    public Item[] getItemArray(){
        return itemArray;
    }
    
}
