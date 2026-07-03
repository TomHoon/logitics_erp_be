docker stop erp-spring
docker rm erp-spring

docker rmi erp-boot-image

docker build -t erp-boot-image .

docker run -d -p 33000:33000 --name erp-spring --net erp-net erp-boot-image
