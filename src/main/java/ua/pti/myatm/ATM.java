package ua.pti.myatm;

public class ATM {
    
    private double moneyInATM;
    private Card cardInATM;
    private boolean cardIsValidForUsing;
    
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM){
         this.moneyInATM = moneyInATM;
         cardInATM = null;
         cardIsValidForUsing = false;
    }

    // Возвращает каоличестов денег в банкомате
    public double getMoneyInATM() {
         return moneyInATM;
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
    public double checkBalance(){
        if(!cardIsValidForUsing){
            System.out.println("NoCardInserted");
            return 0;
        }
        return cardInATM.getAccount().getBalance();
    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount){
        if(!cardIsValidForUsing){
            System.out.println("NoCardInserted");
            return 0;
        }
        else if(checkBalance() > amount){
            System.out.println("NotEnoughMoneyInAccount");
            return 0;
        }
        else if(checkBalance() > moneyInATM){
            System.out.println("NotEnoughMoneyInATM");
            return 0;
        }
        moneyInATM -= amount;
        return checkBalance() - cardInATM.getAccount().withdrow(amount);
    }
}
