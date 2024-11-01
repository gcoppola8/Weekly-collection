package dev.coppola.weekly.accountant.cli;

import dev.coppola.weekly.accountant.core.Importer;
import dev.coppola.weekly.accountant.core.Product;
import dev.coppola.weekly.accountant.core.ProductRecord;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Gennaro Coppola "coppola612@gmail.com"
 */
public class DefaultProductImporter implements Importer<Set<ProductRecord>> {

    @Override
    public Set<ProductRecord> importData() throws Exception {

        Set<ProductRecord> set = new HashSet<>();

        //Read URI/Path of a file wherever it is.
        //Add here a new file if you need.
        var basicURI = getClass().getClassLoader().getResource("data/Basic.txt").toURI();
        var deluxURI = getClass().getClassLoader().getResource("data/Delux.txt").toURI();

        //Convert the raw data in the file in a structured container.
        ProductRecord basicCupcakeRecord =
                getProductRecord(basicURI, "Basic Cupcake", Money.of(CurrencyUnit.USD, 5));
        ProductRecord deluxCupcakeRecord =
                getProductRecord(deluxURI, "Delux Cupcake", Money.of(CurrencyUnit.USD, 6));

        //Add all the structured data to the set before to return.
        set.add(basicCupcakeRecord);
        set.add(deluxCupcakeRecord);

        return set;
    }

    /**
     * getProductRecord provide a function to put raw data of one file into a ProductRecord
     * and to specify the which is the product and how much does it cost.
     * @param uri {@link URI} of the file to read.
     * @param productName {@link String} name of the product we are working with.
     * @param price {@link Money} price of the product we are working with.
     * @return ProductRecord returned contains info on the product and the historic data of it.
     * @throws IOException
     */
    private ProductRecord getProductRecord(URI uri, String productName, Money price) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(uri));
        Product product = new Product(productName, price);
        ProductRecord productRecord = new ProductRecord(product);
        List<Integer> data = new LinkedList<>();
        Integer readValue;

        while(bufferedReader.ready()){
            String line = bufferedReader.readLine();
            try{
                readValue = Integer.parseInt(line);
                data.add(0, readValue);
            } catch (NumberFormatException e){
                //Read value is not a number.
                //And we simply ignore it.
            }
        }

        Map<LocalDate, Integer> record = productRecord.getRecord();
        int rowNumber = 0;
        while(rowNumber < data.size()) {
            record.put(LocalDate.now(Clock.systemUTC()).plusDays(rowNumber), data.get(rowNumber));
            rowNumber++;
        }

        return productRecord;
    }
}
