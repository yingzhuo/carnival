timestamp := $(shell /bin/date "+%F %T")

no_default:
	@echo "no default target"

clean:
	@mvn -f $(CURDIR)/pom.xml clean -q

compile:
	@mvn -f $(CURDIR)/pom.xml clean compile

package:
	@mvn -f $(CURDIR)/pom.xml clean package

deploy:
	@mvn -f $(CURDIR)/pom.xml clean deploy -P Sonar

install:
	@mvn -f $(CURDIR)/pom.xml clean install

version:
	@mvn -f $(CURDIR)/pom.xml versions:set
	@mvn -f $(CURDIR)/pom.xml -N versions:update-child-modules
	@mvn -f $(CURDIR)/pom.xml versions:commit

github: clean
	@git add .
	@git commit -m "$(timestamp)"

.PHONY: no_default clean compile package install deploy version github