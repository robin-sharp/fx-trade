
CREATE TABLE IF NOT EXISTS entity.tradeparty(
    partyId UUID,
    partyCode text,
    fullName text,
    countryCode text,
    creationTime timestamp,
    entityStatus text,
    statusChangeTime timestamp,
    emails list<text>,
    users list<text>,
    PRIMARY KEY(partyId)
);

CREATE INDEX IF NOT EXISTS tradeparty_partycode_idx ON entity.tradeparty (partyCode);


