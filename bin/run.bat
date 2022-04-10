@echo off
echo.
echo [FROG] frog game-server start !!!
echo.

cd %~dp0
cd ../game-server/target

set JAVA_OPTS=-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -jar %JAVA_OPTS% game-server.jar

cd bin
pause