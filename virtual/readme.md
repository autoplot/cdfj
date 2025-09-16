This area attempts to describe each virtual function used in NASA CDF files.  These have been implemented
in the IDL software for CDAWeb, but a specification has never been written in an accessible form.  

Note this has been created independently from CDAWeb, by inspecting their code.

# Conventions
`data` is the name of the CDF data which has been loaded and will be modified for display.  This is often the
first argument (component) to the virtual function.

`fill` is the fill value associated with the virtual variable metadata.

`index` is the record number.  
