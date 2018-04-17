echo off
title Python NT Install
echo Are you sure that you want to install Network tables?
pause
echo Started
python -m pip install --upgrade pip
echo Done 1/3
py -3 -m pip install pynetworktables
echo Done 2/3
py -3 -m pip install --upgrade pynetworktables
cls
echo DONE
pause