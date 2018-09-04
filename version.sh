#!/usr/bin/env bash

echo "请输入你要部署的版本: "
read version

# 更新版本
mvn versions:set -DnewVersion="${version}"
mvn -N versions:update-child-modules
mvn versions:commit
