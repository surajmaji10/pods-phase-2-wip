docker run -p 8080:8080 --rm --name app1 --add-host=host.docker.internal:host-gateway as &

docker run -p 8081:8080 --rm --name app2 --add-host=host.docker.internal:host-gateway ms &

docker run -p 8082:8080 --rm --name app3 --add-host=host.docker.internal:host-gateway ws &
