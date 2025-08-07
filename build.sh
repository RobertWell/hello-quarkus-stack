#!/bin/bash

echo "Building frontend..."
cd frontend
npm run build:copy
cd ..

echo "Starting Quarkus backend..."
cd backend
./mvnw quarkus:dev