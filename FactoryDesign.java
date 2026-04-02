class Payment{

    PaymentType paymentType;

    Payment(PaymentType paymentType){

        this.paymentType = paymentType;

    }

    public void pay(){

        paymentType.pay();

    }

}

interface PaymentType{

    void pay();

}

class UPIPayment implements PaymentType{

    public void pay(){

        System.out.println("pay using upi");

    }

}

class CreditCardPayment implements PaymentType{

    public void pay(){

        System.out.println("pay using credit card");

    }

}

class DebitCardPayment implements PaymentType{

    public void pay(){

        System.out.println("pay using debit card");

    }

}

class DirectPayment implements PaymentType{

    public void pay(){

        System.out.println("pay using cash");

    }

}

class PaymentTypeFactory{

    PaymentTypeFactory(){

        System.out.println("payment type factory initialized");

    }

    public PaymentType getPaymentType(String type){

        type = type.toLowerCase();

        if(type.equals("upi")) return new UPIPayment();

        if(type.equals("credit")) return new CreditCardPayment();

        if(type.equals("debit")) return new DebitCardPayment();

        if(type.equals("cash")) return new DirectPayment();

        else throw new IllegalArgumentException("Invalid payment type");

    }

}

public class FactoryDesign {

    public static void main(String[] args){

        // Payment p = new Payment(new UPIPayment());
        // instead of doing this,
        // we create objects of PaymentType using factory which keeps object creation logic aside

        PaymentTypeFactory paymentTypeFactory = new PaymentTypeFactory();

        try{

            PaymentType paymentType = paymentTypeFactory.getPaymentType("upii");

            Payment p = new Payment(paymentType);

            p.pay();

        }

        catch(IllegalArgumentException err){

            System.out.println(err);

        }

        //This keeps object creation logic aside , which improves flexibility

        //This is not only simple facotry design,
        // it is a hybrid desgin with startedy design pattern + factory design pattern
    }
    
}