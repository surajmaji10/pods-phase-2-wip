#!/bin/bash

# Define green color and reset (No Color)
GREEN='\033[0;32m'
UNDERLINE='\033[4m'
NC='\033[0m' # No Color (reset)

# Check for required tools
if ! command -v minikube &> /dev/null || ! command -v kubectl &> /dev/null || ! command -v docker &> /dev/null
then
    echo -e "${GREEN}Error: Minikube, kubectl, or Docker is not installed.${NC}"
    echo -e "${GREEN}_________________________________________________________${NC}"
    exit 1
fi

echo -e "${GREEN}Starting Minikube...${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
minikube start --driver=docker
echo

echo -e "${GREEN}Checking Minikube status...${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
minikube status
echo

echo -e "${GREEN}Setting Minikube docker...${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
eval $(minikube -p minikube docker-env)
echo "docker env is now minikube's env"
echo

# Define services
SERVICES=("account-service" "marketplace-service" "wallet-service")

SERVICE="h2db-service"
echo -e "${GREEN}Deploying $SERVICE...${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
cd "$SERVICE" || exit

kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl rollout status deployment/"$SERVICE"

cd ..
echo

# Deploy services
for SERVICE in "${SERVICES[@]}"; do
    echo -e "${GREEN}Deploying $SERVICE...${NC}"
    echo -e "${GREEN}_________________________________________________________${NC}"
    cd "$SERVICE" || exit

    docker build -t "$SERVICE:1.0" .

    kubectl apply -f deployment.yaml
    kubectl apply -f service.yaml
    kubectl rollout status deployment/"$SERVICE"

    cd ..
    echo
done

cd "marketplace-service" || exit
kubectl apply -f hpa.yaml
cd ..

# Verify pods
echo -e "${GREEN}Checking pods...${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
kubectl get pods
echo

# Port forwarding (Run in background)
echo -e "${GREEN}Setting up port forwarding...${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
kubectl port-forward service/account-service 8080:8080 &
kubectl port-forward service/marketplace-service 8081:8080 &
kubectl port-forward service/wallet-service 8082:8080 &
kubectl port-forward service/h2db-service 8083:8082 &

# Minikube tunnel (Runs in background)
minikube tunnel &
echo

# Output service URLs with underlined service names
echo -e "${GREEN}Services are available at:${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
echo -e "${GREEN}Account Service: http://localhost:8080${NC}"
echo -e "${GREEN}Marketplace Service: http://localhost:8081${NC}"
echo -e "${GREEN}Wallet Service: http://localhost:8082${NC}"
echo -e "${GREEN}H2DB Service: http://localhost:8083${NC}"
echo
echo -e "${GREEN}All Done.${NC}"
echo -e "${GREEN}_________________________________________________________${NC}"
echo
wait


