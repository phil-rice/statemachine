package one.xingyi.statemachine;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.Function;

public interface ResultOrError<T> {
    <T1> ResultOrError<T1> map(Function<T, T1> fn);

    <T1> ResultOrError<T1> flatMap(Function<T, ResultOrError<T1>> fn);

    static <T> Result<T> result(T t) {
        return new Result<>(t);
    }

    static <T> Error<T> error(String message) {
        return new Error<>(message);
    }
}

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
class Result<T> implements ResultOrError<T> {
    final T result;

    public <T1> ResultOrError<T1> map(Function<T, T1> fn) {
        return new Result<>(fn.apply(result));
    }

    public <T1> ResultOrError<T1> flatMap(Function<T, ResultOrError<T1>> fn) {
        return fn.apply(result);
    }
}

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
class Error<T> implements ResultOrError<T> {
    final String message;

    public <T1> ResultOrError<T1> map(Function<T, T1> fn) {
        return new Error<>(message);
    }

    public <T1> ResultOrError<T1> flatMap(Function<T, ResultOrError<T1>> fn) {
        return new Error<>(message);
    }
}