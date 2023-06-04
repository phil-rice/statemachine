package one.xingyi.stateprocessor;

import one.xingyi.statemachine.StateFixture;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StateProcessorContextTest implements StateFixture {

    @Test
    void testContextDelegatesFindStateIn() {
        assertEquals("state", context.findStateIn("from", new ToForTest("state", Optional.of("event"))));
    }

    @Test
    void testContextDelegatesFindEventIn() {
        assertEquals(Optional.of("event"), context.findEventIn("from", new ToForTest("state", Optional.of("event"))));
    }
}