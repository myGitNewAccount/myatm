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
public class NotEnoughMoneyInATMException extends Exception{
    private final static long serialVersionUID = 14122014L;
    public NotEnoughMoneyInATMException(){
    }
    public NotEnoughMoneyInATMException(String msg){
        super(msg);
    }
}
