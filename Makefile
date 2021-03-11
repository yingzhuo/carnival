timestamp := $(shell /bin/date "+%F %T")

usage:
	@echo "=============================================================="
	@echo "usage   =>  显示菜单"
	@echo "clean   =>  清理"
	@echo "package =>  打包"
	@echo "deploy  =>  发布"
	@echo "install =>  本地安装"
	@echo "version =>  调整版本号"
	@echo "github  =>  提交源代码"
	@echo "=============================================================="

clean:
	@mvn -f $(CURDIR)/pom.xml clean -q

package:
	@mvn -f $(CURDIR)/pom.xml clean package

deploy:
	@mvn -f $(CURDIR)/pom.xml clean deploy -P"sonar"

install:
	@mvn -f $(CURDIR)/pom.xml clean install

version:
	@mvn -f $(CURDIR)/pom.xml versions:set
	@mvn -f $(CURDIR)/pom.xml -N versions:update-child-modules
	@mvn -f $(CURDIR)/pom.xml versions:commit

github: clean
	@git add .
	@git commit -m "$(timestamp)"

.PHONY: usage clean package install deploy version github