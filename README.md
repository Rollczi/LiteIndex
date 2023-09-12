# LiteIndex
💜 LiteIndex - Fast and extensive library for indexing box/area in space by vectors/location/position based on hash indexing.

### Simple Example
```java
public class Main {
    
    public static void main(String[] args) {
        int SPACE_SIZE = 10_000;
        int AREA_SIZE = 80;
        
        // create index
        SpaceIndex<Area, Location> index = SpaceIndex.<Area, Location>builder()
            .axisX(location -> location.getX())
            .axisZ(location -> location.getZ())
            .axisY(location -> location.getY())
            .space(area -> area.getMin(), area -> area.getMax())
            .indexing(IndexingAlgorithm.calculateOptimal(SPACE_SIZE, AREA_SIZE))
            .concurrent(false)
            .build();
        
        // put areas to index
        index.put(new Area(new Location(0, 0, 0), new Location(10, 10, 10)));
        index.put(new Area(new Location(3, 3, 3), new Location(7, 7, 7)));
        index.put(new Area(new Location(5, 5, 5), new Location(15, 15, 15)));
        
        // fast search
        List<Area> areas = index.get(new Location(5, 5, 5));
    }
}
```

**Variables:**
It's not limit, it's just for optimization and memory saving.
- `MAP_SIZE` - expected maximum size of space
- `AREA_SIZE` - expected maximum size of area

**SpaceIndex builder**
- `axisX` - function for getting **X** coordinate
- `axisZ` - function for getting **Z** coordinate
- `axisY` - function for getting **Y** coordinate
- `space` - function for getting **min** and **max** location from area
- `indexing` - set indexing algorithm (configure for **better performance**)
- `concurrent` - set concurrent mode (if you want to use index in multiple threads)

**SpaceIndex methods**
- `put` - put area to index
- `get` - get areas by location
- `getAll` - get all areas from index
- `getFirst` - get first area by location
- `remove` - remove area from index
- `removeAll` - remove all areas from index
- `contains` - check if index contains area
- `size` - get size of index

**IndexingAlgorithm**
- `calculateOptimal` - calculate optimal indexing algorithm for your space and area size
- `chunk` - create indexing algorithm **(advanced)**

### Gradle
```kotlin
repositories {
    maven("https://repo.eternalcode.pl/releases")
}
```
```kotlin
dependencies {
    implementation("dev.rollczi:liteindex:1.0.0")
}
```
### Maven
```xml
<repositories>
    <repository>
        <id>eternalcode-repo</id>
        <url>https://repo.eternalcode.pl/releases</url>
    </repository>
</repositories>
```
```xml
<dependencies>
    <dependency>
        <groupId>dev.rollczi</groupId>
        <artifactId>liteindex</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```