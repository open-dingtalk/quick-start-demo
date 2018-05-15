package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.config.Constant;
import com.enums.BizTypeEnum;
import com.mapper.ding.OpenSyncBizDataMapper;
import com.model.OpenSyncBizDataDO;
import com.sdk.CorpTokenVO;
import com.sdk.HttpSDK;
import com.sdk.SuiteTokenVO;
import com.util.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * DEMO
 * 实现了最简单的免登功能
 */
@RestController
public class IndexController {
	private static final Logger bizLogger = LoggerFactory.getLogger(IndexController.class);
	@Resource
	private OpenSyncBizDataMapper openSyncBizDataMapper;
	@Resource
	private HttpSDK httpSDK;

	/**
	 * 钉钉用户登录，显示当前登录的企业和用户
	 * @param corpId			授权企业的CorpId
	 * @param requestAuthCode	免登临时code
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(@RequestParam(value = "corpId") String corpId,
									@RequestParam(value = "requestAuthCode") String requestAuthCode) {
		Long start = System.currentTimeMillis();
		String subscribeId = Constant.SUITE_ID+"_0";
		Integer bizType = BizTypeEnum.SUITE_TICKET.getValue();
		String bizId = String.valueOf(Constant.SUITE_ID);
		//1.获取套件推送的ticket，调用钉钉开放平台的http接口获取套件的suite_token
		OpenSyncBizDataDO ticketDO = openSyncBizDataMapper.getOpenSyncBizData(subscribeId,corpId,bizType,bizId);
		if(null==ticketDO){
			return null;
		}
		String suiteTicket = JSON.parseObject(ticketDO.getBizData()).getString("suiteTicket");
		bizType = BizTypeEnum.ORG_AUTH.getValue();
		ServiceResult<SuiteTokenVO> suiteTokenSr = httpSDK.getSuiteToken(Constant.SUITE_KEY,Constant.SUITE_SECRET,suiteTicket);
		String suiteToken = suiteTokenSr.getResult().getSuiteToken();
		//2.获取授权企业信息，调用钉钉开放平台的http接口获取企业的access_token
		OpenSyncBizDataDO authDO = openSyncBizDataMapper.getOpenSyncBizData(subscribeId,corpId,bizType,bizId);
		if(null==authDO){
			return null;
		}
		JSONObject authInfoObect = JSON.parseObject(authDO.getBizData());
		String pcode = authInfoObect.getString("permanent_code");
		ServiceResult<CorpTokenVO> corpTokenSr = httpSDK.getCorpToken(corpId,pcode,suiteToken);
		String corpToken = corpTokenSr.getResult().getCorpToken();
		//3.查询得到当前用户的userId
		ServiceResult<String> userIdSr = httpSDK.getLoginUserId(requestAuthCode,corpToken);
		String userId = userIdSr.getResult();
		//返回结果
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("userId",userId);
		resultMap.put("corpName",authInfoObect.getJSONObject("auth_corp_info").getString("corp_name"));
		resultMap.put("errcode",'0');
		bizLogger.info("cost:"+(System.currentTimeMillis()-start));
		return resultMap;
	}


}


