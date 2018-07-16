#!/usr/bin/env bash

echo "请输入你要部署的版本: "
echo "RELEASE (R) / SNAPSHOT (S)"
read version

if [[ "${version}" = "R" ]] || [[ "${version}" = "r" ]];
then
    version=1.0.0

elif [[ "${version}" = "S" ]] || [[ "${version}" = "s" ]];
then
    version=1.0.0-SNAPSHOT
else
    echo "输入错误!!!"
    exit 1
fi

# 更新版本 (快照)
mvn versions:set -DnewVersion="${version}"
mvn -N versions:update-child-modules
mvn versions:commit

# 编译 + 打包 + 部署 + 清理
mvn clean deploy -Dmaven.test.skip=true
mvn clean -q
