# POstman Collection

A set of postman collections and environment files for smoke testing the controllers can be found 
in the root postman directory. You can use Postman to generate curl files.

## Installation 

Download postman for Windows from:-

https://www.postman.com/downloads/  

Import the postman *_collection.json and *_environment.json files from root /postman directory

## Servers

Run the following application servers (see README.md) and Cassandra Db (see Cassandra.md)

- trade-server/FxTradeServer

- entity-server/EntityServer

# OAuth2

The root Postman /trade folder has a Basic Auth called user/password that is configured in
the Java server WebSecurityConfiguration file. 

This can be overridden in the client specific basis (e.g. /fxtrade or /party) by changing 
the Authorization from Inherit auth from Parent to OAuth 2.0.

### Set up OAuth 20 Access Token in Postman

To generate an access token you can go to the /fxtrade and /entity folders, edit and select the
authorisation tab and set up an Oauth 2.0 request.

Token Name : Entity Access Token (or FxTradeAccess token) 
Grant Type : Password Credentials
Access Token URL : {{keycloak-uri}}/auth/realms/trade-realm/protocol/openid-connect/token
Username : robin.sharp
Password : password (in reality use a variable)
Client ID :entity-client (or fxtrade-client)
Client Secret : (UUID from keycloak /client/credentials tab)
Scope :openid

The Client Secret comes from the Keycloak Admin Console /clients/credentials tab. It will 
expire so will need renewing

After this form has been completed request the token and Use Token for Authorization. 
Repeat this for each /client in Postman.

The token can be checked in jwt.io to check the grants are what you expect. 

The access_token is then set  in the header as follows:-

headers: {
    'Authorization': 'Bearer' + access_token
}