package service;

import com.ohrim.dto.CurrencyDto;
import com.ohrim.repository.CurrencyRepositoryImpl;
import com.ohrim.service.CurrencyService;
import com.ohrim.service.CurrencyServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.DatabaseInitializer;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyServiceTest {

    private static CurrencyService currencyService;

    @BeforeAll
    public static void setup() {
        DatabaseInitializer.initializeDatabase();
        currencyService = new CurrencyServiceImpl(new CurrencyRepositoryImpl());
    }

    @Test
    public void testGetAllCurrencies() {
        var currencies = currencyService.getAllCurrencies();
        assertNotNull(currencies, "Currencies list should not be null");
        assertTrue(currencies.size() > 0, "Currencies list should not be empty");
    }


    @Test
    public void testGetCurrencyByCode() {
        var currency = currencyService.getCurrencyByCode("USD");
        assertNotNull(currency, "Currency should not be null");
        assertEquals("USD", currency.getCode(), "Currency code should be USD");
        assertEquals("United States Dollar", currency.getFullName(), "Currency full name should be 'United States Dollar'");
    }

    @Test
    public void testCreateCurrency() {
        var newCurrency = new CurrencyDto(null, "Test Currency", "TST", "T$");
        var createdCurrency = currencyService.createCurrency(newCurrency);
        assertNotNull(createdCurrency, "Created currency should not be null");
        assertNotNull(createdCurrency.getId(), "Created currency ID should not be null");
        assertEquals("TST", createdCurrency.getCode(), "Created currency code should be TST");
    }

    @Test
    public void testUpdateCurrency() {
        var existingCurrency = currencyService.getCurrencyByCode("USD");
        assertNotNull(existingCurrency, "Existing currency should not be null");

        existingCurrency.setFullName("Updated United States Dollar");
        var updatedCurrency = currencyService.updateCurrency(existingCurrency);
        assertNotNull(updatedCurrency, "Updated currency should not be null");
        assertEquals("Updated United States Dollar", updatedCurrency.getFullName(), "Currency full name should be updated");
    }
}
