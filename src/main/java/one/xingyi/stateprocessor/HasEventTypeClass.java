package one.xingyi.stateprocessor;

import java.util.Optional;

public interface HasEventTypeClass<From,To, E> {
    Optional<E> event(From from, To to);
}
