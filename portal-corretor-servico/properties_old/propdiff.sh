echo on

WORK_DIR=/c/Users/Vector/Downloads/rotasNovasSprint15
HOME_GIT=/c/vector/workspaceEdu/est-portalcorretor-api
PROPERTIES_PATH=/portal-corretor-servico/properties_old
SPRINT_ANT=sprint14
SPRINT_ATU=sprint15
PropertiesTag="="
PropertiesFile=*.properties
PropertiesTxt=properties.txt
PropertiesDiffTxt=PropertiesDiff.txt

cd $WORK_DIR

mkdir $SPRINT_ANT
mkdir $SPRINT_ATU

cd $HOME_GIT/$PROPERTIES_PATH

#git stash
git checkout $SPRINT_ANT
cp * $WORK_DIR/$SPRINT_ANT

git checkout $SPRINT_ATU
cp * $WORK_DIR/$SPRINT_ATU

cd $WORK_DIR/$SPRINT_ANT
grep $PropertiesTag $PropertiesFile > $PropertiesTxt

cd $WORK_DIR/$SPRINT_ATU
grep $PropertiesTag $PropertiesFile > $PropertiesTxt

cd $WORK_DIR
diff $SPRINT_ANT/$PropertiesTxt $SPRINT_ATU/$PropertiesTxt > $PropertiesDiffTxt
