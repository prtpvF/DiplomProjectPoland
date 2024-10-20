FROM node:22.8.0-alpine

WORKDIR /app

COPY dist/ /app/dist/

COPY package*.json .
COPY vite.config.ts .

RUN npm install typescript

EXPOSE 3000

CMD [ "npm", "run", "preview" ]