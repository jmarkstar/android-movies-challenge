#sudo chmod 755 ./run.sh

./gradlew clean buildDebug lintDebug ktlintDebugCheck testDebugUnitTest testDebugUnitTestCoverage --stacktrace

# unit test specific package
# ./gradlew clean testDebugUnitTest  --tests "com.jmarkstar.princestheatre.presentation.*"