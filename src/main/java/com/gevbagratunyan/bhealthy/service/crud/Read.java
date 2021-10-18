package com.gevbagratunyan.bhealthy.service.crud;


public interface Read<DTO, ID>{
    DTO get(ID id);
}
