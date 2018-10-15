#!/usr/bin/env bash

echo "请输入新版本号: "
read version

# 更新版本
mvn versions:set -DnewVersion="${version}"
mvn -N versions:update-child-modules
mvn versions:commit
