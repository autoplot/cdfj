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
| MMS1_FPI_BRST_L2_DES-DIST | mms1_des_dist_brst1_even | apply_filter_flag | mms1_des_dist_brst mms1_des_steptable_parity_brst | 2025-07-31T18:29:23Z/2025-07-31T18:31:22Z |
| MSL_RAD_OBS-L1 | LET_A1_f | apply_filter_flag | LET_A1 FLAGGED_DATA | 2025-08-29 |

# Implementations
## Autoplot
Autoplot supports this at line https://github.com/autoplot/autoplot/blob/abd103d614b499036c01dededf332d81c2c08db3/CdfJavaDataSource/src/org/autoplot/cdf/CdfVirtualVars.java#L233
* vap+cdaweb:ds=MSL_RAD_OBS-L1&filter=MSL&id=LET_A1_f&timerange=2025-08-29
* vap+cdaweb:ds=MMS1_FPI_BRST_L2_DES-DIST&id=mms1_des_dist_brst1_even&timerange=2025-07-31T18:29:23Z/2025-07-31T18:31:22Z
* vap+cdaweb:ds=PSP_SWP_SPC_L3I&id=vp_moment_SC_gd&timerange=2025-05-29

## Java
https://github.com/hapi-server/server-java/blob/main/CDAWebServer/src/org/hapiserver/source/cdaweb/adapters/ApplyFilterFlag.java

## IDL (CDAWeb)
https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/source/virtual_funcs.pro (line 5324)
