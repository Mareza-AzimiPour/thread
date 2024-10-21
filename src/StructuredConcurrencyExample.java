import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;

public class StructuredConcurrencyExample
{

    // Simulating an API call to fetch data
    static String fetchFromApiA() throws InterruptedException {
        Thread.sleep(1000); // Simulate delay
        return "Data from API A";
    }

    // Simulating another API call to fetch data
    static String fetchFromApiB() throws InterruptedException {
        Thread.sleep(1500); // Simulate delay
        return "Data from API B";
    }

    public static void main(String[] args) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            // Forking concurrent tasks JDK20/JDK19
            /*Future<String> futureA = scope.fork(StructuredConcurrencyExample::fetchFromApiA);
            Future<String> futureB = scope.fork(StructuredConcurrencyExample::fetchFromApiB);*/

            StructuredTaskScope.Subtask<String> subtaskA = scope.fork(StructuredConcurrencyExample::fetchFromApiA);
            StructuredTaskScope.Subtask<String> subtaskB = scope.fork(StructuredConcurrencyExample::fetchFromApiB);

            // Joining the tasks and handling exceptions
            scope.join();
            scope.throwIfFailed();

           //if use future better use resultNow for non blocking and doing job well
            /*String resultA = futureA.resultNow();
            String resultB = futureB.resultNow();*/


            // Collecting the results and block and close//jdk 23 better look
            String resultA = subtaskA.get();
            String resultB = subtaskB.get();

            System.out.println("Result A: " + resultA);
            System.out.println("Result B: " + resultB);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
