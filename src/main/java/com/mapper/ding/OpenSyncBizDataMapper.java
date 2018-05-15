package com.mapper.ding;

import com.model.OpenSyncBizDataDO;
import org.apache.ibatis.annotations.Param;

/**
 * 读取钉钉云推送RDS数据表的DAO
 */
public interface OpenSyncBizDataMapper {
    OpenSyncBizDataDO getOpenSyncBizData(@Param("subscribeId") String subscribeId,
                                         @Param("corpId") String corpId,
                                         @Param("bizType") Integer bizType,
                                         @Param("bizId") String bizId);
}
