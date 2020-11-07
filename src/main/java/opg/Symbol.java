package opg;

import java.util.*;

public class Symbol {
    char value;

    static int nonterminalCount;
    static Character[] nonterminals;
    static Map<Character, Integer> indexes;
    static Integer[][] relationMatrix;

    static {
        nonterminals = new Character[]{'+', '*', 'i', '(', ')', '#'};
        indexes = new Hashtable<>();
        for (nonterminalCount = 0; nonterminalCount < nonterminals.length; nonterminalCount++) {
            indexes.put(nonterminals[nonterminalCount], nonterminalCount);
        }
        relationMatrix = new Integer[][]{
                {1, -1, -1, -1, 1, 1},
                {1, 1, -1, -1, 1, 1},
                {1, 1, null, null, 1, 1},
                {-1, -1, -1, -1, 0, null},
                {1, 1, null, null, 1, 1},
                {-1, -1, -1, -1, null, 0}
        };
    }

    public Symbol(char v) {
        this.value = v;
    }

    public boolean isNonterminal() {
        return Arrays.asList(nonterminals).contains(value);
    }

    Integer index() {
        return indexes.get(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Character) return o.equals(value);
        if (!(o instanceof Symbol)) return false;
        Symbol symbol = (Symbol) o;
        return value == symbol.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static Optional<Integer> compare(Symbol s1, Symbol s2) {
        var i1 = s1.index();
        var i2 = s2.index();
        if (i1 == null || i2 == null) return Optional.empty();
        return Optional.ofNullable(relationMatrix[i1][i2]);
    }
}
