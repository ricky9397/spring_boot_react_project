# Dockerfile

FROM node:alpine as builder
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY package.json .
RUN npm install -g npm@8.15.0
COPY ./ ./
#RUN npm run build
CMD ["npm", "run", "build"]

FROM nginx 
EXPOSE 3000
COPY ./default.conf /etc/nginx/conf.d/default.conf 
COPY --from=builder /usr/src/app/build /usr/share/nginx/html

# /usr/src/app/build -> 앞에 . 붙여보기 ./usr/src/app/build
# yarn으로 해보기...
