package model;

import main.Payable;

public class Client extends Person implements Payable {
    private int memberId;
    private Amount balance;
    private static final int MEMBER_ID = 456;
    private static final double BALANCE = 50.00;

    public Client(String name, int memberId, Amount balance) {
        super(name);
        this.memberId = memberId;
        this.balance = balance != null ? balance : new Amount(0.0); // Inicializar saldo a 0 si es null
    }

    public Amount getBalance() {
        return balance;
    }

    @Override
    public boolean pay(Amount amount) {
        if (balance.isGreaterThan(amount) || balance.equals(amount)) {
            balance = balance.subtract(amount);
            return true;
        } else {
            return false;
        }
    }
}