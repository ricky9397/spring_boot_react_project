FROM node:16-alpine as builder

WORKDIR /usr/src/app
COPY package.json ./
COPY tailwind.config.js ./

RUN npm install -g npm@8.19.3
RUN npm install react-scripts@5.0.1 -g --silent

RUN #npm yarn
RUN yarn add react@latest
RUN yarn add react-dom@latest
COPY ./ ./
RUN npm run build --loglevel verbose


FROM nginx
EXPOSE 3000
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /usr/src/app/build /usr/share/nginx/html

