#!/bin/bash

# Define green color and reset (No Color)
GREEN='\033[0;32m'
UNDERLINE='\033[4m'
NC='\033[0m' # No Color (reset)

# Deleting services
echo -e "${GREEN}Deleting services...${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
kubectl delete services marketplace-service
kubectl delete services account-service
kubectl delete services wallet-service
kubectl delete services h2db-service
kubectl delete deployments wallet-service
kubectl delete deployments account-service
kubectl delete deployments marketplace-service
kubectl delete deployments h2db-service
kubectl delete hpa marketplace-service-hpa
kubectl delete pvc h2-pvc

echo "Please wait...This may take a while..."
echo

# Setting Minikube Docker
echo -e "${GREEN}Setting Minikube docker...${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
eval $(minikube -p minikube docker-env)
echo
docker rmi marketplace-service:1.0
docker rmi account-service:1.0
docker rmi wallet-service:1.0
docker rmi oscarfonts/h2:latest

minikube stop

# Completion message
echo -e "${GREEN}All Done.${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
