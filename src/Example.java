import java.util.concurrent.*;

public class Example {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<User> userFuture = executor.submit(User::findUser);
        Future<Order> orderFuture = executor.submit(Order::fetchOrder);

        try {
            User user = userFuture.get();
            Order order = orderFuture.get();
            System.out.println("User: " + user.toString());
            System.out.println("Order: " + order.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    static class User {
        static String name;
        static String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public static User findUser() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return new User("Ali", "ali@example.com");
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Email: " + email;
        }

    }

    static class Order {
        static int number;
        static String name;
        static int receipt;

        public Order(int number, String name, int receipt) {
            this.number = number;
            this.name = name;
            this.receipt = receipt;
        }

        public static Order fetchOrder() {
            try {
                Thread.sleep(1500); // تأخیر ۱.۵ ثانیه‌ای
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return new Order(123, "Product A", 1);
        }

        @Override
        public String toString() {
            return "Number:" + number + "name:" + name + "receipt:" + receipt;
        }
    }

}