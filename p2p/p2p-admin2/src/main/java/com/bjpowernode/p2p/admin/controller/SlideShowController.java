package com.bjpowernode.p2p.admin.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bjpowernode.p2p.admin.constants.Constants;
import com.bjpowernode.p2p.admin.fastdfs.FastdfsClient;
import com.bjpowernode.p2p.admin.model.SlideShow;
import com.bjpowernode.p2p.admin.rto.ResponseObject;
import com.bjpowernode.p2p.admin.service.SlideShowService;
import com.bjpowernode.p2p.admin.util.MyDateUtils;

/**
 * 轮播图相关处理Controller
 * 
 * @author yanglijun
 *
 */
@Controller
public class SlideShowController {

	private static final Logger logger = LogManager.getLogger(SlideShowController.class);
	
	@Autowired
	private SlideShowService slideShowService;
	
	/**
	 * 查询轮播图信息列表
	 * 
	 * @param model
	 * @param productType
	 * @return
	 */
	@RequestMapping(value="/admin/slideShow")
	public String slideShow (Model model, 
			@RequestParam(value="currentPage", required=false) Integer currentPage,
			@RequestParam(value="pageSize", required=false) Integer pageSize) {
		
		if (null == currentPage) {
			currentPage = 1;//当前页从1开始
		}
		if (null == pageSize) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;//当前页从1开始
		}
		
		Map<String, Object> paramMap = new ConcurrentHashMap<String, Object>();
		//符合查询条件的轮播图数据总条数
		int totalRows = slideShowService.getSlideShowByTotal(paramMap);
		
		//计算有多少页
		int totalPage = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPage = totalPage + 1;
		}
		//判断前端传过来的当前页是否大于总页数
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		
		//分页查询数据库的起始行
		int startRow = (currentPage-1) * pageSize;
		paramMap.put("currentPage", startRow);
		paramMap.put("pageSize", pageSize);
		
		//分页查询轮播图列表
		List<SlideShow> slideShowList = slideShowService.getSlideShowByPage(paramMap);
		
		model.addAttribute("slideShowList", slideShowList);//产品列表
		model.addAttribute("totalPage", totalPage);//总页数
		model.addAttribute("currentPage", currentPage);//当前页
		model.addAttribute("startRow", startRow);//分页查询数据库的起始行
		model.addAttribute("pageSize", pageSize);//每页显示多少条数据
		model.addAttribute("totalRows", totalRows);//总数据条数
		
		return "slideShow";
	}
	
	/**
	 * 去添加轮播图
	 * 
	 * @param model
	 * @param currentPage
	 * @param productType
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/admin/toAddSlideShow")
	public String toAddSlideShow () {
		return "addSlideShow";
	}
	
	/**
	 * 去编辑修改某个轮播图信息
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/toEditSlideShow/{id}")
	public String toEditSlideShow (Model model, @PathVariable(value="id") Integer id) {
		
		SlideShow slideShow = slideShowService.getSlideShowById(id);
		model.addAttribute("slideShow", slideShow);
		return "addSlideShow";
	}
	
	/**
	 * 删除某个轮播图
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/deleteSlideShow")
	public @ResponseBody Object deleteSlideShow (@RequestParam(value="slideShowId") Integer id) {
		
		//删除数据库中的数据
		int deleteRow = slideShowService.deleteSlideShowById(id);
		
		//再把fastdfs上的图片文件也删除一下（暂时省略的）
		
		return deleteRow;
	}
	
	/**
	 * 上传文件
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping("/admin/uploadFile")
	public @ResponseBody String uploadFile (@RequestParam(value="fileName") MultipartFile file) {

		//获取文件原始名称
		String originalFilename = file.getOriginalFilename();
		//获取文件扩展名
		String fileExtend = originalFilename.substring(originalFilename.indexOf(".")+1);
		
		//把接收到的前端的文件上传到fastdfs上
		try {
			String[] strArray = FastdfsClient.uploadFile(file.getBytes(), fileExtend);
			
			//上传完文件之后，将文件的完整访问路径存入数据库
			if (strArray.length == 2) {//上传成功
				//轮播图访问路径
				String imageUrl = ResourceBundle.getBundle("config").getString("slideShow_url_prefix") + strArray[1];
				
				return "<script>window.parent.callback('0', '"+imageUrl+"')</script>";
			} else {
				return "<script>window.parent.callback('1', '')</script>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 提交保存轮播图数据
	 * 
	 * @param request
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value="/admin/addSlideShow", method=RequestMethod.POST)
	public @ResponseBody ResponseObject submitSlideShow (HttpServletRequest request,
			@RequestParam (value="slideTitle", required=false) String slideTitle,
			@RequestParam (value="slideUrl", required=false) String slideUrl,
			@RequestParam (value="slideStartTime", required=false) String slideStartTime,
			@RequestParam (value="slideEndTime", required=false) String slideEndTime,
			@RequestParam (value="slideStatus", required=false) Integer slideStatus,
			@RequestParam (value="slideImageUrl", required=false) String slideImageUrl,
			@RequestParam (value="slideShowId", required=false) Integer slideShowId) {

		ResponseObject responseObject = new ResponseObject();

		//验证前台传过来的参数是否合法
		if (StringUtils.isEmpty(slideTitle)) {
			responseObject.setErrorCode(Constants.ERROR);
        	responseObject.setErrorMessage("轮播图标题为空");
        	return responseObject;
		} else if (StringUtils.isEmpty(slideUrl)) {
			responseObject.setErrorCode(Constants.ERROR);
        	responseObject.setErrorMessage("轮播图链接为空");
        	return responseObject;
		} else if (StringUtils.isEmpty(slideStartTime)) {
			responseObject.setErrorCode(Constants.ERROR);
        	responseObject.setErrorMessage("轮播图生效时间为空");
        	return responseObject;
		} else if (StringUtils.isEmpty(slideEndTime)) {
			responseObject.setErrorCode(Constants.ERROR);
        	responseObject.setErrorMessage("轮播图失效时间为空");
        	return responseObject;
		} else if (null == slideStatus) {
			responseObject.setErrorCode(Constants.ERROR);
        	responseObject.setErrorMessage("轮播图状态为空");
        	return responseObject;
		} else if (StringUtils.isEmpty(slideImageUrl)) {
			responseObject.setErrorCode(Constants.ERROR);
        	responseObject.setErrorMessage("轮播图图片未上传");
        	return responseObject;
		}
		
        //将数据写入数据库
        SlideShow slideShow = new SlideShow();
        slideShow.setSlideTitle(slideTitle);
        slideShow.setSlideUrl(slideUrl);
        slideShow.setSlideCreateTime(new Date());
        slideShow.setSlideUpdateTime(new Date());
        slideShow.setSlideStartTime(MyDateUtils.strToDate(slideStartTime));
        slideShow.setSlideEndTime(MyDateUtils.strToDate(slideEndTime));
        slideShow.setSlideStatus(slideStatus);
        slideShow.setSlideImageUrl(slideImageUrl);
        
        if (null == slideShowId) {
        	int addRows = slideShowService.addSlideShow(slideShow);
        	if (addRows <= 0) {
            	responseObject.setErrorCode(Constants.ERROR);
            	responseObject.setErrorMessage("轮播图数据保存失败了，请重试~");
            } else {
            	responseObject.setErrorCode(Constants.OK);
            	responseObject.setErrorMessage("轮播图数据保存成功");
            }
        } else {
        	//修改轮播图信息
        	slideShow.setId(slideShowId);
        	int updateRows = slideShowService.updataSlideShow(slideShow);
        	if (updateRows <= 0) {
            	responseObject.setErrorCode(Constants.ERROR);
            	responseObject.setErrorMessage("轮播图数据修改失败了，请重试~");
            } else {
            	responseObject.setErrorCode(Constants.OK);
            	responseObject.setErrorMessage("轮播图数据修改成功");
            }
        }
        return responseObject;
	}
}
