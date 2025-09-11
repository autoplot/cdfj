# apply_esa_qflag(data,flag)
Returns the data as long as the filter variable is zero.

# Implementations
## Java
```java
        double d= data.adaptDouble(index);
        int i= flag.adaptInteger(index);
        if ( i==0 ) {
            return d;
        } else {
            return fill;
        }
```

## IDL (CDAWeb)
https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/source/apply_esa_qflag.pro
