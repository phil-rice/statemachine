package one.xingyi.stateprocessor;

import one.xingyi.statemachine.ResultOrError;

public interface StateChanged<From, To, S, E> {
    void stateChanged(From from, To to, S oldState, E event, ResultOrError<S> state);
}
