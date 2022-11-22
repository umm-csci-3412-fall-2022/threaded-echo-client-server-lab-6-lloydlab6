    #!/bin/bash

    numCalls=$1
    bigFile=$2

    for (( i=0; i<$numCalls; i++ ))
    do
        echo "Doing run $i"
        java echoserver.EchoClient < $bigFile > "/tmp/test$i" 
    done
    echo "Now waiting for all the processes to terminate"
    # `date` will output the date *and time* so you can see how long
    # you had to wait for all the processes to finish.
    wait
    echo "Done waiting; all processes are finished"
    for (( i=0; i<$numCalls; i++ ))
    do
        diff -s $2 "/tmp/test$i"
    done
           