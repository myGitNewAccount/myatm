package ua.pti.myatm;

public class MyATM {

    public static void main(String[] args) {
        int moneyInATM = 1000;
        ATM atm;
        try {
            atm = new ATM(moneyInATM, 2);
            /*Card card = null;
            atm.validateCard(card, 1234);
            atm.checkBalance();
            atm.getCash(999.99);*/
            System.out.println(atm.getMoneyInATM());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
