echo on

WORK_DIR=/c/Users/Vector/Downloads/rotasNovasSprint15
HOME_GIT=/c/vector/workspaceEdu/est-portalcorretor-api
CONTROLLER_PATH=/portal-corretor-servico/src/main/java/br/com/odontoprev/portal/corretor/controller
SPRINT_ANT=sprint14
SPRINT_ATU=sprint15
RequestMappingTag="@RequestMapping"
RequestMappingFilePattern=*Controller.java
RequestMappingTxt=RequestMapping.txt
RequestMappingDiffTxt=rotasdiff.txt

cd $WORK_DIR

mkdir $SPRINT_ANT
mkdir $SPRINT_ATU

cd $HOME_GIT/$CONTROLLER_PATH

#git stash
git checkout $SPRINT_ANT
cp * $WORK_DIR/$SPRINT_ANT

git checkout $SPRINT_ATU
cp * $WORK_DIR/$SPRINT_ATU

cd $WORK_DIR/$SPRINT_ANT
grep $RequestMappingTag $RequestMappingFilePattern > $RequestMappingTxt

cd $WORK_DIR/$SPRINT_ATU
grep $RequestMappingTag $RequestMappingFilePattern > $RequestMappingTxt

cd $WORK_DIR
diff $SPRINT_ANT/$RequestMappingTxt $SPRINT_ATU/$RequestMappingTxt > $RequestMappingDiffTxt
