package opg;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class OPG {
    StringBuilder res;

    public void analyze(List<Symbol> input) {
        res = new StringBuilder();
        Deque<Symbol> in = new LinkedList<>(input);
        in.add(new Symbol('#'));

        Deque<Symbol> symbolStack = new LinkedList<>();
        symbolStack.push(new Symbol('#'));
        while (!in.isEmpty()) {
            Symbol right = in.element();
            Symbol left = findLastNonterminal(symbolStack);
            var cmp = Symbol.compare(left, right);
            if (cmp.isEmpty()) {
                res.append("E\n");
                throw new RuntimeException(String.format("no relation between %s and %s", left, right));
            }
            var c = cmp.get();
            if (c == 0) {
                if (left.equals('#')) {
                    return;
                }
                res.append("I)\n");
                res.append("R\n");
                symbolStack.remove(left);
                in.remove();
            } else if (c < 0) {
                symbolStack.push(in.remove());
                res.append("I" + right + "\n");
            } else {
                switch (left.toString()) {
                    case "i":
                        expect(symbolStack, 'i');
                        symbolStack.push(new Symbol('E'));
                        break;
                    case "+":
                        expect(symbolStack, 'E');
                        expect(symbolStack, '+');
                        expect(symbolStack, 'E');
                        symbolStack.push(new Symbol('E'));
                        break;
                    case "*":
                        expect(symbolStack, 'E');
                        expect(symbolStack, '*');
                        expect(symbolStack, 'E');
                        symbolStack.push(new Symbol('E'));
                        break;
                    default:
                        res.append("RE\n");
                        throw new RuntimeException("reduce " + left.toString());
                }
                res.append("R\n");
//                symbolStack.remove(left);
//                while (!symbolStack.isEmpty()) {
//                    var symbol = symbolStack.element();
//                    if (symbol.isNonterminal() && Symbol.compare(symbol, left).get() < -1) break;
//                    symbolStack.pop();
//                }
            }
        }
        throw new RuntimeException("abnormal exit");
    }

    Symbol findLastNonterminal(Deque<Symbol> symbols) {
        for (var symbol : symbols) {
            if (symbol.isNonterminal()) return symbol;
        }
        throw new RuntimeException("without #");
    }

    public String getRes() {
        return res.toString();
    }

    Symbol expect(Deque<Symbol> symbols, char v) {
        if (symbols.isEmpty()) {
            res.append("RE\n");
            throw new RuntimeException(String.format("expect %c, but get nothing", v));
        }
        var s = symbols.remove();
        if (!s.equals(v)) {
            res.append("RE\n");
            throw new RuntimeException(String.format("expect %c, but get %s", v, s));
        }
        return s;
    }
}
