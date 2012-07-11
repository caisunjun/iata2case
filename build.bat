call call.bat
echo test suite
cd suite
java CopyFolder ../test-output d:\\report
cd ..
call failed.bat
echo test failed
cd suite
java CopyFolder ../test-output d:\\report
cd ..