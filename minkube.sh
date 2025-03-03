#!/bin/bash

# Check for required tools
if ! command -v minikube &> /dev/null || ! command -v kubectl &> /dev/null || ! command -v docker &> /dev/null
then
    echo "Error: Minikube, kubectl, or Docker is not installed."
    exit 1
fi

echo "Starting Minikube..."
minikube start --driver=docker
echo

echo "Checking Minikube status..."
minikube status
echo

echo "Setting Minikube docker..."
eval $(minikube -p minikube docker-env)
echo

# Define services
SERVICES=("account-service" "marketplace-service" "wallet-service")

# Create directories if they don't exist
# echo "Creating service directories..."
# for SERVICE in "${SERVICES[@]}"; do
#     mkdir -p "$SERVICE"
# done

SERVICE="h2db-service"
echo "Deploying $SERVICE..."
cd "$SERVICE" || exit

kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl rollout status deployment/"$SERVICE"

cd ..
echo

# Deploy services
for SERVICE in "${SERVICES[@]}"; do
    echo "Deploying $SERVICE..."
    cd "$SERVICE" || exit

    docker build -t "$SERVICE:1.0" .

    # docker tag "$SERVICE:latest" "$SERVICE:1.0"
    # minikube image load "$SERVICE:1.0"

    kubectl apply -f deployment.yaml
    kubectl apply -f service.yaml
    kubectl rollout status deployment/"$SERVICE"

    cd ..
    echo
done


# Verify pods
echo "Checking pods..."
kubectl get pods
echo

# Port forwarding (Run in background)
echo "Setting up port forwarding..."
kubectl port-forward service/account-service 8080:8080 &
kubectl port-forward service/marketplace-service 8081:8080 &
kubectl port-forward service/wallet-service 8082:8080 &
kubectl port-forward service/h2db-service 8083:8082 &

# Minikube tunnel (Runs in background)
minikube tunnel &
echo

# Output service URLs
echo "Services are available at:"
echo "Account Service: http://localhost:8080"
echo "Marketplace Service: http://localhost:8081"
echo "Wallet Service: http://localhost:8082"
echo "H2DB Service: http://localhost:8083"
echo

# Keep script running
wait
