package com.drcome.project.pharmacy.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.drcome.project.common.service.FileUploadService;
import com.drcome.project.common.service.PageDTO;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.mem.service.UserMemberService;
import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;
import com.drcome.project.pharmacy.service.PharmacyService;

/** 약국 사용자 페이지(처방내역 조회, 처방현황 확인, 정보 수정)
 * 
 * @author 최해주
 * @since 2024.02
 *
 */

@Controller
public class PharmacyController {

	@Autowired
	PharmacyService pservice;
	
	@Autowired
	UserMemberService userMemService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private FileUploadService fileUploadService;

	@GetMapping("/pharmacy")
	public String home() {
		return "pharmacy/home";
	}
	
	/**
	 * 약국별 정보 조회
	 * @param principal 세션
	 * @param pharmacyVO 약국 정보
	 * @param model 화면모델
	 * @return pharmacy/pharmacyInfo
	 */
	@GetMapping("/pharmacy/info")
	public String pharmacy(Principal principal, PharmacyVO pharmacyVO, Model model) {
		String pharmacyId = principal.getName();
		pharmacyVO.setPharmacyId(pharmacyId);
		PharmacyVO findVO = pservice.selectPharmacyInfo(pharmacyVO);
		model.addAttribute("pinfo", findVO);
		return "pharmacy/pharmacyInfo";
	}
	
	/**
	 * 약국 처방현황 페이지
	 * @param principal 세선
	 * @param pharmacyId
	 * @return pharmacy/perscriptionStatus
	 */
	@GetMapping("/pharmacy/status")
	public String pharmacyprint(Principal principal, String pharmacyId) {
		pharmacyId = principal.getName();
		/*
		 * List<Map<String, Object>> plist = pservice.selectPrescriptionList(date,
		 * pharmacyId); model.addAttribute("plist", plist);
		 */
		return "pharmacy/perscriptionStatus";
	}

	/**
	 * 날짜별 처방내역/현황 리스트 조회
	 * @param principal 세션
	 * @param page 페이징
	 * @param date 날짜 조건
	 * @param parammap 검색조건 정보
	 * @return map
	 */
	@GetMapping("/pharmacy/status/{date}")
	@ResponseBody
	public Map<String, Object> pharmacyList(Principal principal, 
											@RequestParam(required = false, defaultValue = "1") int page, 
			                                @PathVariable String date, 
			                                @RequestParam Map<String, Object> parammap) {
		
		String pharmacyId = principal.getName();
		parammap.put("pharmacyId", pharmacyId);
		parammap.put("page", page);
		
		System.out.println("keword가 있을까요??" + parammap);
		
		// result map
		Map<String, Object> map = new HashMap();
		
		/*
		 * map.put("page", parammap.get("page")); map.put("pharmacyId",
		 * parammap.get("pharmacyId")); map.put("date", date); map.put("keyword",
		 * parammap.get("keyword")); map.put("type", parammap.get("type"));
		 * System.out.println("map" + map);
		 */
		
		
		// 리스트 전체갯수 가져오기
		int total = pservice.percount(parammap, date);
		System.out.println("토탈" + total);
		
		
		// 선택된 페이지 인트로 변환

		// 페이지네이션(currentpage, total)
		PageDTO dto = new PageDTO(page, total);
		System.out.println("page" + page);
		System.out.println("total" + total);
		System.out.println("dtd 객체 " + dto);
		
		List<Map<String, Object>> plist = pservice.selectPrescriptionList(parammap, date);
		
		System.out.println(plist.size());
		
		// ajax는 return으로 
//		model.addAttribute("plist", plist);
//		model.addAttribute("dto", dto);
		
		map.put("plist", plist); 
		map.put("pagedto", dto); 
		
		return map;
	}
	
	/**
	 * 처방전 반환 시, 반환 사유를 저장
	 * @param id 세션
	 * @param pharmacyselectVO 상태수정 정보
	 * @return pservice.updaterejection(pharmacyselectVO)
	 */
	@PostMapping("/pharmacy/rejection")
	@ResponseBody
	public Map<String, Object> updaterejection(@SessionAttribute(name = "userId", required = false) String id, PharmacySelectVO pharmacyselectVO) {
		System.out.println(pharmacyselectVO);
		pharmacyselectVO.setPharmacyId(id);
		return pservice.updaterejection(pharmacyselectVO);
	}
	
	/**
	 * 약국 정보 조회(공통 사용)
	 * @return findVO
	 */
	@ModelAttribute("pharmacy") 
	public PharmacyVO getServer() { 
		PharmacyVO
		pharmacyVO = new PharmacyVO(); String pharmacyId = "pharmacy1";
		pharmacyVO.setPharmacyId(pharmacyId); PharmacyVO findVO =
		pservice.selectPharmacyInfo(pharmacyVO); 
		return findVO;
	}
	
	@GetMapping("/pharmacy/paminfoupdate")
	public String pamUpdateForm(Principal principal, PharmacyVO pharmacyVO, Model model) {
		String pharmacyId = principal.getName();
		pharmacyVO.setPharmacyId(pharmacyId);
		PharmacyVO findVO = pservice.selectPharmacyInfo(pharmacyVO);
		model.addAttribute("pinfo", findVO);
		return "pharmacy/pamupdate";
	}
	
	@PostMapping("/pharmacy/paminfoupdate")
	@ResponseBody
	public Map<String, Object> pamUpdate(PharmacyVO pVO) {		
		Map<String, Object> response = new HashMap<>();
		String password = pVO.getPharmacyPw();
		password = bCryptPasswordEncoder.encode(password);
		pVO.setPharmacyPw(password);
		
		if(pVO.getUploadFiles() != null) {
			List<String> imageList = fileUploadService.uploadFiles(pVO.getUploadFiles());
			
			String uploadedFileName = imageList.get(0); // 첫 번째 파일의 경로
			pVO.setPharmacyImg(uploadedFileName);
		}

		System.out.println(pVO);
		
		try {
			int cnt = userMemService.updatePamInfo(pVO);
			if (cnt > 0) {
				response.put("result", true);
				response.put("msg", "정상적으로 수정되었습니다.");
			} else {
				response.put("result", false);
				response.put("msg", "수정에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("result", false);
			response.put("msg", "에러 발생");
		}
		return response;
	}
}
