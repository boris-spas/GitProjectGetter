FILENAME=$1
count=0
complProjs=`cat complProjs.txt`
mkdir cloned-projects
cat $FILENAME | while read LINE
do
let count++
echo "$count $LINE"
isFound=`echo $complProjs  | grep $LINE`
if [ "$isFound" == '' ]; then
	cd cloned-projects
	git clone $LINE
	cd ..
	echo $LINE  >> complProjs.txt
	echo 'GOING TO SLEEP FOR 3 MIN'
	sleep 180
else
	echo 'FOUND IT IN complProjs.txt, so skiping...'
fi
done
echo -e "DONE!"