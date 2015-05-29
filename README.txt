Before you can run everything successully, you have to run a few commands to open 
communication to you android virtual machine. The commands are as follows:

In the Android/sdk/platform-tools folder, run:

adb forward tcp:9898 tcp:9898
adb forward tcp:9899 tcp:9899
adb forward tcp:8251 tcp:8251