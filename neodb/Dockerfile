# Neo4j 5.x 버전을 사용하기 위한 기본 이미지
FROM neo4j:latest

# 환경 변수 설정
# 사용자명/비밀번호 설정
ENV NEO4J_AUTH=neo4j/root1234
# 기본 데이터베이스 이름 설정
ENV NEO4J_dbms_default__database=neodb
# APOC 플러그인 활성화 및 자동 다운로드 설정
ENV NEO4JLABS_PLUGINS='["apoc"]'
ENV NEO4J_dbms_security_procedures_unrestricted=apoc.*
ENV NEO4J_apoc_import_file_enabled=true
ENV NEO4J_apoc_export_file_enabled=true
ENV NEO4J_apoc_import_file_use__neo4j__config=true

# 기타 Neo4j 설정을 추가할 수 있음
# 예를 들어, 메모리 할당량이나 로깅 설정 등

# 컨테이너가 시작할 때 실행할 명령어 (필요한 경우)
CMD ["neo4j"]
