#!/bin/sh

# Download the data file from https://www.gov.uk/government/statistical-data-sets/uk-house-price-index-data-downloads-april-2016
# Send a data file to the service
curl -X POST --data-binary @$HOME/Downloads/UK-HPI-full-file-2016-04.csv 'http://localhost:9000'
