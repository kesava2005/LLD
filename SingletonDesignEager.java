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

//Singleton class

class UPIPayment implements PaymentType{

    static UPIPayment instance = new UPIPayment();

    private UPIPayment(){

        System.out.println("upi payment constructor called");

    }

    public static UPIPayment getUPIPaymentInstance(){

        return instance;

    }

    public void pay(){

        System.out.println("pay using upi");

    }

}

//singleton class

class CreditCardPayment implements PaymentType{

    static CreditCardPayment instance = new CreditCardPayment();

    private CreditCardPayment(){

        System.out.println("credit card payment constructor called");

    }

    public static CreditCardPayment getCreditCardPaymentInstance(){

        return instance;

    }

    public void pay(){

        System.out.println("pay using credit card");

    }

}

//singleton class

class DebitCardPayment implements PaymentType{

    static DebitCardPayment instance = new DebitCardPayment();

    private DebitCardPayment(){

        System.out.println("debit card payment constructor called");

    }

    public static DebitCardPayment getDebitCardPaymentInstance(){

        return instance;

    }

    public void pay(){

        System.out.println("pay using debit card");

    }

}

//singelton class

class DirectPayment implements PaymentType{

    static DirectPayment instance = new DirectPayment();

    private DirectPayment(){

        System.out.println("direct payment constructor called");

    }

    public static DirectPayment getUDirectPaymentInstance(){

        return instance;

    }

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

        if(type.equals("upi")){
            
            UPIPayment obj = UPIPayment.getUPIPaymentInstance();

            return obj;

        }

        if(type.equals("credit")){
            
            CreditCardPayment obj = CreditCardPayment.getCreditCardPaymentInstance();

            return obj;

        }
        if(type.equals("debit")){
            
            DebitCardPayment obj = DebitCardPayment.getDebitCardPaymentInstance();

            return obj;

        }
        if(type.equals("direct")){
            
            DirectPayment obj = DirectPayment.getUDirectPaymentInstance();

            return obj;

        }

        else throw new IllegalArgumentException("Invalid payment type");

    }

}

public class SingletonDesignEager {

    public static void main(String[] args){

        PaymentTypeFactory paymentTypeFactory = new PaymentTypeFactory();

        try{

            PaymentType paymentType = paymentTypeFactory.getPaymentType("upi");

            Payment p = new Payment(paymentType);

            p.pay();

        }

        catch(IllegalArgumentException err){

            System.out.println(err);

        }

        // Instead of creating obj evertime we use only one object per class whenever required,
        // paymentType like upi single obj can be reused multiple types to avoid over heap space
        // So we use only single instance of upi for all upi payments,
        // This is the typical definition of singleton design

        // But this model is not threadSafe and this is Eager loading,
        // Creates instance eagerly even we didnt needed,
        // Which is done by BeanFactory in springBoot

    }
    
}