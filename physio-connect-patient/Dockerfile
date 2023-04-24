FROM node:16-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:1.23.3-alpine
WORKDIR /app
COPY --from=build /app/dist/physio-connect-patient /usr/share/nginx/html
COPY ./config/nginx.conf /etc/nginx/conf.d/default.conf
