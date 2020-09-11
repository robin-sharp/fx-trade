# Trade

Trade is a simple example trading application running a mini trading system.
Currently only FX Spot sand Forwards are implemented. The system has the following 
features:-

- A Trade Server for saving, getting and publishing trades.
- An Entity server for saving, getting, publishing and caching party and user legal entities.
- A Rate Server for publishing rates.
- An Order Server to automatically execute trades 

## Tech stack 

Trade uses the following tech stack:-

- Java 8
- Spring 5 - Framework
- Spring 2 Boot - Configuration
- Spring Batch
- Spring Websockets/STOMP
- Spring Integration
- Tomcat 9.0.36 - Rest
- Casandra 3.11.6.0 - Database 
- Kafka 2.5.0 - Messaging (todo)
- Hazelcast - Cache (todo)
- Keycloak 11.0.0 - OpenID/IOAuth2

## Installation 
For installation read the following:-

### Keycloak README

See keycloak-service/keycloak.md

### Postman README

See poastman/postman.md

### Cassandra README

See cassandra-service/cassandra.md

### Hazlecast README

See hazelcast-service/hazelcast.md

### Kafka README

See kafka-service/kafka.md

### Spring Batch 

See entity-batch

### Spring Websockets/STOMP and Spring Integration

See rate-server/rate-server.md

## Run 

Set the env variable to SPRING_PROFILES_ACTIVE to dev or test

A Postman runner can be used to smoke test the rest endpoints (see postman.md)

- Tech Servers (non-application)
 - Cassandra Server (see cassandra-service/cassandra.md)
 - Keycloak Server (see keycloak-service/keycloak.md)
 - Hazelcast (todo)
 - Kafka (todo)

- FxTrade Server 
  - Start FxTrade Server (see com.trade.fxtrade.FxTradeServer)
  - Save Trades in the TradeServer (see Postman POST /fxtrade)
  - Search for Trades from the TradeServer (see Postman GET /fxtrade)
  - Secure FxTradeController using OAuth2 from Keycloak
  - (todo) Subscribe to Parties from the EntityServer using Kafka
  - (todo) Publish Trades from the TradeServer using Hazelcast

- Entity Server
  - Start the EntityServer (see com.trade.entity.EntityServer)
  - Save Parties and Users in the EntityServer (see Postman POST /party and /user)
  - Search for Parties and Users in the EntityServer (see Postman GET /party and /user)
  - Secure PartyController using OAuth2 from Keycloak
  - (todo) Publish Parties from the EntityServer using Kafka
  - (todo) Cache Parties and Users in the Entity Server using Hazelcast

- Batch Runner
  - Start the Batch Runner (see com.trade.entity.batch.SwiftPartyBatchRunner)
  - Upload Parties into the Party table
  
- Rate Server
  - Start FxRateServer 
  - Start FxRateClient

- Order Server
  - (todo) Start the OrderServer
  - (todo) Save Orders to the Order Server
  - (todo) Get Orders from the Order Server
  - (todo) Subscrive to Rate from teh Rate Server
  - (todo) Execute trades from the Order Server

- Trade Client
  - (todo) Start the TradeClient
  - (todo) Access JWT bearer token from Keycloak
  - (todo) Subscribe to Parties and Users from the EntityServer
  - (todo) Subscribe to Rates from the RateServer
  - (todo) Subscribe to Trades from the TradeServer 
  - (todo) Execute Trades from the TradeClient

## Modules

There are the following runnable application modules:-

In addition to the application modules there are service modules that include
a tech stack service in each server module to to start those services. These 
services define consistent yml configuration for each service. These services 
are as follows:-

- keycloak-service
- cassandra-service
- kafka-service
- rest-service
- postman


