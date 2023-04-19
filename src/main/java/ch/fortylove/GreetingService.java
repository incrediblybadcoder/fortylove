package ch.fortylove;

public class GreetingService {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else if (name.equals("Jonas")) {
            return "Welcome Jonas, you look great today!!!";
        } else {
            return "Hello " + name;
        }
    }
}
