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
import ua.pti.exceptions.NoCardInsertedException;
import ua.pti.exceptions.NotEnoughMoneyInATMException;
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
    public void testValidateCardIsBlocked() throws WrongMoneyAmountException {
        int pinCode = 1234;
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(true);
        
        ATM instance = new ATM(0);
        boolean result = instance.validateCard(card, pinCode);
        
        assertFalse(result);
    }
    
    @Test
    public void testValidateCardOrderMethods() throws WrongMoneyAmountException {
        int pinCode = 1234;
        boolean expResult = false;
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(true);
        
        ATM instance = new ATM(0);
        instance.validateCard(card, pinCode);
        
        InOrder order = inOrder(card);
        order.verify(card, atLeastOnce()).checkPin(anyInt());
        order.verify(card).isBlocked();
    }
    
    @Test(expected = NoCardInsertedException.class) 
    public void testCheckBalanceifcardIsNotValidForUsing() throws NoCardInsertedException, WrongMoneyAmountException{
        ATM instance = new ATM(0);
        instance.checkBalance();
    }
    
    @Test 
    public void testCheckBalanceIfcardIsValidForUsing() throws WrongMoneyAmountException, NoCardInsertedException{
        double expResult = 123.67;
        ATM instance = new ATM(0);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(123.67);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        double result = instance.checkBalance();
        
        assertEquals(expResult, result, 0);
    }
    
    @Test 
    public void testCheckPinInCheckBalanceIfcardIsValidForUsing() throws WrongMoneyAmountException, NoCardInsertedException{
        ATM instance = new ATM(0);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(78.45);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        instance.checkBalance();
        
        verify(card, atLeastOnce()).checkPin(anyInt());
    }
    
    @Test
    public void testGetCashWithAllRightValues() throws WrongMoneyAmountException, NoCardInsertedException, NotEnoughMoneyInATMException {
        double expResult = 9000.79;
        ATM instance = new ATM(1209, 2, 0, 1, 1, 2, 1, 0, 3, 3);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(9678.79);
        when(account.withdrow(anyDouble())).thenReturn(678.0);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        double result = instance.getCash(678);
        
        assertEquals(expResult, result, 0);
    }
    
        @Test 
    public void testIsBlockedInCheckBalanceIfcardIsValidForUsing() throws WrongMoneyAmountException, NoCardInsertedException{
        ATM instance = new ATM(0);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(78.45);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        instance.checkBalance();
        
        verify(card, atLeastOnce()).isBlocked();
    }
    
    @Test(expected = NoCardInsertedException.class)
    public void testGetCashCardIsNotValidForUse() throws WrongMoneyAmountException, NoCardInsertedException, NotEnoughMoneyInATMException {
        ATM instance = new ATM(1209, 2, 0, 2, 0, 0, 0, 0, 3, 3);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(9678.67);
        when(account.withdrow(anyDouble())).thenReturn(678.0);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(true);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        instance.getCash(678);
    }
    
    @Test(expected = NotEnoughMoneyInATMException.class)
    public void testGetCashAmountBiggerThanCheckBalance() throws WrongMoneyAmountException, NoCardInsertedException, NotEnoughMoneyInATMException {
        ATM instance = new ATM(1209, 2, 0, 2, 0, 0, 0, 0, 3, 3);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(8.67);
        when(account.withdrow(anyDouble())).thenReturn(678.0);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        instance.getCash(678);
    }
    
    @Test(expected = NotEnoughMoneyInATMException.class)
    public void testGetCashAmountBiggerThanMoneyInATM() throws WrongMoneyAmountException, NoCardInsertedException, NotEnoughMoneyInATMException {
        ATM instance = new ATM(209, 0, 0, 2, 0, 0, 0, 0, 3, 3);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(2434778.67);
        when(account.withdrow(anyDouble())).thenReturn(678.0);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        instance.getCash(678);
    }
    
    @Test(expected = NotEnoughMoneyInATMException.class)
    public void testGetCashNotEnoughAmountOfDenominatesInATM() throws WrongMoneyAmountException, NoCardInsertedException, NotEnoughMoneyInATMException {
        ATM instance = new ATM(250, 0, 0, 2, 1);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(2434778.67);
        when(account.withdrow(anyDouble())).thenReturn(220.0);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        instance.getCash(220);
    }
    
    @Test
    public void testGetCashCheckMoneyInATM() throws WrongMoneyAmountException, NoCardInsertedException, NotEnoughMoneyInATMException {
        int expResult = 200;
        ATM instance = new ATM(250, 0, 0, 2, 1);
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(2434778.67);
        when(account.withdrow(anyDouble())).thenReturn(50.0);
        
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        
        instance.validateCard(card, anyInt());
        instance.getCash(50);
        int result = instance.getMoneyInATM();
        
        assertEquals(expResult, result, 0);
    }
    
    
    @Test(expected = WrongMoneyAmountException.class) 
    public void testWrongDenominatesValuesUnderZero() throws WrongMoneyAmountException {
        ATM instance = new ATM(100, 0, 1, -1);
    }
}
