package one.xingyi.stateprocessor;

import one.xingyi.statemachine.StateFixture;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateProcessorTest implements StateFixture {

    Function<String, ToForTest> fnWithEvent = StateProcessor.add(context, from -> {
        assertEquals("from", from);
        return new ToForTest("doorOpen", Optional.of("doorClosed"));
    });
    Function<String, ToForTest> fnWithOutEvent = StateProcessor.add(context, from -> {
        assertEquals("from", from);
        return new ToForTest("doorOpen", Optional.empty());
    });

    @Test
    void testPerformsBizLogic() {
        assertEquals(new ToForTest("doorOpen", Optional.of("doorClosed")), fnWithEvent.apply("from"));
        assertEquals(new ToForTest("doorOpen", Optional.empty()), fnWithOutEvent.apply("from"));
    }


    @Test
    void testCallsListenerWithAnyStateChanges() {
        Function<String, ToForTest> fn = s -> new ToForTest("doorOpen", Optional.of("doorClosed"));
        AtomicInteger count = new AtomicInteger(0);
        StateProcessorContext<String, ToForTest, String, String> c = StateFixture.makeContext((from, to, oldState, event, newState) -> {
            assertEquals("from", from);
            assertEquals("doorOpen", to.getState());
            assertEquals(Optional.of("doorClosed"), to.getEvent());
            count.incrementAndGet();
        });
        StateProcessor.add(c, fn).apply("from");
        assertEquals(1, count.get());
    }
}
