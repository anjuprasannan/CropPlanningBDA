'''
@author: Joice T
'''
from pyspark.sql.types import *
from pyspark.sql.functions import *

#CropPlanningBDA\src\main\resources\spark_input\area_production_and_productivity_of_principal_crops_2019.csv 
df=spark.read.csv("/FileStore/tables/area1-6.csv",header="true") 

df2=df.select("Crop","Demand","Group").groupBy("Group").agg(sum("Demand"))
df2.show()
