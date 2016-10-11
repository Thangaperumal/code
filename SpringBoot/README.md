# pim-data-loader
service to import data into PIM


Java Command to Run the Jar file:
java -jar -Dspring.profiles.active=qa target/pim-dataloader-0.0.1-SNAPSHOT.jar --fileName=2016.DataLoad.v1.0.xlsx --agency=newyork --site=18

VM Arguments (explained):
spring.profiles.active:- this specifies the environment into which the data needs to be loaded. It can take any of the following values: dev, qa, demo, stage.

Java Runtime Arguments (explained):
fileName:- name of the file which is the source of the data to be loaded into PIM.
agency:- the hospice agency into which the data needs to be loaded.
site:- the site in the agency for which the data needs to be loaded into.
