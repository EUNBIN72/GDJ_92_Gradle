package com.winter.app.board.notice;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RequestMapping("/notice/**")
@RestController
@CrossOrigin
public class NoticeController {

	
	@Autowired
	private NoticeService noticeService;
	
	// https://juntcom.tistory.com/219
	// 페이징처리
	

	@GetMapping("")
	public Page<NoticeVO> list(@PageableDefault(sort = "boardNum", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
		return noticeService.list(pageable);
	}
	
	@GetMapping("{boardNum}")
	public NoticeVO detail(@PathVariable("boardNum") Long boardNum) throws Exception {
		NoticeVO noticeVO = noticeService.detail(boardNum);
		return noticeVO;
	}
	
	@PostMapping("")
	public boolean add(NoticeVO noticeVO) throws Exception {
		noticeVO = noticeService.add(noticeVO);
		if(noticeVO != null) {
			return true;
		}
		return false;
		
		
	}
	
	
	
//	//list
//	@GetMapping("/notice/{pageNum}/{kind}/{search}")
//	public String doIt()throws Exception {
//		return"list";
//	}
//	
//	//detail
//	@GetMapping("/notice/{boardNum}")
//	public String doIt(@PathVariable("num") Long num, @PathVariable("kind") String kind)throws Exception {
//		System.out.println(num);
//		System.out.println(kind);
//		return"detail";
//	}
//	
//	@PostMapping("/notice")
//	public void doIt(VO vo) {
//		
//	}
//	
//	
//	@PutMapping("/notice/")
//	public void doIt(VO vo) {
//		
//	}
//	
//	@DeleteMapping("/notice/{}")
//	public void doIt() {
//		
//	}
//	
	
	

}