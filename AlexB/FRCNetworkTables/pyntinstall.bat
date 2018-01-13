echo off
title Python NT Install
echo Are you sure that you want to install Network tables?
pause
python -m pip install --upgrade pip
py -3 -m pip install pynetworktables
cls
echo DONE
pause