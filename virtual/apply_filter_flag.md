# apply_filter_flag(base)
To use the filter variable to "filter" out unwanted data points.
This one is different than the rest in that the user, through the
master cdf can specify the value to be tested against by using
the variable attribute COMPARE_VAL, if not defined, value defaults
to zero. It also looks for COMPARE_OPERATOR, defaults to "eq".

# use
| SPID | Name | Funct | Components | Date |
|---|---|---|---|---|
| PSP_SWP_SPC_L3I | vp_moment_SC_gd | apply_filter_flag | vp_moment_SC general_flag | 2025-05-29 |

# Implementations
## Java

## IDL (CDAWeb)
https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/source/virtual_funcs.pro (line 5324)
