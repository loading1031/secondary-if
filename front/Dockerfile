# 첫 번째 스테이지: Node.js를 기반으로 빌드 환경 설정
FROM node:alpine as build
WORKDIR /app
# package.json (및 package-lock.json) 파일만 먼저 복사
COPY package.json package-lock.json* ./
RUN npm install --silent
# 애플리케이션 소스 코드 전체를 복사
COPY . /app
RUN npm run build || (cat npm-debug.log; exit 1)

# 두 번째 스테이지: Nginx 서버 설정
FROM nginx
# build 스테이지에서 생성된 dist 디렉토리를 nginx의 html 디렉토리에 복사
COPY --from=build /app/dist /usr/share/nginx/html
ENTRYPOINT ["nginx", "-g", "daemon off;"]
