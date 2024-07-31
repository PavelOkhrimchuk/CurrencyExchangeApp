CREATE TABLE IF NOT EXISTS Currencies (
                                          ID SERIAL PRIMARY KEY,
                                          Code VARCHAR(3) UNIQUE NOT NULL,
    FullName VARCHAR(255) NOT NULL,
    Sign VARCHAR(10)
    );

CREATE TABLE IF NOT EXISTS ExchangeRates (
                                             ID SERIAL PRIMARY KEY,
                                             BaseCurrencyId INT NOT NULL,
                                             TargetCurrencyId INT NOT NULL,
                                             Rate DECIMAL(10,6) NOT NULL,
    CONSTRAINT exchange_rates_unique_pair UNIQUE (BaseCurrencyId, TargetCurrencyId),
    CONSTRAINT exchange_rates_base_currency_fkey FOREIGN KEY (BaseCurrencyId)
    REFERENCES Currencies(ID) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exchange_rates_target_currency_fkey FOREIGN KEY (TargetCurrencyId)
    REFERENCES Currencies(ID) ON UPDATE NO ACTION ON DELETE NO ACTION
    );