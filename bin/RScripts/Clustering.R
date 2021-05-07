library(factoextra)
library(ggplot2)
library(sqldf)

# Prepare and load the dataset
data<-read.csv("C:\\Users\\kbipi\\eclipse-workspace\\CropPlanningBDA\\src\\main\\resources\\input\\apy.csv")
# Adding column names
colnames(data) <- c("State_Name", "District_Name", "Crop_Year", "Season","Crop","Area","Production")  
head(data)

# Data Summarization
summary(data)

# Sample observations from total dataset
index = sample(1:nrow(data), size = 0.0005*nrow(data))
data=data[index,]

# Prepare data matrix for sample dataset
index<-as.matrix(index)

# Applying k-means clustering to sample dataset with 6 clusters using 123 sample observations. 
set.seed(123)
kmeans_basic <- kmeans(index,6, nstart = 25)
kmeans_basic

kmeans_basic_table <- data.frame(kmeans_basic$size, kmeans_basic$centers)
kmeans_basic_df <- data.frame(Cluster = kmeans_basic$cluster, index)
# head of df
head(kmeans_basic_df)

kmeans_result=merge(data,kmeans_basic_df,by.x = "row.names",by.y = "index")

#The scipen option determines how likely R is to switch to scientific notation, the higher the value the less likely it is to switch
options(scipen=50)

#Count of Clusters by Season
ggplot(data = kmeans_result, aes(y = Cluster)) +
		geom_bar(aes(fill = Season)) +
		ggtitle("Count of Clusters by Season") +
		theme(plot.title = element_text(hjust = 0.5))+
  xlab("Count of Clusters") +
  ylab("Clusters")

dat <- kmeans_result[, c("Crop_Year", "Area")]
fviz_cluster(kmeans_basic, data = scale(dat), geom = c("point"),ellipse.type = "euclid",main="Clusters by Crop Year and Area")

#removes rows with missing values in columns
kmeans_result_final<-kmeans_result[complete.cases(kmeans_result), ]

#Season by Crop Production
prod_by_season_df=aggregate(kmeans_result_final$Production, by=list(Season=kmeans_result_final$Season), FUN=mean)
ggplot(data=prod_by_season_df, aes(x=Season, y=x, group=1))+
		ggtitle("Season by Crop Production") + 
		geom_line(color="red")+ 
		geom_point()+
  xlab("Seasons") +
  ylab("Production")

#Area by Crop Production from 1997
prod_after_1997 = sqldf("select Crop_Year,Crop,avg(Production) as production from data where Crop_Year>1997 group by Crop_Year,Crop order by Crop_Year asc")
ggplot(prod_after_1997, aes(x=Crop_Year, y=production, fill=Crop)) +
		ggtitle("Area by Crop Production from 1997") + 
		geom_bar(stat="identity",position=position_dodge())+
  xlab("Year") +
  ylab("Production")

#Demand by Crop
demand_data<-read.table("C:\\Users\\kbipi\\eclipse-workspace\\MapReduceExample\\src\\main\\resources\\semproject_output\\part-r-00000",sep="\t")
colnames(demand_data) <- c("Crop", "Demand")  # Adjust column names
top_demand_crops=sqldf("select * from demand_data order by demand desc limit 10")
ggplot(data=top_demand_crops, aes(x=Crop, y=Demand, group=1)) +
		ggtitle("Demand by Crop") + 
		geom_line(color="red")+ 
		geom_point()

#Production by Crop
prod_by_crop = sqldf("select Crop,avg(Production) as production from data group by Crop order by production desc limit 10")
ggplot(prod_by_crop, aes(x = 2, y = as.numeric(as.character(production)), fill = Crop)) +
		geom_bar(stat = "identity", color = "white") +
		coord_polar(theta = "y", start = 0)+
		theme_void()+
		ggtitle("Production by Crop") + 
		xlim(0.5, 2.5)
