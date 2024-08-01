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
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('CNY', 'Chinese Yuan', '¥');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('INR', 'Indian Rupee', '₹');
INSERT INTO Currencies (Code, FullName, Sign) VALUES ('BRL', 'Brazilian Real', 'R$');




INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 2, 0.67);  -- AUD to USD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 3, 0.86);  -- USD to EUR
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 4, 139.50); -- EUR to JPY
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 5, 0.0065); -- JPY to GBP
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 1, 1.75);  -- GBP to AUD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 2, 0.012);  -- RUB to USD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 6, 60.10);  -- CAD to RUB
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 3, 0.93);  -- CHF to EUR
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 8, 0.62);  -- AUD to CHF
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 7, 1.33);  -- USD to CAD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 6, 80.50);  -- EUR to RUB
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 7, 0.0076); -- JPY to CAD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 6, 105.80); -- GBP to RUB
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 8, 0.11);  -- RUB to CHF
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 8, 0.12);  -- CAD to CHF
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 2, 1.07);  -- CHF to USD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 9, 4.50);  -- AUD to INR
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 3, 0.010);  -- INR to EUR
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 10, 3.50);  -- AUD to BRL
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 3, 0.19);  -- BRL to EUR
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 7, 0.057);  -- INR to CAD
INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 10, 0.062);  -- CAD to BRL


