set -e

cp $SITE_DIR/application.properties src/main/resources/application.properties
cp $SITE_DIR/context.xml src/main/webapp/META-INF/context.xml

mvn package -DskipTests

war_file=$(find target -name \*.war)
mv $war_file $WAR_TARGET_LOCATION
