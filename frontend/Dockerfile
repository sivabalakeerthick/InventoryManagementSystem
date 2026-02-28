# ---------- BUILD STAGE ----------
    FROM node:18-alpine AS build
    
    WORKDIR /app
    
    COPY package*.json ./
    RUN npm install
    
    COPY . .
    RUN npm run build -- --configuration production
    
    
    # ---------- NGINX STAGE ----------
    FROM nginx:alpine
    
    # Remove default config
    RUN rm -rf /usr/share/nginx/html/*
    
    # Copy Angular build output
    COPY --from=build /app/dist/inventory-management-system /usr/share/nginx/html
    
    EXPOSE 80
    
    CMD ["nginx", "-g", "daemon off;"]