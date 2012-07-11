@echo off

:loop

call ant -f suite/TestAutoSeleniumBuild.xml
ping localhost -n 2 > nul

goto :loop

pause>>nul
