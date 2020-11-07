package opg;

import org.junit.Assert.*;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class SymbolTest {
    @Test
    public void testCompare() {
        var c1 = Symbol.compare(new Symbol('+'), new Symbol('*'));
        assertTrue(c1.get() < 0);

        var c2 = Symbol.compare(new Symbol('('), new Symbol(')'));
        assertTrue(c2.get() == 0);

        var c3 = Symbol.compare(new Symbol('i'), new Symbol('#'));
        assertTrue(c3.get() > 0);

        var c4 = Symbol.compare(new Symbol('i'), new Symbol('('));
        assertEquals(c4, Optional.empty());
    }

    @Test
    public void testEquals() {
        assertEquals(new Symbol('+'), new Symbol('+'));
        assertEquals(new Symbol('*'), '*');
        assertFalse(new Symbol('(').equals(')'));
    }

    @Test
    public void testNonterminal() {
        assertTrue(new Symbol('+').isNonterminal());
        assertFalse(new Symbol('T').isNonterminal());
    }
}
