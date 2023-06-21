FROM alpine:latest
WORKDIR /app
COPY files /app
RUN apk add --update nodejs npm
ENV NODE_PORT 80
CMD ["npm", "start"]