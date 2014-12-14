package ua.pti.myatm;

import static java.lang.Integer.min;
import ua.pti.exceptions.*;

public class ATM {
    private final static String atmWeb = "PrivatBankATMWeb";
    private final int[] denomination500_1 = {500, 200, 100, 50, 20, 10, 5, 2, 1};
    
    private Card cardInATM;
    private boolean cardIsValidForUsing;
    private int moneyInATM;
    private int[] denomination500_1Values;
    
    public final int denominationMoneyCount(Integer denominationValues[]){
        int sum = 0;
        for (int i = 0; i < denominationValues.length; i++) {
            sum += denominationValues[i] * denomination500_1[i];
        }
        return sum;
    }
            
    //Можно задавать количество денег в банкомате 
    ATM(int moneyInATM, Integer... denominationValues) throws WrongMoneyAmountException{
        if(moneyInATM < 0){
            throw new WrongMoneyAmountException("Money amount, you have typed in, is wrong.");
        }
        else if(moneyInATM != denominationMoneyCount(denominationValues)){
            throw new WrongMoneyAmountException();
        }
        else{
            this.moneyInATM = moneyInATM;
            cardInATM = null;
            cardIsValidForUsing = false;
            denomination500_1Values = new int [denominationValues.length];
            for(int i = 0; i < denominationValues.length; i++){
                denomination500_1Values[i] = denominationValues[i];
            }
        }
    }

    // Возвращает каоличестов денег в банкомате
    public int getMoneyInATM() {
         return moneyInATM;
    }
    
    public boolean getCardIsValidForUsing() {
        return cardIsValidForUsing;
    }
    
    public Card getCardInATM(){
        return cardInATM;
    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode){
        cardInATM = card;
        if(cardInATM.checkPin(pinCode)){
            if(!cardInATM.isBlocked()){
                cardIsValidForUsing = true;
                return cardIsValidForUsing;
            }
        }
        return cardIsValidForUsing;
    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance() throws NoCardInsertedException{
        if(!cardIsValidForUsing){
            throw new NoCardInsertedException("No card found in ATM.");
        }
        return cardInATM.getAccount().getBalance();
    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(int amount) throws NoCardInsertedException, NotEnoughMoneyInATMException {
        int amountCopy = amount;
        int min  = 0;
        
        if(!cardIsValidForUsing){
            throw new NoCardInsertedException();
        }
        else if(checkBalance() < amount){
            throw new NotEnoughMoneyInATMException();
        }
        else if(moneyInATM < amount){
            throw new NotEnoughMoneyInATMException("Not enough money in ATM for to do this operation.");
        }
        
        for(int i = 0; i < denomination500_1Values.length; i++){
            min = min(amountCopy / denomination500_1[i], denomination500_1Values[i]);
            amountCopy -= min * denomination500_1[i];
            denomination500_1[i] -= min;
        }
        
        if(amountCopy != 0){
            throw new NotEnoughMoneyInATMException();
        }
        
        moneyInATM -= amount;
        return checkBalance() - cardInATM.getAccount().withdrow(amount);
    }
}
