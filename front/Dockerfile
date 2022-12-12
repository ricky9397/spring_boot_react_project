# Dockerfile

FROM node:16-alpine as builder
WORKDIR /usr/src/app
COPY package*.json ./
#RUN npm install
RUN npm install -g npm@9.2.0
RUN npm install react-scripts@5.0.1 -g --silent
COPY ./ ./
RUN npm run build


FROM nginx 
EXPOSE 3000
COPY ./default.conf /etc/nginx/conf.d/default.conf 
COPY --from=builder /usr/src/app/build /usr/share/nginx/html

