# apply_gmom_qflag(data,flag)
Returns the data as long as the filter variable is zero.  This is implemented with the same 
code as apply_esa_qflag.

# use
| SPID | Name | Funct | Components |
|---|---|---|---|
| THE_L2_GMOM  | the_ptiff_densityQ  | apply_gmom_qflag | the_ptiff_density,the_ptiff_data_quality |

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
