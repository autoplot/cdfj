This area attempts to describe each virtual function used in NASA CDF files.  These have been implemented
in the IDL software for CDAWeb, but a specification has never been written in an accessible form.  

Note this has been created independently from CDAWeb, by inspecting their code.

# List
| SPID | Name | Funct | Components |
|---|---|---|---|
| OMNI2_H0_MRG1HR | Epoch_1800 | add_1800 | Epoch |
| IMAGE_M2_EUV | Mapped_Plasmapause_Grid_flip_vert | alternate_view_flip_vert | Mapped_Plasmapause_Gridb |
| THE_L2_GMOM  | the_ptiff_densityQ  | apply_gmom_qflag | the_ptiff_density,the_ptiff_data_quality |
| MESSENGER_MAG_RTN  | B_radial_q | apply_rtn_qflag | B_radial, Quality_Flag |
| IMAGE_M2_EUV | Equatorial_EUV_Grid_log_Movie | convert_log10_flip_vert | Equatorial_EUV_Grid |
| IM_K0_WIC | WIC_PIXELS_LOG | convert_log10 | WIC_PIXELS |
| FA_ESA_L2_EEB | pitch_angle_median | arr_slice | pitch_angle  |
| MVN_SWE_L2_SVYPAD | pa_E31 | arr_slice | pa |

# Conventions
`data` is the name of the CDF data which has been loaded and will be modified for display.  This is often the
first argument (component) to the virtual function.

`fill` is the fill value associated with the virtual variable metadata.

`index` is the record number.  
