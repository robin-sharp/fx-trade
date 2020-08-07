# Keycloak

Keycloak is an OpenSource OpenID/OAuth2 server running on windows. 

Keycloak handles the the authentication and authorization. 

Keycloak is used to manage, users, groups roles and permissions.

Different projects can run in different "realms". This project runs in the trade-realm.

The trade-realm is made up of a number of clients (uri)

- entity - http://localhost:8081/trade/v1/entity - to handle legal entities
- fx-trade - http://localhost:8082/trade/v1/fx/trade - to handle fx trades 

Roles are set on a client by client basis and are based on the find, save, delete
methods in the Service classes. Roles are added to groups based on a user model of
groups in an investment bank, which distinguishes between internal and external users.

Keycloak does not handle application authorization, such as Party-User relationships,
which needs to be handled at the application level, such as SecurityServlet filters.

To install keycloak follow the installation instructions below.


## Running Keycloak Server and Admin Console

Open the cmd console up as admin:-

cd C:\Users\Robin\IdeaProjects\keycloak-11.0.0\bin

Run the Keycloak server on port 8090:-

.\standalone.bat -Djboss.socket.binding.port-offset=10

To run the Keycloak Admin Console

http://localhost:8090/  (admin/passord)



## Accessing Keycloak token_endpoint directly from the client

See the postman.md for details how to configure a client. 

Go to postman and the "keycloak openid" folder and and run an end point to generate a
JWT access token such as "/fx-trade/token_endpoint", passing in the client_secret from the
Keycloak Admin Console /clients/credentials tab and the user name and password.

Access Token Lifespan  is set to 15 hours

## Installation

Follow the getting the started instructions to downloadand extract the zip file:-

https://www.keycloak.org/getting-started/getting-started-zip

Create an admin user/password "adin"/password

Create a trade realm "trade-realm"

### Import Realm and Users

Open the cmd console up as admin:-

cd C:\Users\Robin\IdeaProjects\keycloak-11.0.0\bin

.\standalone.bat  -Djboss.socket.binding.port-offset=10 -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.realmName=trade-realm -Dkeycloak.migration.file=C:\Users\Robin\IdeaProjects\fx-trade\keycloak-service\src\main\resources\trade-realm\v1\trade-realm-v1.json -Dkeycloak.migration.strategy=OVERWRITE_EXISTING

### Export Realm and Users

Later if you want to export changes.

Open the cmd console up as admin:-

cd C:\Users\Robin\IdeaProjects\keycloak-11.0.0\bin

.\standalone.bat -Djboss.socket.binding.port-offset=10 -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.realmName=trade-realm -Dkeycloak.migration.file=C:\Users\Robin\IdeaProjects\fx-trade\keycloak-service\src\main\resources\trade-realm\v1\trade-realm-v1.json






