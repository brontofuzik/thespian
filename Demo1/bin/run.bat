@ECHO OFF

REM Solution directory for HP
SET SOLUTION_DIR=C:\DATA\projects\MAS\MetaMAS

REM Solution directory for Prestigio
REM SET SOLUTION_DIR=D:\projects\MAS\MetaMAS

SET PROJECT_NAME=Demo1
SET PROJECT_DIR=%SOLUTION_DIR%\%PROJECT_NAME%
SET JADE_VERSION=4.1.1

REM ----- CLASSPATH -----
SET JADE_JAR=%SOLUTION_DIR%\lib\Jade\%JADE_VERSION%\jade.jar
SET COMMONS_CODEC_JAR=%SOLUTION_DIR%\lib\commons-codec\1.3\commons-codec-1.3.jar
SET JADEORG_JAR=%SOLUTION_DIR%\JadeOrg\dist\JadeOrg.jar
SET PROJECT_JAR=%PROJECT_DIR%\dist\%PROJECT_NAME%.jar
SET CLASSPATH=%JADE_JAR%;%COMMONS_CODEC_JAR%;%JADEORG_JAR%;%PROJECT_JAR%

REM ----- Agents -----
SET ROOT_NAMESPACE=demo1

REM ----- Organizations -----
SET ORGANIZATION_PACKAGE=%ROOT_NAMESPACE%.organizations
SET DEMO_ORGANIZATION=demo_Organization:%ORGANIZATION_PACKAGE%.Demo_Organization
SET ORGANIZATIONS=%DEMO_ORGANIZATION%

REM ----- Players -----
SET PLAYER_PACKAGE=%ROOT_NAMESPACE%.players
SET DEMO1_PLAYER=demo1_Player:%PLAYER_PACKAGE%.Demo1_Player
SET DEMO2_PLAYER=demo2_Player:%PLAYER_PACKAGE%.Demo2_Player
SET PLAYERS=%DEMO1_PLAYER%;%DEMO2_PLAYER%

Rem ----- Options -----
SET LOGGING_CONFIG_FILE=%PROJECT_DIR%\logging.properties
SET JAVA_OPTIONS=-classpath %CLASSPATH% -Djava.util.logging.config.file=%LOGGING_CONFIG_FILE%
SET JADE_OPTIONS=-gui

SET SNIFFER=sniffer:jade.tools.sniffer.Sniffer
SET AGENTS=%SNIFFER%;%ORGANIZATIONS%;%PLAYERS%

@ECHO ON

java %JAVA_OPTIONS% jade.Boot %JADE_OPTIONS% %AGENTS%