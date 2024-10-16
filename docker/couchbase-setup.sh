#!/bin/bash

CB_USERNAME=admin
CB_PASSWORD=password
CB_HOST=couchbase

until $(curl --output /dev/null --silent --head --fail http://$CB_HOST:8091); do
    printf '.'
    sleep 5
done

curl -X POST http://$CB_HOST:8091/pools/default/buckets \
  -u $CB_USERNAME:$CB_PASSWORD \
  -d name=quiz \
  -d ramQuotaMB=100 \
  -d bucketType=ephemeral

# Scope oluştur
curl -X POST http://$CB_HOST:8091/pools/default/buckets/quiz/scopes \
  -u $CB_USERNAME:$CB_PASSWORD \
  -d name=quiz

# Collection oluştur
curl -X POST http://$CB_HOST:8091/pools/default/buckets/quiz/scopes/quiz/collections \
  -u $CB_USERNAME:$CB_PASSWORD \
  -d name=cache-collection

echo "Bucket, scope ve collection oluşturuldu!"