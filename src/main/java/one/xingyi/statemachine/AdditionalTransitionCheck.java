package one.xingyi.statemachine;

import java.util.Optional;

public interface AdditionalTransitionCheck<S, E> {
    Optional<Error<S>> validate(S oldState, E event, S newState);
}
