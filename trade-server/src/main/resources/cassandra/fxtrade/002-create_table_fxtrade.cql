
CREATE TABLE IF NOT EXISTS fxtrade.fxtrade(
    tradeId UUID,
    trader text,
    party text,
    counterparty text,
    tradeStatus text,
    executionTime timestamp,
    buySell text,
    amount decimal,
    forwardDate date,
    currencyPair text,
    rate decimal,
    PRIMARY KEY(tradeId)
);

//CREATE INDEX IF NOT EXISTS fxtrade_executionTime_idx ON fxtrade.fxtrade (executionTime);

//CREATE INDEX IF NOT EXISTS fxtrade_user_idx ON fxtrade.fxtrade (user);