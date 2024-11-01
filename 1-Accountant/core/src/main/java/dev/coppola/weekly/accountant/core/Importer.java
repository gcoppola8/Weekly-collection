package dev.coppola.weekly.accountant.core;

import java.net.URISyntaxException;

public interface Importer<E> {

    E importData() throws Exception;
}
