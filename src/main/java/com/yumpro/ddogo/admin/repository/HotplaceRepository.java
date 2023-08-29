package com.yumpro.ddogo.admin.repository;

public interface HotplaceRepository {
    select hotplace_no
    from hotplace
    where hotplace_no in (select hotplace_no from mymap where recom='Y' group by hotplace_no order by count(recom) desc)
}
