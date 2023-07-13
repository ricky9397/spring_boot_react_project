FROM node:16-alpine as builder

WORKDIR /usr/src/app
COPY package.json ./
COPY tailwind.config.js ./

RUN npm install -g npm@1.1.0
RUN npm install react-scripts@5.2.0 -g --silent

RUN #npm yarn
RUN yarn add react@^18.2.0
RUN yarn add react-dom@^18.2.0
COPY ./ ./
#RUN npm run build:tailwind
RUN npm run build


FROM nginx
EXPOSE 3000
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /usr/src/app/build /usr/share/nginx/html

