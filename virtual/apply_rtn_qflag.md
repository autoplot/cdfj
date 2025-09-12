# apply_rtn_qflag(data,quality,fill)
Returns the data as long as the parameter "quality" is not 222 or 223, fill otherwise.

# Implementations
## Java
```java
        double d= data.adaptDouble(index);
        int i= flag.adaptInteger(index);
        if ( i!=222 && i!=223 ) {
            return d;
        } else {
            return fill;
        }
```

## IDL (CDAWeb)
https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/source/apply_esa_qflag.pro search for name.

```idl
    temp = where((quality_data ne 222 and quality_data ne 223), badcnt)
    if (badcnt ge 1) then begin
      print, 'found some bad rtn data, replacing ',badcnt, ' out of ', data_size[1],' values with fill.'
      parent_data[temp] = fill_val
    endif else begin
      print, 'All ',astruct.(index).COMPONENT_0,' data good'
    endelse
```
