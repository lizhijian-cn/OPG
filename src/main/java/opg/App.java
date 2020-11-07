package opg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        var inputFileName = args[0];
        InputStream input;
        if (inputFileName.equals("-")) {
            input = System.in;
        } else {
            try {
                input = new FileInputStream(inputFileName);
            } catch (FileNotFoundException e) {
                System.err.println("No such file: " + inputFileName);
                return;
            }
        }
        var scanner = new Scanner(input);
        var opg = new OPG();
        var sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.next());
        }
        // a simple tokenizer
        List<Symbol> symbols = sb.toString().chars()
                .mapToObj(i -> new Symbol((char) i))
                .filter(i -> i.isNonterminal())
                .collect(Collectors.toList());
        System.err.println(symbols);
        try {
            opg.analyze(symbols);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
        System.out.print(opg.getRes());
    }
}
