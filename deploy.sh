#!/usr/bin/env bash

# 更新版本 (SNAPSHOT)
mvn versions:set -DnewVersion=1.0.0-SNAPSHOT
mvn -N versions:update-child-modules
mvn versions:commit

# 编译 + 打包 + 提交 + 清理
mvn clean deploy -Dmaven.test.skip=true
mvn clean -q
