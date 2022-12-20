httpCodes=$(grep Response rollingFile.log | cut -f 1 -d ,| cut -f 2 -d : | cut -f 2 -d [ | cut -f 1 -d ] | cut -f 1 -d ' ')
executionTime=$(grep execution rollingLogFile.2022-12-15.log | cut -f 2 -d , | cut -f 4 -d ' ')

httpRequestTotal=0
httpFailures=0
executionFailures=0

# then loop through the log data and perform any necessary operations to initialize your variables

for code in $httpCodes
do
        ((httpRequestTotal++))
        if [ $code -eq 500 ]
        then
                ((httpFailures++))
        fi
done

for time in $executionTime
do
        if (( $(echo "$time > 200" |bc -l) ));
        then
                ((executionFailures++))
        fi
done

# save the SLI value and return it

httpSuccess=$(($httpRequestTotal - $httpFailures))
resultHttpSuccess=$(echo "scale=2; $httpSuccess / $httpRequestTotal" | bc) # this option might not be available via gitbash on your local computer.

executionSuccess=$(($httpRequestTotal - $executionFailures))
resultExecutionSuccess=$(echo "scale=2; $executionSuccess / $httpRequestTotal" | bc)

echo "total requests: $httpRequestTotal"
echo "HTTP success rate: $resultHttpSuccess%"
echo "Executions within 200ms: $resultExecutionSuccess%"
