package com.mapper;

import com.alibaba.fastjson.JSON;
import com.config.Constant;
import com.enums.BizTypeEnum;
import com.mapper.ding.OpenSyncBizDataMapper;
import com.model.OpenSyncBizDataDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenSyncBizDataMapperTest {

	@Resource
	private OpenSyncBizDataMapper openSyncBizDataMapper;

	@Test
	public void testGetOpenSyncBizData() {
		String subscribeId = Constant.SUITE_ID+"_0";
		String corpId = "ding9f50b15bccd16741";
		Integer bizType = BizTypeEnum.ORG_AUTH.getValue();
		String bizId = String.valueOf(Constant.SUITE_ID);
		OpenSyncBizDataDO openSyncBizDataDO = openSyncBizDataMapper.getOpenSyncBizData(subscribeId,corpId,bizType,bizId);
		System.out.println(JSON.toJSONString(openSyncBizDataDO));
	}
}