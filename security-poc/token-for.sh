#!/bin/sh

# shellcheck disable=SC2155
export TOKEN=$(http -a $1 -f POST :9000/oauth2/token grant_type=client_credentials | jq -r .access_token)
echo "$TOKEN"
http -a $1 -f POST :9000/oauth2/introspect token="$TOKEN"