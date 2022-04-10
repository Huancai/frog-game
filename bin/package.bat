@echo off
echo.
echo 准备开始打包frog-game
echo.

%~d0
cd %~dp0

cd ..
call mvn clean package -Dmaven.test.skip=true

pause