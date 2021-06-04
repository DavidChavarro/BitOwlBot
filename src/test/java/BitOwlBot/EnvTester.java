package BitOwlBot;

import java.util.Map;

public class EnvTester
{
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }
        System.out.println(System.getenv("TEST_ENV"));
    }
}
