package dev.coppola.weekly.accountant.cli;

import dev.coppola.weekly.accountant.core.CashRecord;
import dev.coppola.weekly.accountant.core.Importer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gennaro Coppola "coppola612@gmail.com"
 */
public class TotalCashImporter implements Importer<CashRecord> {
    @Override
    public CashRecord importData() throws Exception {
        var totalURI = getClass().getClassLoader().getResource("data/Total.txt").toURI();
        List<Integer> data = new LinkedList<>();
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(totalURI));

        Integer readValue;
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            try {
                readValue = Integer.parseInt(line);
                data.add(0, readValue);
            } catch (NumberFormatException e) {
                //Read value is not a number.
                //And we simply ignore it.
            }
        }

        CashRecord record = new CashRecord();
        Money tmpMoney;
        int rowNumber = 0;
        while (rowNumber < data.size()) {
            tmpMoney = Money.of(CurrencyUnit.USD, data.get(rowNumber));
            record.put(LocalDate.now(Clock.systemUTC()).plusDays(rowNumber), tmpMoney);
            rowNumber++;
        }

        return record;
    }
}
