/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.exceptions;

/**
 *
 * @author Anton
 */
public class WrongMoneyAmountException extends Exception{
    private final static long serialVersionUID = 12122014L;
    public WrongMoneyAmountException(){
    }
    public WrongMoneyAmountException(String msg){
        super(msg);
    }
}
