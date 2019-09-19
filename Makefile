timestamp             = $(shell /bin/date "+%F %T")

usage:
	@echo " target           | 功能"
	@echo "------------------|---------------------------------"
	@echo " usage            | 显示本菜单"
	@echo " clean            | 清理"
	@echo " compile          | 编译"
	@echo " package          | 打包"
	@echo " deploy           | 部署到Maven中央仓库"
	@echo " version          | 更改版本号"
	@echo " push-code        | 推送代码到Github"
	@echo "------------------|---------------------------------"

clean:
	@mvn -f $(CURDIR)/pom.xml clean -q

compile:
	@mvn -f $(CURDIR)/pom.xml clean compile -Dmaven.test.skip=true

package:
	@mvn -f $(CURDIR)/pom.xml clean package -Dmaven.test.skip=true

deploy:
	@mvn -f $(CURDIR)/pom.xml clean deploy -Psonar -Dmaven.test.skip=true

version:
	@mvn -f $(CURDIR)/pom.xml versions:set
	@mvn -f $(CURDIR)/pom.xml -N versions:update-child-modules
	@mvn -f $(CURDIR)/pom.xml versions:commit

push-code: clean
	@git add .
	@git commit -m "$(timestamp)"
	@git push

.PHONY: usage clean compile package deploy push-code change-version
