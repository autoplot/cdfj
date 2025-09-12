# convert_log10(base)
Returns the base 10 logarithm of the data.

# use
| SPID | Name | Funct | Components |
|---|---|---|---|
| IM_K0_WIC | WIC_PIXELS_LOG | convert_log10 | WIC_PIXELS |

# Implementations
## Java
```java
        return Math.log10( base.adaptDouble(index) );
```

## IDL (CDAWeb)
https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/source/convert_log10.pro
