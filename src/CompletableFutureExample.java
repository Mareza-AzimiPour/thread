import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

    public static void main(String[] args) {
        CompletableFuture<String> futureTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "result work";
        });

        System.out.println("doing another task");

        CompletableFuture<String> processedFuture = futureTask.thenApply(result -> {
            return "process: " + result;
        });

        try {
            String finalResult = processedFuture.get();//blocking
            System.out.println(finalResult);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("error" + e.getMessage());
        }
    }
}
