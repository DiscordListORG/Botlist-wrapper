package org.dicordlist.botlistwrapper.util;

/**
 * Class that produces an instance of any kind of class
 * @param <T> The type of the class
 */
@FunctionalInterface
public interface Producer<T> {
    T produce();

}
