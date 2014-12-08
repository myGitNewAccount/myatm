/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Anton
 */
public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        double expResult = 1000.0;
        ATM instance = new ATM(1000.0);
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 1000.0);
    }

    @Test
    public void testValidateCard() {
        int pinCode = 1234;
        boolean expResult = false;
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(false);
        
        ATM instance = new ATM(0);
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }
/*
    @Test
    public void testCheckBalance() {
        System.out.println("checkBalance");
        ATM instance = null;
        double expResult = 0.0;
        double result = instance.checkBalance();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetCash() {
        System.out.println("getCash");
        double amount = 0.0;
        ATM instance = null;
        double expResult = 0.0;
        double result = instance.getCash(amount);
        assertEquals(expResult, result, 0.0);
    }
    */
}
