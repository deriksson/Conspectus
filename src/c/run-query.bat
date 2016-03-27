@echo off
chcp 65001 > NUL
query.exe localhost 5005 %1
