#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/gym
cd $REPOSITORY

APP_NAME=gym
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &


##!/usr/bin/env bash: 이 줄은 Bash 스크립트임을 나타냅니다.

 #REPOSITORY=/home/ec2-user/cicdproject: 애플리케이션 코드와 JAR 파일이 저장된 디렉토리 경로를 지정합니다. 이 스크립트에서 사용할 경로입니다.

 #cd $REPOSITORY: 스크립트가 작업 디렉토리를 지정한 REPOSITORY 디렉토리로 변경합니다.

 #APP_NAME=cicdproject: 애플리케이션의 이름을 지정합니다. 이 변수는 현재 실행 중인 프로세스를 찾을 때 사용됩니다.

 #JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1): 빌드된 JAR 파일 중에서 가장 최신의 스냅샷 JAR 파일을 찾습니다. ls 명령어와 grep를 사용하여 스냅샷 JAR 파일을 찾습니다.

 #JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME: 찾은 최신 JAR 파일의 경로를 JAR_PATH 변수에 저장합니다.

 #CURRENT_PID=$(pgrep -f $APP_NAME): 현재 실행 중인 프로세스 중에서 $APP_NAME과 일치하는 프로세스의 PID를 검색합니다. 만약 해당 애플리케이션이 실행 중이지 않다면 $CURRENT_PID 변수는 비어있을 것입니다.

 #if [ -z $CURRENT_PID ]: $CURRENT_PID 변수가 비어있는지 확인하여 애플리케이션이 실행 중인지 확인합니다.

 #-z: 문자열이 비어 있는지 확인하는 조건문입니다.
 #echo "> 종료할것 없음.": 애플리케이션이 실행 중이 아니라면 종료할 것이 없음을 출력합니다.

 #else: 애플리케이션이 실행 중이라면 다음 코드 블록을 실행합니다.

 #echo "> kill -15 $CURRENT_PID": 실행 중인 애플리케이션 프로세스를 종료하기 위해 kill 명령어를 사용합니다. -15는 SIGTERM 시그널을 사용하여 프로세스를 정상적으로 종료하는 것을 의미합니다.

 #sleep 5: 애플리케이션 프로세스를 종료한 후 5초 동안 대기합니다. 이 시간 동안 이전 프로세스가 완전히 종료되도록 기다립니다.

 #echo "> $JAR_PATH 배포": 새로운 JAR 파일을 배포하고 있음을 출력합니다.

 #nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &: 새로운 JAR 파일을 백그라운드에서 실행합니다. nohup 명령어를 사용하여 터미널 연결이 끊겨도 프로세스가 계속 실행되도록 합니다. > /dev/null 2> /dev/null < /dev/null는 표준 출력, 표준 오류, 표준 입력을 모두 무시하도록 설정합니다. &는 프로세스를 백그라운드에서 실행하도록 합니다.
