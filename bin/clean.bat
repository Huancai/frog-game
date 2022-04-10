@echo off
echo.
echo clean up frog-game
echo.

%~d0
cd %~dp0

cd ..
call mvn clean

pause