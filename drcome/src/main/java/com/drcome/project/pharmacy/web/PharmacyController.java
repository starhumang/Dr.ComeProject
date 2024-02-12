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

import com.drcome.project.FileUploadService;
import com.drcome.project.common.service.PageDTO;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.mem.service.UserMemberService;
import com.drcome.project.pharmacy.PharmacySelectVO;
import com.drcome.project.pharmacy.PharmacyVO;
import com.drcome.project.pharmacy.service.PharmacyService;

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

	@GetMapping("/pharmacy/info")
	public String pharmacy(Principal principal, PharmacyVO pharmacyVO, Model model) {
		String pharmacyId = principal.getName();
		pharmacyVO.setPharmacyId(pharmacyId);
		PharmacyVO findVO = pservice.selectPharmacyInfo(pharmacyVO);
		model.addAttribute("pinfo", findVO);
		return "pharmacy/pharmacyInfo";
	}

	@GetMapping("/pharmacy/status")
	public String pharmacyprint(String date,Principal principal, String pharmacyId, Model model) {
		pharmacyId = principal.getName();
		/*
		 * List<Map<String, Object>> plist = pservice.selectPrescriptionList(date,
		 * pharmacyId); model.addAttribute("plist", plist);
		 */
		return "pharmacy/perscriptionStatus";
	}

	@GetMapping("/pharmacy/status/{date}")
	@ResponseBody
	public Map<String, Object> pharmacyList(@RequestParam(required = false, defaultValue = "1") String page, 
			                                @PathVariable String date, 
			                                String pharmacyId,
			                                @RequestParam Map<String, Object> parammap ) {
		pharmacyId = "pharmacy1";
		
		// result map
		Map<String, Object> map = new HashMap();
		
		// 리스트 전체갯수 가져오기
		int total = pservice.percount(date, pharmacyId);
		System.out.println("토탈" + total);
		
		
		// 선택된 페이지 인트로 변환
		int cpage = Integer.parseInt(page);
		System.out.println("선택된페이지" + cpage);

		// 페이지네이션(currentpage, total)
		PageDTO dto = new PageDTO(cpage, total);
		System.out.println("dtd 객체 " + dto);
		
		List<Map<String, Object>> plist = pservice.selectPrescriptionList(cpage, date, pharmacyId);
		
		System.out.println(plist.size());
		
		// ajax는 return으로 
//		model.addAttribute("plist", plist);
//		model.addAttribute("dto", dto);
		
		map.put("plist", plist); 
		map.put("pagedto", dto); 
		
		return map;
	}

	@PostMapping("/pharmacy/rejection")
	@ResponseBody
	public Map<String, Object> updaterejection(@SessionAttribute(name = "userId", required = false) String id, PharmacySelectVO pharmacyselectVO) {
		System.out.println(pharmacyselectVO);
		pharmacyselectVO.setPharmacyId(id);
		return pservice.updaterejection(pharmacyselectVO);
	}

	@ModelAttribute("pharmacy")
	public PharmacyVO getServer() {
		PharmacyVO pharmacyVO = new PharmacyVO();
		String pharmacyId = "pharmacy1";
		pharmacyVO.setPharmacyId(pharmacyId);
		PharmacyVO findVO = pservice.selectPharmacyInfo(pharmacyVO);
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
