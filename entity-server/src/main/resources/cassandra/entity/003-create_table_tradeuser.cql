
CREATE TABLE IF NOT EXISTS entity.tradeuser(
    userId UUID,
    loginName text,
    fullName text,
    creationTime timestamp,
    statusChangeTime timestamp,
    email text,
    PRIMARY KEY(userId)
);

CREATE INDEX IF NOT EXISTS tradeuser_loginname_idx ON entity.tradeuser (loginName);