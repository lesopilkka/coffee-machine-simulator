import java.util.Scanner;

public class CoffeeMachineApp {
    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        Scanner scanner = new Scanner(System.in);

        while (machine.isRunning()) {
            System.out.println("\nWrite action (buy, fill, take, clean, remaining, exit): ");
            String inputAction = scanner.nextLine();
            machine.action(inputAction, scanner);
        }
    }
}

class CoffeeMachine {
    private int water;
    private int milk;
    private int cofBeans;
    private int cups;
    private int money;
    private int coffeeMade;
    private boolean isRunning;

    public CoffeeMachine(int water, int milk, int cofBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.cofBeans = cofBeans;
        this.cups = cups;
        this.money = money;
        this.coffeeMade = 0;
        this.isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void action(String inputAction, Scanner scanner) {
        switch (inputAction) {
            case "buy" -> buy(scanner);
            case "fill" -> fill(scanner);
            case "take" -> take();
            case "clean" -> clean();
            case "remaining" -> printResources();
            case "exit" -> exit();
            default -> System.out.println("Invalid input");
        }
    }

    private void buy(Scanner scanner) {
        if (coffeeMade >= 10) {
            System.out.println("I need cleaning!");
            return;
        }

        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String choice = scanner.nextLine();

        CoffeeType coffee = switch (choice) {
            case "1" -> CoffeeType.espresso();
            case "2" -> CoffeeType.latte();
            case "3" -> CoffeeType.cappuccino();
            case "back" -> null;
            default -> {
                System.out.println("Invalid input");
                yield null;
            }
        };

        if (coffee == null) return;

        if (checkResources(coffee)) {
            makeCoffee(coffee);
            System.out.println("I have enough resources, making you a coffee!");
        }
    }

    public static int deficit = 0;
    public static boolean isResourceEnough;

    private void fill(Scanner scanner) {
        this.water += askResource(scanner, "ml of water");
        scanner.nextLine();
        this.milk += askResource(scanner, "ml of milk");
        scanner.nextLine();
        this.cofBeans += askResource(scanner, "g of coffee");
        scanner.nextLine();
        this.cups += askResource(scanner, "disposable cups");
        scanner.nextLine();
    }
    private int askResource(Scanner scanner, String resourceName) {
        System.out.println("Write how many " + resourceName + " you want to add: ");
        return scanner.nextInt();
    }

    private void take() {
        System.out.println("\nI gave you $" + money);
        money = 0;
    }

    private void clean() {
        System.out.println("I have been cleaned!");
        coffeeMade = 0;
    }

    private void exit() {
        isRunning = false;
    }

    private boolean checkResources(CoffeeType coffee) {
        if (water < coffee.getWater()) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (milk < coffee.getMilk()) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (cofBeans < coffee.getCoffeeBeans()) {
            System.out.println("Sorry, not enough coffee!");
            return false;
        }
        if (cups < coffee.getCups()) {
            System.out.println("Sorry, not enough cups!");
            return false;
        }
        return true;
    }

    private void makeCoffee(CoffeeType coffee) {
        water -= coffee.getWater();
        milk -= coffee.getMilk();
        cofBeans -= coffee.getCoffeeBeans();
        cups -= coffee.getCups();
        money += coffee.getPrice();
        coffeeMade++;
    }

    private void printResources() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(cofBeans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money");
    }
}

class CoffeeType {
    private final int water;
    private final int milk;
    private final int coffeeBeans;
    private final int cups;
    private final int price;

    private CoffeeType(int water, int milk, int coffeeBeans, int cups, int price) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.price = price;
    }

    public static CoffeeType espresso() {
        return new CoffeeType(250, 0, 16, 1, 4);
    }
    public static CoffeeType latte() {
        return new CoffeeType(350, 75, 20, 1, 7);
    }
    public static CoffeeType cappuccino() {
        return new CoffeeType(200, 100, 12, 1, 6);
    }

    public int getWater() {
        return water;
    }
    public int getMilk() {
        return milk;
    }
    public int getCoffeeBeans() {
        return coffeeBeans;
    }
    public int getCups() {
        return cups;
    }
    public int getPrice() {
        return price;
    }
}

