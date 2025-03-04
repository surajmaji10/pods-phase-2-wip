kubectl delete services marketplace-service
kubectl delete services account-service
kubectl delete services wallet-service
kubectl delete services h2db-service
kubectl delete deployments wallet-service
kubectl delete deployments account-service
kubectl delete deployments marketplace-service
kubectl delete deployments h2db-service
kubectl delete hpa marketplace-service-hpa
echo "Setting Minikube docker..."
eval $(minikube -p minikube docker-env)
echo
docker rmi marketplace-service:1.0
docker rmi account-service:1.0
docker rmi wallet-service:1.0
minikube stop
