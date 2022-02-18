:: Note that each application will run on a separate CPU core
cd ..\..\
call mvn -T 2 clean javafx:run -pl hermes-server,hermes-client
cd "run scripts\windows"