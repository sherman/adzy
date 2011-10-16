#!/bin/bash

rm out
for counter in {1..1000}; do `wget -S "http://localhost:5544/test" -O banner && cat banner | grep -E "is:[0-9]" -o >> out`; done;

for bannerId in 1 2 3
do
	value=`cat out | grep $bannerId -c`
	echo "$bannerId: $value" 
done;

