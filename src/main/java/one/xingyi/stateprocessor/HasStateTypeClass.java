package one.xingyi.stateprocessor;

public interface HasStateTypeClass<From,To, S> {
    S findStateIn(From from, To to);
}
