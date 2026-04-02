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

class UPIPayment implements PaymentType {

    private static volatile UPIPayment instance = null;

    private UPIPayment(){
        System.out.println("upi payment constructor called");
    }

    public static UPIPayment getInstance(){
        if(instance == null){
            synchronized(UPIPayment.class){
                if(instance == null){
                    instance = new UPIPayment();
                }
            }
        }
        return instance;
    }

    public void pay(){
        System.out.println("pay using upi");
    }
}

class CreditCardPayment implements PaymentType {

    private static volatile CreditCardPayment instance = null;

    private CreditCardPayment(){
        System.out.println("credit card payment constructor called");
    }

    public static CreditCardPayment getInstance(){
        if(instance == null){
            synchronized(CreditCardPayment.class){
                if(instance == null){
                    instance = new CreditCardPayment();
                }
            }
        }
        return instance;
    }

    public void pay(){
        System.out.println("pay using credit card");
    }
}

class DebitCardPayment implements PaymentType {

    private static volatile DebitCardPayment instance = null;

    private DebitCardPayment(){
        System.out.println("debit card payment constructor called");
    }

    public static DebitCardPayment getInstance(){
        if(instance == null){
            synchronized(DebitCardPayment.class){
                if(instance == null){
                    instance = new DebitCardPayment();
                }
            }
        }
        return instance;
    }

    public void pay(){
        System.out.println("pay using debit card");
    }
}

class DirectPayment implements PaymentType {

    private static volatile DirectPayment instance = null;

    private DirectPayment(){
        System.out.println("direct payment constructor called");
    }

    public static DirectPayment getInstance(){
        if(instance == null){
            synchronized(DirectPayment.class){
                if(instance == null){
                    instance = new DirectPayment();
                }
            }
        }
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
            
            UPIPayment obj = UPIPayment.getInstance();

            return obj;

        }

        if(type.equals("credit")){
            
            CreditCardPayment obj = CreditCardPayment.getInstance();

            return obj;

        }
        if(type.equals("debit")){
            
            DebitCardPayment obj = DebitCardPayment.getInstance();

            return obj;

        }
        if(type.equals("direct")){
            
            DirectPayment obj = DirectPayment.getInstance();

            return obj;

        }

        else throw new IllegalArgumentException("Invalid payment type");

    }

}

public class SingletonThreadSafe {

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

        // But this model is threadSafe with double locking and this is Lazy loading,
        // Only creates instance when required,
        // Which is done by ApplicationContext in springBoot

        // volatile is required in Double Checked Locking
        // To prevent instruction reordering and ensure visibility across threads.
        // Without it, a thread may observe a partially constructed object.”

    }
    
}