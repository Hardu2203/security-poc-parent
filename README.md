## About The Security-poc-parent

This is a proof of concept application, to show how to add a custom filter `TenantHeaderAuthFilter` after the `BearerTokenAuthenticationFilter`.

## Start Auth Server
```
cd sso
./gradlew bootRun
```

## Start security poc
```
cd security-poc
./gradlew bootRun
```

## Perform auth and call service
```
//Get token for danny 
. ./token-for.sh danny:dischem

//Create server
http POST :8090/api/servers "Authorization: Bearer $TOKEN" "tenant: Dis-Chem"
```
