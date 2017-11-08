#!/bin/bash
set -e

export EXIT_STATUS=0

curl -O https://raw.githubusercontent.com/grails/grails-guides/master/travis/build-guide
chmod 777 build-guide

./build-guide || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
    echo "Build failed" 
    exit $EXIT_STATUS
fi

curl -O https://raw.githubusercontent.com/grails/grails-guides/master/travis/republish-guides-website.sh
chmod 777 republish-guides-website.sh

./republish-guides-website.sh || EXIT_STATUS=$?

exit $EXIT_STATUS
