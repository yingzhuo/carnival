#!/usr/bin/env bash

# 更新版本 (RELEASE)
mvn versions:set -DnewVersion=1.0.0
mvn -N versions:update-child-modules
mvn versions:commit

# 安装 + 清理
mvn clean install -Dmaven.test.skip=true
mvn clean -q
