// Dockerfile
FROM openjdk:8-alpine
ENV BUILD_TOOLS "26.0.2"
ENV SDK_TOOLS_API "26"
ENV ANDROID_HOME "/opt/sdk"
ENV GLIBC_VERSION "2.27-r0"
# Install required dependencies
RUN apk add --no-cache --virtual=.build-dependencies wget unzip ca-certificates bash && \
    wget https://raw.githubusercontent.com/sgerrand/alpine-pkg-glibc/master/sgerrand.rsa.pub -O /etc/apk/keys/sgerrand.rsa.pub && \
    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VERSION}/glibc-${GLIBC_VERSION}.apk -O /tmp/glibc.apk && \
    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VERSION}/glibc-bin-${GLIBC_VERSION}.apk -O /tmp/glibc-bin.apk && \
    apk add --no-cache /tmp/glibc.apk /tmp/glibc-bin.apk && \
    rm -rf /tmp/*
# Download Android SDK tools
RUN wget http://dl.google.com/android/repository/sdk-tools-linux-3859397.zip -O /tmp/tools.zip && \
    mkdir -p ${ANDROID_HOME} && \
    unzip /tmp/tools.zip -d ${ANDROID_HOME} && \
    rm -v /tmp/tools.zip
# Install Android packages & libraries
RUN export PATH=$PATH:$ANDROID_HOME/tools/bin && \
    mkdir -p /root/.android/ && touch /root/.android/repositories.cfg && \
    yes | sdkmanager "--licenses" && \
    sdkmanager --verbose "build-tools;${BUILD_TOOLS}" "platform-tools" "platforms;android-${SDK_TOOLS_API}" \
    && sdkmanager --verbose "extras;android;m2repository" "extras;google;google_play_services" "extras;google;m2repository"
# Install pip for grip export README.md to html
RUN apk add --no-cache --virtual=.build-dependencies py-pip \
    && pip install --upgrade pip \
    && pip install grip
# Install git
RUN apk add --no-cache git
# Install application dependencies
WORKDIR /workspace
COPY workspace /workspace
RUN ./gradlew assembleDebug \
    && ./gradlew testDebugUnitTest \
    && ./gradlew clean
