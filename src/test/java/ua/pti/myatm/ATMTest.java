/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import org.mockito.InOrder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ua.pti.exceptions.WrongMoneyAmountException;

/**
 *
 * @author Anton
 */
public class ATMTest {

    @Test
    public void testCardIsValidForUsingStart() throws WrongMoneyAmountException {
        ATM instance = new ATM(0);
        boolean result = instance.getCardIsValidForUsing();
        assertFalse(result);
    }
    
    @Test
    public void testCardInATMStart() throws WrongMoneyAmountException {
        Card expResult = null;
        ATM instance = new ATM(0);
        Card result = instance.getCardInATM();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testDenominationRightReceive() throws WrongMoneyAmountException {
        int expResult = 4210;
        Integer denominationArr[] = {6, 6, 0, 0, 0, 1};
        ATM instance = new ATM(4210, 6, 6, 0, 0, 0, 1);
        int result = instance.denominationMoneyCount(denominationArr);
        assertEquals(expResult, result, 0);
    }
    
    @Test
    public void testGetMoneyInATM2Denominates500() throws WrongMoneyAmountException {
        int expResult = 1000;
        ATM instance = new ATM(1000, 2);
        int result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0);
    }
    
    @Test(expected = WrongMoneyAmountException.class)
    public void testGetMoneyInATM9Denominates100() throws WrongMoneyAmountException {
        int expResult = 1000;
        ATM instance = new ATM(1000, 0, 0, 9);
        int result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0);
    }
    
    @Test
    public void testGetMoneyInATM0Denominates() throws WrongMoneyAmountException {
        int expResult = 0;
        ATM instance = new ATM(0);
        int result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0);
    }
    
    @Test(expected = WrongMoneyAmountException.class)
    public void testSetWrongMoneyAmountInAtmAndTryToGet() throws WrongMoneyAmountException{
        ATM instance = new ATM(-10);
    }

    @Test
    public void testValidateCardWrongPin() throws WrongMoneyAmountException {
        int pinCode = 1234;
        boolean expResult = false;
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(false);
        
        ATM instance = new ATM(0);
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateCardOrderMethods() throws WrongMoneyAmountException {
        int pinCode = 1234;
        boolean expResult = false;
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        
        ATM instance = new ATM(0);
        instance.validateCard(card, pinCode);
        
        InOrder order =inOrder(card);
        order.verify(card, atLeastOnce()).checkPin(anyInt());
        order.verify(card).isBlocked();
    }
}
