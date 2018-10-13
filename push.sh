#!/usr/bin/env bash

##
# I am so lazy. 应卓
##
mvn clean -q
git add . && git commit -m 'update' && git push
exit 0
