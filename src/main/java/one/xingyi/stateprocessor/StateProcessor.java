package one.xingyi.stateprocessor;

import one.xingyi.statemachine.ResultOrError;

import java.util.Optional;
import java.util.function.Function;

public interface StateProcessor {
    static <From, To, S, E> Function<From, To> add(StateProcessorContext<From, To, S, E> context, Function<From, To> bizLogic) {
        return from -> {
            To to = bizLogic.apply(from);
            Optional<E> event = context.findEventIn(from, to);
            event.ifPresent(e -> {
                S oldState = context.findStateIn(from, to);
                ResultOrError<S> newState = context.stateMachine.transition(oldState, e);
                context.stateChanged.stateChanged(from, to, oldState, e, newState);
            });
            return to;
        };
    }

}

