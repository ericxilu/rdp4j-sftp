#!/usr/bin/env sh

ps -ef | grep rdp4j | grep -v grep | awk '{print $2}' | xargs kill -9 2>&1