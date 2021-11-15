
build:
	mvn clean package
	cd gateway-demo && docker build -t gateway-demo:latest .

run:
	cd springcloud-deploy && docker-compose up -d

stop:
	cd springcloud-deploy && docker-compose down