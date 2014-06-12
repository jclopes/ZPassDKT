package zpassdkt;

import java.io.Console;

import static zpassdkt.Enconding.genPass;

/**
 * Created by joao on 2014/06/10.
 */
public class Cli {
    public static void main(String[] args) {
        Console cons;
        cons = System.console();
        String secret = String.valueOf(cons.readPassword("Secret :"));
        String key = String.valueOf(cons.readPassword("Keyword :"));
        String uri = cons.readLine("What URI? :");
        String pass = genPass(secret, uri, key);

        System.out.println(pass);
    }
}