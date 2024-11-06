set -o allexport

echo "Publishing..."

./gradlew :library:clean :library:publishToMavenLocal
