package dev.coppola.weekly.accountant.cli;

import dev.coppola.weekly.accountant.core.CashRecord;
import dev.coppola.weekly.accountant.core.Importer;
import dev.coppola.weekly.accountant.core.ProductRecord;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.RoundingMode;
import java.util.Set;

/**
 * @author Gennaro Coppola "coppola612@gmail.com"
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Importer<Set<ProductRecord>> cakesImporter = new DefaultProductImporter();
        Importer<CashRecord> totalCashImporter = new TotalCashImporter();

        CashRecord totalRecords = totalCashImporter.importData();

        double sum = totalRecords.values().stream().mapToDouble(e -> (double) e.getAmountMajorLong()).sum();
        Money sumUSD = Money.of(CurrencyUnit.USD, sum);
        long days = totalRecords.keySet().size();

        System.out.printf("Number of Weeks: %d \n", days / 7);
        System.out.printf("Number of Months: %d \n", days / 30);
        System.out.printf("Number of Years: %d \n", days / 365);
        System.out.printf("Income per week is %s \n", sumUSD.dividedBy(days / 7, RoundingMode.FLOOR));
        System.out.printf("Income per month is %s \n", sumUSD.dividedBy(days / 30, RoundingMode.FLOOR));
        System.out.printf("Income per year is %s \n", sumUSD.dividedBy(days / 365, RoundingMode.FLOOR));

    }
}
