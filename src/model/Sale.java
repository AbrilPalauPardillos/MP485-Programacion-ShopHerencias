package model;

import java.util.Map;

public class Sale {
    private Client client;
    private Map<String, Integer> products;
    private Amount amount;

    public Sale(Client client, Map<String, Integer> products, Amount amount) {
        this.client = client;
        this.products = products;
        this.amount = amount;
    }

    public Client getClient() {
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
        sb.append("Cliente: ").append(client.getName()).append("\n");
        sb.append("Productos:\n");
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            sb.append("  - ").append(entry.getKey())
              .append(" x").append(entry.getValue()).append("\n");
        }
        sb.append("Total: ").append(amount).append("\n");
        return sb.toString();
    }
}