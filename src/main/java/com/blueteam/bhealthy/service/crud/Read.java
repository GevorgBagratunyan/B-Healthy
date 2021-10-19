package com.blueteam.bhealthy.service.crud;


public interface Read<DTO, ID>{
    DTO get(ID id);
}
