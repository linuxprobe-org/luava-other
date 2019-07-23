package org.linuxprobe.luava.convert;

/**
 * 转换, 把S转换为T
 *
 * @param <S> 要转换的对象
 * @param <T> 要得到的对象
 */
@FunctionalInterface
public interface Converter<S, T> {
    T convert(S source);
}
