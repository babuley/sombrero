# sombrero
![Java CI with Maven](https://github.com/babuley/sombrero/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
Extended OptaPlanner playground for school timetable.

### Samples
To run the samples
```./solve.sh```

or just 
```curl  -i -X POST -d @problemSpace2.json http://localhost:8080/timeTable/solve  -H "Content-Type:application/json"```

### Docker
To build img: 
```build docker .```

To run the container:
```docker run -p 8080:8080 --name sombrero $imageId```



