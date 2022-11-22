    #!/bin/bash

    numCalls=$1
    bigFile=$2

    for (( i=0; i<$numCalls; i++ ))
    do
        echo "Doing run $i"
        java echoserver.EchoClient < $bigFile > "/tmp/test$i$3" 
    done
    echo "Now waiting for all the processes to terminate"
    wait
    echo "Done waiting; all processes are finished"
    for (( i=0; i<$numCalls; i++ ))
    do
        diff -s $2 "/tmp/test$i$3"
    done
           