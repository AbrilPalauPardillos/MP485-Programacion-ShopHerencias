package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.Amount;
import model.Client;
import model.Employee;
import model.Product;
import model.Sale;

public class Shop {

    private Amount cash = new Amount(100.00); // Dinero en caja
    private ArrayList<Product> inventory; // Lista de productos en inventario
    private ArrayList<Sale> sales; // Lista de ventas realizadas
    private final double TAX_RATE = 1.04; // Tasa de impuestos (4%)

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.initSession(); // Iniciar sesi�n antes de mostrar el men�
        shop.showMenu(); // Mostrar el men� principal
    }

    // M�todo para iniciar sesi�n
    public void initSession() {
        Scanner scanner = new Scanner(System.in);
        Employee employee = new Employee("test", "test", 123, 123, "test");

        while (true) {
            System.out.print("Introduce el n�mero de empleado: ");
            int employeeId = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de l�nea
            System.out.print("Introduce la contrase�a: ");
            String password = scanner.nextLine();

            if (employee.login(employeeId, password)) {
                System.out.println("Inicio de sesi�n exitoso.");
                break;
            } else {
                System.out.println("N�mero de empleado o contrase�a incorrectos. Intente de nuevo.");
            }
        }
    }

    // M�todo para mostrar el men� principal
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("--------------------------------------------");
            System.out.println("1. Mostrar dinero en caja");
            System.out.println("2. A�adir producto");
            System.out.println("3. Mostrar inventario");
            System.out.println("4. Realizar venta");
            System.out.println("5. Mostrar ventas");
            System.out.println("6. Mostrar total de ventas");
            System.out.println("9. Eliminar producto");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opci�n: ");
            choice = scanner.nextInt();
            System.out.println("--------------------------------------------");
            scanner.nextLine(); // Consumir el salto de l�nea

            switch (choice) {
                case 1:
                    showCash();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    showInventory();
                    break;
                case 4:
                    sale();
                    break;
                case 5:
                    showSales();
                    break;
                case 6:
                    showTotalSales();
                    break;
                case 9:
                    deleteProduct();
                    break;
                case 10:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opci�n no v�lida. Intente de nuevo.");
                    break;
            }
        } while (choice != 10);
    }

    // M�todo para mostrar el dinero en caja
    public void showCash() {
        System.out.println("Dinero actual en caja: " + cash);
    }

    // M�todo para a�adir un producto al inventario
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del producto (escriba 'c' para cancelar): ");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("c")) {
            System.out.println("Operaci�n cancelada.");
            return;
        }

        if (productExists(name)) {
            System.out.println("El producto ya existe en el inventario.");
            return;
        }

        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();

        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        // Crear y agregar el producto al inventario
        inventory.add(new Product(name, wholesalerPrice, true, stock));
        System.out.println("Producto a�adido: " + name);
    }

    // M�todo para verificar si un producto ya existe en el inventario
    private boolean productExists(String name) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // M�todo para mostrar el inventario
    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("El inventario est� vac�o.");
            return;
        }

        System.out.println("Contenido de la tienda:");
        for (Product product : inventory) {
            System.out.println("-----------------------------------");
            System.out.println("Producto: " + product.getName() + ", Precio p�blico: " + product.getPublicPrice() + ", Stock: " + product.getStock());
        }
    }

    // M�todo para realizar una venta
    public void sale() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("VENTA: escribir nombre cliente: ");
        String clientName = scanner.nextLine();

        // Crear cliente con saldo inicial de 50.00?
        Client client = new Client(clientName, 456, new Amount(50.00));
        Map<String, Integer> productsSold = new HashMap<>();
        Amount totalAmount = new Amount(0.0); // Inicializar totalAmount a 0.0

        while (true) {
            System.out.print("Introduce el nombre del producto (escribir 0 para terminar): ");
            String name = scanner.nextLine();
            if (name.equals("0")) {
                break;
            }

            Product product = findProduct(name);
            if (product != null && product.isAvailable()) {
                System.out.print("Cantidad a comprar: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de l�nea

                if (quantity > product.getStock()) {
                    System.out.println("Stock insuficiente. Disponible: " + product.getStock());
                } else {
                    // Calcular el subtotal usando el m�todo multiply de Amount
                    Amount subtotal = product.getPublicPrice().multiply(quantity);
                    totalAmount = totalAmount.add(subtotal); // Sumar al total usando el m�todo add de Amount
                    product.setStock(product.getStock() - quantity);
                    if (product.getStock() == 0) {
                        product.setAvailable(false);
                    }
                    productsSold.put(name, productsSold.getOrDefault(name, 0) + quantity);
                    System.out.println("Producto a�adido.");
                }
            } else {
                System.out.println("Producto no encontrado o sin stock.");
            }
        }

        if (totalAmount.isGreaterThan(new Amount(0.0))) {
            // Aplicar impuestos usando el m�todo multiply de Amount
            totalAmount = totalAmount.multiply(TAX_RATE);

            // Verificar si el cliente puede pagar
            if (client.pay(totalAmount)) {
                // A�adir el total a la caja usando el m�todo add de Amount
                cash = cash.add(totalAmount);
                sales.add(new Sale(client, productsSold, totalAmount));
                System.out.println("Venta realizada. Total: " + totalAmount);
            } else {
                // Calcular la cantidad que el cliente debe usando el m�todo subtract de Amount
                Amount cantidadDebe = totalAmount.subtract(client.getBalance());
                System.out.println("Venta realizada. El cliente debe: " + cantidadDebe);
            }
        } else {
            System.out.println("No se realiz� ninguna venta.");
        }
    }

    // M�todo para mostrar las ventas realizadas
    public void showSales() {
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            System.out.println("------------------------------");
            System.out.println(sale);
        }
    }

    // M�todo para mostrar el total de ventas
    public void showTotalSales() {
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        Amount totalAmount = new Amount(0.0);
        for (Sale sale : sales) {
            totalAmount = totalAmount.add(sale.getAmount());
        }

        System.out.println("Total de dinero conseguido: " + totalAmount);
    }

    // M�todo para eliminar un producto del inventario
    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del producto (escriba 'c' para cancelar): ");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("c")) {
            System.out.println("Operaci�n cancelada.");
            return;
        }

        Product productToRemove = null;
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                productToRemove = product;
                break;
            }
        }

        if (productToRemove != null) {
            inventory.remove(productToRemove);
            System.out.println("Se ha eliminado el producto.");
        } else {
            System.out.println("El producto no existe en el inventario.");
        }
    }

    // M�todo para buscar un producto por nombre
    private Product findProduct(String name) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}