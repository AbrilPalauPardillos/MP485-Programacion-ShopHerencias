package model;

public class Amount {
    private double value;
    private String currency = "$"; // Moneda fija

    public Amount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    // M�todo para sumar dos Amount
    public Amount add(Amount other) {
        return new Amount(this.value + other.value);
    }

    // M�todo para restar dos Amount
    public Amount subtract(Amount other) {
        return new Amount(this.value - other.value);
    }

    // M�todo para multiplicar un Amount por un factor
    public Amount multiply(double factor) {
        return new Amount(this.value * factor);
    }

    // M�todo para comparar si un Amount es mayor que otro
    public boolean isGreaterThan(Amount other) {
        return this.value > other.value;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, currency);
    }
}