GitProjectGetter
================

PLEASE NOTE: I am not liable for *ANY* problems you might have as a result of using this software. 
For example: Github has been known to reduce speeds for people generating to much traffic (like these programs might). 


GitProjectGetter is used to get a bunch of projects from github as determined by a search query. 
This repository contains the source and the executables of the project.

HOW TO USE:
- java -jar gitProjectGetter.jar 'https://github.com/search?utf8=%E2%9C%93&q=Your+search+here&type=Repositories&ref=searchresults'


this will create a projs.txt file with the project url's.

- ./script.sh projs.txt


this will start cloning all the projects in to the cloned-projects folder