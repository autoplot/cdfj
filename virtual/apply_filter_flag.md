# apply_filter_flag(base)
Filter a variable based on another using HANDLE, COMPARE_OPERATOR, COMPARE_VAL

# use
| SPID | Name | Funct | Components |
|---|---|---|---|
| PSP_SWP_SPC_L3I | vp_moment_SC_gd | apply_filter_flag | vp_moment_SC general_flag |

# Implementations
## Java
```java
        return Math.log10( base.adaptDouble(index) );
```

## IDL (CDAWeb)
https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/source/convert_log10.pro
