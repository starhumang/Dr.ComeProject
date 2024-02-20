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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.drcome.project.common.service.AlarmDao;
import com.drcome.project.common.service.AlarmService;
import com.drcome.project.common.service.FileUploadService;
import com.drcome.project.common.service.PageDTO;
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
	
	@Autowired
	AlarmService aservice;

	// 사용 안 함
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
	 * @return pharmacy/perscriptionStatus
	 */
	@GetMapping("/pharmacy/status")
	public String pharmacyprint() {
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
		
		// result map
		Map<String, Object> map = new HashMap();
		
		// 리스트 전체갯수 가져오기
		int total = pservice.percount(parammap, date);

		// 페이지네이션(currentpage, total)
		PageDTO dto = new PageDTO(page, total);
		
		List<Map<String, Object>> plist = pservice.selectPrescriptionList(parammap, date);
		
		map.put("plist", plist); 
		map.put("pagedto", dto); 
		
		return map;
	}
	
	// 현재날짜 처방 재역
	@GetMapping("/pharmacy/cstatus/{date}")
	@ResponseBody
	public Map<String, Object> currpharmacyList(Principal principal, 
											@RequestParam(required = false, defaultValue = "1") int page, 
			                                @PathVariable String date, 
			                                @RequestParam Map<String, Object> parammap) {
		
		String pharmacyId = principal.getName();
		parammap.put("pharmacyId", pharmacyId);
		parammap.put("page", page);
		
		// result map
		Map<String, Object> map = new HashMap();
		
		// 리스트 전체갯수 가져오기
		int total = pservice.currpercount(parammap, date);
		System.out.println("토탈" + total);


		// 페이지네이션(currentpage, total)
		PageDTO dto = new PageDTO(page, total);
		
		List<Map<String, Object>> currplist = pservice.perCurrList(parammap, date);
		
		
		map.put("currplist", currplist); 
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
	public Map<String, Object> updaterejection(@SessionAttribute(name = "userId", required = false) String id, 
											   PharmacySelectVO pharmacyselectVO) {
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
	
	/**
	 * 처방전 반환 시, 알림 테이블에 insert
	 * @param dao 상태저장 정보
	 * @return aservice.saveAlarmPharmacy(dao)
	 */
	@PostMapping("/saveAlarmP")
	@ResponseBody
	public int savePharmacyAlarm(@RequestBody AlarmDao dao) {
		return aservice.saveAlarmPharmacy(dao);
	}
	
	/**
	 * 처방전 조회 
	 * @param no clinicNo 
	 * @return service
	 */
	@GetMapping("/pharmacy/perscription/{no}")
	@ResponseBody
	public List<PharmacySelectVO> perscriptionInfo(@PathVariable Integer no) {

		return pservice.getPerscription(no);
	}
	
	@PostMapping("/printStatusupdate")
	@ResponseBody
	public int updateprintStatus(@RequestBody PharmacySelectVO vo,
	                             Principal principal) {
	    // 출력 상태 업데이트
	    String pharmacyId = principal.getName();
	    pservice.printStatusModify(vo);
	    
	    // 출력한 약국 id insert
	    vo.setPharmacyId(pharmacyId);
	    int result = pservice.printpharmacyModify(vo);

	    if (result == 1) {
	        return 1; // 성공적으로 실행되었을 경우 1 반환
	    } else {
	        return 0; // 실패했을 경우 0 반환
	    }
	}
	
}

