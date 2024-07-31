package service;

import com.ohrim.exception.NotFoundException;
import com.ohrim.model.Currency;
import com.ohrim.model.ExchangeRate;
import com.ohrim.repository.ExchangeRateRepository;
import com.ohrim.service.ExchangeRateService;
import com.ohrim.service.ExchangeRateServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExchangeRateServiceTest {

    private static ExchangeRateRepository exchangeRateRepository;
    private static ExchangeRateService exchangeRateService;

    @BeforeAll
    public static void setup() {
        exchangeRateRepository = mock(ExchangeRateRepository.class);
        exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository);
    }

    @BeforeEach
    public void resetMocks() {
        Mockito.reset(exchangeRateRepository);
    }

    @Test
    public void testGetAllExchangeRates() {
        Currency usd = new Currency(1, "USD", "United States Dollar", "$");
        Currency eur = new Currency(2, "EUR", "Euro", "€");
        ExchangeRate exchangeRate = new ExchangeRate(1, usd, eur, BigDecimal.valueOf(0.85));

        when(exchangeRateRepository.findALL()).thenReturn(List.of(exchangeRate));

        var exchangeRates = exchangeRateService.getAllExchangeRates();
        assertNotNull(exchangeRates, "Exchange rates list should not be null");
        assertFalse(exchangeRates.isEmpty(), "Exchange rates list should not be empty");
        assertEquals("USD", exchangeRates.get(0).getBaseCurrency().getCode(), "Base currency should be USD");
    }

    @Test
    public void testGetExchangeRate() {
        Currency usd = new Currency(1, "USD", "United States Dollar", "$");
        Currency eur = new Currency(2, "EUR", "Euro", "€");
        ExchangeRate exchangeRate = new ExchangeRate(1, usd, eur, BigDecimal.valueOf(0.85));

        when(exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode("USD", "EUR"))
                .thenReturn(Optional.of(exchangeRate));

        var exchangeRateDto = exchangeRateService.getExchangeRate("USD", "EUR");

        assertNotNull(exchangeRateDto, "Exchange rate should not be null");
        assertEquals("USD", exchangeRateDto.getBaseCurrency().getCode(), "Base currency should be USD");
        assertEquals("EUR", exchangeRateDto.getTargetCurrency().getCode(), "Target currency should be EUR");
        assertEquals(0.85, exchangeRateDto.getRate().doubleValue(), "Exchange rate should be 0.85");
    }





    @Test
    public void testExchangeCurrencyNotFound() {
        when(exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(anyString(), anyString())).thenReturn(Optional.empty());
        when(exchangeRateRepository.findALL()).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> {
            exchangeRateService.exchangeCurrency("USD", "EUR", BigDecimal.valueOf(100));
        }, "Should throw NotFoundException when exchange rate is not found");
    }












}
