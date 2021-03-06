package com.itheima.babasport.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.itheima.babasport.service.product.UploadService;
import com.itheima.babasport.web.Constants;

/**
 * 上传图片
 * @author Administrator
 *
 */
@Controller
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
	
	
	
	//上传单张图片
	@RequestMapping(value="/upload/uploadPic.do")
	public void uploadPic(MultipartFile pic , HttpServletRequest request,HttpServletResponse response) throws  Exception{
		
		/*System.out.println(pic.getOriginalFilename());
		//原文件名
		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
		//保存的文件名
		String path ="/upload/"+UUID.randomUUID().toString() + "." + ext;
		//全路径
		String url = request.getSession().getServletContext().getRealPath(path);
		//保存图片到指定位置
		pic.transferTo(new File(url));*/

		
		//保存图片到fastDFS中
		String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		
		//拼写json
		JSONObject jo=new JSONObject();
		jo.put("path", Constants.IMG_URL + path);
		
		//回调图片
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jo.toString());
		
	}
	
	
	//上传多张图片
	@RequestMapping(value="/upload/uploadPics.do")
	public @ResponseBody
	List<String> uploadPics(@RequestParam(required=false)MultipartFile[] pics) throws Exception{
		List<String> urls=new ArrayList<>();
		for(MultipartFile pic :pics){
			//保存图片到FastDFS中
			String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			urls.add(Constants.IMG_URL + path);
		}
		return urls;
	}
	
	//上传富文本编辑的多张图片
	@RequestMapping(value="/upload/uploadFck.do")
	public void uploadFck(HttpServletRequest request ,HttpServletResponse response ) throws Exception{
		
		//无敌版接受图片
		//spring提供了MultipartRequest
		MultipartRequest mr=(MultipartRequest)request;
		//只有图片
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		Set<Entry<String,MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile pic = entry.getValue();
			//保存图片到FastDFS中
			String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			//回显到富文本编辑器上 json
			JSONObject jo=new JSONObject();
			jo.put("url", Constants.IMG_URL+path);
			jo.put("error", 0);
			//回调图片
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jo.toString());
		}
	}
	
	
	
	
	
	
	
	
	
	
}
