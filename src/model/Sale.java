// Clase Sale
package model;

import java.util.Map;

public class Sale {
    private String client;
    private Map<String, Integer> products;
    private Amount amount; // Cambio al tipo Amount

    public Sale(String client, Map<String, Integer> products, double amount) {
        this.client = client;
        this.products = products;
        this.amount = new Amount(amount); // Uso de Amount
    }

    public String getClient() {
        return client;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(client).append("\n");
        sb.append("Productos:\n");
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            sb.append("  - ").append(entry.getKey())
              .append(" x").append(entry.getValue()).append("\n");
        }
        sb.append("Total: ").append(amount).append("\n"); // Se muestra el monto y la moneda
        return sb.toString();
    }
}