interface Coffee{

    String description();
    double getCost();
        
}

class SimpleCoffee implements Coffee{

    private double cost = 20.0;

    public String description(){

        return "desc: " + "Simple coffee with steamed fluid";
 
    }

    public double getCost(){

        return cost;

    }

}

abstract class CoffeDecorator implements Coffee{

    protected Coffee coffee;

    CoffeDecorator(Coffee coffee){

        this.coffee = coffee;

    }

    public String description(){

        return coffee.description();

    }

    public double getCost(){

        return coffee.getCost();

    }

}

class MilkCoffee extends CoffeDecorator{

    MilkCoffee(Coffee coffee){

        super(coffee);

    }

    public String description(){

        return coffee.description() + ", Milk";
 
    }

    public double getCost(){

        return coffee.getCost() + 10;

    }

}

class ChocolateCofee extends CoffeDecorator{

    ChocolateCofee(Coffee coffee){

        super(coffee);

    }

    public String description(){

        return coffee.description() + ", Chocolate";
 
    }

    public double getCost(){

        return coffee.getCost() + 15;

    }

}

class ColdMilkCoffee extends CoffeDecorator{

    ColdMilkCoffee(Coffee coffee){

        super(coffee);

    }

    public String description(){

        return coffee.description() + ", Cold Milk";
 
    }

    public double getCost(){

        return coffee.getCost() + 15;

    }

}

class WhippedCreamCoffee extends CoffeDecorator{

    WhippedCreamCoffee(Coffee coffee){

        super(coffee);

    }

    public String description(){

        return coffee.description() + ", Whipped Cream";
 
    }

    public double getCost(){

        return coffee.getCost() + 5;

    }


}

public class DecoratorDesign {

    public static void main(String[] args) {
        
        Coffee c = new SimpleCoffee();

        System.out.println(c.description());
        System.out.println(c.getCost());

        Coffee c2 = new MilkCoffee(c);

        System.out.println(c2.description());
        System.out.println(c2.getCost());

        Coffee c3 = new ChocolateCofee(c);

        System.out.println(c3.description());
        System.out.println(c3.getCost());

        Coffee c4 = new ColdMilkCoffee(new MilkCoffee(c));

        System.out.println(c4.description());
        System.out.println(c4.getCost());

        Coffee c5 = new WhippedCreamCoffee(new MilkCoffee(c));

        System.out.println(c5.description());
        System.out.println(c5.getCost());

        Coffee c6 = new WhippedCreamCoffee(new ColdMilkCoffee(new MilkCoffee(c)));

        System.out.println(c6.description());
        System.out.println(c6.getCost());

        Coffee c7 = new WhippedCreamCoffee(new ColdMilkCoffee(new ChocolateCofee(new MilkCoffee(c))));

        System.out.println(c7.description());
        System.out.println(c7.getCost());

    }
}