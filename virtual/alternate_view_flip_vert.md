# alternate_view_flip_vert(base)
Returns the base 10 logarithm of the data, and then flip the values vertically.

# use
| SPID | Name | Funct | Components |
|---|---|---|---|q
| IMAGE_M2_EUV | Mapped_Plasmapause_Grid_flip_vert | alternate_view_flip_vert | Mapped_Plasmapause_Gridb |

# Implementations
## Java

## IDL (CDAWeb)
https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/source/virtual_functoins.pro
```idl
          if keyword_set(flip_vert) then begin
            handle_value,buf.(component0_index).HANDLE,img
            flipimg=img ;  placeholder. images will be flipped
            im_size=size(img)
            if im_size[0] eq 3 then imgs=im_size[3] else imgs=1
            for j=0,imgs-1 do begin
              flipimg[*,*,j]= reverse(img[*,*,j])
            endfor
            buf.(vvtag_indices[i]).HANDLE=handle_create(value=flipimg)
          endif else $
            buf.(vvtag_indices[i]).HANDLE=buf.(component0_index).HANDLE
```
