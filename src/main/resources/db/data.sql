CREATE TABLE IF NOT EXISTS Currencies
(
    ID       SERIAL PRIMARY KEY,
    Code     VARCHAR(3) UNIQUE NOT NULL,
    FullName VARCHAR(255)      NOT NULL,
    Sign     VARCHAR(10)

    );

CREATE TABLE IF NOT EXISTS ExchangeRates(
                                            ID SERIAL PRIMARY KEY,
                                            BaseCurrencyId INT NOT NULL,
                                            TargetCurrencyId INT NOT NULL,
                                            Rate DECIMAL(10,6) NOT NULL,
    CONSTRAINT exchange_rates_unique_pair UNIQUE (BaseCurrencyId,TargetCurrencyId),
    CONSTRAINT exchange_rates_base_currency_fkey FOREIGN KEY (BaseCurrencyId)
    REFERENCES Currencies(ID) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT exchange_rates_target_currency_fkey FOREIGN KEY (TargetCurrencyId)
    REFERENCES Currencies(ID) ON UPDATE NO ACTION ON DELETE NO ACTION

    );


INSERT INTO Currencies (Code, FullName, Sign) VALUES ('AUD', 'Australian Dollar', 'A$');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('USD', 'United States Dollar', '$');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('EUR', 'Euro', '€');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('JPY', 'Japanese Yen', '¥');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('GBP', 'British Pound', '£');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('RUB', 'Russian Ruble', '₽');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('CAD', 'Canadian Dollar', 'C$');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('CHF', 'Swiss Franc', 'Fr');


INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 2, 0.73);  -- AUD to USD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 3, 0.84);  -- USD to EUR
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 4, 130.17); -- EUR to JPY
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 5, 0.0068); -- JPY to GBP
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 1, 1.84);  -- GBP to AUD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 2, 0.013);  -- RUB to USD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 6, 57.32);  -- CAD to RUB
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 3, 0.92);  -- CHF to EUR
