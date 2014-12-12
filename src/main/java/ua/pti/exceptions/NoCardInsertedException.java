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
public class NoCardInsertedException extends Exception{
    private final static long serialVersionUID = 13122014L;
    public NoCardInsertedException(){
    }
    public NoCardInsertedException(String msg){
        super(msg);
    }
}
