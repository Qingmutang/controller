package com.bjpowernode.p2p.admin.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.p2p.admin.constants.Constants;
import com.bjpowernode.p2p.admin.mapper.CreditorRightsMapper;
import com.bjpowernode.p2p.admin.model.BidInfo;
import com.bjpowernode.p2p.admin.model.CreditorContract;
import com.bjpowernode.p2p.admin.model.CreditorRights;
import com.bjpowernode.p2p.admin.seal.stub.BidInfoVO;
import com.bjpowernode.p2p.admin.seal.stub.CreditorVO;
import com.bjpowernode.p2p.admin.seal.stub.SealServiceImpl;
import com.bjpowernode.p2p.admin.seal.stub.SealServiceImplService;
import com.bjpowernode.p2p.admin.service.CreditorRightsService;
import com.bjpowernode.p2p.admin.util.HttpClientUtils;
import com.bjpowernode.p2p.admin.util.RSAUtils;
import com.bjpowernode.p2p.admin.util.XMLGregorianCalendarUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 债权相关处理Service接口实现
 * 
 * @author yanglijun
 *
 */
@Service("creditorRightsService")
public class CreditorRightsServiceImpl implements CreditorRightsService {
	
	private static final Logger logger = LogManager.getLogger(CreditorRightsServiceImpl.class);
	
	@Autowired
	private CreditorRightsMapper creditorRightsMapper;

	/**
	 * 分页查询债权信息--获取债权列表
	 * 
	 */
	@Override
	public List<CreditorRights> getCreditorRightsByPage(Map<String, Object> paramMap) {
		return creditorRightsMapper.getCreditorRightsByPage(paramMap);
	}

	/**
	 * 分页查询债权信息--获取债权总数
	 * 
	 */
	@Override
	public int getCreditorRightsByTotal(Map<String, Object> paramMap) {
		return creditorRightsMapper.getCreditorRightsByTotal(paramMap);
	}
	
	/**
	 * 添加债权
	 * 
	 * @param creditorRights
	 * @return
	 */
	@Override
	public int addCreditorRights (CreditorRights creditorRights) {
		return creditorRightsMapper.insertSelective(creditorRights);
	}
	
	/**
	 * 根据主键id查询债权信息
	 * 
	 * @param id
	 * @return
	 */
	public CreditorRights getCreditorRights(int id) {
		return creditorRightsMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据主键id删除债权信息
	 * 
	 * @param id
	 * @return
	 */
	public int deleteCreditorRights(int id) {
		return creditorRightsMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据债权状态查询债权信息
	 * 
	 * @param matchStatus
	 * @return
	 */
	public List<CreditorRights> getCreditorRightsByMatchStatus(Map<String, Object> paramMap) {
		//return creditorRightsMapper.getCreditorRightsByMatchStatus(paramMap);
		return null;
	}
	
	/**
	 * 修改债权信息
	 * 
	 * @param creditorRights
	 * @return
	 */
	public int updateCreditorRights(CreditorRights creditorRights) {
		return creditorRightsMapper.updateByPrimaryKeySelective(creditorRights);
	}
	
	/**
	 * 接收第三方债权
	 * 
	 */
	public void receiveCreditor () {
		//第三方债权接口地址
		String url = ResourceBundle.getBundle("config").getString("creditor_url");
		
		//用我们自己的私钥进行签名
		String sign = RSAUtils.sign(Constants.API_KEY, Constants.CLIENT_PRIVATE_KEY);
		Map<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
		paramMap.put("apiKey", Constants.API_KEY);
		paramMap.put("sign", sign);
		
		//调用第三方债权接口获取债权数据
		String json = HttpClientUtils.doPost(url, paramMap);
		
		System.out.println(json);
		
		JSONObject jsonObject = JSONObject.parseObject(json);
		String errorCode = jsonObject.getString("errorCode");
		if (StringUtils.equals(errorCode, "0")) {//接口调用成功，可以进行数据解析
			JSONArray jsonArray = jsonObject.getJSONArray("object");
			
			//把json数组转成java对象
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, CreditorRights.class);  
			try {
				List<CreditorRights> list =  (List<CreditorRights>)mapper.readValue(jsonArray.toJSONString(), javaType);
				
				for (CreditorRights creditorRights : list) {
					
					//第三方返回的债权对象，不完全符合我们的数据库格式要求，需要转化一下
					
					if (creditorRights.getApplyPurpose().equals("品质生活")) {
						creditorRights.setApplyPurpose("1");
					} else if (creditorRights.getApplyPurpose().equals("旅游消费")) {
						creditorRights.setApplyPurpose("2");
					} else if (creditorRights.getApplyPurpose().equals("扩大经营")) {
						creditorRights.setApplyPurpose("3");
					} else if (creditorRights.getApplyPurpose().equals("资金周转")) {
						creditorRights.setApplyPurpose("4");
					}
					//银行转化
					
					
					//工作转化 
					
					//把债权数据插入到我们的数据库中
					int insertRow = creditorRightsMapper.insertSelective(creditorRights);
					System.out.println("接收债权入库： " + insertRow);
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}   
		}
	}
	
	/**
	 * 调用webservice服务，生成pdf和签章
	 */
	public void sealPdf(Integer creditorId) {
		
		//查询符合生成合同条件的债权信息数据
		
		List<CreditorContract> creditorContractList = creditorRightsMapper.selectCreditorRightsForContract(creditorId);
		
		for (CreditorContract creditorContract : creditorContractList) {
			
			//封装数据，调用远程webservice
			CreditorVO creditorVO = new CreditorVO();
			creditorVO.setApplyNo(creditorContract.getApplyNo());
			creditorVO.setApplyPurpose(creditorContract.getApplyPurpose());
			creditorVO.setAuditLoanMoney(creditorContract.getAuditLoanMoney());
			creditorVO.setAuditLoanTerm(creditorContract.getAuditLoanTerm());
			creditorVO.setBorrowerIdcard(creditorContract.getBorrowerIdcard());
			creditorVO.setBorrowerPresentAddress(creditorContract.getBorrowerPresentAddress());
			creditorVO.setBorrowerRealname(creditorContract.getBorrowerRealname());
			creditorVO.setBorrowerSex(creditorContract.getBorrowerSex());
			creditorVO.setCollectFinishTime(XMLGregorianCalendarUtils.convertToXMLGregorianCalendar(creditorContract.getCollectFinishTime()));
			
			//从生成的代码中获取bidInfoVOList
			List<BidInfoVO> bidInfoVOList = creditorVO.getBidInfoVOList();
			
			List<BidInfo> bidinfolist = creditorContract.getBidInfoList();
			
			for (BidInfo bidInfo : bidinfolist) {
				BidInfoVO bidInfoVO = new BidInfoVO();
				bidInfoVO.setBidId(bidInfo.getBidId());
				bidInfoVO.setBidMoney(bidInfo.getBidMoney());
				bidInfoVO.setIdCard(bidInfo.getIdCard());
				bidInfoVO.setName(bidInfo.getName());
				bidInfoVO.setPhone(bidInfo.getPhone());
				bidInfoVO.setUserId(bidInfo.getUserId());
				//将封装好的投资信息对象添加到bidInfoVOList中
				bidInfoVOList.add(bidInfoVO);
			}
			
			//调用远程的webservice接口
			
			//1、创建一个webservice的客户端对象
			SealServiceImplService sealServiceImplService = new SealServiceImplService();
			
			//2、通过webservice的客户端对象，获取远程接口对象
			SealServiceImpl sealServiceImpl = sealServiceImplService.getSealServiceImplPort();
			
			//3、通过远程接口对象调用远程的服务方法 ： //调用签章系统的webservice接口完成合同生成及签章
			String sealPdf = sealServiceImpl.pdfSeal(creditorVO);
			
			System.out.println(sealPdf);
			
			if (StringUtils.isNotEmpty(sealPdf) && StringUtils.startsWith(sealPdf, "http://")) {
				//将合同文件访问路径更新到债权表中
				CreditorRights record = new CreditorRights();
				record.setId(creditorContract.getId());//债权ID
				record.setLoanContractPath(sealPdf);//合同路径
				
				int updateRow = creditorRightsMapper.updateByPrimaryKeySelective(record);
				logger.info("更新债权合同路径结果：" + updateRow);
			}
		}
	}
}
