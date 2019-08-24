#!/usr/bin/env bash

mvn clean -q

git add . && \
    git commit -m 'update' && \
    git push
