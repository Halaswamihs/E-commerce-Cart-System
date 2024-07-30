import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ECommerceApp {

    // Product class
    static class Product {
        private String name;
        private double price;
        private boolean available;

        public Product(String name, double price, boolean available) {
            this.name = name;
            this.price = price;
            this.available = available;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public boolean isAvailable() {
            return available;
        }

        @Override
        public String toString() {
            return name + " - $" + price + (available ? " (Available)" : " (Out of stock)");
        }
    }

    // Cart class
    static class Cart {
        private Map<Product, Integer> items;

        public Cart() {
            items = new HashMap<>();
        }

        public void addItem(Product product, int quantity) {
            if (!product.isAvailable()) {
                System.out.println(product.getName() + " is out of stock.");
                return;
            }
            items.put(product, items.getOrDefault(product, 0) + quantity);
        }

        public void updateQuantity(Product product, int quantity) {
            if (!items.containsKey(product)) {
                System.out.println(product.getName() + " is not in the cart.");
                return;
            }
            items.put(product, quantity);
        }

        public void removeItem(Product product) {
            items.remove(product);
        }

        public double getTotal() {
            double total = 0;
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                total += entry.getKey().getPrice() * entry.getValue();
            }
            return total;
        }

        public void displayCart() {
            System.out.println("Your Cart:");
            if (items.isEmpty()) {
                System.out.println("Cart is empty.");
                return;
            }
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                System.out.println(entry.getKey().getName() + " - Quantity: " + entry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Store products in a map for easy access
        Map<String, Product> productCatalog = new HashMap<>();

        // Create a shopping cart
        Cart cart = new Cart();

        while (true) {
            System.out.println("\nAvailable commands:");
            System.out.println("1: Add product to catalog");
            System.out.println("2: Display products");
            System.out.println("3: Add product to cart");
            System.out.println("4: View cart");
            System.out.println("5: Update product quantity in cart");
            System.out.println("6: Remove product from cart");
            System.out.println("7: Calculate total bill");
            System.out.println("0: Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Is the product available (true/false): ");
                    boolean available = scanner.nextBoolean();
                    scanner.nextLine();  
                    Product product = new Product(name, price, available);
                    productCatalog.put(name, product);
                    System.out.println("Product added to catalog.");
                    break;

                case 2:
                    System.out.println("\nProduct List:");
                    for (Product productInCatalog : productCatalog.values()) {
                        System.out.println(productInCatalog);
                    }
                    break;

                case 3:
                    System.out.print("Enter product name to add to cart: ");
                    String addProductName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int addQuantity = scanner.nextInt();
                    scanner.nextLine();  
                    Product addProduct = productCatalog.get(addProductName);
                    if (addProduct != null) {
                        cart.addItem(addProduct, addQuantity);
                        System.out.println("Added " + addQuantity + " " + addProduct.getName() + "(s) to cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 4:
                    cart.displayCart();
                    break;

                case 5:
                    System.out.print("Enter product name to update quantity: ");
                    String updateProductName = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int updateQuantity = scanner.nextInt();
                    scanner.nextLine();  
                    Product updateProduct = productCatalog.get(updateProductName);
                    if (updateProduct != null) {
                        cart.updateQuantity(updateProduct, updateQuantity);
                        System.out.println("Updated quantity for " + updateProduct.getName() + " to " + updateQuantity + ".");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 6:
                    System.out.print("Enter product name to remove from cart: ");
                    String removeProductName = scanner.nextLine();
                    Product removeProduct = productCatalog.get(removeProductName);
                    if (removeProduct != null) {
                        cart.removeItem(removeProduct);
                        System.out.println("Removed " + removeProduct.getName() + " from cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 7:
                    System.out.println("Total Bill: $" + cart.getTotal());
                    break;

                case 0:
                    System.out.println("Exiting.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
