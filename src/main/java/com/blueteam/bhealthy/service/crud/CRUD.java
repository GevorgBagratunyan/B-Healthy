package com.blueteam.bhealthy.service.crud;

public interface CRUD<DTO, ID> extends
        Create<DTO>,
        Read<DTO, ID>,
        Update<DTO>,
        Delete<ID>{

    @Override
    void create(DTO dto);

    @Override
    void delete(ID id);

    @Override
    DTO get(ID id);

    @Override
    void update(DTO dto);

}
