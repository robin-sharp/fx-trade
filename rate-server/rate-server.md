# Rate Server

Rate server is a very simple implementation of a rate server using WebSockets/STOMP and Spring Integration.

The basic design is that clients call to a Websocket rest endpoint to subscribe and unsubscribe to an 
fxrate topic, such as GBPUSD-SPOT. These subscriptions are managed by a SubscriptionManager. 

A FxRate source pumps out rates that have been subscribed to every second. The rates are taken from
a standard OpenFx Rate source and are then drifted randomly. These rates are sent down a Spring Integration 
Channel to a "product" specific handler, which is configured to pump out rates at a product specific rate 
and apply a product specific spread.

The product specific rates are then sent to a WebSocketPublisher which will publish these rates back 
to clients who have subscribed to them, by appending a client session id.

Client's can unsubscribe from the server, but if their connection closes unexpectedly then a WebSocket 
heartbeat manager is scheduled to run every n (10) seconds to check for any clients who have not
sent a heartbeat for the past n (25) seconds, and that client's session is then unsubscribed. 

WebSocket security is partly implemented, up to the point of calling into a KeyCloak server, which has
already been done in the TradeServer module. 

## Run

Start  

com.trade.fxrates.server.server.FxRateServer 

then start 

com.trade.fxrates.server.client.FxRateClient

### TODO

- The Architectural WebSocket and Integration code has not been unit tested - due to time constraints
- The code only handles one rate source and one rate product, though the channels are there to handle more.
- Only SPOT and not ForwardRates have been implemented, which would require a FwdRate source/curve.
- The SubscriptionManager does not handle products.
- Scheduler is not sympathetic to the order of handling of rates through the system.
- Keycloak integration.
- Object allocation optimization, e.g Object pools.

