
# height_isis(data)
Returns just the component of the geo_coord for height.

# use
| SPID | Name | Funct | Components |
|---|---|---|---|
| ALOUETTE2_AV_LIM  |height | height_isis | geo_coord |

# Implementations
## Java
```java
        double[] d= data.adaptDouble(index);
        return d[1];
```

## IDL (CDAWeb)
https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/source/virtual_funcs.pro search for name.

```idl
   for i=3L,n_elements(geo_coord)-1,3 do height=[height,geo_coord[i]]
   height=height[1:*]
```
