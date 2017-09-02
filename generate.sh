#!/bin/bash
echo "Application Name:   $1"
echo "Package Name:       $2"
echo "Package Folder:     $3"
echo "Server Port:        $4"
echo "Database Type :     $5"
echo "Application Prefix: $6"
echo "Extras:             $7"
 


echo "start generating code for application $1" 

rm -rf data/$1

mkdir data/$1

cp templates/*.json data/$1

mv data/$1/type1-yo-rc.json data/$1/.yo-rc.json

cd data/$1/

find . -type f -name '*.json' -exec sed -i '' s:application-name:$1: {} +
find . -type f -name '*.json' -exec sed -i '' s:package-name:$2: {} +
find . -type f -name '*.json' -exec sed -i '' s:package-folder:$3: {} +
find . -type f -name '*.json' -exec sed -i '' s:server-port:$4: {} +
find . -type f -name '*.json' -exec sed -i '' s:database-type:$5: {} +
find . -type f -name '*.json' -exec sed -i '' s:application-prefix:$6: {} +



jhipster --regenerate
