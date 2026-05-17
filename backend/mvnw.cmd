@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM Begin all REM://
@echo off
@REM set title of command window
title %0
@setlocal

set ERROR_CODE=0

@REM To isolate internal variables from possible post scripts, we use another setlocal
@setlocal

@REM ==== START VALIDATION ====
if NOT "%JAVA_HOME%"=="" goto OkJHome
for %%i in (java.exe) do set "JAVACMD=%%~$PATH:i"
goto checkJCmd

:OkJHome
set "JAVACMD=%JAVA_HOME%\bin\java.exe"

:checkJCmd
if exist "%JAVACMD%" goto init

echo The JAVA_HOME environment variable is not defined correctly, >&2
echo this environment variable is needed to run this program. >&2
goto error

:init
@REM Find the project basedir, i.e. the directory that contains the folder ".mvn".
set MAVEN_PROJECTBASEDIR=%~dp0
:findBaseDir
IF NOT EXIST "%MAVEN_PROJECTBASEDIR%\.mvn" goto findBaseDirUp
goto baseDirFound

:findBaseDirUp
set MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%
for %%a in ("%MAVEN_PROJECTBASEDIR%") do set MAVEN_PROJECTBASEDIR=%%~dpa
IF "%MAVEN_PROJECTBASEDIR:~-1%"=="\" set MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%
goto findBaseDir

:baseDirFound

IF NOT EXIST "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" (
    set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain
    "%JAVACMD%" %MAVEN_OPTS% ^
        -Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR% ^
        -classpath %MAVEN_PROJECTBASEDIR%\.mvn\wrapper ^
        %WRAPPER_LAUNCHER% %*
    if ERRORLEVEL 1 goto error
    goto end
)

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

"%JAVACMD%" %MAVEN_OPTS% ^
    -Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR% ^
    -classpath %WRAPPER_JAR% ^
    %WRAPPER_LAUNCHER% %*
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & set ERROR_CODE=%ERROR_CODE%

cmd /C exit /B %ERROR_CODE%
