#sudo chmod 755 ./run.sh

# Circle CI command
 ./gradlew clean buildDebug lintDebug ktlintDebugCheck testDebugUnitTest testDebugUnitTestCoverage --stacktrace

# unit test specific package
# ./gradlew clean testDebugUnitTest  --tests "com.jmarkstar.princestheatre.presentation.movie_list.MovieListFragmentTest"


# add ./gradlew --info to see the `println()` logs on the console.