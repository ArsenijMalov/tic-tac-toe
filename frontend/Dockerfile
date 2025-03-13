FROM node:20-alpine AS build-stage

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

RUN npm run build --prod

FROM nginx:1.25-alpine AS production-stage

COPY --from=build-stage /app/dist/frontend/browser /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 4200

CMD ["nginx", "-g", "daemon off;"]
