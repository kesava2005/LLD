import java.util.*;

class MenuItems{

    private int menu_id;
    private String name;
    private double price;
    public MenuItems(int menu_id, String name, double price) {
        this.menu_id = menu_id;
        this.name = name;
        this.price = price;
    }
    public int getMenu_id() {
        return menu_id;
    }
    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

}

class Restraunt{

    private int restraunt_id;
    private List<MenuItems> menuItems;
    private String name;
    public int getRestraunt_id() {
        return restraunt_id;
    }
    public void setRestraunt_id(int restraunt_id) {
        this.restraunt_id = restraunt_id;
    }
    public List<MenuItems> getMenuItems() {
        return menuItems;
    }
    public void setMenuItems(List<MenuItems> menuItems) {
        this.menuItems = menuItems;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}

class RestrauntManager{

    private Map<Integer,Restraunt> restraunts = new HashMap<>();

    private static volatile RestrauntManager instance;

    private RestrauntManager(){

        System.out.println("restraunt manager constructor called");

    }

    public static RestrauntManager getInstance(){

        if(instance == null){
            synchronized(RestrauntManager.class){
                if(instance == null){
                    instance = new RestrauntManager();
                }
            }
        }
        return instance;

    }

    public void addRestraunt(int restraunt_id, Restraunt r){

        restraunts.put(restraunt_id, r);

    }

    public void updateRestraunt(int restraunt_id, Restraunt r){

        restraunts.put(restraunt_id, r);

    }

    public void removeRestraunt(int restraunt_id){

        if(!restraunts.containsKey(restraunt_id)) return;

        restraunts.remove(restraunt_id);

    }

}

class User{

    private int user_id;
    private String user_name;
    private String address;

    private Cart cart;

    private Order order;

    private Map<Integer, Order> orders = new HashMap<>();
    
    public int getUser_id() {
        return user_id;
    }
    public User(int user_id, String user_name, String address) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.address = address;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void placeImmediateOrder(int orderId, PaymentType paymentType){

        order = OrderFactory.createOrder("Immediate", orderId, this, paymentType, null);

        placeOrder(orderId);

    }

    public void placeScheduledOrder(int orderId, PaymentType paymentType, Date ScheduledTime){

        if(ScheduledTime == null){

            throw new IllegalArgumentException("Time is needed to schedule");

        }

        order = OrderFactory.createOrder("scheduled", orderId, this, paymentType, ScheduledTime);

        placeOrder(orderId);

    }

    public void placeOrder(int orderId){

        if(cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        // Convert Cart → OrderItems
        order.setItems(cart.getItems());

        order.process();
        orders.put(orderId, order);
        
        cart.clear();

    }

    public void cancelOrder(int orderId){

        if(!orders.containsKey(orderId)){

            System.out.println("no such order exists");
            return;

        }

        orders.get(orderId).cancelOrder();

    }

}

class Cart{

    private Map<Integer, CartItem> cart = new HashMap<>();

    public boolean isEmpty(){

        return cart.size() == 0;

    }

    public void clear(){

        cart.clear();

    }

    public void addItem(MenuItems menuItem){

        int id = menuItem.getMenu_id();
        String name = menuItem.getName();
        double price = menuItem.getPrice();

        if(!cart.containsKey(id)) cart.put(id, new CartItem(id, name, price, 1));

        else cart.get(id).incrementQuantity(1);

    }

    public void addItem(MenuItems menuItem, int freq){

        int id = menuItem.getMenu_id();
        String name = menuItem.getName();
        double price = menuItem.getPrice();

        if(!cart.containsKey(id)) cart.put(id, new CartItem(id, name, price, 1));

        else cart.get(id).incrementQuantity(freq);

    }

    public void removeItem(MenuItems menuItem){

        if(!cart.containsKey(menuItem.getMenu_id())) return;

        cart.get(menuItem.getMenu_id()).decrementQuantity(1);

        if(cart.get(menuItem.getMenu_id()).getQuantity() == 0) cart.remove(menuItem.getMenu_id());

    }

    public void removeItem(MenuItems menuItem, int freq){

        if(!cart.containsKey(menuItem.getMenu_id())) return;

        cart.get(menuItem.getMenu_id()).decrementQuantity(freq);

        if(cart.get(menuItem.getMenu_id()).getQuantity() == 0) cart.remove(menuItem.getMenu_id());

    }

    public double getBill(){

        double total_price = 0.0;

        for(int cartItemId : cart.keySet()){

            total_price += cart.get(cartItemId).getPrice() * cart.get(cartItemId).getQuantity();

        }

        return total_price;
            
    }

    public void generateBill(){

        for(int cartItemId : cart.keySet()){

            cart.get(cartItemId).printCartItem();

            System.out.println("---------------------------------------------");

        }

    }

    public Collection<CartItem> getItems() {
        return cart.values();
    }


}

class CartItem{

    private int id;
    private String name;
    private double price;
    private int quantity;

    public int getId() {
        return id;
    }
    public CartItem(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void printCartItem(){

        System.out.println("Item - id: " + id);
        System.out.println("Item - name: " + name);
        System.out.println("Item - price: " + price);
        System.out.println("Item - quantity: " + quantity);
        System.out.println("total: " + price * quantity);

    }

    public void incrementQuantity(int freq){

        this.quantity += freq;

    }

    public void decrementQuantity(int freq){

        this.quantity -= freq;

        quantity = Math.max(0, quantity);

    }

}
abstract class Order{

    protected int orderId;
    protected User user;
    protected PaymentType paymentType;

    protected List<OrderItem> items = new ArrayList<>();

    protected OrderStatus orderstatus = null;


    public void setItems(Collection<CartItem> cartItems){
        items = new ArrayList<>();

        for(CartItem c : cartItems){
            items.add(new OrderItem(
                c.getId(),
                c.getName(),
                c.getPrice(),
                c.getQuantity()
            ));
        }
    }

    public OrderStatus getStatus(){

        return this.orderstatus;

    }

    public void cancelOrder(){

        if(orderstatus == OrderStatus.PLACED){
            System.out.println("Order already placed, but cancelling now...");
            orderstatus = OrderStatus.CANCELLED;
        }
        else if(orderstatus == OrderStatus.CREATED){
            System.out.println("Cancelling before placing...");
            orderstatus = OrderStatus.CANCELLED;
        }
        else if(orderstatus == OrderStatus.CANCELLED){
            System.out.println("Order already cancelled");
        }
        else{
            System.out.println("Order cannot be cancelled");
        }

    }

    public abstract boolean process();

    public int getOrderId(){

        return this.orderId;

    }

    public void setPaymentType(PaymentType paymentType){

        this.paymentType = paymentType;

    }

}

class ImmediateOrder extends Order{

    ImmediateOrder(int orderId, User user, PaymentType paymentType){
        this.orderId = orderId;
        this.user = user;
        this.paymentType = paymentType;
        this.orderstatus = OrderStatus.CREATED;
    }

    public boolean process(){

        System.out.println("Order is being placed...");
        
        if(orderstatus == OrderStatus.CANCELLED){
            System.out.println("Order cancelled cant place now..");
            return false;
        }

        orderstatus = OrderStatus.PLACED;

        double bill = 0.0;

        for(OrderItem item: items){

            bill += item.getPrice() * item.getQuantity();

        }

        paymentType.pay(bill);

        System.out.println("Order placed successfully");
        return true;

    }

}

class ScheduledOrder extends Order{

    private Date scheduledTime;

    ScheduledOrder(int orderId, User user, PaymentType paymentType,Date scheduledTime){
        this.orderId = orderId;
        this.user = user;
        this.paymentType = paymentType;
        this.scheduledTime = scheduledTime;
        this.orderstatus = OrderStatus.CREATED;
    }

    public boolean process(){

        System.out.println("Order is being placed...");
        
        if(orderstatus == OrderStatus.CANCELLED){
            System.out.println("Order cancelled cant place now..");
            return false;
        }

        orderstatus = OrderStatus.SCHEDULED;

        double bill = 0.0;

        for(OrderItem item: items){

            bill += item.getPrice() * item.getQuantity();

        }

        paymentType.pay(bill);

        System.out.println("Order scheduled at : "  + scheduledTime.toString());
        return true;

    }

}

class OrderFactory{

    public static Order createOrder(String type, int orderId, User user, PaymentType paymentType, Date scheduledTime){

        type = type.toLowerCase();

        if(type.equals("immediate")){

            return new ImmediateOrder(orderId, user, paymentType);

        }

        if(type.equals("scheduled")){

            if(scheduledTime == null){

                throw new IllegalArgumentException("Scheduled time required");

            }

            return new ScheduledOrder(orderId, user, paymentType, scheduledTime);

        }

        throw new IllegalArgumentException("Invalid order type");

    }

}

enum OrderStatus {
    CREATED, PLACED, CANCELLED, DELIVERED, SCHEDULED
}

class OrderItem{

    private int menuId;
    private String name;
    private double price;
    private int quantity;
    
    public OrderItem(int menuId, String name, double price, int quantity) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void setMenuId(int id){

        menuId = id;

    }

    public int getMenuId(){

        return menuId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    

}

class OrderManager{

    private static volatile OrderManager instance;
    private Map<Integer,Order> orders = new HashMap<>();

    private OrderManager(){

        System.out.println("order manager constructor called");

    }

    public static OrderManager getInstance(){

        if(instance == null){
            synchronized(OrderManager.class){
                if(instance == null){
                    instance = new OrderManager();
                }
            }
        }
        return instance;

    }

    public void addOrder(Order order){

        orders.put(order.getOrderId(), order);

    }

    public void removeOrder(Order order){

        if(!orders.containsKey(order.getOrderId())) return;

        orders.remove(order.getOrderId());

    }

}

class PaymentType{

    Payment payment;

    PaymentType(Payment payment){

        this.payment = payment;

    }

    public void pay(double amount){

        payment.pay(amount);

    }

}

interface Payment{

    void pay(double amount);

}

class NetBankingPayment implements Payment{

    public void pay(double amount){

        System.out.println("paying using netBanking... " + "Amount: " + amount);

    }
    
}

class UPIPayment implements Payment{

    public void pay(double amount){

        System.out.println("paying using UPI... " + "Amount: " + amount);

    }
    
}

class DirectPayment implements Payment{

    public void pay(double amount){

        System.out.println("paying using cash... " + "Amount: " + amount);

    }
    
}

class PaymentFactory{

    public static Payment createPayment(String type){

        type = type.toLowerCase();

        if(type.equals("upi")){

            return new UPIPayment();

        }

        if(type.equals("netbanking")){

            return new NetBankingPayment();

        }

        if(type.equals("direct") || type.equals("cash")){

            return new DirectPayment();

        }

        throw new IllegalArgumentException("not a valid type!");

    }

}

public class SimpleTomato{

    public static void main(String[] args) {

        // ----------- MENU ITEMS -----------
        MenuItems pizza = new MenuItems(1, "Pizza", 200);
        MenuItems burger = new MenuItems(2, "Burger", 120);
        MenuItems coke = new MenuItems(3, "Coke", 50);

        // ----------- RESTAURANT -----------
        Restraunt r = new Restraunt();
        r.setRestraunt_id(101);
        r.setName("Dominos");

        List<MenuItems> menu = new ArrayList<>();
        menu.add(pizza);
        menu.add(burger);
        menu.add(coke);

        r.setMenuItems(menu);

        RestrauntManager.getInstance().addRestraunt(101, r);

        // ----------- USER -----------
        User user = new User(1, "Kesava", "Hyderabad");

        Cart cart = new Cart();
        user.setCart(cart);

        // ----------- ADD ITEMS TO CART -----------
        cart.addItem(pizza);
        cart.addItem(burger);
        cart.addItem(coke);

        System.out.println("\n--- CART BILL ---");
        cart.generateBill();
        System.out.println("Total Bill: " + cart.getBill());

        // ----------- IMMEDIATE ORDER (UPI) -----------
        System.out.println("\n--- IMMEDIATE ORDER (UPI) ---");

        Payment upi = PaymentFactory.createPayment("upi");
        PaymentType upiType = new PaymentType(upi);

        user.placeImmediateOrder(1, upiType);

        // ----------- CANCEL ORDER -----------
        System.out.println("\n--- CANCEL ORDER ---");
        user.cancelOrder(1);

        // ----------- ADD ITEMS AGAIN -----------
        cart.addItem(pizza);
        cart.addItem(pizza);

        // ----------- SCHEDULED ORDER -----------
        System.out.println("\n--- SCHEDULED ORDER (NETBANKING) ---");

        Payment net = PaymentFactory.createPayment("netbanking");
        PaymentType netType = new PaymentType(net);

        Date futureTime = new Date(System.currentTimeMillis() + 60000); // +1 min

        user.placeScheduledOrder(2, netType, futureTime);

        // ----------- CASH ORDER -----------
        System.out.println("\n--- IMMEDIATE ORDER (CASH) ---");

        cart.addItem(burger);

        Payment cash = PaymentFactory.createPayment("cash");
        PaymentType cashType = new PaymentType(cash);

        user.placeImmediateOrder(3, cashType);

        System.out.println("\n--- TEST COMPLETED ---");

    }
}