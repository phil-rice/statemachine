package one.xingyi.stateprocessor;

import java.util.Optional;

public interface HasEventTypeClass<From,To, E> {
    Optional<E> findEventIn(From from, To to);
}
