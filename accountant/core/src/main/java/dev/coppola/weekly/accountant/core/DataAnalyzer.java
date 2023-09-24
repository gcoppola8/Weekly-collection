package dev.coppola.weekly.accountant.core;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Gennaro Coppola "coppola612@gmail.com"
 */
public interface DataAnalyzer<E extends Map<LocalDate, Object>> {
    void analyze(E e);
}
